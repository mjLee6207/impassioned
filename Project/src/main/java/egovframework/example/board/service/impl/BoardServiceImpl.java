package egovframework.example.board.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import egovframework.example.board.service.BoardService;
import egovframework.example.board.service.BoardVO;
import egovframework.example.board.service.ReviewVO;
import egovframework.example.common.Criteria;
import egovframework.example.file.service.FileService;
import egovframework.example.like.service.LikeService;

@Service
public class BoardServiceImpl implements BoardService {
   @Autowired
   private BoardMapper boardMapper;
   
   @Autowired
   private FileService fileService;

   @Autowired
   private LikeService likeService;
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
       int boardId = boardVO.getBoardId();

       // 1. 댓글 전체 삭제
       boardMapper.deleteAllReviewsByBoardId(boardId);  // BoardMapper에 구현 필요

       // 2. 좋아요 전체 삭제
       likeService.deleteAllByBoardId(boardId);  // LikeServiceImpl에 구현 필요

       // 3. 첨부파일 전체 삭제
       fileService.deleteAllByTargetIdAndType((long) boardId, "board");  // FileServiceImpl에 구현 필요

       // 4. 게시글 삭제
       return boardMapper.delete(boardVO);
   }
   
   @Override
   public void adminDeleteBoard(int boardId) throws Exception {
       // 1. 댓글 전체 삭제
       boardMapper.deleteAllReviewsByBoardId(boardId);  // BoardMapper에 구현 필요

       // 2. 좋아요 전체 삭제
       likeService.deleteAllByBoardId(boardId);  // LikeServiceImpl에 구현 필요

       // 3. 첨부파일 전체 삭제
       fileService.deleteAllByTargetIdAndType((long) boardId, "board");  // FileServiceImpl에 구현 필요

       // 4. 게시글 삭제
       boardMapper.adminDeleteBoard(boardId); 
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
public void editReview(int reviewId, Long memberIdx, String content) {
	// TODO Auto-generated method stub
	 boardMapper.editReview(reviewId, memberIdx, content);
}

@Override
public void deleteReview(int reviewId, Long memberIdx) {
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
@Override
public List<BoardVO> selectBestPostsByCategory(String category) {
    return boardMapper.selectBestPostsByCategory(category);
}
}
