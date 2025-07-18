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
    
    List<LikeVO> selectLikeListByTarget(LikeVO vo);


    // 좋아요 수 조회 (게시판/레시피)
    int countLikes(LikeVO vo);

    // 좋아요 여부 확인
    int existsLike(LikeVO vo);
    int checkLike(LikeVO vo);

    // 좋아요 등록/삭제
    void insertLike(LikeVO vo);
    void deleteLike(LikeVO vo);

    // 좋아요 수 증가/감소
    void increaseLikeCount(LikeVO vo);
    void decreaseLikeCount(LikeVO vo);
    
    // 7/11 민중 게시글삭제를위한 달려있는 모든 좋아요 삭제 기능
    // void deleteAllByBoardId(int boardId);
    
//  레시피게시판 좋아요 기능 추가 -> 기준을 보드에서 두개다로할수있게
    void deleteAllByTarget(LikeVO vo);
}
