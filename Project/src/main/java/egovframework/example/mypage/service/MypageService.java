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
	 List<MypageLikeVO> selectLikedPosts(Long memberIdx);  // 내가 좋아요 한 글 
	 List<MypageMyPostVO> selectMyPosts(Long memberIdx);   // 내가 작성한 글
}
