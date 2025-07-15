package egovframework.example.recipe.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import egovframework.example.recipe.service.RecipeService;
import egovframework.example.recipe.service.RecipeVO;

@Service
public class RecipeServiceImpl implements RecipeService {

	@Autowired RecipeMapper recipeMapper;

	@Override
	public RecipeVO selectRecipe(String recipeId) {
		return recipeMapper.selectRecipe(recipeId);
	}
}
