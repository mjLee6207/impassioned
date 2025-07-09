package egovframework.example.board.service;

import java.sql.Date;

import egovframework.example.common.Criteria;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 게시판 테이블의 정보를 임시 저장하는 VO 클래스
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = false)
public class BoardVO extends Criteria {

   private int boardId;            // 기본키, 시퀀스 값
   private String category;        // 카테고리
   private String title;           // 제목
   private String prepare;         // 재료준비
   private String content;         // 내용
   private String thumbnail;       // 썸네일 이미지 경로/URL
   private Date writeDate;         // 작성일
   private int writerIdx;          // 작성자 회원 번호
   private int viewCount;          // 조회수
   private String nickname;        // 추가: 작성자 닉네임       // 
   private String searchKeyword;   // 카테고리별 검색을 위해 7월 8일추가: 강승태
   private int likeCount; // 게시판쪽 게시글 좋아요수 표시를 위해 추가 7월 9일: 강승태
}
