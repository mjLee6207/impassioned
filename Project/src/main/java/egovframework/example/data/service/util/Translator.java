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
                params.add("text", text); // ✅ 다수 전송 방식
            }
            params.add("target_lang", lang);

            HttpEntity<?> request = new HttpEntity<>(params, headers);
            ResponseEntity<Map> response = restTemplate.postForEntity(apiUrl, request, Map.class);

            List<Map<String, String>> translations = (List<Map<String, String>>) response.getBody().get("translations");
            if (translations == null || translations.size() != texts.size()) {
                log.warn("❌ 다중 번역 응답 불일치: 요청={}, 응답={}", texts.size(), translations != null ? translations.size() : 0);
                return texts.stream().map(t -> "번역 실패: " + t).collect(Collectors.toList());
            }

            return translations.stream()
                    .map(map -> map.get("text"))
                    .collect(Collectors.toList());

        } catch (Exception e) {
            log.error("❌ 다중 번역 오류: {}", e.getMessage(), e);
            return texts.stream().map(t -> "번역 실패: " + t).collect(Collectors.toList());
        }
    }
}