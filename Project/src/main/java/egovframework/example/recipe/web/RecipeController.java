package egovframework.example.recipe.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RecipeController {

	@GetMapping("/recipeView.do")
	public String showRecipePage() {
		
		return "/recipe/recipeView";
	}
}
