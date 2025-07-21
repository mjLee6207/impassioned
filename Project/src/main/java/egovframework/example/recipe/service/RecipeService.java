package egovframework.example.recipe.service;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;

import egovframework.example.common.Criteria;

public interface RecipeService {
	
	// ⭐️ 페이징+검색+카테고리 (리스트)
    List<EgovMap> selectRecipeListPaging(Criteria criteria);

    // ⭐️ 페이징+검색+카테고리 (총 개수)
    int getTotalRecipeCount(Criteria criteria);

    // (필요하면 상세, 랜덤 등 기존 방식은 그대로!)
    RecipeVO selectRecipe(String recipeId);  // 상세조회
    List<RecipeVO> selectRandomRecipesByCategory(String categoryKr, int count);  // 랜덤레시피
    //	7월21일 메인페이지 인기 레시피 조회를 위해 추가
    List<RecipeVO> selectBestRecipes();
}
