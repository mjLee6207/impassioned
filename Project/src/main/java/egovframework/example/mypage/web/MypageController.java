package egovframework.example.mypage.web;


import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import egovframework.example.member.service.MemberVO;
import egovframework.example.mypage.service.MypageMyPostService;
import egovframework.example.mypage.service.MypageMyPostVO;
import egovframework.example.mypage.service.MypageLikeService;
import egovframework.example.mypage.service.MypageLikeVO;

@Controller
@RequestMapping("/mypage")
public class MypageController {

    @Autowired
    private MypageMyPostService mypageMyPostService;

    @Autowired
    private MypageLikeService mypageLikeService;

    @GetMapping("/mypage.do")
    public String showMypage(HttpSession session, Model model) {
        MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");

        if (loginUser == null || loginUser.getMemberIdx() == null) {
            return "redirect:/member/login.do";
        }

        Long memberIdx = loginUser.getMemberIdx();

        // 내가 쓴 글
        List<MypageMyPostVO> myPosts = mypageMyPostService.selectMyPosts(memberIdx);
        model.addAttribute("myPosts", myPosts);

        // 좋아요한 글
        List<MypageLikeVO> likedPosts = mypageLikeService.selectLikedPosts(memberIdx);
        model.addAttribute("likedPosts", likedPosts);

        return "mypage/mypage"; // jsp 파일명에 맞게!
    }
}
