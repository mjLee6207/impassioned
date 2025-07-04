package egovframework.example.mypage.service.impl;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

import egovframework.example.mypage.service.MypageLikeVO;
import egovframework.example.mypage.service.MypageMyPostVO;
@Mapper
public interface MypageMapper {
    List<MypageLikeVO> selectLikedPosts(Long memberIdx);
    List<MypageMyPostVO> selectMyPosts(Long memberIdx);
}
