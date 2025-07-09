package egovframework.example.board.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import egovframework.example.board.service.BoardService;
import egovframework.example.board.service.BoardVO;
import egovframework.example.board.service.ReviewVO;
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

@Override
public List<ReviewVO> selectReviewList(int boardId) {
	// TODO Auto-generated method stub
	return boardMapper.selectReviewList(boardId);
}

@Override
public int insertReview(ReviewVO reviewVO) {
	// TODO Auto-generated method stub
	return boardMapper.insertReview(reviewVO);
}

@Override
public void editReview(int reviewId, int memberIdx, String content) {
	// TODO Auto-generated method stub
	 boardMapper.editReview(reviewId, memberIdx, content);
}

@Override
public void deleteReview(int reviewId, int memberIdx) {
	// TODO Auto-generated method stub
	boardMapper.deleteReview(reviewId, memberIdx);
}

@Override
public List<BoardVO> selectBestPosts() {
	// TODO Auto-generated method stub
	return boardMapper.selectBestPosts();
}

@Override
public void updateThumbnail(BoardVO boardVO) {
    boardMapper.updateThumbnail(boardVO);
   
}
}
