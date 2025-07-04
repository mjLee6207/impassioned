package egovframework.example.board.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import egovframework.example.board.service.BoardService;
import egovframework.example.board.service.BoardVO;
import egovframework.example.common.Criteria;

@Service
public class BoardServiceImpl implements BoardService {
	@Autowired
	private BoardMapper boardMapper;

	@Override
	public List<?> selectBoardList(Criteria criteria) {
		// TODO Auto-generated method stub
		return boardMapper.selectBoardList(criteria);
	}

	@Override
	public int selectBoardListTotCnt(Criteria criteria) {
		// TODO Auto-generated method stub
		return boardMapper.selectBoardListTotCnt(criteria);
	}

	@Override
	public int insert(BoardVO boardVO) {
		// TODO Auto-generated method stub
		return boardMapper.insert(boardVO);
	}

	@Override
	public BoardVO selectBoard(int boardId) {
		// TODO Auto-generated method stub
		return boardMapper.selectBoard(boardId);
	}

	@Override
	public int update(BoardVO boardVO) {
		// TODO Auto-generated method stub
		return boardMapper.update(boardVO);
	}

	@Override
	public int delete(BoardVO boardVO) {
		// TODO Auto-generated method stub
		return boardMapper.delete(boardVO);
	}

	@Override
	public void increaseViewCount(int boardId) throws Exception {
		// TODO Auto-generated method stub
		boardMapper.increaseViewCount(boardId);
	}

	@Override
	public BoardVO selectBoardDetail(int boardId) {
		// TODO Auto-generated method stub
		return boardMapper.selectBoardDetail(boardId);
	}
}
