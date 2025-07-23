package egovframework.example.like.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

import egovframework.example.common.Criteria;
import egovframework.example.like.service.LikeVO;

@Mapper
public interface LikeMapper {

    // [공통] 전체 좋아요 리스트 및 개수
    List<LikeVO> selectLikeList(Criteria criteria);
    int selectLikeListTotCnt(Criteria criteria);

    // [게시판 전용] 특정 게시글의 좋아요 리스트
    List<LikeVO> selectLikeListByBoardId(@Param("boardId") int boardId);

    // [공통] 게시글/레시피 대상의 좋아요 리스트
    List<LikeVO> selectLikeListByTarget(LikeVO vo);

    // [공통] 좋아요 수 조회 (BOARD_ID 또는 RECIPE_ID 기준)
    int countLikes(LikeVO vo);

    // [게시판 전용] 좋아요 여부 확인
    int existsLike(LikeVO vo);

    // [공통] 중복 체크 용도
    int checkLike(LikeVO vo);

    // [게시판 전용] 좋아요 등록/삭제
    void insertLike(LikeVO vo);
    void deleteLike(LikeVO vo);

    // [레시피 전용] 좋아요 등록/삭제
    void insertRecipeLike(LikeVO vo);
    void deleteRecipeLike(LikeVO vo);

    // [게시판 전용] 좋아요 수 증가/감소
    void increaseLikeCount(LikeVO vo);
    void decreaseLikeCount(LikeVO vo);

    // [레시피 전용] 좋아요 수 증가/감소 → API_RECIPE 테이블 조작
    void increaseRecipeLikeCount(LikeVO vo);
    void decreaseRecipeLikeCount(LikeVO vo);

    // [공통] 게시글/레시피 삭제 시 연결된 좋아요 전체 삭제
    void deleteAllByTarget(LikeVO vo);

    // [레시피 전용] 좋아요 수 조회
    int countRecipeLikes(@Param("recipeId") String recipeId);

    // [레시피 전용] 좋아요 여부 확인
    int existsRecipeLike(LikeVO vo);

    // [게시판 전용] 게시글 삭제 시 좋아요 전체 삭제
    void deleteAllByBoardId(@Param("boardId") int boardId);

    // [레시피 전용] 레시피 삭제 시 좋아요 전체 삭제
    void deleteAllByRecipeId(@Param("recipeId") String recipeId);
    
    // 회원이 누른 좋아요 삭제(탈퇴)
    void deleteAllByMemberIdx(@Param("memberIdx") Long memberIdx);

}
