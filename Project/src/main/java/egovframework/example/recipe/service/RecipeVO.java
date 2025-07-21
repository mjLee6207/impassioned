package egovframework.example.recipe.service;

import lombok.Data;

@Data
public class RecipeVO {
	private String recipeId;
	private String titleKr;
	private String categoryKr;
	private String instructionKr;
	private String ingredientKr;
	private String thumbnail;
//	7월21일 메인페이지 인기 레시피 조회를 위해 추가
	private Integer likeCount; 
}
