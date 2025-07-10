package egovframework.example.data.service;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true) // 혹시 API에서 불필요한 필드가 오더라도 무시하게 해주는 안전장치입니다.
public class DataResponse {
    
    private List<DataVO> meals;

    public List<DataVO> getMeals() {
        return meals;
    }

    public void setMeals(List<DataVO> meals) {
        this.meals = meals;
    }
}