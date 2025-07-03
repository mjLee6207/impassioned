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

	private int boardId; // 기본키, 시퀀스 값
//	private String boardType; // 게시판 유형
	private String category; // 카테고리
	private String title; // 제목
	private String content; // 내용
	private String thumbnail; // 썸네일 이미지 경로/URL
//	private String cookTime; // 조리 시간
//	private String difficulty; // 난이도
//	private int servings; // 인분 수
	private Date writeDate; // 작성일
	private int writerIdx; // 작성자 회원 번호
}
