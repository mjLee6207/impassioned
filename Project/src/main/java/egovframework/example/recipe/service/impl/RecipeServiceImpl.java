package egovframework.example.recipe.service.impl;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import egovframework.example.common.Criteria;
import egovframework.example.recipe.service.RecipeService;
import egovframework.example.recipe.service.RecipeVO;

@Service
public class RecipeServiceImpl implements RecipeService {

	@Autowired
	private RecipeMapper recipeMapper;

	//  페이징+검색+카테고리 리스트
	@Override
	public List<EgovMap> selectRecipeListPaging(Criteria criteria) {
	    return recipeMapper.selectRecipeListPaging(criteria);
	}

	//  페이징+검색+카테고리 총 개수
	@Override
	public int getTotalRecipeCount(Criteria criteria) {
	    return recipeMapper.getTotalRecipeCount(criteria);
	}

	// (필요시) 상세조회/랜덤레시피 
	@Override
	public RecipeVO selectRecipe(String recipeId) {
	    return recipeMapper.selectRecipe(recipeId);
	}

	@Override
	public List<RecipeVO> selectRandomRecipesByCategory(String categoryKr, int count) {
	    return recipeMapper.selectRandomRecipesByCategory(categoryKr, count);
	}
	@Override
	public List<RecipeVO> selectBestRecipes() {
	    return recipeMapper.selectBestRecipes();
	}

}
