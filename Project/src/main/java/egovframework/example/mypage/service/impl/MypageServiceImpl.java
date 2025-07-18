package egovframework.example.mypage.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import egovframework.example.common.Criteria;
import egovframework.example.mypage.service.MypageLikeVO;
import egovframework.example.mypage.service.MypageMyPostVO;
import egovframework.example.mypage.service.MypageService;

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

    @Override
    public List<?> selectMyBoardList(Criteria criteria, Long memberIdx) {
        return mypageLikeMapper.selectMyBoardList(criteria, memberIdx);
    }

    @Override
    public int selectMyBoardListTotCnt(Criteria criteria, Long memberIdx) {
        return mypageLikeMapper.selectMyBoardListTotCnt(criteria, memberIdx);
    }

    @Override
    public List<MypageLikeVO> selectMyLikeList(Criteria criteria, Long memberIdx, String likeType) {
        return mypageLikeMapper.selectMyLikeList(criteria, memberIdx, likeType);
    }

    @Override
    public int selectMyLikeListTotCnt(Criteria criteria, Long memberIdx, String likeType) {
        return mypageLikeMapper.selectMyLikeListTotCnt(criteria, memberIdx, likeType);
    }


}

    
	


