/**
 * 
 */
package egovframework.example.mypage.service;
import lombok.Data;
import java.util.Date;

@Data
public class MypageLikeVO {
	  // 공통 구분자
    private String likeType;      // "BOARD" 또는 "RECIPE"

    // 게시글 좋아요용 (BOARD)
    private Long boardId;         // BOARD_ID
    private String title;         // 게시글 제목
    private String category;      // 게시글 카테고리
    private String thumbnail;     // 게시글 썸네일
    private Date writeDate;       // 게시글 작성일
    private String writerName;    // 게시글 작성자명 (MEMBER에서 조인)
    private int viewCount;        // 게시글 조회수
    private int likeCount;        // 게시글 좋아요수

    // 레시피 좋아요용 (API_RECIPE)
    private String recipeId;         // 레시피 PK (varchar)
    private String recipeTitle;      // 레시피 제목
    private String recipeCategory;   // 레시피 카테고리
    private String recipeThumbnail;  // 레시피 썸네일
    private int recipeLikeCount;     // 레시피 좋아요수

    // 좋아요 날짜 (공통)
    private Date likeDate;           // 내가 좋아요 누른 날짜
}