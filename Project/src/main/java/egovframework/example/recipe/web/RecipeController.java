package egovframework.example.recipe.web;

import java.util.List;

import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import egovframework.example.common.Criteria;
import egovframework.example.recipe.service.RecipeService;
import egovframework.example.recipe.service.RecipeVO;

@Controller
public class RecipeController {
	
	@Autowired RecipeService recipeService;
	
	// 레시피 전체조회 & 카테고리별조회
	@GetMapping("recipe/recipe.do")
	public String showRecipeListCategory(Model model,
	    @RequestParam(defaultValue = "") String categoryKr,
	    @RequestParam(defaultValue = "1") int pageIndex) {

	    Criteria criteria = new Criteria();
	    criteria.setPageIndex(pageIndex);
	    criteria.setPageUnit(12); // 원하는 페이지당 표시 개수

	    PaginationInfo paginationInfo = new PaginationInfo();
	    paginationInfo.setCurrentPageNo(criteria.getPageIndex());
	    paginationInfo.setRecordCountPerPage(criteria.getPageUnit());
	    paginationInfo.setPageSize(10); // 원하는 페이지 블럭 개수

	    criteria.setFirstIndex(paginationInfo.getFirstRecordIndex());

	    List<?> recipeList;
	    int total;

	    if (categoryKr.isEmpty()) {
	        // 전체 조회 페이징
	        recipeList = recipeService.selectRecipeListPaging(criteria);
	        total = recipeService.getTotalRecipeCount();
	    } else {
	        // 카테고리 조회 페이징
	        recipeList = recipeService.selectRecipeListCategoryPaging(criteria, categoryKr);
	        total = recipeService.getTotalRecipeCountByCategory(categoryKr);
	    }

	    paginationInfo.setTotalRecordCount(total);

	    model.addAttribute("recipeList", recipeList);
	    model.addAttribute("paginationInfo", paginationInfo);
	    model.addAttribute("pageIndex", pageIndex);
	    model.addAttribute("categoryKr", categoryKr);

	    return "/recipe/recipelist";
	}

	
//	레시피 상세조회
	@GetMapping("/recipe/view.do")
	public String showRecipeView(Model model,
			@RequestParam(defaultValue = " ") String recipeId) {
		RecipeVO recipeVO = recipeService.selectRecipe(recipeId);
		model.addAttribute("recipeVO", recipeVO);
		
		return "/recipe/recipeview";
	}
}
