package egovframework.example.recipe.service;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;

import egovframework.example.common.Criteria;

public interface RecipeService {
	
// 레시피 조회 관련
	
	List<?> selectRecipeListCategory(Criteria criteria); //	레시피 카테고리별조회
	List<?> selectRecipeList(Criteria criteria); // 레시피 전체조회
	RecipeVO selectRecipe(String recipeId); // 레시피 상세 조회
	
//	페이지 관련
	
	//	페이징 처리
	List<EgovMap> selectRecipeListPaging(Criteria criteria);
	int getTotalRecipeCount();
    //  카테고리별 페이징
	List<EgovMap> selectRecipeListCategoryPaging(Criteria criteria);
    int getTotalRecipeCountByCategory(String categoryKr);	
}
