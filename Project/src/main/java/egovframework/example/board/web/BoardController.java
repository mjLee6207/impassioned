package egovframework.example.board.web;

import java.util.List;

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
import egovframework.example.common.Criteria;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
public class BoardController {
	@Autowired
	private BoardService boardService;

//	전체조회
	@GetMapping("/board/board.do")
	public String name(@ModelAttribute Criteria criteria, Model model) {
//		1) 등차자동계산 클래스: PaginationInfo
//		   - 필요정보: (1) 현재페이지번호(pageIndex),(2) 보일 개수(pageUnit): 3
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(criteria.getPageIndex());
		paginationInfo.setRecordCountPerPage(criteria.getPageUnit());
//		등차를 자동 계산: firstRecordIndex 필드에 있음
		criteria.setFirstIndex(paginationInfo.getFirstRecordIndex());

//		전체조회 서비스 메소드 실행
		List<?> boards = boardService.selectBoardList(criteria);
		log.info("테스트 : " + boards);
		model.addAttribute("boards", boards);

//		페이지 번호 그리기: 페이지 플러그인(전체테이블 행 개수 필요함)
		int totCnt = boardService.selectBoardListTotCnt(criteria);
		paginationInfo.setTotalRecordCount(totCnt);
		log.info("테스트2 : " + totCnt);
//		페이지 모든 정보: paginationInfo
		model.addAttribute("paginationInfo", paginationInfo);

		return "board/boardlist";
	}

//	추가 페이지 열기
	@GetMapping("/board/addition.do")
	public String createBoardView() {
		return "board/add_board";
	}
		
	// 글 작성 폼 화면 보여주기
	@GetMapping("/board/add.do")
	public String showAddForm(Model model) {
	    model.addAttribute("boardVO", new BoardVO()); // 빈 폼 바인딩
	    return "board/add_board"; // 글 작성 폼 JSP or HTML 경로
	}

//	insert : 저장 버튼 클릭시
	@PostMapping("/board/add.do")
	public String insert(@ModelAttribute BoardVO boardVO) {
//		boardVO 내용 확인
		log.info("테스트3 :" + boardVO);
//		서비스의 insert 실행
		boardService.insert(boardVO);

		return "redirect:/board/board.do";
	}

//	수정페이지 열기(상세조회)
	@GetMapping("/board/edition.do")
	public String updateBoardView(@RequestParam int boardId, Model model) {
//		서비스의 상세조회
		BoardVO boardVO = boardService.selectBoard(boardId);
		model.addAttribute("boardVO", boardVO);
		return "board/update_board";
	}

	
//	수정: 버튼 클릭시 실행
	@PostMapping("/board/edit.do")
	public String update(@RequestParam int boardId, @ModelAttribute BoardVO boardVO) {
//		서비스의 수정 실행
		boardService.update(boardVO);
		return "redirect:/board/board.do";
	}

//	삭제
	@PostMapping("/board/delete.do")
	public String delete(@ModelAttribute BoardVO boardVO) {
//		서비스의 삭제 실행
		boardService.delete(boardVO);
		return "redirect:/board/board.do";
	}
}
