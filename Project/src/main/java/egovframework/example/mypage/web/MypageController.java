package egovframework.example.mypage.web;


import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import egovframework.example.member.service.MemberService;
import egovframework.example.member.service.MemberVO;
import egovframework.example.mypage.service.MypageLikeVO;
import egovframework.example.mypage.service.MypageMyPostVO;
import egovframework.example.mypage.service.MypageService;

@Controller
@RequestMapping("/mypage")
public class MypageController {

    @Autowired
    private MypageService mypageLikeService;
    @Autowired
    private MemberService memberService; // 회원정보 수정 서비스


    @GetMapping("/mypage.do")
    public String showMypage(HttpSession session, Model model) {
        MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");

        if (loginUser == null || loginUser.getMemberIdx() == null) {
            return "redirect:/member/login.do";
        }

        Long memberIdx = loginUser.getMemberIdx();

        // 내가 쓴 글
        List<MypageMyPostVO> myPosts = mypageLikeService.selectMyPosts(memberIdx);
        model.addAttribute("myPosts", myPosts);

        // 좋아요한 글
        List<MypageLikeVO> likedPosts = mypageLikeService.selectLikedPosts(memberIdx);
        model.addAttribute("likedPosts", likedPosts);

        return "mypage/mypage"; // jsp 파일명에 맞게!
    }
   
}

