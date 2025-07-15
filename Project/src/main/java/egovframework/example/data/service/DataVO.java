package egovframework.example.data.service;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DataVO {

    // ========== 공통 필드 ==========
    /** 레시피 고유 ID */
    @JsonProperty("idMeal")
    private String recipeId;

    /** 썸네일 이미지 */
    @JsonProperty("strMealThumb")
    private String thumbnail;

    /** 지역 (영문) */
    @JsonProperty("strArea")
    private String area;

    // ========== 영어 원문 필드 ==========
    /** 영어 제목 */
    @JsonProperty("strMeal")
    private String titleEn;

    /** 영어 조리 설명 */
    @JsonProperty("strInstructions")
    private String instructionEn;

    /** 영어 카테고리 */
    @JsonProperty("strCategory")
    private String categoryEn;

    /** 재료 리스트 (영문) */
    @JsonIgnore
    private List<String> ingredientEn;

    /** 계량 리스트 (영문) */
    @JsonIgnore
    private List<String> measureEn;

    /** CSV 변환 (DB 저장용) */
    private String ingredientEnStr;
    private String measureEnStr;

    // ========== 한글 번역 필드 ==========
    /** 한글 제목 */
    private String titleKr;

    /** 한글 조리 설명 */
    private String instructionKr;

    /** 한글 카테고리 */
    private String categoryKr;

    /** 재료 리스트 (한글) */
    private List<String> ingredientKr;

    /** 계량 리스트 (한글) */
    private List<String> measureKr;

    /** CSV 변환 (DB 저장용) */
    private String ingredientKrStr;
    private String measureKrStr;
}
