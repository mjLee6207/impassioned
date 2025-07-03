package egovframework.example.member.web;

import egovframework.example.member.service.MemberService;
import egovframework.example.member.service.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Map;

@Controller
public class MemberController {

    @Autowired
    private MemberService memberService;

    // 회원가입 처리
    @PostMapping("/member/register.do")
    public String register(MemberVO memberVO, Model model) {
        try {
            memberService.register(memberVO);
            return "redirect:/member/login.do"; // 회원가입 후 로그인 페이지로 이동
        } catch (Exception e) {
            model.addAttribute("errorMsg", e.getMessage());
            return "member/login"; // 실패 시 회원가입 페이지로 되돌림
        }
    }

    // 닉네임 중복 검사 (AJAX)
    @ResponseBody
    @GetMapping("/member/nicknameCheck.do")
    public Map<String, Boolean> nicknameCheck(@RequestParam("nickname") String nickname) {
        boolean available = memberService.isNicknameAvailable(nickname);
        return Collections.singletonMap("available", available);
    }
    
    // 아이디 중복 검사 (AJAX)
    @ResponseBody
    @GetMapping("/member/idCheck.do")
    public Map<String, Boolean> idCheck(@RequestParam("id") String id) {
        boolean available = memberService.isIdAvailable(id);
        return Collections.singletonMap("available", available);
    }
    
    // 로그인 처리
    @PostMapping("/member/login.do")
    public String login(MemberVO memberVO, HttpSession session, Model model) {
        try {
            MemberVO loginUser = memberService.authenticate(memberVO);
            session.setAttribute("loginUser", loginUser);
            return "redirect:/index.jsp";
        } catch (Exception e) {
            model.addAttribute("errorMsg", e.getMessage());
            return "member/login";
        }
    }

    // 로그인 페이지 열기 (GET)
    @GetMapping("/member/login.do")
    public String loginPage() {
        return "member/login";
    }

    // 로그아웃 처리
    @GetMapping("/member/logout.do")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    // 이메일 인증 코드 전송
    @ResponseBody
    @PostMapping(value = "/member/sendEmailCode.do", produces = "text/plain;charset=UTF-8")
    public String sendEmailCode(@RequestBody Map<String, String> data, HttpSession session) {
        String email = data.get("email");
        String code = String.valueOf((int) (Math.random() * 900000 + 100000));
        session.setAttribute("emailCode", code);
        return "인증번호가 전송되었습니다. (테스트 코드: " + code + ")";
    }

    // 이메일 인증 코드 확인
    @ResponseBody
    @PostMapping("/member/verifyCode.do")
    public Map<String, Boolean> verifyCode(@RequestBody Map<String, String> data, HttpSession session) {
        String inputCode = data.get("code");
        String sessionCode = (String) session.getAttribute("emailCode");
        boolean success = inputCode != null && inputCode.equals(sessionCode);
        return Collections.singletonMap("success", success);
    }
} 
