package egovframework.example.data.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import egovframework.example.data.service.DataManager;
import egovframework.example.data.service.DataVO;
import egovframework.example.data.service.util.Translator;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class SpoonacularService implements DataManager {

    @Autowired private DataMapper dataMapper;
    @Autowired private Translator translator;

    private static final String API_KEY = "e0d9170d050847558aa61826aa4eea78";
    private static final List<String> CUISINES = List.of("chinese", "japanese");

    private int totalTranslatedChars = 0;
    private volatile boolean isRunning = false;

    @Override
    public List<DataVO> fetch() {
        List<DataVO> allData = new ArrayList<>();
        for (String cuisine : CUISINES) {
            log.info("🌏 Spoonacular: {} 요리 불러오기 시작", cuisine);
            allData.addAll(fetchByCuisine(cuisine));
        }
        return allData;
    }

    @Override
    public void execute() {
        isRunning = true;
        List<DataVO> dataList = fetch(); // 빠르게 가져오기

        for (DataVO data : dataList) {
            if (!isRunning) {
                log.warn("🛑 중지 요청 감지 → 저장 중단");
                break;
            }

            if (dataMapper.existsRecipe(data.getRecipeId()) > 0) {
                log.warn("⏭ 중복 레시피 건너뜀: {}", data.getRecipeId());
                continue;
            }

            try {
                // ✅ 재료/계량 번역
                translator.translateIngredients(data); // INGREDIENT_KR, MEASURE_KR 포함

                // ✅ 레시피 제목/내용 번역 (HTML 태그 제거 후)
                String titleKr = translator.translate(data.getTitle(), "KO");

                String cleanInstruction = stripHtml(data.getInstruction()); // HTML 태그 제거
                String instrKr = translator.translate(cleanInstruction, "KO");

                data.setTitleKr(titleKr);
                data.setInstructionskr(instrKr);

                int charCount = (data.getTitle() != null ? data.getTitle().length() : 0)
                        + (cleanInstruction != null ? cleanInstruction.length() : 0);
                totalTranslatedChars += charCount;
                log.info("🔤 번역 글자 수: {} (누적: {})", charCount, totalTranslatedChars);

                dataMapper.insertRecipe(data);
                log.info("✅ 저장 성공: {} ({})", data.getTitle(), data.getCategory());

                // ✅ 번역/저장마다 20초 대기
                Thread.sleep(20000);

            } catch (Exception e) {
                log.error("❌ 저장 실패 (id={}): {}", data.getRecipeId(), e.getMessage());
            }
        }

        isRunning = false;
    }

    private List<DataVO> fetchByCuisine(String cuisine) {
        List<DataVO> result = new ArrayList<>();

        try {
            String url = "https://api.spoonacular.com/recipes/complexSearch?number=30&cuisine=" + cuisine
                    + "&addRecipeInformation=true&apiKey=" + API_KEY;

            String json = new RestTemplate().getForObject(url, String.class);
            JsonNode results = new ObjectMapper().readTree(json).path("results");

            int count = 1;

            for (JsonNode node : results) {
                if (!isRunning) {
                    log.warn("🛑 중지 요청 감지 → fetch 중단");
                    break;
                }

                String recipeId = node.path("id").asText();

                try {
                    String detailUrl = "https://api.spoonacular.com/recipes/" + recipeId + "/information?apiKey=" + API_KEY;
                    String detailJson = new RestTemplate().getForObject(detailUrl, String.class);
                    JsonNode detail = new ObjectMapper().readTree(detailJson);

                    DataVO data = new DataVO();
                    data.setRecipeId(recipeId);
                    data.setTitle(detail.path("title").asText());
                    data.setInstruction(detail.path("instructions").asText());
                    data.setThumbnail(detail.path("image").asText());
                  
                    // ✅ 카테고리/지역 설정
                    data.setCategory("중식".equals(cuisine) ? "중식" : "일식"); // category에 중식/일식 넣기
                    data.setCategoryKr("중식".equals(cuisine) ? "중식" : "일식"); // categoryKr에도 동일하게 넣기
                    data.setArea(""); // 지역은 비워둠 (정책에 따라)

                    // ✅ 재료/계량 추출 및 로그
                    List<String> ingredients = new ArrayList<>();
                    List<String> measures = new ArrayList<>();

                    JsonNode extIng = detail.path("extendedIngredients");

                    if (!detail.has("extendedIngredients")) {
                        log.warn("❌ extendedIngredients 없음: recipeId={}", recipeId);
                    } else if (!extIng.isArray() || extIng.size() == 0) {
                        log.warn("⚠️ 재료 배열이 비어 있음: recipeId={}", recipeId);
                    } else {
                        log.info("📦 재료 수: {} (recipeId={})", extIng.size(), recipeId);
                        for (JsonNode ing : extIng) {
                            String name = ing.path("name").asText("");
                            String original = ing.path("original").asText("");

                            log.debug("🧂 name={}, original={}", name, original);

                            ingredients.add(name);
                            measures.add(original);
                        }
                    }

                    data.setIngredient(ingredients);
                    data.setMeasure(measures);
                    data.setIngredientStr(String.join(",", ingredients));
                    data.setMeasureStr(String.join(",", measures));

                    log.info("🚀 {}번째 레시피 준비 완료 (id={})", count++, data.getRecipeId());
                    result.add(data);

                } catch (Exception ex) {
                    log.warn("⚠️ 상세 요청 실패: recipeId={}, 이유={}", recipeId, ex.getMessage());
                }
            }

        } catch (Exception e) {
            log.error("❌ Spoonacular {} 처리 실패", cuisine, e);
        }

        return result;
    }


    public void stop() {
        isRunning = false;
    }

    public boolean isRunning() {
        return isRunning;
    }
    
    private String stripHtml(String html) {
        if (html == null) return "";

        return html	
                .replaceAll("(?i)</li>|</p>|<br\\s*/?>", "\n")  // 줄바꿈 보존
                .replaceAll("<[^>]*>", "")                      // 나머지 태그 제거
                .replaceAll("&nbsp;", " ")                      // HTML 공백 치환
                .replaceAll("\\n+", "\n")                       // 줄바꿈 2개 이상 → 1개
                .trim();
    }
}
