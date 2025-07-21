/**
 * 
 */
package egovframework.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import egovframework.example.recipe.service.RecipeService;

/**
 * @author user
 * 메인화면: http://localhost:8080
 */
@Controller
public class HomeController {
	@Autowired RecipeService recipeService;
	@GetMapping("/home.do")
	public String home(Model model) {
		model.addAttribute("koreanRecipe", recipeService.selectRandomRecipesByCategory("한식", 5));
        model.addAttribute("westernRecipe", recipeService.selectRandomRecipesByCategory("양식", 5));
        model.addAttribute("chineseRecipe", recipeService.selectRandomRecipesByCategory("중식", 5));
        model.addAttribute("japaneseRecipe", recipeService.selectRandomRecipesByCategory("일식", 5));
        model.addAttribute("dessertRecipe", recipeService.selectRandomRecipesByCategory("디저트", 5));
        model.addAttribute("bestRecipes", recipeService.selectBestRecipes());
		return "home";
	}
}
