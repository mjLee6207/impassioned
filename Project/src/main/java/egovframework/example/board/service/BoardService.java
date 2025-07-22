package egovframework.example.board.service;

import java.util.List;

import egovframework.example.common.Criteria;

public interface BoardService {
   List<?> selectBoardList(Criteria criteria);

   int selectBoardListTotCnt(Criteria criteria); // 총 개수 구하기

   int insert(BoardVO boardVO); // insert

   BoardVO selectBoard(int boardId); // 수정페이지 조회
   
   BoardVO selectBoardDetail(int boardId); // 공개 상세조회용

   int update(BoardVO boardVO); // update 메소드

   int delete(BoardVO boardVO); // delete 메소드
   
   void adminDeleteBoard(int boardId) throws Exception;  // 관리자 삭제
   
   void increaseViewCount(int boardId) throws Exception; // 조회수 증가
   
// 댓글 리스트
List<ReviewVO> selectReviewList(int boardId);
// 댓글 등록
int insertReview(ReviewVO reviewVO);
// 댓글 수정
void editReview(int reviewId, Long memberIdx, String content);
// 댓글 삭제
void deleteReview(int reviewId, Long memberIdx);
List<BoardVO> selectBestPosts();
List<BoardVO> selectBestPostsByCategory(String category);
void updateThumbnail(BoardVO boardVO); // 썸네일 경로만 수정
}
