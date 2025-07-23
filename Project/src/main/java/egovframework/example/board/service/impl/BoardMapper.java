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
   
   void adminDeleteBoard(int boardId); // 관리자 delete 메소드
   
   public int increaseViewCount(int boardId); // 조회수 증가
   
   List<ReviewVO> selectReviewList(int boardId);
   
   int insertReview(ReviewVO reviewVO);
   
   void editReview(@Param("reviewId") int reviewId, @Param("memberIdx") Long memberIdx, @Param("content") String content);
   
   void deleteReview(@Param("reviewId") int reviewId, @Param("memberIdx") Long memberIdx);
   
   int deleteAllReviewsByBoardId(int boardId); // 모든 댓글 삭제용(게시글삭제에 필요)

   List<BoardVO> selectBestPosts();
   /* 카테고리별 인기 게시글  */
   List<BoardVO> selectBestPostsByCategory(@Param("category") String category);
   
   void updateThumbnail(BoardVO boardVO);
   
   List<BoardVO> selectByMemberIdx(@Param("memberIdx") Long memberIdx); // 탈퇴할떄 모든게시글 삭제를 위한 회원번호로 검색
}
