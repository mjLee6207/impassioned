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

	@Autowired RecipeMapper recipeMapper;

	@Override
	public List<?> selectRecipeListCategory(String categoryKr) {
		return recipeMapper.selectRecipeListCategory(categoryKr);
	}
	
	@Override
	public List<?> selectRecipeList() {
		return recipeMapper.selectRecipeList();
	}

	@Override
	public RecipeVO selectRecipe(String recipeId) {
		return recipeMapper.selectRecipe(recipeId);
	}

	@Override
	public List<EgovMap> selectRecipeListPaging(Criteria criteria) {
		return recipeMapper.selectRecipeListPaging(criteria);
	}

	@Override
	public int getTotalRecipeCount() {
		return recipeMapper.getTotalRecipeCount();
	}
	
	@Override
    public List<EgovMap> selectRecipeListCategoryPaging(Criteria criteria, String categoryKr) {
        return recipeMapper.selectRecipeListCategoryPaging(criteria, categoryKr);
    }

    @Override
    public int getTotalRecipeCountByCategory(String categoryKr) {
        return recipeMapper.getTotalRecipeCountByCategory(categoryKr);
    }
	
	
}
