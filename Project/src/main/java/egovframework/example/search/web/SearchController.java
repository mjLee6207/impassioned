package egovframework.example.search.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import egovframework.example.common.Criteria;
import egovframework.example.search.service.SearchService;
import egovframework.example.search.service.SearchVO;

@Controller
public class SearchController {
	
	@Autowired
	SearchService searchService;

	@GetMapping("/search/all.do")
	public String searchAll(Model model,
			@RequestParam(name = "keyword", defaultValue = "") String keyword) {

        // Criteria에 검색어 set
        Criteria criteria = new Criteria();
        criteria.setSearchKeyword(keyword);

        // 통합 검색 결과 가져오기
        List<SearchVO> searchList = searchService.searchAll(criteria);

        // RECIPE/BOARD로 분리
        List<SearchVO> recipeList = new ArrayList<>();
        List<SearchVO> boardList = new ArrayList<>();

        for (SearchVO vo : searchList) {
            if ("RECIPE".equals(vo.getSource())) {
                recipeList.add(vo);
            } else if ("BOARD".equals(vo.getSource())) {
                boardList.add(vo);
            }
        }
        // 8개까지만 잘라서 미리보기 (8개 이하면 그대로)
        if (recipeList.size() > 8) {
            recipeList = recipeList.subList(0, 8);
        }
        if (boardList.size() > 8) {
            boardList = boardList.subList(0, 8);
        }
        
        model.addAttribute("recipeList", recipeList);
        model.addAttribute("boardList", boardList);
        model.addAttribute("searchKeyword", keyword);

        return "/search/searchAll";
    }
}
