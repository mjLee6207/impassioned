package egovframework.example.board.web;	

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import egovframework.example.board.service.BoardService;
import egovframework.example.board.service.BoardVO;
import egovframework.example.board.service.ReviewVO;
import egovframework.example.common.Criteria;
import egovframework.example.file.service.FileService;
import egovframework.example.file.service.FileVO;
import egovframework.example.member.service.MemberVO;
import lombok.extern.log4j.Log4j2;
	
	@Log4j2
	@Controller
	public class BoardController {
	   @Autowired
	   private BoardService boardService;
	   @Autowired
	   private FileService fileService;
	
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
	   // ====== 7월15일 카테고리별 인기게시글 조회를 위해 추가 및 수정(장호) ======
	  	List<BoardVO> bestPosts;
	  	if (category == null || category.isEmpty()) {
	  	    // 전체 탭 - 기존 방식 (모든 카테고리 인기글)
	  	    bestPosts = boardService.selectBestPosts();
	  	} else {
	  	    // 카테고리별 인기글
	  	    bestPosts = boardService.selectBestPostsByCategory(category);
	  	}
	  	for (BoardVO board : bestPosts) {
	  	    if (board.getThumbnail() == null || board.getThumbnail().isEmpty()) {
	  	        board.setThumbnail("/img/no-image.png");
	  	    }
	  	}
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
				@RequestParam(value = "images", required = false) List<MultipartFile> images, 
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
	
		
		    log.info("작성자 포함 게시글: {}", boardVO);
		    boardService.insert(boardVO);
		    // ============ DB BLOB 저장 방식 =============
		 // ========== 다중 이미지 업로드 ==========
		    Long firstFileId = null; // 썸네일로 쓸 첫 번째 파일ID를 미리 선언

		    if (images != null) { // 이미지가 하나라도 있을 때만
		        for (MultipartFile image : images) {
		            String filename = image.getOriginalFilename();
		            if (!image.isEmpty() && filename != null && !filename.trim().isEmpty()) {
		            	// 업로드할 파일 정보를 FileVO에 저장
		            	FileVO fileVO = new FileVO();
		                fileVO.setFileName(filename); // 원본 파일명
		                fileVO.setFileType(image.getContentType()); // 파일 MIME 타입
		                fileVO.setUseType("BOARD"); // 게시판 용도
		                fileVO.setUseTargetId((long) boardVO.getBoardId());// 게시글 PK
		                fileVO.setUploaderId((long) loginUser.getMemberIdx());// 업로더(회원PK)
		                fileVO.setFilePath("/uploads/" + filename); // 가상 경로(DB용)
		                try {
		                    fileVO.setFileData(image.getBytes());
		                } catch (IOException e) {
		                    throw new RuntimeException("이미지 변환 실패", e);
		                }
		                fileService.insertFile(fileVO); // 실제 파일 DB 저장
		             // 썸네일용 첫 번째 이미지 fileId 저장
		                if (firstFileId == null) {
		                    firstFileId = fileVO.getFileId();
		                }
		            }
		            
		        }
		    }
		 // 첫 번째 파일이 있으면, 해당 파일로 썸네일 경로 지정
		    if (firstFileId != null) {
                boardVO.setThumbnail("/file/download.do?fileId=" + firstFileId);
                boardService.updateThumbnail(boardVO);
            }
		
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
	       // ✅ 기존 이미지 리스트 모델에 추가!
	       List<FileVO> fileList = fileService.getFilesByBoardId((long)boardId);
	       model.addAttribute("fileList", fileList);

	       return "board/boardupdate";
	   }
		// 수정: 버튼 클릭시 실행
		// 7/7일 수정 후 원래 카테고리로 돌아가기, 리퀘스트팜,리턴 추가 (민중)
		@PostMapping("/board/edit.do")
		public String update(@ModelAttribute BoardVO boardVO,
				 @RequestParam(value = "images", required = false) List<MultipartFile> images,  // 다중 이미지
				 @RequestParam(value = "deleteImageIds", required = false) String deleteImageIds,
		         @RequestParam(required = false) String searchKeyword,
		         @RequestParam(required = false, defaultValue = "1") int pageIndex,HttpSession session)
		                    		 throws UnsupportedEncodingException {
			
		    // ✅ 카테고리 누락 방지
		    if (boardVO.getCategory() == null || boardVO.getCategory().isBlank()) {
		        throw new IllegalArgumentException("카테고리는 필수입니다.");
		    }
		    // 기존 썸네일 값 기억
		    String prevThumbnail = boardVO.getThumbnail();

		    // 1. 삭제할 파일 id가 있다면 반복해서 삭제
		    if (deleteImageIds != null && !deleteImageIds.isEmpty()) {
		        for (String idStr : deleteImageIds.split(",")) {
		            try {
		                fileService.deleteFile(Long.parseLong(idStr)); // 파일 PK로 삭제
		            } catch (NumberFormatException e) {
		                // 무시 또는 로그
		            }
		        }
		    }

		    // 2. 새 이미지 업로드 (있다면 반복)
		    MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
		    Long firstFileId = null;
		    if (images != null) {
		        for (MultipartFile image : images) {
		            String filename = image.getOriginalFilename();
		            if (!image.isEmpty() && filename != null && !filename.trim().isEmpty()) {
		                FileVO fileVO = new FileVO();
		                fileVO.setFileName(filename);
		                fileVO.setFileType(image.getContentType());
		                fileVO.setUseType("BOARD");
		                fileVO.setUseTargetId((long) boardVO.getBoardId());
		                fileVO.setUploaderId(loginUser.getMemberIdx());
		                fileVO.setFilePath("/uploads/" + filename);
		                try {
		                    fileVO.setFileData(image.getBytes());
		                } catch (IOException e) {
		                    throw new RuntimeException("이미지 변환 실패", e);
		                }
		                fileService.insertFile(fileVO);
		                if (firstFileId == null) {
		                    firstFileId = fileVO.getFileId();
		                }
		            }
		        }
		    }

		    // 남아있는 파일 체크
		    List<FileVO> remainFiles = fileService.getFilesByBoardId((long) boardVO.getBoardId());

		    // 썸네일 처리! 👇
		    if (firstFileId != null) {
		    	// 새로 올린 이미지 있으면 그걸로 썸네일 지정
		        boardVO.setThumbnail("/file/download.do?fileId=" + firstFileId);
		        boardService.updateThumbnail(boardVO); // DB도 업데이트
		    } else if (!remainFiles.isEmpty()) {
		    	 // 새로 올린 이미지 없고, 기존 남은 이미지 있으면 첫 번째 파일로 썸네일 지정
		        boardVO.setThumbnail("/file/download.do?fileId=" + remainFiles.get(0).getFileId());
		        boardService.updateThumbnail(boardVO);
		    } else {
		    	 // 이미지가 하나도 없으면 기본 썸네일 경로로 지정
		        boardVO.setThumbnail("/img/no-image.png");
		        boardService.updateThumbnail(boardVO);
		    }

		    // 게시글 내용 등 기타 정보 수정
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
		
		// 관리자 삭제
		@PostMapping("/board/adminDelete.do")
		public String adminDeleteBoard(@RequestParam("boardId") int boardId, HttpSession session, RedirectAttributes rttr) throws Exception {
		    MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");

		    if (loginUser == null || !"ADMIN".equals(loginUser.getRole())) {
		        rttr.addFlashAttribute("message", "권한이 없습니다.");
		        return "redirect:/board/board.do";
		    }

		    boardService.adminDeleteBoard(boardId);
		    rttr.addFlashAttribute("message", "게시글이 삭제되었습니다.");
		    return "redirect:/board/board.do";
		}
		
//		상세조회: 읽기 전용 페이지 (조회만 가능)
		@GetMapping("/board/view.do")
		public String view(@RequestParam("boardId") int boardId, Model model, HttpSession session) {
		    // 조회수 증가
		    try {
		        boardService.increaseViewCount(boardId);
		    } catch (Exception e) {
		        log.error("조회수 증가 실패: ", e);
		    }
		 // 댓글(리뷰) 리스트 조회 후 모델에 추가
		    List<ReviewVO> reviews = boardService.selectReviewList(boardId);
		    model.addAttribute("reviews", reviews);

		    // 닉네임 포함 상세 게시글 조회
		    BoardVO board = boardService.selectBoardDetail(boardId);
		    if (board == null) {
		        throw new RuntimeException("해당 게시글을 찾을 수 없습니다.");
		    }

		    // ✅ 세션에서 로그인 정보 꺼내서 모델에 추가
		    MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
		    model.addAttribute("loginUser", loginUser);


		    // ✅ 썸네일 및 파일 처리
		    List<FileVO> fileList = fileService.getFilesByBoardId((long)boardId);
		    model.addAttribute("board", board);
		    model.addAttribute("fileList", fileList);
		    
		    return "board/boardview"; // 읽기 전용 JSP로 이동
		}
		// 댓글 작성
		@PostMapping("/board/review/add.do")
		   public String addReview(@ModelAttribute ReviewVO reviewVO, HttpSession session) {
		       MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
		       if (loginUser == null) {
		           return "redirect:/member/login.do";
		       }
		       reviewVO.setWriterIdx(loginUser.getMemberIdx().intValue()); // 로그인 유저 정보로 작성자 설정
		       boardService.insertReview(reviewVO); // 댓글 저장
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
		 // 댓글 ID, 로그인 유저, 수정 내용 전달 → 서비스에서 본인 댓글만 수정 가능하게 처리
		    boardService.editReview(reviewId, loginUser.getMemberIdx(), content);
		    return "redirect:/board/view.do?boardId=" + boardId;
		}
	
		// 댓글 삭제
		@PostMapping("/board/review/delete.do")  // ⬅️ 여기!
		public String deleteReview(@RequestParam int reviewId, @RequestParam int boardId, HttpSession session) {
		    MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
		    if (loginUser == null) {
		        return "redirect:/member/login.do";
		    }
		    // 본인 댓글만 삭제 가능
		    boardService.deleteReview(reviewId, loginUser.getMemberIdx());
		    // 삭제 후 해당 게시글 상세로 이동
		    return "redirect:/board/view.do?boardId=" + boardId;
		}
		
//		사이트가이드페이지
	    @GetMapping("/guide.do")  
	    public String showGuidePage() {
	        return "support/guide";  
	    }
//		QNA페이지
	    @GetMapping("/qna.do")  
	    public String showQnaPage() {
	        return "support/qna";  
	    }
	
	}