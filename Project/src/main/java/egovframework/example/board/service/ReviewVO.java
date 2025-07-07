/**
 * 
 */
package egovframework.example.board.service;

/**
 * @author user
 *
 */

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = false)
public class ReviewVO {
	private int reviewId;
    private int boardId;
    private int writerIdx;
    private String content;
    private String nickname;
    private Date writeDate;
}
