/**
 * 
 */
package egovframework.example.mypage.service;

import java.util.List;

/**
 * @author user
 *
 */
public interface MypageLikeService {
	 List<MypageLikeVO> selectLikedPosts(Long memberIdx);
}
