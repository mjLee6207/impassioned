package egovframework.example.mypage.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import egovframework.example.file.service.FileService;
import egovframework.example.file.service.FileVO;
import egovframework.example.member.service.MemberService;
import egovframework.example.member.service.MemberVO;
import egovframework.example.mypage.service.MypageLikeVO;
import egovframework.example.mypage.service.MypageMyPostVO;
import egovframework.example.mypage.service.MypageService;

@Controller
@RequestMapping("/mypage")
public class MypageController {

    @Autowired
    private MypageService mypageService;   // 마이 페이지
    @Autowired
    private MemberService memberService;       // 회원정보 수정 서비스
    @Autowired
    private FileService fileService;

    // 마이페이지
    @GetMapping("/mypage.do")
    public String showMypage(HttpSession session, Model model) {
        MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");

        if (loginUser == null || loginUser.getMemberIdx() == null) {
            return "redirect:/member/login.do";
        }

        Long memberIdx = loginUser.getMemberIdx();

        List<MypageMyPostVO> myPosts = mypageService.selectMyPosts(memberIdx);
        model.addAttribute("myPosts", myPosts);

        List<MypageLikeVO> likedPosts = mypageService.selectLikedPosts(memberIdx);
        model.addAttribute("likedPosts", likedPosts);

        return "mypage/mypage";
    }

    // 회원 정보 수정 페이지 열기
    @GetMapping("/mycorrection.do")
    public String showEditForm(HttpSession session, Model model) {
        MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");

        if (loginUser == null || loginUser.getMemberIdx() == null) {
            return "redirect:/member/login.do";
        }

        MemberVO memberInfo = memberService.selectMemberByIdx(loginUser.getMemberIdx());
        model.addAttribute("member", memberInfo);

        return "mypage/mycorrection";
    }

    // 회원 정보 수정 처리 (수정완료 버튼 눌렀을 때)
    @PostMapping("/update.do")
    public String updateMemberInfo(MemberVO memberVO,  
    @RequestParam(value = "profileImage", required = false) MultipartFile profileImage,
    		HttpSession session)
    		throws IOException {

        MemberVO loginUser = (MemberVO) session.getAttribute("loginUser"); // 1

        if (loginUser == null) {
            return "redirect:/member/login.do";
        }

        // 로그인 사용자 정보 기준으로 memberIdx 고정
        memberVO.setMemberIdx(loginUser.getMemberIdx());

        // 서비스 호출 - DB 정보 업데이트
        memberService.updateMember(memberVO);
     // [2] 프로필 이미지 파일 업로드/삭제 처리 추가
        if (profileImage != null && !profileImage.isEmpty()) {
            // 기존 프로필 이미지가 있다면 삭제 (FileService 활용)
            // (FileService를 @Autowired로 선언 필요)
            FileVO oldProfile = fileService.getProfileFileByMemberId(loginUser.getMemberIdx());
            if (oldProfile != null) {
                fileService.deleteFile(oldProfile.getFileId());
            }
            // 새 파일 등록
            FileVO newFile = new FileVO();
            newFile.setFileName(profileImage.getOriginalFilename());
            newFile.setFileType(profileImage.getContentType());
            newFile.setUseType("MEMBER");
            newFile.setUseTargetId(loginUser.getMemberIdx());
            newFile.setUploaderId(loginUser.getMemberIdx());
            newFile.setFilePath("/profile/" + profileImage.getOriginalFilename());
            newFile.setFileData(profileImage.getBytes());
            fileService.insertFile(newFile);

            // 멤버 테이블의 profile 필드(이미지URL) 업데이트
            String profileUrl = "/file/download.do?fileId=" + newFile.getFileId();
            memberService.updateProfileImage(loginUser.getMemberIdx(), profileUrl);
        }

        // 세션 갱신 (닉네임이나 프로필이 바뀌었을 경우 반영)
        MemberVO updated = memberService.selectMemberByIdx(loginUser.getMemberIdx());
        session.setAttribute("loginUser", updated);

        // 마이페이지로 리다이렉트
        return "redirect:/mypage/mypage.do";
    }
}

