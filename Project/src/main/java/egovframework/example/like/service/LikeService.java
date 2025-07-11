package egovframework.example.like.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import egovframework.example.common.Criteria;

public interface LikeService {
	List<?> selectLikeList(Criteria criteria); // 전체 조회
	int selectLikeListTotCnt(Criteria criteria); // 총 개수 구하기
	int countLikes(int boardId);;
    boolean existsLike(LikeVO likevo);
    void addLike(LikeVO likevo);
    void removeLike(LikeVO likevo);
    boolean checkLike(LikeVO likeVO) ;
    void increaseLikeCount(int boardId); // 7월 11일 좋아요 카운트 DB에볼수있게할려고 추가: 강승태
    void decreaseLikeCount(int boardId); // 7월 11일 좋아요 카운트 DB에볼수있게할려고 추가: 강승태
    // (선택) 리스트가 필요하면:
    List<LikeVO> selectLikeListByBoardId(@Param("boardId") int boardId);
    
    // 7/11 민중 게시글삭제를위한 달려있는 모든 좋아요 삭제 기능
    void deleteAllByBoardId(int boardId);

}
