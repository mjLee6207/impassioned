package egovframework.example.recipe.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.egovframe.rte.psl.dataaccess.mapper.Mapper;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;

import egovframework.example.common.Criteria;
import egovframework.example.recipe.service.RecipeVO;

@Mapper
public interface RecipeMapper {
	List<?> selectRecipeListCategory(String categoryKr); //	레시피 카테고리별조회
	List<?> selectRecipeList(); // 레시피 전체조회
	RecipeVO selectRecipe(String recipeId); // 레시피 상세 조회
	//	  레시피 전체조회 페이징 처리 
    List<EgovMap> selectRecipeListPaging(Criteria criteria);
    int getTotalRecipeCount();
    //   레시피 카테고리별 페이징
    List<EgovMap> selectRecipeListCategoryPaging(@Param("criteria") Criteria criteria, @Param("categoryKr") String categoryKr);
    int getTotalRecipeCountByCategory(@Param("categoryKr") String categoryKr);

    
}
