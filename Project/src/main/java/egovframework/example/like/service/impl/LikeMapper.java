package egovframework.example.like.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

import egovframework.example.common.Criteria;
import egovframework.example.like.service.LikeVO;

@Mapper
public interface LikeMapper {
    List<LikeVO> selectLikeList(Criteria criteria);
    int selectLikeListTotCnt(Criteria criteria);

    List<LikeVO> selectLikeListByBoardId(@Param("boardId") int boardId);

    int countLikes(@Param("boardId") int boardId);
    int existsLike(LikeVO likevo);
    int checkLike(LikeVO likevo);

    void insertLike(LikeVO likevo);
    void deleteLike(LikeVO likevo);
    
 // 좋아요 수 증가
    void increaseLikeCount(int boardId);

    // 좋아요 수 감소
    void decreaseLikeCount(int boardId);
    
    // 7/11 민중 게시글삭제를위한 달려있는 모든 좋아요 삭제 기능
    void deleteAllByBoardId(int boardId);
}
