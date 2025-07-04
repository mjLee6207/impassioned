/**
 * 
 */
package egovframework.example.mypage.service;

import java.util.List;

/**
 * @author user
 *
 */
public interface MypageService {
	 List<MypageLikeVO> selectLikedPosts(Long memberIdx);
	 List<MypageMyPostVO> selectMyPosts(Long memberIdx);
}
