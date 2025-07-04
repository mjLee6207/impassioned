package egovframework.example.mypage.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import egovframework.example.member.service.MemberVO;
import egovframework.example.mypage.service.MypageLikeService;
import egovframework.example.mypage.service.MypageLikeVO;

@Controller
@RequestMapping("/mypage")
public class MypageLikeController {

    @Autowired
    private MypageLikeService mypageLikeService;

    @GetMapping("/mypage.do")
    public String showLikeList(HttpSession session, Model model) {
        MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");

        // 로그인 여부 확인
        if (loginUser == null || loginUser.getMemberIdx() == null) {
            return "redirect:/member/login.do";
        }

        Long memberIdx = loginUser.getMemberIdx(); // Long 타입 사용
        // 만약 서비스 메서드가 int를 받으면 int로 변환
        // int memberIdx = loginUser.getMemberIdx().intValue();

        List<MypageLikeVO> likedPosts = mypageLikeService.selectLikedPosts(memberIdx);
        model.addAttribute("likedPosts", likedPosts);

        return "mypage/mypage"; // /WEB-INF/jsp/mypage/mypage.jsp
    }
}
