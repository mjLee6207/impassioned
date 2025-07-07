package egovframework.example.board.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

//   전체조회
   @GetMapping("/board/board.do")
   public String name(@ModelAttribute Criteria criteria, Model model) {
       criteria.setPageUnit(10);
//      1) 등차자동계산 클래스: PaginationInfo
//         - 필요정보: (1) 현재페이지번호(pageIndex),(2) 보일 개수(pageUnit): 3
      PaginationInfo paginationInfo = new PaginationInfo();
      paginationInfo.setCurrentPageNo(criteria.getPageIndex());
      paginationInfo.setRecordCountPerPage(criteria.getPageUnit());
//      등차를 자동 계산: firstRecordIndex 필드에 있음
      criteria.setFirstIndex(paginationInfo.getFirstRecordIndex());

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

   // insert : 저장 버튼 클릭시
   @PostMapping("/board/add.do")
   public String insert(@ModelAttribute BoardVO boardVO, HttpSession session) {
       MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
       if (loginUser == null) {
           return "redirect:/member/login.do"; // 비로그인 시 로그인 페이지로
       }
       // 서버에서 로그인된 사용자의 인덱스 강제 설정
       boardVO.setWriterIdx(loginUser.getMemberIdx().intValue());

       log.info("작성자 포함 게시글: {}", boardVO);
       boardService.insert(boardVO);

       return "redirect:/board/board.do";
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

   
//   수정: 버튼 클릭시 실행
   @PostMapping("/board/edit.do")
   public String update(@ModelAttribute BoardVO boardVO) {
//      서비스의 수정 실행
      boardService.update(boardVO);
      return "redirect:/board/board.do";
   }

//   삭제
   @PostMapping("/board/delete.do")
   public String delete(@ModelAttribute BoardVO boardVO) {
//      서비스의 삭제 실행
      boardService.delete(boardVO);
      return "redirect:/board/board.do";
   }
   
   //   상세조회: 읽기 전용 페이지 (조회만 가능)
   @GetMapping("/board/view.do")
   public String view(@RequestParam("boardId") int boardId, Model model
         , HttpSession session) {
       // 조회수 증가
       try {
           boardService.increaseViewCount(boardId);
       } catch (Exception e) {
           log.error("조회수 증가 실패: ", e);
       }
  
       // 닉네임 포함 상세 게시글 조회
       BoardVO board = boardService.selectBoardDetail(boardId); // ✅ 변경
       if (board == null) {
           throw new RuntimeException("해당 게시글을 찾을 수 없습니다.");
       }
       // ✅ 세션에서 로그인 정보 꺼내서 모델에 추가
       MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
       model.addAttribute("loginUser", loginUser);
       
       model.addAttribute("board", board);
       List<ReviewVO> reviews = boardService.selectReviewList(boardId);
       model.addAttribute("reviews", reviews);
       return "board/boardview"; // 읽기 전용 JSP로 이동
    // BoardService에서 댓글 리스트 조회 메소드 필요
       
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
}
