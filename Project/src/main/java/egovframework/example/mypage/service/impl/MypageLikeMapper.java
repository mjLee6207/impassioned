package egovframework.example.mypage.service.impl;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

import egovframework.example.mypage.service.MypageLikeVO;
@Mapper
public interface MypageLikeMapper {
    List<MypageLikeVO> selectLikedPosts(Long memberIdx);
}
