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

    @Autowired private RestTemplate restTemplate;

    private static final String API_KEY = "SpoonacularAPI키를 입력해주세요";
    private static final List<String> CUISINES = List.of("chinese", "japanese");

    private int totalTranslatedChars = 0;
    private volatile boolean isRunning = false;

    @Override
    public List<DataVO> fetch() {
        List<DataVO> allData = new ArrayList<>();
        for (String cuisine : CUISINES) {
            log.info("Spoonacular: {} 요리 불러오기 시작", cuisine);
            allData.addAll(fetchByCuisine(cuisine));
        }
        return allData;
    }

    @Override
    public void execute() {
        isRunning = true;

        List<DataVO> dataList = fetch();

        for (DataVO data : dataList) {
            if (!isRunning) {
                log.warn("중지 요청 감지 → 저장 중단");
                break;
            }

            if (dataMapper.existsRecipe(data.getRecipeId()) > 0) {
                log.warn("중복 레시피 건너뜀: {}", data.getRecipeId());
                continue;
            }

            try {
                // 재료/계량 번역 (한글)
                translator.translateIngredients(data);

                // 제목/설명 번역 (HTML 제거 포함)
                String titleKr = translator.translate(data.getTitleEn(), "KO");
                String cleanInstruction = stripHtml(data.getInstructionEn());
                String instructionKr = translator.translate(cleanInstruction, "KO");

                data.setTitleKr(titleKr);
                data.setInstructionKr(instructionKr);

                int charCount = (data.getTitleEn() != null ? data.getTitleEn().length() : 0)

                        + (cleanInstruction != null ? cleanInstruction.length() : 0);
                totalTranslatedChars += charCount;
                log.info("번역 글자 수: {} (누적: {})", charCount, totalTranslatedChars);

                // 저장
                dataMapper.insertRecipe(data);
                log.info("저장 성공: {} ({})", data.getTitleEn(), data.getCategoryEn());

                Thread.sleep(20000);
            } catch (Exception e) {
                log.error("저장 실패 (id={}): {}", data.getRecipeId(), e.getMessage());
            }
        }

        isRunning = false;
    }

    private List<DataVO> fetchByCuisine(String cuisine) {
        List<DataVO> result = new ArrayList<>();

        try {
            String url = "https://api.spoonacular.com/recipes/complexSearch?number=30&cuisine=" + cuisine
                    + "&addRecipeInformation=true&apiKey=" + API_KEY;

            String json = restTemplate.getForObject(url, String.class);
            JsonNode results = new ObjectMapper().readTree(json).path("results");

            int count = 1;

            for (JsonNode node : results) {
                if (!isRunning) {
                    log.warn("중지 요청 감지 → fetch 중단");
                    break;
                }

                String recipeId = node.path("id").asText();

                try {
                    String detailUrl = "https://api.spoonacular.com/recipes/" + recipeId + "/information?apiKey=" + API_KEY;
                    String detailJson = restTemplate.getForObject(detailUrl, String.class);
                    JsonNode detail = new ObjectMapper().readTree(detailJson);

                    DataVO data = new DataVO();
                    data.setRecipeId(recipeId);
                    data.setTitleEn(detail.path("title").asText());
                    data.setInstructionEn(stripHtml(detail.path("instructions").asText()));
                    data.setThumbnail(detail.path("image").asText());

                    // 카테고리/지역
                    String category = "chinese".equals(cuisine) ? "중식" : "일식";
                    data.setCategoryEn(category);
                    data.setCategoryKr(category);
                    data.setArea("");

                    // 재료/계량
                    List<String> ingredients = new ArrayList<>();
                    List<String> measures = new ArrayList<>();

                    JsonNode extIng = detail.path("extendedIngredients");
                    if (!detail.has("extendedIngredients") || !extIng.isArray() || extIng.size() == 0) {
                        log.warn("재료 정보 없음: recipeId={}", recipeId);
                    } else {
                        log.info("재료 수: {} (recipeId={})", extIng.size(), recipeId);
                        for (JsonNode ing : extIng) {
                            String name = ing.path("name").asText("");
                            String original = ing.path("original").asText("");

                            log.debug("name={}, original={}", name, original);

                            ingredients.add(name);
                            measures.add(original);
                        }
                    }
                    data.setIngredientEn(ingredients);
                    data.setMeasureEn(measures);
                    data.setIngredientEnStr(String.join(",", ingredients));
                    data.setMeasureEnStr(String.join(",", measures));

                    log.info("{}번째 레시피 준비 완료 (id={})", count++, data.getRecipeId());
                    result.add(data);

                } catch (Exception ex) {
                    log.warn("상세 요청 실패: recipeId={}, 이유={}", recipeId, ex.getMessage());
                }
            }

        } catch (Exception e) {
            log.error("Spoonacular {} 처리 실패", cuisine, e);
        }

        return result;
    }

    public void stop() {
        isRunning = false;
    }

    public boolean isRunning() {
        return isRunning;
    }

//  HTML 태그 필터링 로직
    private String stripHtml(String html) {
        if (html == null) return "";
        return html
                .replaceAll("(?i)</li>|</p>|<br\\s*/?>", "\n")
                .replaceAll("<[^>]*>", "")
                .replaceAll("&nbsp;", " ")
                .replaceAll("\\n+", "\n")
                .trim();
    }
}
