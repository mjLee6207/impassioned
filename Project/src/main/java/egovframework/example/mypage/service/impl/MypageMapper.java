package egovframework.example.mypage.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

import egovframework.example.common.Criteria;
import egovframework.example.mypage.service.MypageLikeVO;
import egovframework.example.mypage.service.MypageMyPostVO;
@Mapper
public interface MypageMapper {
    List<MypageLikeVO> selectLikedPosts(Long memberIdx);  // 내가 좋아요 한 글 
    List<MypageMyPostVO> selectMyPosts(Long memberIdx);   // 내가 작성한 글
 // 내가 쓴 글 (페이징/검색 포함)
    List<MypageMyPostVO> selectMyBoardList(@Param("criteria") Criteria criteria, @Param("memberIdx") Long memberIdx);
    int selectMyBoardListTotCnt(@Param("criteria") Criteria criteria, @Param("memberIdx") Long memberIdx);

 // 내가 좋아요한 글(레시피/게시글) - likeType 파라미터 추가!
    List<MypageLikeVO> selectMyLikeList(
        @Param("criteria") Criteria criteria,
        @Param("memberIdx") Long memberIdx,
        @Param("likeType") String likeType    
    );
    int selectMyLikeListTotCnt(
        @Param("criteria") Criteria criteria,
        @Param("memberIdx") Long memberIdx,
        @Param("likeType") String likeType   
    );
    
    
}
