package egovframework.example.member.web;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import egovframework.example.board.service.BoardService;
import egovframework.example.board.service.BoardVO;
import egovframework.example.board.service.impl.BoardMapper;
import egovframework.example.file.service.FileService;
import egovframework.example.like.service.impl.LikeMapper;
import egovframework.example.member.service.MemberService;
import egovframework.example.member.service.MemberVO;
import egovframework.example.member.service.impl.EmailService;
import egovframework.example.member.service.impl.MemberMapper;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
public class MemberController {

    @Autowired
    private MemberService memberService;
    
    @Autowired
    private EmailService emailService;
    
    @Autowired
    private BoardMapper boardMapper;
    
    @Autowired
    private BoardService boardService;

    @Autowired
    private LikeMapper likeMapper;

    @Autowired
    private FileService fileService;

    @Autowired
    private MemberMapper memberMapper;

    // ✅ [회원가입 처리]
    @PostMapping("/member/register.do")
    public String register(MemberVO memberVO, RedirectAttributes rttr, Model model) {
        try {
            memberService.register(memberVO);

            // 성공 시 플래시 메시지 추가
            rttr.addFlashAttribute("signupSuccess", true);

            return "redirect:/member/login.do";
        } catch (Exception e) {
            model.addAttribute("errorMsg", e.getMessage());
            return "member/login";
        }
    }

    // ✅ [닉네임 중복 검사 - AJAX]
    @ResponseBody
    @GetMapping("/member/nicknameCheck.do")
    public Map<String, Boolean> nicknameCheck(@RequestParam("nickname") String nickname, HttpSession session) {
        MemberVO loginMember = (MemberVO) session.getAttribute("loginMember");
        Long currentIdx = loginMember != null ? loginMember.getMemberIdx() : null;

        boolean available = memberService.isNicknameAvailable(nickname, currentIdx);
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

            // ✅ TEMP_PASSWORD_YN = 'Y' 이면서 일반 회원인 경우에만 분기
            if ("Y".equals(loginUser.getTempPasswordYn()) && loginUser.getKakaoId() == null) {
                session.setAttribute("redirectAfterLogin", redirect != null ? redirect : "/");
                return "redirect:/redirect/confirm.do";
            }

            // ✅ redirect가 유효하면 이동
            if (redirect != null && !redirect.trim().isEmpty() && !redirect.contains("/WEB-INF")) {
                return "redirect:" + redirect;
            }

            return "redirect:/";

        } catch (Exception e) {
            model.addAttribute("errorMsg", e.getMessage());
            return "member/login";
        }
    }
    
