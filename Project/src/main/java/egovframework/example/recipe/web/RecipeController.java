package egovframework.example.recipe.web;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import egovframework.example.common.Criteria;
import egovframework.example.recipe.service.RecipeImageService;
import egovframework.example.recipe.service.RecipeService;
import egovframework.example.recipe.service.RecipeVO;

@Controller
public class RecipeController {
    
    @Autowired
    RecipeService recipeService;
    @Autowired
    private RecipeImageService recipeImageService;
    
    @GetMapping(value = "/recipe/test.do", produces = "text/plain; charset=UTF-8") 
    @ResponseBody
    public String runOneTimeImageDownload() {
        recipeImageService.downloadAndCacheAllImages();
        return "✅ 이미지 다운로드 및 DB 경로 변경 완료!";
    }
    
    // 레시피 전체조회 & 카테고리/검색/페이징 모두 이 한 메서드
    @GetMapping("recipe/recipe.do")
    public String showRecipeListCategory(Model model,
        @RequestParam(defaultValue = "") String categoryKr,
        @RequestParam(defaultValue = "1") int pageIndex,
        @RequestParam(defaultValue = "") String searchKeyword) {

        Criteria criteria = new Criteria();
        criteria.setPageIndex(pageIndex);
        criteria.setPageUnit(12); // 원하는 페이지당 표시 개수
        criteria.setCategoryKr(categoryKr);
        criteria.setSearchKeyword(searchKeyword);

        // PaginationInfo 세팅 (전자정부 페이징)
        PaginationInfo paginationInfo = new PaginationInfo();
        paginationInfo.setCurrentPageNo(criteria.getPageIndex());
        paginationInfo.setRecordCountPerPage(criteria.getPageUnit());
        paginationInfo.setPageSize(10); // 페이지 블럭 개수
        criteria.setFirstIndex(paginationInfo.getFirstRecordIndex());

        // ⭐️ 딱 2개 메서드만 사용!
        List<EgovMap> recipeList = recipeService.selectRecipeListPaging(criteria);
        int total = recipeService.getTotalRecipeCount(criteria);

        paginationInfo.setTotalRecordCount(total);

        model.addAttribute("recipeList", recipeList);
        model.addAttribute("paginationInfo", paginationInfo);
        model.addAttribute("pageIndex", pageIndex);
        model.addAttribute("categoryKr", categoryKr);
        model.addAttribute("searchKeyword", searchKeyword);

        return "/recipe/recipelist";
    }

    // 레시피 상세조회
    @GetMapping("/recipe/view.do")
    public String showRecipeView(Model model,
            @RequestParam(defaultValue = " ") String recipeId) {
        RecipeVO recipeVO = recipeService.selectRecipe(recipeId);
        model.addAttribute("recipeVO", recipeVO);
        
        return "/recipe/recipeview";
    }
}
