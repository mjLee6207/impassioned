package egovframework.example.mypage.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import egovframework.example.mypage.service.MypageMyPostService;
import egovframework.example.mypage.service.MypageMyPostVO;

@Service("mypageMyPostService")
public class MypageMyPostServiceImpl implements MypageMyPostService {

    @Autowired
    private MypageMyPostMapper mypageMyPostMapper;

    @Override
    public List<MypageMyPostVO> selectMyPosts(Long memberIdx) {
        return mypageMyPostMapper.selectMyPosts(memberIdx);
    }
}