package egovframework.example.search.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import egovframework.example.common.Criteria;
import egovframework.example.search.service.SearchService;

@Controller
public class SearchController {
	
	@Autowired
	SearchService searchService;

	@GetMapping("/search/all.do")
	public String searchAll(Model model,
			@RequestParam(defaultValue = "") String searchKeyword) {
		
		Criteria criteria = new Criteria();
		criteria.setSearchKeyword(searchKeyword);
		
		model.addAttribute("searchList", searchService.searchAll(criteria));
		model.addAttribute("searchKeyword", searchKeyword);
		
		return "/search/searchAll";
	}
}
