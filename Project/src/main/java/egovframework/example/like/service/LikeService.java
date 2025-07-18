package egovframework.example.like.service;

import java.util.List;

import egovframework.example.common.Criteria;

public interface LikeService {

    // 전체 좋아요 리스트 및 개수
    List<?> selectLikeList(Criteria criteria);
    int selectLikeListTotCnt(Criteria criteria);

    // 좋아요 수 조회 (게시글 or 레시피 구분)
    int countLikes(LikeVO vo);

    // 좋아요 여부 확인
    boolean existsLike(LikeVO likevo);      // 게시판 전용
    boolean existsRecipeLike(LikeVO likevo); // 레시피 좋아요 여부 확인용
    boolean checkLike(LikeVO likeVO);       // 공통 중복 확인용

    // 좋아요 등록/삭제
    void addLike(LikeVO likevo);
    void removeLike(LikeVO likevo);

    // 좋아요 수 증가/감소 (게시글 or 레시피 자동 분기)
    void increaseLikeCount(LikeVO vo);
    void decreaseLikeCount(LikeVO vo);

    // 특정 게시글 또는 레시피의 좋아요 리스트
    List<LikeVO> selectLikeListByTarget(LikeVO vo);

    // 삭제 시 좋아요 전체 삭제
    void deleteAllByTarget(LikeVO vo);      // 게시글 or 레시피 모두 대응
    void deleteAllByBoardId(int boardId);   // 게시판 전용
}
