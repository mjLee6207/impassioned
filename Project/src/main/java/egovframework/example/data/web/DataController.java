package egovframework.example.data.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

// import egovframework.example.data.service.impl.DataKF; // 한식 임시 제외
import egovframework.example.data.service.impl.DataWF;

@Controller
public class DataController {

    // ✅ 전세계 요리 수집기 (DataWF → execute 방식 사용)
    @Autowired
    private DataWF dataWF;

    @GetMapping(value = "/wf.do", produces = "text/html; charset=UTF-8")
    @ResponseBody
    public String runWorldApiToDb() {
        try {
            dataWF.execute(); // ✅ 안전하게 감싸기
            return "<span style='color:green;'>세계 요리 API → DB 저장 완료!</span>";
        } catch (Exception e) {
            e.printStackTrace(); // ✅ 콘솔에 에러 원인 출력
            return "<span style='color:red;'>API 호출 실패</span>";
        }
    }

    // ✅ JSP 페이지 이동 (디자인용)
    @GetMapping("/dsDev.do")
    public String showDsDev() {
        return "dsdev";
    }
    
//  중지 기능
    @GetMapping(value = "/stop.do", produces = "text/plain; charset=UTF-8")
    @ResponseBody
    public String stopDataInsert() {
        dataWF.stop();
        return "🚫 데이터 저장 중지 요청 완료";
    }
}