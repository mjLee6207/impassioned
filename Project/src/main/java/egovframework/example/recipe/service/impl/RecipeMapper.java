package egovframework.example.recipe.service.impl;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

import egovframework.example.recipe.service.RecipeVO;

@Mapper
public interface RecipeMapper {
	List<?> selectRecipeList();
	RecipeVO selectRecipe(String recipeId); // 레시피 상세 조회
}
