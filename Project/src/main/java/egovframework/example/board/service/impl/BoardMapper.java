package egovframework.example.board.service.impl;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

import egovframework.example.board.service.BoardVO;
import egovframework.example.common.Criteria;

@Mapper
public interface BoardMapper {
	public List<?> selectBoardList(Criteria criteria);

	public int selectBoardListTotCnt(Criteria criteria); // 총 개수 구하기

	public int insert(BoardVO boardVO); // insert

	public BoardVO selectBoard(int boardId); // 상세조회

	public int update(BoardVO boardVO); // update 메소드

	public int delete(BoardVO boardVO); // delete 메소드
}
