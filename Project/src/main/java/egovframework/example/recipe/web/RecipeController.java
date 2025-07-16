package egovframework.example.recipe.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import egovframework.example.recipe.service.RecipeService;
import egovframework.example.recipe.service.RecipeVO;

@Controller
public class RecipeController {
	
	@Autowired RecipeService recipeService;
	
//	레시피 전체조회 & 카테고리별조회
	@GetMapping("recipe/recipe.do")
	public String showRecipeListCategory(Model model,
			@RequestParam(defaultValue = "") String categoryKr) {
		List<?> recipeList;
		
	    if (categoryKr.isEmpty()) {
	        recipeList = recipeService.selectRecipeList();
	    } else {
	        recipeList = recipeService.selectRecipeListCategory(categoryKr);
	    }
		
		model.addAttribute("recipeList", recipeList);
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
