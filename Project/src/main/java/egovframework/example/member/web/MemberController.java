package egovframework.example.member.web;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import egovframework.example.member.service.MemberService;
import egovframework.example.member.service.MemberVO;

@Controller
public class MemberController {

    @Autowired
    private MemberService memberService;
 
    

    // ✅ [회원가입 처리]
    @PostMapping("/member/register.do")
    public String register(MemberVO memberVO, Model model) {
        try {
            memberService.register(memberVO);
            return "redirect:/member/login.do"; // 회원가입 후 로그인 페이지로 이동
        } catch (Exception e) {
            model.addAttribute("errorMsg", e.getMessage());
            return "member/login"; // 실패 시 로그인 페이지로 이동
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
        
//  로그인 처리(여러 개 파라미터 대응 버전)
    @PostMapping("/member/login.do")
    public String login(MemberVO memberVO,
                        HttpSession session,
                        Model model,
                        @RequestParam(value = "redirect", required = false) String redirect) {
        try {
            MemberVO loginUser = memberService.authenticate(memberVO);
            session.setAttribute("loginUser", loginUser); // 로그인 성공 시 세션 저장

            // ✅ redirect가 유효하고 /WEB-INF 포함 안하면 리다이렉트
            if (redirect != null && !redirect.trim().isEmpty() && !redirect.contains("/WEB-INF")) {

                int idx = redirect.indexOf("?");
                if (idx != -1) {
                    String path = redirect.substring(0, idx);
                    String queryString = redirect.substring(idx + 1);

                    // 개별 쿼리 파라미터 인코딩
                    String[] params = queryString.split("&");
                    StringBuilder encodedParams = new StringBuilder();
                    for (int i = 0; i < params.length; i++) {
                        String[] pair = params[i].split("=");
                        String key = URLEncoder.encode(pair[0], StandardCharsets.UTF_8.toString());
                        String value = pair.length > 1 ? URLEncoder.encode(pair[1], StandardCharsets.UTF_8.toString()) : "";
                        encodedParams.append(key).append("=").append(value);
                        if (i < params.length - 1) encodedParams.append("&");
                    }

                    String encodedRedirect = path + "?" + encodedParams.toString();
                    return "redirect:" + encodedRedirect;

                } else {
                    return "redirect:" + redirect;
                }
            }

            // redirect가 없거나 잘못된 경우 기본 이동
            return "redirect:/";

        } catch (Exception e) {
            model.addAttribute("errorMsg", e.getMessage());
            return "member/login";
        }
    }
    
    // ✅ [로그인 폼 페이지 GET]
    @GetMapping("/member/login.do")
    public String loginPage() {
        return "member/login";
    }

    // [로그아웃 처리 - 원래페이지로 이동시킴]
    @GetMapping("/member/logout.do")
    public String logout(HttpSession session,
                         @RequestParam(value = "redirect", required = false) String redirect) {
        session.invalidate(); // ✔ 세션 전체 삭제

        if (redirect != null && !redirect.trim().isEmpty()) {
            return "redirect:" + redirect; // 🔁 원래 페이지로 이동
        }

        return "redirect:/"; // 기본: 메인 페이지
    }

    // ✅ [이메일 인증번호 전송]
    @ResponseBody
    @PostMapping(value = "/member/sendEmailCode.do", produces = "text/plain;charset=UTF-8")
    public String sendEmailCode(@RequestBody Map<String, String> data, HttpSession session) {
        String email = data.get("email");

        // 인증 코드 생성 (6자리)
        String code = String.valueOf((int) (Math.random() * 900000 + 100000));

        session.setAttribute("emailCode", code); // 세션에 저장
        session.setAttribute("emailForCode", email);
        return "인증번호가 전송되었습니다. (인증번호: " + code + ")";
    }

    // ✅ [이메일 인증번호 확인]
    @ResponseBody
    @PostMapping("/member/verifyCode.do")
    public Map<String, Boolean> verifyCode(@RequestBody Map<String, String> data, HttpSession session) {
        String inputCode = data.get("code");
        String sessionCode = (String) session.getAttribute("emailCode");
        String email = (String) session.getAttribute("emailForCode"); // 인증 대상 이메일
        
        boolean success = inputCode != null && inputCode.equals(sessionCode);
        if (success && email != null) {
            session.setAttribute("verifiedEmail", email); //  이메일 인증 완료되면 저장
        }
        return Collections.singletonMap("success", success);
    }
    
//  아이디 찾기 폼 페이지 GET
    @GetMapping("/member/findidform.do")   
    public String findIdForm() {
        return "member/findidform";
    }

//  아이디 찾기 (이메일 인증 확인 후 아이디 화면 출력)
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
    
//  비밀번호 찾기 폼 페이지 GET
    @GetMapping("/member/findpasswordform.do")
    public String findPasswordForm() {
        return "member/findpasswordform";
    }
    
//  비밀번호 찾기 + 인증코드 없이 간이 테스트용 임시 비밀번호 발급 (메일 없이 화면 출력)
    @PostMapping("/member/findPassword.do")
    public String findPassword(@RequestParam("id") String id,
                               @RequestParam("email") String email,
                               HttpSession session,
                               Model model) {

        // 1. 이메일 인증 확인
        String verifiedEmail = (String) session.getAttribute("verifiedEmail");
        if (verifiedEmail == null || !verifiedEmail.equals(email)) {
            model.addAttribute("msg", "이메일 인증이 완료되지 않았습니다.");
            return "member/findpasswordform";
        }

        // 2. 회원 존재 여부 확인
        MemberVO member = memberService.findByIdAndEmail(id, email);
        if (member == null) {
            model.addAttribute("msg", "일치하는 회원 정보가 없습니다.");
            return "member/findpasswordform";
        }

        // 3. 임시 비밀번호 생성 및 암호화
        String tempPw = UUID.randomUUID().toString().substring(0, 8);
        String encPw = BCrypt.hashpw(tempPw, BCrypt.gensalt());
        member.setPassword(encPw);
        memberService.updatePassword(member);

        // 4. 결과 메시지 출력 (메일 대신 화면에 표시)
        session.removeAttribute("verifiedEmail");
        model.addAttribute("msg", "임시 비밀번호는 '" + tempPw + "' 입니다. 로그인 후 반드시 변경해주세요.");

        return "member/findpasswordform";
    }
  
}