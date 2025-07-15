package egovframework.example.recipe.web;

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

	@GetMapping("recipe/list.do")
	public String showRecipeList() {
		
		return "/recipe/recipelist";
	}
	
	@GetMapping("/recipe/view.do")
	public String showRecipeView(Model model,
			@RequestParam(defaultValue = " ") String recipeId) {
		RecipeVO recipeVO = recipeService.selectRecipe(recipeId);
		model.addAttribute("recipeVO", recipeVO);
		
		return "/recipe/recipeview";
	}
}
