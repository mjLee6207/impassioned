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

    // (선택) 리스트가 필요하면:
    List<LikeVO> selectLikeListByBoardId(@Param("boardId") int boardId);

}
