package egovframework.example.data.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import egovframework.example.data.service.impl.SpoonacularService;

@Controller
public class SpoonacularController {

    @Autowired
    private SpoonacularService spoonacularService;

    // ✅ Spoonacular API 실행
    @GetMapping(value = "/import.do", produces = "text/html; charset=UTF-8")
    @ResponseBody
    public String runSpoonacularImport() {
        try {
            spoonacularService.execute();
            return "<span style='color:green;'>Spoonacular API → DB 저장 완료!</span>";
        } catch (Exception e) {
            e.printStackTrace();
            return "<span style='color:red;'>Spoonacular API 호출 실패</span>";
        }
    }

    // ✅ Spoonacular API 중지
    @GetMapping(value = "/import/stop.do", produces = "text/plain; charset=UTF-8")
    @ResponseBody
    public String stopSpoonacularImport() {
        spoonacularService.stop();
        return "🚫 Spoonacular 데이터 저장 중지 요청 완료";
    }
}