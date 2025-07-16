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
}
