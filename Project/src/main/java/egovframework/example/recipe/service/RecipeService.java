package egovframework.example.recipe.service;

import java.util.List;

public interface RecipeService {
	List<?> selectRecipeList();
	RecipeVO selectRecipe(String recipeId);
}
