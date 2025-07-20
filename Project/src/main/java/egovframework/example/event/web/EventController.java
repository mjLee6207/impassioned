package egovframework.example.event.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EventController {

	@GetMapping("/event/test.do")
	public String showEventTest() {
		
		return "/event/test";
	}
}