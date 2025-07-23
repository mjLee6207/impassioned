	package egovframework.example.mypage.web;
	
	import java.io.IOException;
	import java.util.List;
	
	import javax.servlet.http.HttpSession;
	
	import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.stereotype.Controller;
	import org.springframework.ui.Model;
	import org.springframework.web.bind.annotation.GetMapping;
	import org.springframework.web.bind.annotation.PostMapping;
	import org.springframework.web.bind.annotation.RequestMapping;
	import org.springframework.web.bind.annotation.RequestParam;
	import org.springframework.web.multipart.MultipartFile;
	import org.springframework.web.servlet.mvc.support.RedirectAttributes;
	
	import egovframework.example.common.Criteria;
	import egovframework.example.file.service.FileService;
	import egovframework.example.file.service.FileVO;
	import egovframework.example.member.service.MemberService;
	import egovframework.example.member.service.MemberVO;
	import egovframework.example.mypage.service.MypageService;
	import lombok.extern.log4j.Log4j2;
	@Log4j2
	@Controller
	@RequestMapping("/mypage")
	public class MypageController {
	
	    @Autowired
	    private MypageService mypageService;   // 마이 페이지
	    @Autowired
	    private MemberService memberService;       // 회원정보 수정 서비스
	    @Autowired
	    private FileService fileService;
	
	 
	
	    // 회원 정보 수정 페이지 열기
	    @GetMapping("/mycorrection.do")
	    public String showEditForm(HttpSession session, Model model) {
	        MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
	
	        if (loginUser == null || loginUser.getMemberIdx() == null) {
	            return "redirect:/member/login.do";
	        }
	
	        // DB에서 최신 정보 조회
	        MemberVO memberInfo = memberService.selectMemberByIdx(loginUser.getMemberIdx());
	        log.info("✅ 회원 정보 조회: {}", memberInfo);
	        // 임시 비밀번호 여부 판단
	        if ("Y".equals(memberInfo.getTempPasswordYn())) {
	            model.addAttribute("showTempPasswordWarning", true);
	        }
	        
	        model.addAttribute("member", memberInfo);
	
	        return "mypage/mycorrection";
	    }
	
	    // 회원 정보 수정 처리 (수정완료 버튼 눌렀을 때)
	    @PostMapping("/update.do")
	    public String updateMemberInfo(MemberVO memberVO,  
	    @RequestParam(value = "profileImage", required = false) MultipartFile profileImage,
	    @RequestParam(value = "originProfileImage", required = false) String originProfileImage,		
	    HttpSession session, 
	    RedirectAttributes rttr )
	    		throws IOException {
	
	        MemberVO loginUser = (MemberVO) session.getAttribute("loginUser"); // 1
	
	        if (loginUser == null) {
	            return "redirect:/member/login.do";
	        }
	
	        // 로그인 사용자 정보 기준으로 memberIdx 고정
	        memberVO.setMemberIdx(loginUser.getMemberIdx());
	        // 아무것도 변경하지 않을 경우 기존 프로필이미지 값 그대로 유지!
	        memberVO.setProfile(originProfileImage);
	
	        // 서비스 호출 - DB 정보 업데이트
	        memberService.updateMember(memberVO);
	        
	        // ✅ 비밀번호 변경이 있었다면 임시 로그인 안내 제거
	        if (memberVO.getPassword() != null && !memberVO.getPassword().isEmpty()) {
	            session.removeAttribute("tempPasswordLogin");
	            memberVO.setTempPasswordYn("N");
	        }
	        
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
	
	        rttr.addFlashAttribute("updateSuccess", true);
	        
	        // 마이페이지로 리다이렉트
	        return "redirect:/mypage/mypage.do";
	    }
	    
	//   마이페이지매핑+페이지네이션
	    @GetMapping("/mypage.do")
	    public String myPage(
	        @RequestParam(value = "tab", required = false, defaultValue = "myboard") String tab,
	        @RequestParam(value = "myPostsPage", defaultValue = "1") int myPostsPage,
	        @RequestParam(value = "likedPostsPage", defaultValue = "1") int likedPostsPage,
	        @RequestParam(value = "category", required = false) String category,
	        @RequestParam(value = "searchKeyword", required = false) String searchKeyword,
	        Model model,
	        HttpSession session) {

	        MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
	        if (loginUser == null || loginUser.getMemberIdx() == null) {
	            return "redirect:/member/login.do";
	        }

	        Long memberIdx = loginUser.getMemberIdx();

	        // ===== 내가 쓴 글 페이징 =====
	        Criteria myPostsCriteria = new Criteria();
	        myPostsCriteria.setPageIndex(myPostsPage);
	        myPostsCriteria.setPageUnit(10);
	        myPostsCriteria.setCategory(category);
	        myPostsCriteria.setSearchKeyword(searchKeyword);

	        PaginationInfo myPostsPaginationInfo = new PaginationInfo();
	        myPostsPaginationInfo.setCurrentPageNo(myPostsCriteria.getPageIndex());
	        myPostsPaginationInfo.setRecordCountPerPage(myPostsCriteria.getPageUnit());
	        myPostsPaginationInfo.setPageSize(10);
	        myPostsCriteria.setFirstIndex(myPostsPaginationInfo.getFirstRecordIndex());

	        List<?> myPosts = mypageService.selectMyBoardList(myPostsCriteria, memberIdx);
	        int myPostsTotCnt = mypageService.selectMyBoardListTotCnt(myPostsCriteria, memberIdx);
	        myPostsPaginationInfo.setTotalRecordCount(myPostsTotCnt);

	        model.addAttribute("myPosts", myPosts);
	        model.addAttribute("myPostsPaginationInfo", myPostsPaginationInfo);
	        model.addAttribute("myPostsTotalCount", myPostsTotCnt);

	        // ===== 좋아요 남긴 글 페이징 =====
	        Criteria likedPostsCriteria = new Criteria();
	        likedPostsCriteria.setPageIndex(likedPostsPage);
	        likedPostsCriteria.setPageUnit(10);
	        likedPostsCriteria.setCategory(category);
	        likedPostsCriteria.setSearchKeyword(searchKeyword);
	        PaginationInfo likedPostsPaginationInfo = new PaginationInfo();
	        likedPostsPaginationInfo.setCurrentPageNo(likedPostsCriteria.getPageIndex());
	        likedPostsPaginationInfo.setRecordCountPerPage(likedPostsCriteria.getPageUnit());
	        likedPostsPaginationInfo.setPageSize(10);
	        likedPostsCriteria.setFirstIndex(likedPostsPaginationInfo.getFirstRecordIndex());  // 이거는 별도 계산 필요 시 따로 처리

	        List<?> likedRecipes = mypageService.selectMyLikeList(likedPostsCriteria, memberIdx, "RECIPE");
	        int likedRecipesTotCnt = mypageService.selectMyLikeListTotCnt(likedPostsCriteria, memberIdx, "RECIPE");

	        List<?> likedBoardPosts = mypageService.selectMyLikeList(likedPostsCriteria, memberIdx, "BOARD");
	        int likedBoardPostsTotCnt = mypageService.selectMyLikeListTotCnt(likedPostsCriteria, memberIdx, "BOARD");
	        likedPostsPaginationInfo.setTotalRecordCount(likedRecipesTotCnt);
	        model.addAttribute("likedRecipes", likedRecipes);
	        model.addAttribute("likedRecipesTotalCount", likedRecipesTotCnt);
	        model.addAttribute("likedPosts", likedBoardPosts);
	        model.addAttribute("likedPostsTotalCount", likedBoardPostsTotCnt);
	        model.addAttribute("likedPostsPaginationInfo", likedPostsPaginationInfo);
	        // ✅ 회원 정보 수정 성공 alert 표시 (세션 → 모델)
	        Boolean updateSuccess = (Boolean) session.getAttribute("updateSuccess");
	        if (updateSuccess != null && updateSuccess) {
	            model.addAttribute("updateSuccess", true);
	            session.removeAttribute("updateSuccess");
	        }

	        // ===== 탭 정보 =====
	        model.addAttribute("tab", tab);

	        return "mypage/mypage";
	    }

	}