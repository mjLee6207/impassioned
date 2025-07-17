/**
 * 
 */
package egovframework.example;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import egovframework.example.recipe.service.RecipeService;
import egovframework.example.recipe.service.RecipeVO;

/**
 * @author user
 * 메인화면: http://localhost:8080
 */
@Controller
public class HomeController {
	@Autowired RecipeService recipeService;
	@GetMapping("/home.do")
	public String home(Model model) {
		  // 카테고리별 레시피 최신순 5개만 추출 (selectRecipeListCategory는 반환 타입 List<?>지만, VO로 캐스팅)
        List<RecipeVO> koreanRecipe = (List<RecipeVO>) recipeService.selectRecipeListCategory("한식");
        List<RecipeVO> westernRecipe = (List<RecipeVO>) recipeService.selectRecipeListCategory("양식");
        List<RecipeVO> chineseRecipe = (List<RecipeVO>) recipeService.selectRecipeListCategory("중식");
        List<RecipeVO> japaneseRecipe = (List<RecipeVO>) recipeService.selectRecipeListCategory("일식");
        List<RecipeVO> dessertRecipe = (List<RecipeVO>) recipeService.selectRecipeListCategory("디저트");
     // 필요시 5개로 자르기 (만약 SQL에서 LIMIT가 안 먹는 경우)
        koreanRecipe = koreanRecipe.size() > 5 ? koreanRecipe.subList(0, 5) : koreanRecipe;
        westernRecipe = westernRecipe.size() > 5 ? westernRecipe.subList(0, 5) : westernRecipe;
        chineseRecipe = chineseRecipe.size() > 5 ? chineseRecipe.subList(0, 5) : chineseRecipe;
        japaneseRecipe = japaneseRecipe.size() > 5 ? japaneseRecipe.subList(0, 5) : japaneseRecipe;
        dessertRecipe = dessertRecipe.size() > 5 ? dessertRecipe.subList(0, 5) : dessertRecipe;

        model.addAttribute("koreanRecipe", koreanRecipe);
        model.addAttribute("westernRecipe", westernRecipe);
        model.addAttribute("chineseRecipe", chineseRecipe);
        model.addAttribute("japaneseRecipe", japaneseRecipe);
        model.addAttribute("dessertRecipe", dessertRecipe);
		return "home";
	}
}
