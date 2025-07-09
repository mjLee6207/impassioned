package egovframework.example.file.service;

import java.sql.Date;
import lombok.Data;

@Data
public class FileVO {
    private Long fileId;           // 파일 아이디(PK)
    private String fileName;       // 업로드 파일명
    private String filePath;       // 저장 경로
    private String fileType;       // 확장자
    private String useType;        // 사용 목적 (예: 'BOARD')
    private Long useTargetId;      // 대상 게시글 번호 (BOARD_ID 등)
    private String usePosition;    // 위치 구분
    private Date uploadDate;       // 업로드 일자
    private Long uploaderId;       // 업로더 회원 번호
    private byte[] fileData;       // 파일 바이너리(BLOB) ← **이 컬럼이 중요**
}

