package egovframework.example.recipe.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import egovframework.example.recipe.service.RecipeService;
import egovframework.example.recipe.service.RecipeVO;

@Service
public class RecipeServiceImpl implements RecipeService {

	@Autowired RecipeMapper recipeMapper;

	
	@Override
	public List<?> selectRecipeList() {
		return recipeMapper.selectRecipeList();
	}

	@Override
	public RecipeVO selectRecipe(String recipeId) {
		return recipeMapper.selectRecipe(recipeId);
	}
}
