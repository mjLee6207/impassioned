package egovframework.example.mypage.service;

import java.util.List;

public interface MypageMyPostService {
	List<MypageMyPostVO> selectMyPosts(Long memberIdx);
}