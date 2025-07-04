package egovframework.example.mypage.service.impl;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

import egovframework.example.mypage.service.MypageMyPostVO;

@Mapper
public interface MypageMyPostMapper {
    List<MypageMyPostVO> selectMyPosts(Long memberIdx);
}
