package egovframework.example.mypage.service.impl;

import egovframework.example.mypage.service.MypageService;
import egovframework.example.mypage.service.MypageLikeVO;
import egovframework.example.mypage.service.MypageMyPostVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service("mypageLikeService")
public class MypageServiceImpl implements MypageService {

    @Autowired
    private MypageMapper mypageLikeMapper;

	@Override
	public List<MypageLikeVO> selectLikedPosts(Long memberIdx) {
		// TODO Auto-generated method stub
		return mypageLikeMapper.selectLikedPosts(memberIdx);
	}
	
    @Override
    public List<MypageMyPostVO> selectMyPosts(Long memberIdx) {
        return mypageLikeMapper.selectMyPosts(memberIdx);
    }
}

    
	


