package egovframework.example.board.service;

import java.util.List;

import egovframework.example.common.Criteria;

public interface BoardService {
	List<?> selectBoardList(Criteria criteria);

	int selectBoardListTotCnt(Criteria criteria); // 총 개수 구하기

	int insert(BoardVO boardVO); // insert

	BoardVO selectBoard(int boardId); // 상세조회

	int update(BoardVO boardVO); // update 메소드

	int delete(BoardVO boardVO); // delete 메소드
}
