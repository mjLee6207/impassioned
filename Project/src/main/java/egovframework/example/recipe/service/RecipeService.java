package egovframework.example.recipe.service;

import java.util.List;

public interface RecipeService {
	List<?> selectRecipeListCategory(String categoryKr); //	레시피 카테고리별조회
	List<?> selectRecipeList(); // 레시피 전체조회
	RecipeVO selectRecipe(String recipeId); // 레시피 상세 조회
}
