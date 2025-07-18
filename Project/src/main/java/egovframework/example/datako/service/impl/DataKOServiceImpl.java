package egovframework.example.datako.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import egovframework.example.datako.service.DataKOResponse;
import egovframework.example.datako.service.DataKOService;
import egovframework.example.datako.service.DataKOVO;

@Service
public class DataKOServiceImpl implements DataKOService{

	@Autowired
	private DataKOMapper mapper;
	
	@Override
	public void saveDataKO() {
		RestTemplate restTemplate = new RestTemplate();
	    String url = "http://openapi.foodsafetykorea.go.kr/api/43a166f2e97e40329c82/COOKRCP01/json/50/150";
	    
        try {
            // 자동 파싱
            DataKOResponse response = restTemplate.getForObject(url, DataKOResponse.class);
            // 수동 파싱용
            String json = restTemplate.getForObject(url, String.class);

            if (response != null && response.getCookRcp() != null && !response.getCookRcp().getRow().isEmpty()) {
                List<DataKOVO> recipes = response.getCookRcp().getRow();

                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode nodeArray = objectMapper.readTree(json)
                                                 .path("COOKRCP01")
                                                 .path("row");

                for (int i = 0; i < recipes.size(); i++) {
                    DataKOVO recipe = recipes.get(i);
                    JsonNode node = nodeArray.get(i);

                    // 수동 파싱
                    StringBuilder textBuilder = new StringBuilder();
                    
                    for (int j = 1; j <= 20; j++) {
                        String key = String.format("MANUAL%02d", j);
                        
                        String step = node.path(key).asText().trim();
                        
                        if (!step.isEmpty()) {
                        	textBuilder.append(step).append("\n");
                        }
                    }
                    recipe.setInstructionKr(textBuilder.toString());

                    // 중복 체크 후 DB 적재
                    if (!mapper.checkRecipe(recipe.getRecipeId())) {
                        recipe.setCategoryKr("한식");
                        mapper.insertDataKO(recipe);
                    }
                }
            }

        } catch (Exception e) {
            System.err.println("한식 API 처리 오류: " + e.getMessage());
        }
    }
}