//  임시비밀번호 경고창
    @GetMapping("/redirect/confirm.do")
    public String showConfirmPage() {
        return "member/redirectConfirm";
    }
    
    // ✅ [로그인 폼 페이지]
    @GetMapping("/member/login.do")
    public String loginPage(@RequestParam(value = "redirect", required = false) String redirect,
		            		HttpServletRequest request) {
		if (redirect == null || redirect.trim().isEmpty()) {
		redirect = "/";
		}
		
		String kakaoLink = "https://kauth.kakao.com/oauth/authorize?" +
		"client_id=d779fae0a4d9df6ea88f8bfed6e1b315" +
		"&redirect_uri=http://localhost:8080/kakaoLogin.do" +
		"&response_type=code" +
		"&state=" + java.net.URLEncoder.encode(redirect, java.nio.charset.StandardCharsets.UTF_8);
		
		request.setAttribute("kakaoLink", kakaoLink);
		
		return "member/login"; // 로그인 폼 JSP
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
    @PostMapping(value = "/member/sendEmailCode.do", produces = "application/json;charset=UTF-8")
    public Map<String, Object> sendEmailCode(@RequestBody Map<String, String> data, HttpSession session) {
        String email = data.get("email");
        String mode = data.get("mode");

        Map<String, Object> result = new HashMap<>();
        
        log.info("이메일 인증 요청됨 | 이메일: {} | 모드: {}", email, mode);

        boolean isRegistered = memberService.isEmailRegistered(email);

        // 모드별 분기 처리
        if ("signup".equals(mode)) {
            if (isRegistered) {
                result.put("success", false);
                result.put("message", "이미 가입된 이메일입니다. 다른 이메일을 입력해주세요.");
                return result;
            }
        } else if ("findId".equals(mode) || "findPw".equals(mode)) {
            if (!isRegistered) {
                result.put("success", false);
                result.put("message", "가입되지 않은 이메일입니다.");
                return result;
            }
        } else {
            result.put("success", false);
            result.put("message", "요청 형식이 올바르지 않습니다.");
            return result;
        }

        // 인증코드 생성 및 세션 저장
        String code = String.format("%06d", new Random().nextInt(1000000));
        session.setAttribute("emailCode", code);
        session.setAttribute("emailForCode", email);
        session.setAttribute("emailCodeExpiry", LocalDateTime.now().plusMinutes(5)); // 만료시간 저장

        try {
            emailService.sendCode(email, code);
            result.put("success", true);
            result.put("message", "인증번호가 이메일로 전송되었습니다.");
            log.info("이메일 발송 성공: {}", email);
        } catch (Exception e) {
        	log.info("❌ 이메일 인증번호 전송 중 예외 발생: {}", e.getMessage());
            result.put("success", false);
            result.put("message", "이메일 전송에 실패했습니다. 관리자에게 문의하세요.");
        }

        return result;
    }

    // [이메일 인증번호 확인]
    @ResponseBody
    @PostMapping("/member/verifyCode.do")
    public Map<String, Object> verifyCode(@RequestBody Map<String, String> data, HttpSession session) {
        String inputCode = data.get("code");
        String sessionCode = (String) session.getAttribute("emailCode");
        String email = (String) session.getAttribute("emailForCode");
        LocalDateTime expiry = (LocalDateTime) session.getAttribute("emailCodeExpiry");

        Map<String, Object> result = new HashMap<>();

        // 만료 시간 확인
        if (expiry == null || LocalDateTime.now().isAfter(expiry)) {
            result.put("success", false);
            result.put("message", "인증 시간이 만료되었습니다. 다시 요청해주세요.");
            return result;
        }

        // 인증번호 비교
        boolean success = inputCode != null && inputCode.equals(sessionCode);
        if (success && email != null) {
            result.put("success", true);
            result.put("message", "인증이 완료되었습니다.");
            session.setAttribute("verifiedEmail", email);

            // 세션 정리
            session.removeAttribute("emailCode");
            session.removeAttribute("emailForCode");
            session.removeAttribute("emailCodeExpiry");
        } else {
            result.put("success", false);
            result.put("message", "인증번호가 일치하지 않습니다.");
        }
        return result;
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
            model.addAttribute("email", email);  // 입력 폼 유지를 위함
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
        member.setTempPasswordYn("Y");
        
        memberService.updatePassword(member);

        session.removeAttribute("verifiedEmail");
        
        model.addAttribute("msg", "임시 비밀번호는 '" + tempPw + "' 입니다. 로그인 후 반드시 변경해주세요.");

        return "member/findpasswordform";
    }
    
//  회원 탈퇴
    @PostMapping("/member/delete.do")
    public String deleteMember(@RequestParam("memberIdx") Long memberIdx,
                               HttpSession session,
                               HttpServletRequest request,
                               RedirectAttributes rttr) {
        MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");

        if (!loginUser.getMemberIdx().equals(memberIdx)) {
            throw new RuntimeException("접근 권한이 없습니다.");
        }

        try {
            log.info("🔥 3. 회원 정보 초기화 시작");
            memberService.softDeleteMember(memberIdx);
            log.info("✅ 3. 회원 정보 초기화 완료");

            // 3. 세션 종료
            session.invalidate();
            request.getSession(true).removeAttribute("loginUser");

            return "redirect:/";

        } catch (Exception e) {
            log.error("❌ 일반 회원 탈퇴 오류", e);
            rttr.addFlashAttribute("message", "회원 탈퇴 처리 중 오류가 발생했습니다.");
            return "redirect:/member/mypage.do?error=deleteFail";
        }
    }

    
//  카카오로그인
    @GetMapping("/kakaoLogin.do")
    public String kakaoLogin(@RequestParam("code") String code,
                             @RequestParam(value = "state", required = false) String redirect,
                             HttpSession session,
                             HttpServletRequest request) {
        try {
            // === [1] 토큰 요청 ===
            String tokenUrl = "https://kauth.kakao.com/oauth/token";
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("grant_type", "authorization_code");
            params.add("client_id", "d779fae0a4d9df6ea88f8bfed6e1b315"); // REST API 키
            params.add("redirect_uri", "http://localhost:8080/kakaoLogin.do"); // ⚠️ 쿼리스트링 제외
            params.add("code", code);

            HttpEntity<MultiValueMap<String, String>> tokenRequest = new HttpEntity<>(params, headers);
            ResponseEntity<String> tokenResponse = restTemplate.postForEntity(tokenUrl, tokenRequest, String.class);

            JSONObject tokenJson = (JSONObject) new JSONParser().parse(tokenResponse.getBody());
            String accessToken = (String) tokenJson.get("access_token");

            // === [2] 사용자 정보 요청 ===
            HttpHeaders infoHeaders = new HttpHeaders();
            infoHeaders.set("Authorization", "Bearer " + accessToken);
            HttpEntity<?> infoRequest = new HttpEntity<>(infoHeaders);

            String infoUrl = "https://kapi.kakao.com/v2/user/me";
            ResponseEntity<String> userInfoResponse = restTemplate.exchange(infoUrl, HttpMethod.GET, infoRequest, String.class);
            JSONObject userJson = (JSONObject) new JSONParser().parse(userInfoResponse.getBody());

            log.info("✅ userJson 전체: {}", userJson.toJSONString());

            Long kakaoId = ((Number) userJson.get("id")).longValue();
            JSONObject kakaoAccount = (JSONObject) userJson.get("kakao_account");
            JSONObject profile = (JSONObject) kakaoAccount.get("profile");

            String email = (String) kakaoAccount.get("email");
            String nickname = (String) profile.get("nickname");

            log.info("✅ kakaoId: {}", kakaoId);
            log.info("✅ nickname: {}", nickname);

            // === [3] 기존 회원 여부 확인
            MemberVO member = memberService.selectByKakaoId(kakaoId);

            if (member != null) {
                session.setAttribute("loginUser", member);
            } else {
            	 // ✅ 닉네임 중복 검사 + 자동 유니크 처리
                String finalNickname = nickname;
                int suffix = 1;
                while (memberService.isNicknameDuplicate(finalNickname)) {
                    finalNickname = nickname + suffix++;
                }

                // ✅ 자동 변경된 경우: 세션에 표시
                if (!finalNickname.equals(nickname)) {
                    session.setAttribute("nicknameAutoRenamedYn", "Y");
                    session.setAttribute("nicknameBefore", nickname);
                    session.setAttribute("nicknameAfter", finalNickname);
                }

                MemberVO kakaoMember = new MemberVO();
                kakaoMember.setKakaoId(kakaoId);
                kakaoMember.setNickname(finalNickname);
                kakaoMember.setEmail(email);
                kakaoMember.setProfile((String) profile.get("thumbnail_image_url"));
                kakaoMember.setRole("USER");

                memberService.insertKakaoMember(kakaoMember);
                member = memberService.selectByKakaoId(kakaoId);
                session.setAttribute("loginUser", member);
            }

            // === [4] 닉네임 자동 변경 시 경고 페이지로 리다이렉트
            if ("Y".equals(session.getAttribute("nicknameAutoRenamedYn"))) {
                return "redirect:/redirect/nicknameConfirm.do";
            }

            // === [5] 그 외 정상 리다이렉트
            if (redirect != null && !redirect.trim().isEmpty() && !redirect.contains("/WEB-INF")) {
                return "redirect:" + redirect;
            }
            return "redirect:/";

        } catch (Exception e) {
            log.error("❌ 카카오 로그인 중 예외 발생", e);
            throw new RuntimeException("카카오 로그인 실패", e);
        }
    }
    
//  카카오 이용자 닉네임 자동 변경 안내 confirm창
    @GetMapping("/redirect/nicknameConfirm.do")
    public String showNicknameConfirmPage() {
        return "member/nicknameWarning";
    }
    
//  카카오 회원 탈퇴
    @GetMapping("/member/kakao-delete.do")
    public String kakaoDelete(HttpSession session,
                              HttpServletRequest request,
                              RedirectAttributes rttr) {
        MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");

        if (loginUser == null || loginUser.getKakaoId() == null) {
            rttr.addFlashAttribute("message", "잘못된 접근입니다.");
            return "redirect:/home.do";
        }

        Long kakaoId = loginUser.getKakaoId();
        Long memberIdx = loginUser.getMemberIdx();

        try {
            // 1. 카카오 연결 해제 (실패해도 계속)
            try {
                memberService.unlinkKakaoUser(kakaoId);
            } catch (HttpClientErrorException e) {
                log.warn("⚠️ 카카오 연결 해제 실패 또는 이미 해제됨: {}", e.getMessage());
            }

            // 3. 회원 정보 초기화 (닉네임 → 탈퇴한 회원 등)
            memberService.softDeleteMember(memberIdx);

            // 4. 세션 종료
            session.invalidate();
            request.getSession(true).removeAttribute("loginUser");

            rttr.addFlashAttribute("message", "카카오 회원 탈퇴가 완료되었습니다.");
            return "redirect:/";

        } catch (Exception e) {
            log.error("❌ 카카오 탈퇴 실패", e);
            rttr.addFlashAttribute("message", "카카오 탈퇴 처리 중 오류가 발생했습니다.");
            return "redirect:/member/mycorrection.do";
        }
    }
}
