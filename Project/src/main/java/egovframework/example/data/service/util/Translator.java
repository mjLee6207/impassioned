package egovframework.example.data.service.util;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import egovframework.example.data.service.DataVO;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class Translator {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${deepl.api.url}")
    private String apiUrl;

    @Value("${deepl.api.key}")
    private String apiKey;

    // ✅ 공통 요청 생성
    private HttpEntity<MultiValueMap<String, String>> buildRequest(String text, String lang) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "DeepL-Auth-Key " + apiKey);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("text", text);
        params.add("target_lang", lang);

        return new HttpEntity<>(params, headers);
    }

    // ✅ 단일 번역
    public String translate(String text, String lang) {
        try {
            HttpEntity<?> request = buildRequest(text, lang);
            ResponseEntity<Map> response = restTemplate.postForEntity(apiUrl, request, Map.class);

            List<Map<String, String>> translations = (List<Map<String, String>>) response.getBody().get("translations");
            if (translations == null || translations.isEmpty()) {
                log.warn("❌ 단일 번역 실패 - 응답이 비어 있음: {}", response.getBody());
                return "번역 실패";
            }

            return translations.get(0).get("text");
        } catch (Exception e) {
            log.error("❌ 단일 번역 오류: {}", e.getMessage(), e);
            return "번역 실패";
        }
    }

    // ✅ 다중 번역
    public List<String> translateBulk(List<String> texts, String lang) {
        if (texts == null || texts.isEmpty()) return List.of();

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.set("Authorization", "DeepL-Auth-Key " + apiKey);

            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            for (String text : texts) {
                params.add("text", text);
            }
            params.add("target_lang", lang);

            HttpEntity<?> request = new HttpEntity<>(params, headers);
            ResponseEntity<Map> response = restTemplate.postForEntity(apiUrl, request, Map.class);

            List<Map<String, String>> translations = (List<Map<String, String>>) response.getBody().get("translations");

            if (translations == null || translations.size() != texts.size()) {
                log.warn("⚠ 일부 누락 감지: 요청 {}, 응답 {}", texts.size(), translations != null ? translations.size() : 0);

                // ✅ fallback: 하나씩 개별 번역
                List<String> result = texts.stream()
                    .map(t -> {
                        try {
                            return translate(t, lang);
                        } catch (Exception e) {
                            log.error("❌ 개별 줄 번역 실패: {}", t);
                            return "번역 실패: " + t;
                        }
                    })
                    .collect(Collectors.toList());

                return result;
            }

            return translations.stream()
                    .map(map -> map.get("text"))
                    .collect(Collectors.toList());

        } catch (Exception e) {
            log.error("❌ 전체 다중 번역 실패, 개별 시도 중: {}", e.getMessage(), e);

            // ✅ fallback: 개별 번역 시도
            return texts.stream()
                .map(t -> {
                    try {
                        return translate(t, lang);
                    } catch (Exception ex) {
                        log.warn("❌ 줄 단위 번역 실패: {}", t);
                        return "번역 실패: " + t;
                    }
                })
                .collect(Collectors.toList());
        }
    }
    
 // Spoonacular
    public void translateIngredients(DataVO data) {
    	List<String> ingredients = data.getIngredientEn(); // ✅ 올바른 이름
    	List<String> measures = data.getMeasureEn();       // ✅ 올바른 이름

    	if (ingredients == null || measures == null) {
    	    log.warn("⚠️ 재료 또는 계량 정보가 없습니다 (recipeId={})", data.getRecipeId());
    	    return;
    	}

    	List<String> ingKr = translateBulk(ingredients, "KO");
    	List<String> meaKr = translateBulk(measures, "KO");

    	data.setIngredientKr(ingKr); // 리스트 자체 보관
    	data.setMeasureKr(meaKr);

    	data.setIngredientKrStr(String.join(",", ingKr)); // 문자열 변환 저장
    	data.setMeasureKrStr(String.join(",", meaKr));

        int totalChars = ingredients.stream().mapToInt(String::length).sum()
                + measures.stream().mapToInt(String::length).sum();

        log.info("🈺 재료/계량 번역 완료 (건수: {}, 총 글자 수: {})", ingKr.size() + meaKr.size(), totalChars);
    }
}