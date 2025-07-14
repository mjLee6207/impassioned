package egovframework.example.datako.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import egovframework.example.datako.service.DataKOService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class DataKOController {

	private final DataKOService dataKOService;
	
	@GetMapping("/datako.do")
	public String show() {
		return "/datako/datako";
	}
	
    @PostMapping("/datako/save.do")
    public String save() {
        dataKOService.saveDataKO();
        return "redirect:/datako.do";
    }
}
