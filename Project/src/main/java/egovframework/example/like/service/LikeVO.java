package egovframework.example.like.service;

import java.util.Date;

import egovframework.example.common.Criteria;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = false)
public class LikeVO extends Criteria {
    private int likeId;      // PK (시퀀스)
    // null값을 위해 integer 사용
    //    private int boardId;     // 게시글 FK
    private Long memberIdx;   // 회원 FK
    private Date likeDate;   // 등록일
    
    // 조회전용 계산 결과용 
    private int likeCount= 1;
    
    private String likeType ="BOARD";
    private Integer boardId;         // 게시글 FK (nullable)
    private String recipeId;
}
