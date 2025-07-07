/**
 * 
 */
package egovframework.example.mypage.service;
import lombok.Data;
import java.util.Date;

@Data
public class MypageLikeVO {
    private Long boardId;      // 게시글 번호
    private String title;      // 게시글 제목
    private String category;   // 게시글 카테고리
    private Date writeDate;    // 게시글 작성일
    private String thumbnail;  // 썸네일 이미지
    private String writerName; // 작성자 이름
    private Date likeDate;     // 좋아요 누른 날짜
    private int viewCount;		// 
}