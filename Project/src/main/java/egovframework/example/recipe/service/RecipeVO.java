package egovframework.example.recipe.service;

import lombok.Data;

@Data
public class RecipeVO {
	private String recipeId;
	private String thumbnail;
	private String title;
	private String category;
	private String ingredient;
	private String instruction;
}
