package egovframework.example.board.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

import egovframework.example.board.service.BoardVO;
import egovframework.example.board.service.ReviewVO;
import egovframework.example.common.Criteria;

@Mapper
public interface BoardMapper {
   public List<?> selectBoardList(Criteria criteria);

   public int selectBoardListTotCnt(Criteria criteria); // 총 개수 구하기

   public int insert(BoardVO boardVO); // insert

   public BoardVO selectBoard(int boardId); // 수정페이지로딩
   
   public BoardVO selectBoardDetail(int boardId); // 공개 상세조회용

   public int update(BoardVO boardVO); // update 메소드

   public int delete(BoardVO boardVO); // delete 메소드
   
   public int increaseViewCount(int boardId); // 조회수 증가
   
   List<ReviewVO> selectReviewList(int boardId);
   int insertReview(ReviewVO reviewVO);
   void editReview(@Param("reviewId") int reviewId, @Param("memberIdx") int memberIdx, @Param("content") String content);
   void deleteReview(@Param("reviewId") int reviewId, @Param("memberIdx") int memberIdx);
   List<BoardVO> selectBestPosts();
}
