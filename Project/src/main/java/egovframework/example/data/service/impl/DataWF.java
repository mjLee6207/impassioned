package egovframework.example.data.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import egovframework.example.data.service.DataManager;
import egovframework.example.data.service.DataResponse;
import egovframework.example.data.service.DataVO;
import egovframework.example.data.service.util.Translator;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class DataWF implements DataManager {

    @Autowired private Translator translator;
    @Autowired private DataMapper dataMapper;

    private static final List<String> AREAS = List.of("Chinese", "Japanese", "American", "French", "Italian", "Spanish");
    private static final List<String> CATEGORIES = List.of("Dessert");
    private volatile boolean isRunning = false;

    // ✅ 전체 번역 누적 글자 수
    private static int totalTranslatedChars = 0;
    // ✅ 저장된 레시피 수 카운터
    private static int savedRecipeCount = 0;

    @Override
    public void execute() {
        isRunning = true;
        try {
            fetchAndSaveByArea(AREAS);
            fetchAndSaveByCategory(CATEGORIES);
        } finally {
            isRunning = false;
        }
    }

    public void stop() {
        isRunning = false;
    }

    private void fetchAndSaveByArea(List<String> areas) {
        for (String area : areas) {
            String listUrl = "https://www.themealdb.com/api/json/v1/1/filter.php?a=" + area;
            fetchAndSaveFromListUrl(listUrl, area, null);
        }
    }

    private void fetchAndSaveByCategory(List<String> categories) {
        for (String category : categories) {
            String listUrl = "https://www.themealdb.com/api/json/v1/1/filter.php?c=" + category;
            fetchAndSaveFromListUrl(listUrl, null, category);
        }
    }

    private void fetchAndSaveFromListUrl(String listUrl, String area, String category) {
        try {
            String json = new RestTemplate().getForObject(listUrl, String.class);
            JsonNode meals = new ObjectMapper().readTree(json).path("meals");

            int count = 1;

            if (meals.isArray()) {
                for (JsonNode meal : meals) {
                    if (!isRunning) {
                        log.warn("⚠ 중지 요청 감지 → 반복 중단");
                        break;
                    }

                    String idMeal = meal.path("idMeal").asText();
                    log.info("🚀 {}번째 레시피 처리 중: idMeal={}", count++, idMeal);

                    boolean saved = saveDetailRecipe(idMeal, area, category);

                    if (saved) {
                        try {
                            Thread.sleep(20000); // ✅ 저장된 경우만 대기
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            log.warn("⚠ 인터럽트로 중단됨");
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("목록 데이터 조회 실패: {}", listUrl, e);
        }
    }

    private boolean saveDetailRecipe(String idMeal, String area, String category) {
        try {
            String url = "https://www.themealdb.com/api/json/v1/1/lookup.php?i=" + idMeal;
            String json = new RestTemplate().getForObject(url, String.class);

            DataVO data = parseAuto(json);
            if (data == null) {
                log.warn("상세 레시피 무시됨 (null): idMeal={}", idMeal);
                return false;
            }

            if (dataMapper.existsRecipe(data.getRecipeId()) > 0) {
                log.warn("⏭ 중복 레시피 건너뜀: {}", data.getRecipeId());
                return false;
            }

            JsonNode node = new ObjectMapper().readTree(json).get("meals").get(0);
            parseManual(data, node);
            translateAll(data);

            if (area != null) {
                data.setArea(area);
                data.setCategoryKr(transArea(area)); // ✅ 지역 기반 번역
            } else if (category != null) {
                data.setCategory(category);
                data.setCategoryKr(transArea(category)); // ✅ 카테고리 기반 번역
            }

            dataMapper.insertRecipe(data);
            savedRecipeCount++;
            log.info("✅ 저장 성공: {} ({}) - 누적 저장 {}건", data.getTitle(), data.getCategory(), savedRecipeCount);
            return true;

        } catch (Exception e) {
            log.error("❌ 레시피 저장 실패: idMeal={}", idMeal, e);
            return false;
        }
    }

    private DataVO parseAuto(String json) throws Exception {
        DataResponse response = new ObjectMapper().readValue(json, DataResponse.class);
        if (response.getMeals() == null || response.getMeals().isEmpty()) return null;
        return response.getMeals().get(0);
    }

    private void parseManual(DataVO data, JsonNode node) {
        List<String> ingredient = new ArrayList<>();
        List<String> measure = new ArrayList<>();

        for (int i = 1; i <= 20; i++) {
            String ing = clean(node.path("strIngredient" + i).asText());
            String mea = clean(node.path("strMeasure" + i).asText());

            if (!ing.isBlank()) {
                ingredient.add(ing);
                measure.add(!mea.isBlank() ? mea : "");
            }
        }

        data.setIngredient(ingredient);
        data.setMeasure(measure);
    }

    private String clean(String s) {
        if (s == null) return "";
        return s.replaceAll("\\(String\\)", "")
                .replaceAll("(?i)null", "")
                .replaceAll("(?i)undefined", "")
                .replaceAll("(?i)N/A", "")
                .trim();
    }

    private void translateAll(DataVO data) {
        String instruction = data.getInstruction();
        if (instruction != null && instruction.length() > 4500) {
            log.warn("⚠ 조리 설명이 너무 깁니다 ({}자) → DeepL 오류 가능성 있음", instruction.length());
        }

        // ✅ 제목, 조리법 번역
        data.setTitleKr(translator.translate(data.getTitle(), "KO"));
        data.setInstructionskr(translator.translate(instruction, "KO"));

        List<String> ingredients = data.getIngredient();
        List<String> measures = data.getMeasure();
        int len = Math.min(ingredients.size(), measures.size());

        List<String> combined = new ArrayList<>();
        for (int i = 0; i < len; i++) {
            combined.add(ingredients.get(i).trim() + " " + measures.get(i).trim());
        }

        String combinedText = String.join("\n", combined);
        String translatedText = translator.translate(combinedText, "KO");
        String[] translatedLines = translatedText.split("\n");

        // ✅ 번역 길이 계산
        int totalLen = (data.getTitle() != null ? data.getTitle().length() : 0)
                     + (instruction != null ? instruction.length() : 0)
                     + combinedText.length();
        totalTranslatedChars += totalLen;
        log.info("🧾 이번 레시피 번역 글자 수: {}자 / 누적: {}자", totalLen, totalTranslatedChars);

        Set<String> knownUnits = Set.of("작은술", "큰술", "컵", "파운드", "그램", "꼬집음", "꼬집", "ml", "l", "tsp", "tbsp", "tbs", "개", "장", "방울");

        List<String> ingredientKr = new ArrayList<>();
        List<String> measureKr = new ArrayList<>();

        for (String line : translatedLines) {
            line = line.trim();
            boolean matched = false;

            for (String unit : knownUnits) {
                int idx = line.lastIndexOf(unit);
                if (idx != -1) {
                    int spaceIdx = line.lastIndexOf(" ", idx);
                    if (spaceIdx != -1) {
                        String ing = line.substring(0, spaceIdx).trim();
                        String mea = line.substring(spaceIdx + 1).trim();
                        ingredientKr.add(ing);
                        measureKr.add(mea);
                        matched = true;
                        break;
                    }
                }
            }

            if (!matched) {
                String[] parts = line.split(" ", 2);
                if (parts.length == 2) {
                    ingredientKr.add(parts[0]);
                    measureKr.add(parts[1]);
                } else {
                    ingredientKr.add(parts[0]);
                    measureKr.add("");
                }
            }
        }

        measureKr = measureKr.stream()
            .map(m -> m.replaceAll("\\b꼬집음\\b", "한 꼬집"))
            .collect(Collectors.toList());

        data.setIngredientKr(ingredientKr);
        data.setMeasureKr(measureKr);

        data.setIngredientStr(String.join(",", ingredients));
        data.setMeasureStr(String.join(",", measures));
        data.setIngredientKrStr(String.join(",", ingredientKr));
        data.setMeasureKrStr(String.join(",", measureKr));
    }

    private String transArea(String area) {
        switch (area) {
            case "Chinese": return "중식";
            case "Japanese": return "일식";
            case "American":
            case "French":
            case "Italian":
            case "Spanish":
            case "British": return "양식";
            case "Dessert": return "디저트";
            default: return "기타";
        }
    }
    
    @PreDestroy
    public void shutdown() {
        log.warn("🛑 서버 종료 감지 → DataWF 작업 중단 시도");
        this.stop(); // isRunning = false 설정
    }
}
