package egovframework.example.recipe.service;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;

import egovframework.example.common.Criteria;

public interface RecipeService {
	List<?> selectRecipeListCategory(String categoryKr); //	레시피 카테고리별조회
	List<?> selectRecipeList(); // 레시피 전체조회
	RecipeVO selectRecipe(String recipeId); // 레시피 상세 조회
	//	페이징 처리 
	List<EgovMap> selectRecipeListPaging(Criteria criteria);
	int getTotalRecipeCount();
    //  카테고리별 페이징
    List<EgovMap> selectRecipeListCategoryPaging(Criteria criteria, String categoryKr);
    int getTotalRecipeCountByCategory(String categoryKr);	
}
