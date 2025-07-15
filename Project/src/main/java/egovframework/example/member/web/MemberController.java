package egovframework.example.member.web;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import egovframework.example.member.service.MemberService;
import egovframework.example.member.service.MemberVO;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
public class MemberController {

    @Autowired
    private MemberService memberService;

    // ✅ [회원가입 처리]
    @PostMapping("/member/register.do")
    public String register(MemberVO memberVO, Model model) {
        try {
            memberService.register(memberVO);
            return "redirect:/member/login.do";
        } catch (Exception e) {
            model.addAttribute("errorMsg", e.getMessage());
            return "member/login";
        }
    }

    // ✅ [닉네임 중복 검사 - AJAX]
    @ResponseBody
    @GetMapping("/member/nicknameCheck.do")
    public Map<String, Boolean> nicknameCheck(@RequestParam("nickname") String nickname) {
        boolean available = memberService.isNicknameAvailable(nickname);
        return Collections.singletonMap("available", available);
    }

    // ✅ [아이디 중복 검사 - AJAX]
    @ResponseBody
    @GetMapping("/member/idCheck.do")
    public Map<String, Boolean> idCheck(@RequestParam("id") String id) {
        boolean available = memberService.isIdAvailable(id);
        return Collections.singletonMap("available", available);
    }

    // ✅ [로그인 처리]
    @PostMapping("/member/login.do")
    public String login(MemberVO memberVO,
                        HttpSession session,
                        Model model,
                        @RequestParam(value = "redirect", required = false) String redirect) {
        try {
            MemberVO loginUser = memberService.authenticate(memberVO);
            session.setAttribute("loginUser", loginUser);

            // ✅ redirect가 유효하고 /WEB-INF 포함 안하면 리다이렉트 (이중 인코딩 방지)
            if (redirect != null && !redirect.trim().isEmpty() && !redirect.contains("/WEB-INF")) {
                return "redirect:" + redirect;
            }

            return "redirect:/"; // 기본 메인 페이지

        } catch (Exception e) {
            model.addAttribute("errorMsg", e.getMessage());
            return "member/login";
        }
    }

    // ✅ [로그인 폼 페이지]
    @GetMapping("/member/login.do")
    public String loginPage() {
        return "member/login";
    }

    // ✅ [로그아웃 처리]
    @GetMapping("/member/logout.do")
    public String logout(HttpSession session,
                         @RequestParam(value = "redirect", required = false) String redirect) {
        session.invalidate();
        if (redirect != null && !redirect.trim().isEmpty()) {
            return "redirect:" + redirect;
        }
        return "redirect:/";
    }

    // ✅ [이메일 인증번호 전송]
    @ResponseBody
    @PostMapping(value = "/member/sendEmailCode.do", produces = "text/plain;charset=UTF-8")
    public String sendEmailCode(@RequestBody Map<String, String> data, HttpSession session) {
        String email = data.get("email");
        String code = String.valueOf((int) (Math.random() * 900000 + 100000));

        session.setAttribute("emailCode", code);
        session.setAttribute("emailForCode", email);

        return "인증번호가 전송되었습니다. (인증번호: " + code + ")";
    }

    // ✅ [이메일 인증번호 확인]
    @ResponseBody
    @PostMapping("/member/verifyCode.do")
    public Map<String, Boolean> verifyCode(@RequestBody Map<String, String> data, HttpSession session) {
        String inputCode = data.get("code");
        String sessionCode = (String) session.getAttribute("emailCode");
        String email = (String) session.getAttribute("emailForCode");

        boolean success = inputCode != null && inputCode.equals(sessionCode);
        if (success && email != null) {
            session.setAttribute("verifiedEmail", email);
        }
        return Collections.singletonMap("success", success);
    }

    // ✅ [아이디 찾기 폼]
    @GetMapping("/member/findidform.do")
    public String findIdForm() {
        return "member/findidform";
    }

    // ✅ [아이디 찾기 처리]
    @PostMapping("/member/findId.do")
    public String findId(@RequestParam("email") String email,
                         HttpSession session,
                         Model model) {
        String verifiedEmail = (String) session.getAttribute("verifiedEmail");
        if (verifiedEmail == null || !verifiedEmail.equals(email)) {
            model.addAttribute("msg", "이메일 인증이 완료되지 않았습니다.");
            return "member/findidform";
        }

        String id = memberService.findIdByEmail(email);
        session.removeAttribute("verifiedEmail");

        if (id == null) {
            model.addAttribute("msg", "입력하신 이메일로 가입된 아이디가 없습니다.");
        } else {
            model.addAttribute("msg", "입력하신 이메일로 가입된 아이디는 '" + id + "' 입니다.");
        }

        return "member/findidform";
    }

    // ✅ [비밀번호 찾기 폼]
    @GetMapping("/member/findpasswordform.do")
    public String findPasswordForm() {
        return "member/findpasswordform";
    }

    // ✅ [비밀번호 찾기 처리]
    @PostMapping("/member/findPassword.do")
    public String findPassword(@RequestParam("id") String id,
                               @RequestParam("email") String email,
                               HttpSession session,
                               Model model) {

        String verifiedEmail = (String) session.getAttribute("verifiedEmail");
        if (verifiedEmail == null || !verifiedEmail.equals(email)) {
            model.addAttribute("msg", "이메일 인증이 완료되지 않았습니다.");
            return "member/findpasswordform";
        }

        MemberVO member = memberService.findByIdAndEmail(id, email);
        if (member == null) {
            model.addAttribute("msg", "일치하는 회원 정보가 없습니다.");
            return "member/findpasswordform";
        }

        String tempPw = UUID.randomUUID().toString().substring(0, 8);
        String encPw = BCrypt.hashpw(tempPw, BCrypt.gensalt());
        member.setPassword(encPw);
        memberService.updatePassword(member);

        session.removeAttribute("verifiedEmail");
        model.addAttribute("msg", "임시 비밀번호는 '" + tempPw + "' 입니다. 로그인 후 반드시 변경해주세요.");

        return "member/findpasswordform";
    }
    
//  회원 탈퇴
    @PostMapping("/member/delete.do")
    public String deleteMember(@RequestParam("memberIdx") int memberIdx, HttpSession session) {
        MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
        Long sessionIdx = loginUser.getMemberIdx();

        if (sessionIdx.longValue() != memberIdx) {
            throw new RuntimeException("접근 권한이 없습니다.");
        }

        try {
            memberService.deleteMember(memberIdx);
            session.invalidate();
            return "redirect:/";
        } catch (Exception e) {
            log.error("❌ 회원 탈퇴 중 오류", e);
            return "redirect:/member/mypage.do?error=deleteFail";
        }
    }
}
