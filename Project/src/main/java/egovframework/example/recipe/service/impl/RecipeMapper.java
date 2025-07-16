package egovframework.example.recipe.service.impl;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

import egovframework.example.recipe.service.RecipeVO;

@Mapper
public interface RecipeMapper {
	List<?> selectRecipeListCategory(String categoryKr); //	레시피 카테고리별조회
	List<?> selectRecipeList(); // 레시피 전체조회
	RecipeVO selectRecipe(String recipeId); // 레시피 상세 조회
}
