package egovframework.example.mypage.service.impl;

import egovframework.example.mypage.service.MypageLikeService;
import egovframework.example.mypage.service.MypageLikeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service("mypageLikeService")
public class MypageLikeServiceImpl implements MypageLikeService {

    @Autowired
    private MypageLikeMapper mypageLikeMapper;

	@Override
	public List<MypageLikeVO> selectLikedPosts(Long memberIdx) {
		// TODO Auto-generated method stub
		return mypageLikeMapper.selectLikedPosts(memberIdx);
	}
}

    
	


