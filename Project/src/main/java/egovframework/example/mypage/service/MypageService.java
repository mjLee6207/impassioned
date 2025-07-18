/**
 * 
 */
package egovframework.example.mypage.service;

import java.util.List;

import egovframework.example.common.Criteria;

/**
 * @author user
 *
 */
public interface MypageService {
	 List<MypageLikeVO> selectLikedPosts(Long memberIdx);  // 내가 좋아요 한 글 
	 List<MypageMyPostVO> selectMyPosts(Long memberIdx);   // 내가 작성한 글
//	 페이징처리
	// 내가 쓴 글 목록
	 List<?> selectMyBoardList(Criteria criteria, Long memberIdx);

	 // 내가 쓴 글 전체 개수
	 int selectMyBoardListTotCnt(Criteria criteria, Long memberIdx);

	// 좋아요한 글 목록 (likeType 추가)
	 List<MypageLikeVO> selectMyLikeList(Criteria criteria, Long memberIdx, String likeType);

	// 좋아요한 글 개수 (likeType 추가)
	int selectMyLikeListTotCnt(Criteria criteria, Long memberIdx, String likeType);

}
