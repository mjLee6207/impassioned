package egovframework.example.mypage.service.impl;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

import egovframework.example.mypage.service.MypageLikeVO;
import egovframework.example.mypage.service.MypageMyPostVO;
@Mapper
public interface MypageMapper {
    List<MypageLikeVO> selectLikedPosts(Long memberIdx);  // 내가 좋아요 한 글 
    List<MypageMyPostVO> selectMyPosts(Long memberIdx);   // 내가 작성한 글
}
