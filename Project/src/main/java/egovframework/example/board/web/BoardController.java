	package egovframework.example.board.web;
	
	import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import egovframework.example.board.service.BoardService;
import egovframework.example.board.service.BoardVO;
import egovframework.example.board.service.ReviewVO;
import egovframework.example.common.Criteria;
import egovframework.example.member.service.MemberVO;
import lombok.extern.log4j.Log4j2;
	
	@Log4j2
	@Controller
	public class BoardController {
	   @Autowired
	   private BoardService boardService;
	
	//	전체조회
		@GetMapping("/board/board.do")
		public String name(
				@ModelAttribute Criteria criteria,
	//			페이지네이션 분모가0 오류해결위해 삽입
				@RequestParam(value = "pageIndex", defaultValue = "1") int pageIndex,
//				카테고리별 검색을 위해 아래 두줄 추가 
		@RequestParam(value = "category", required = false) String category,
		@RequestParam(value = "searchKeyword", required = false) String searchKeyword,				
				Model model) {
			criteria.setPageIndex(pageIndex);
			 criteria.setPageUnit(10);
	//		1) 등차자동계산 클래스: PaginationInfo
	//		   - 필요정보: (1) 현재페이지번호(pageIndex),(2) 보일 개수(pageUnit): 3
			PaginationInfo paginationInfo = new PaginationInfo();
			paginationInfo.setCurrentPageNo(criteria.getPageIndex());
			paginationInfo.setRecordCountPerPage(criteria.getPageUnit());
			paginationInfo.setPageSize(10); 
	//		등차를 자동 계산: firstRecordIndex 필드에 있음
			criteria.setFirstIndex(paginationInfo.getFirstRecordIndex());
//			카테고리별검색을위해 추가: 7월 8일 오후 2시 50분 강승태
		    criteria.setCategory(category);
		    criteria.setSearchKeyword(searchKeyword);
		    log.info("🔥 category = {}", category);
		    log.info("🔥 criteria.getCategory() = {}", criteria.getCategory());
		    log.info("🔥 searchKeyword = {}", searchKeyword);
	
	
	//      전체조회 서비스 메소드 실행
	      List<?> boards = boardService.selectBoardList(criteria);
	      log.info("테스트 : " + boards);
	      model.addAttribute("boards", boards);
	
	//      페이지 번호 그리기: 페이지 플러그인(전체테이블 행 개수 필요함)
	      int totCnt = boardService.selectBoardListTotCnt(criteria);
	      paginationInfo.setTotalRecordCount(totCnt);
	      log.info("테스트2 : " + totCnt);
	//      페이지 모든 정보: paginationInfo
	      model.addAttribute("paginationInfo", paginationInfo);
	      List<BoardVO> bestPosts = boardService.selectBestPosts();
	      model.addAttribute("bestPosts", bestPosts);
	      return "board/boardlist";
	   }
	 
		/*
		 * // 추가 페이지 열기
		 * 
		 * @GetMapping("/board/addition.do") public String createBoardView() { return
		 * "board/boardwrite"; }
		 */
			
	// 글 작성 폼 화면 보여주기
	 @GetMapping("/board/add.do")
	 public String showAddForm(Model model) {
	     model.addAttribute("boardVO", new BoardVO()); // 빈 폼 바인딩
	     return "board/boardwrite"; // 글 작성 폼 JSP or HTML 경로
	 }
	
	//	insert : 저장 버튼 클릭시
	//	7/7 삭제 후 원래 카테고리로 돌아가기,  리퀘스트팜,리턴 추가 (민중)
		@PostMapping("/board/add.do")
		public String insert(@ModelAttribute BoardVO boardVO, 
		@RequestParam(value = "image", required = false) MultipartFile image, 
				HttpSession session,HttpServletRequest req) throws UnsupportedEncodingException {
		    MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
		    if (loginUser == null) {
		        return "redirect:/member/login.do"; // 비로그인 시 로그인 페이지로
		    }
		
		    // 🔒 서버 측 category null 방어 로직 추가
		    if (boardVO.getCategory() == null || boardVO.getCategory().isBlank()) {
		        throw new IllegalArgumentException("카테고리는 필수입니다.");
		    }
		
		    // 서버에서 로그인된 사용자의 인덱스 강제 설정
		    boardVO.setWriterIdx(loginUser.getMemberIdx().intValue());
	
		    // ==========================
		    // 1. 이미지 파일 처리 (여기서부터)
		    // ==========================
		    if (image != null && !image.isEmpty()) {
		        String uploadDir = "/upload";
		        String realPath = req.getServletContext().getRealPath(uploadDir);
		        File uploadFolder = new File(realPath);
		        if (!uploadFolder.exists()) uploadFolder.mkdirs();
	
		        String uuid = UUID.randomUUID().toString();
		        String saveName = uuid + "_" + image.getOriginalFilename();
		        File dest = new File(uploadFolder, saveName);
	
		        try {
		            image.transferTo(dest); // 파일 저장
	
		            // [ERD 참고]
		            // 1) t_file에도 저장하려면 FileVO 생성 및 fileService.insert() 등의 추가 코드 필요
		            //    (간단 버전은 board 테이블만 써도 됨!)
		            // 2) 게시글에도 썸네일 경로 저장
		            boardVO.setThumbnail(uploadDir + "/" + saveName); // **board.thumbnail 컬럼**
	
		        } catch (IOException e) {
		            e.printStackTrace();
		            // 필요시: 오류 안내
		        }
		    }
		
		    log.info("작성자 포함 게시글: {}", boardVO);
		    boardService.insert(boardVO);
		
		    // 한글 카테고리 URL 인코딩 처리 (작성 후 이동용)
		    String encodedCategory = URLEncoder.encode(boardVO.getCategory(), "UTF-8");
		
		    return "redirect:/board/board.do?category=" + encodedCategory;
		}
	
	
	//   수정페이지 열기
	   @GetMapping("/board/edition.do")
	   public String updateBoardView(@RequestParam("boardId") int boardId, Model model) {
	       BoardVO boardVO = boardService.selectBoard(boardId);
	       if (boardVO == null) {
	           throw new IllegalArgumentException("해당 게시글이 존재하지 않습니다.");
	       }
	       model.addAttribute("boardVO", boardVO);
	       return "board/boardupdate";
	   }
		// 수정: 버튼 클릭시 실행
		// 7/7일 수정 후 원래 카테고리로 돌아가기, 리퀘스트팜,리턴 추가 (민중)
		@PostMapping("/board/edit.do")
		public String update(@ModelAttribute BoardVO boardVO,
		                     @RequestParam(required = false) String searchKeyword,
		                     @RequestParam(required = false, defaultValue = "1") int pageIndex) throws UnsupportedEncodingException {
	
		    // ✅ 카테고리 누락 방지
		    if (boardVO.getCategory() == null || boardVO.getCategory().isBlank()) {
		        throw new IllegalArgumentException("카테고리는 필수입니다.");
		    }
	
		    boardService.update(boardVO);
	
		    String encodedCategory = URLEncoder.encode(boardVO.getCategory(), "UTF-8");
		    return "redirect:/board/board.do?category=" + encodedCategory
		         + "&searchKeyword=" + searchKeyword
		         + "&pageIndex=" + pageIndex;
		}
	
		// 삭제
		// 7/7 삭제 후 원래 카테고리로 돌아가기,  리퀘스트팜,리턴 추가 (민중)
		@PostMapping("/board/delete.do")
		public String delete(@ModelAttribute BoardVO boardVO,
		                     @RequestParam(required = false) String searchKeyword,
		                     @RequestParam(required = false, defaultValue = "1") int pageIndex) throws UnsupportedEncodingException {
	
		    // ✅ 카테고리 누락 방지
		    if (boardVO.getCategory() == null || boardVO.getCategory().isBlank()) {
		        throw new IllegalArgumentException("카테고리는 필수입니다.");
		    }
	
		    boardService.delete(boardVO);
	
		    String encodedCategory = URLEncoder.encode(boardVO.getCategory(), "UTF-8");
		    return "redirect:/board/board.do?category=" + encodedCategory
		         + "&searchKeyword=" + searchKeyword
		         + "&pageIndex=" + pageIndex;
		}
		
	//	상세조회: 읽기 전용 페이지 (조회만 가능)
		@GetMapping("/board/view.do")
		public String view(@RequestParam("boardId") int boardId, Model model, HttpSession session) {
		    // 조회수 증가
		    try {
		        boardService.increaseViewCount(boardId);
		    } catch (Exception e) {
		        log.error("조회수 증가 실패: ", e);
		       
		    }
		    List<ReviewVO> reviews = boardService.selectReviewList(boardId);
	        model.addAttribute("reviews", reviews);
	
		    // 닉네임 포함 상세 게시글 조회
		    BoardVO board = boardService.selectBoardDetail(boardId); // ✅ 변경
		    if (board == null) {
		        throw new RuntimeException("해당 게시글을 찾을 수 없습니다.");
		    }
		    // ✅ 세션에서 로그인 정보 꺼내서 모델에 추가
		    MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
		    model.addAttribute("loginUser", loginUser);
		    
		    model.addAttribute("board", board);
		    return "board/boardview"; // 읽기 전용 JSP로 이동
		}
		@PostMapping("/board/review/add.do")
		   public String addReview(@ModelAttribute ReviewVO reviewVO, HttpSession session) {
		       MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
		       if (loginUser == null) {
		           return "redirect:/member/login.do";
		       }
		       reviewVO.setWriterIdx(loginUser.getMemberIdx().intValue());
		       boardService.insertReview(reviewVO);
		       // 댓글 작성 후 다시 상세페이지로 이동
		       return "redirect:/board/view.do?boardId=" + reviewVO.getBoardId();
		   }
		// 댓글 수정
		@PostMapping("/board/review/edit.do")    // ⬅️ 여기!
		public String editReview(
		        @RequestParam int reviewId,
		        @RequestParam int boardId,
		        @RequestParam String content,
		        HttpSession session
		) {
		    MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
		    if (loginUser == null) {
		        return "redirect:/member/login.do";
		    }
		    boardService.editReview(reviewId, loginUser.getMemberIdx().intValue(), content);
		    return "redirect:/board/view.do?boardId=" + boardId;
		}
	
		// 댓글 삭제
		@PostMapping("/board/review/delete.do")  // ⬅️ 여기!
		public String deleteReview(@RequestParam int reviewId, @RequestParam int boardId, HttpSession session) {
		    MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
		    if (loginUser == null) {
		        return "redirect:/member/login.do";
		    }
		    // 서비스에서 로그인 유저가 작성한 댓글만 삭제
		    boardService.deleteReview(reviewId, loginUser.getMemberIdx().intValue());
		    // 삭제 후 해당 게시글 상세로 이동
		    return "redirect:/board/view.do?boardId=" + boardId;
		}
	
		}
	

