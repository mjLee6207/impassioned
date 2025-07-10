package egovframework.example.data.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    @Autowired
    private Translator translator;

    @Autowired
    private DataMapper dataMapper;

    private static final List<String> AREAS = List.of("Chinese", "Japanese", "American", "French", "Italian", "Spanish");
    private static final List<String> CATEGORIES = List.of("Dessert");
    private volatile boolean isRunning = false;  // 중지 기능 추가

    @Override
    public void execute() {
        isRunning = true; // 실행 시작 표시
        try {
            fetchAndSaveByArea(AREAS);
            fetchAndSaveByCategory(CATEGORIES);
        } finally {
            isRunning = false; // 예외 발생 여부와 무관하게 반드시 false 설정
        }
    }
    
//  중지 기능 추가
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

            if (meals.isArray()) {
                for (JsonNode meal : meals) {
                    if (!isRunning) {
                        log.warn("⚠ 중지 요청 감지 → 반복 중단");
                        break;
                    }

                    String idMeal = meal.path("idMeal").asText();
                    saveDetailRecipe(idMeal, area, category);
                }
            }            
        } catch (Exception e) {
            log.error("목록 데이터 조회 실패: {}", listUrl, e);
        }
    }

    private void saveDetailRecipe(String idMeal, String area, String category) {
        try {        	
            String url = "https://www.themealdb.com/api/json/v1/1/lookup.php?i=" + idMeal;
            String json = new RestTemplate().getForObject(url, String.class);

            DataVO data = parseAuto(json);
            if (data == null) {
                log.warn("상세 레시피 무시됨 (null): idMeal={}", idMeal);
                return;
            }

//        	중복 확인
        	if (dataMapper.existsRecipe(data.getRecipeId()) > 0) {
        	    log.warn("⏭ 중복 레시피 건너뜀: {}", data.getRecipeId());
        	    return;
        	}
        	
            JsonNode node = new ObjectMapper().readTree(json).get("meals").get(0);
            parseManual(data, node);
            translateAll(data);

            data.setArea(area);
            data.setCategoryKr(transCategory(data.getCategory()));

            dataMapper.insertRecipe(data);
            log.info("✅ 저장 성공: {} ({})", data.getTitle(), data.getCategory());

        } catch (Exception e) {
            log.error("❌ 레시피 저장 실패: idMeal={}", idMeal, e);
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
            String ing = node.path("strIngredient" + i).asText();
            String mea = node.path("strMeasure" + i).asText();
            if (ing != null && !ing.isBlank()) {
                ingredient.add(ing.trim());
                measure.add(mea != null && !mea.isBlank() ? mea.trim() : "");
            }
        }
        data.setIngredient(ingredient);
        data.setMeasure(measure);
    }

    private void translateAll(DataVO data) {
        data.setTitleKr(translator.translate(data.getTitle(), "KO"));
        data.setInstructionskr(translator.translate(data.getInstruction(), "KO"));

        List<String> ingredientKr = translator.translateBulk(data.getIngredient(), "KO");
        List<String> measureKr = translator.translateBulk(data.getMeasure(), "KO");

        data.setIngredientKr(ingredientKr);
        data.setMeasureKr(measureKr);

        data.setIngredientStr(String.join(",", data.getIngredient()));
        data.setMeasureStr(String.join(",", data.getMeasure()));
        data.setIngredientKrStr(String.join(",", ingredientKr));
        data.setMeasureKrStr(String.join(",", measureKr));
    }

    private String transCategory(String category) {
        switch (category) {
            case "Chinese": return "중식";
            case "Japanese": return "일식";
            case "American": case "French": case "Italian": case "Spanish": return "양식";
            case "Dessert": return "디저트";
            default: return "기타";
        }
    }
}
