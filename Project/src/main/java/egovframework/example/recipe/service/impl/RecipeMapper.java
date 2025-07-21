package egovframework.example.recipe.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.egovframe.rte.psl.dataaccess.mapper.Mapper;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;

import egovframework.example.common.Criteria;
import egovframework.example.recipe.service.RecipeVO;

@Mapper
public interface RecipeMapper {

	 //  상세 조회 
    RecipeVO selectRecipe(String recipeId);

    //  랜덤 레시피 
    List<RecipeVO> selectRandomRecipesByCategory(
        @Param("categoryKr") String categoryKr,
        @Param("count") int count
    );

    //  페이징+검색+카테고리 통합 (이거 "2개"만 남김)
    List<EgovMap> selectRecipeListPaging(Criteria criteria);   // 리스트 조회
    int getTotalRecipeCount(Criteria criteria);                // 총 개수 조회
    
    List<RecipeVO> selectAllRecipeThumb();
    void updateThumbnailPath(RecipeVO recipe);
    //	7월21일 메인페이지 인기 레시피 조회를 위해 추가
    List<RecipeVO> selectBestRecipes();
	
	
	
	
//// 레시피 조회 관련
//	
//	List<?> selectRecipeListCategory(Criteria criteria); //	레시피 카테고리별조회
//	List<?> selectRecipeList(Criteria criteria); // 레시피 전체조회
//	RecipeVO selectRecipe(String recipeId); // 레시피 상세 조회
//	
////	페이징 관련
//	
//	//	  레시피 전체조회 페이징 처리 
//    List<EgovMap> selectRecipeListPaging(Criteria criteria);
//    int getTotalRecipeCount();
//    //   레시피 카테고리별 페이징
//    List<EgovMap> selectRecipeListCategoryPaging(Criteria criteria);
//    int getTotalRecipeCountByCategory(@Param("categoryKr") String categoryKr);
//    List<RecipeVO> selectRandomRecipesByCategory(@Param("categoryKr") String categoryKr, @Param("count") int count);
    
}
