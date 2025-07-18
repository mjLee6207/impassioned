package egovframework.example.like.service;

import java.util.List;

import egovframework.example.common.Criteria;

public interface LikeService {
    List<?> selectLikeList(Criteria criteria); // 전체 조회
    int selectLikeListTotCnt(Criteria criteria); // 총 개수 구하기

    // int countLikes(int boardId);; → 기존 게시판 전용
    // 7월 18일 게시글 + 레시피 분기형으로 통합
    int countLikes(LikeVO vo);

    boolean existsLike(LikeVO likevo);
    void addLike(LikeVO likevo);
    void removeLike(LikeVO likevo);
    boolean checkLike(LikeVO likeVO);

    // void increaseLikeCount(int boardId); // 7월 11일 게시판용
    // void decreaseLikeCount(int boardId); // 7월 11일 게시판용
    // 7월 18일 게시글 + 레시피 모두를 위한 통합
    void increaseLikeCount(LikeVO vo); // likeCount + 1
    void decreaseLikeCount(LikeVO vo); // likeCount - 1
    // (선택) 리스트가 필요하면:
//    List<LikeVO> selectLikeListByBoardId(@Param("boardId") int boardId);
    // 좋아요 리스트 조회
    // List<LikeVO> selectLikeListByBoardId(int boardId); → 기존 게시판 전용
    // 7월 18일 게시글 + 레시피 모두 대응하도록 수정
    List<LikeVO> selectLikeListByTarget(LikeVO vo);
    
    
    // 7/11 민중 게시글삭제를위한 달려있는 모든 좋아요 삭제 기능
    // 7월 18일 게시글 + 레시피 삭제를위한 수정 
    // void deleteAllByBoardId(int boardId); : 기존코드
    void deleteAllByTarget(LikeVO vo);
    void deleteAllByBoardId(int boardId);  // 게시판 전용 삭제
    
}
