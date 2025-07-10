package egovframework.example.data.service;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DataVO {

    // ========== 기본 영문 필드 (API 원본) ==========

    /** 레시피 고유 ID */
    @JsonProperty("idMeal")
    private String recipeId;

    /** 영어 제목 */
    @JsonProperty("strMeal")
    private String title;

    /** 레시피 카테고리 (ex: Beef, Dessert 등) */
    @JsonProperty("strCategory")
    private String category;

    /** 레시피 지역 (ex: Chinese, Japanese 등) */
    @JsonProperty("strArea")
    private String area;

    /** 영어 조리 설명 */
    @JsonProperty("strInstructions")
    private String instruction;

    /** 레시피 썸네일 이미지 URL */
    @JsonProperty("strMealThumb")
    private String thumbnail;

    /** 재료 리스트 (영문) */
    @JsonIgnore
    private List<String> ingredient;

    /** 계량 정보 리스트 (영문) */
    @JsonIgnore
    private List<String> measure;

    /** 영문 재료를 CSV 문자열로 변환한 값 (DB 저장용) */
    private String ingredientStr;

    /** 영문 계량 정보를 CSV 문자열로 변환한 값 (DB 저장용) */
    private String measureStr;


    // ========== 번역된 한글 필드 (번역 API 결과) ==========

    /** 한글 제목 */
    private String titleKr;

    /** 한글 카테고리 (ex: 중식, 일식, 양식 등으로 직접 매핑) */
    private String categoryKr;

    /** 한글 조리 설명 */
    private String instructionskr;

    /** 재료 리스트 (한글 번역) */
    @JsonProperty
    private List<String> ingredientKr;

    /** 계량 정보 리스트 (한글 번역) */
    @JsonProperty
    private List<String> measureKr;

    /** 한글 재료를 CSV 문자열로 변환한 값 (DB 저장용) */
    private String ingredientKrStr;

    /** 한글 계량 정보를 CSV 문자열로 변환한 값 (DB 저장용) */
    private String measureKrStr;
}