package egovframework.example.mypage.service;

import java.util.Date;

import lombok.Data;

@Data
public class MypageMyPostVO {
    private Long boardId;      // 게시글 번호
    private String title;      // 게시글 제목
    private String category;   // 게시글 카테고리
    private Date writeDate;    // 게시글 작성일
    private String thumbnail;  // 썸네일 이미지
    private int viewCount;     // 조회수 필드 추가
    private int likeCount; // 게시판에 좋아요를 보여주기위해 추가 25년 7월 9일 : 강승태

    
    // 필요시 작성자 닉네임 등 추가 가능
}

