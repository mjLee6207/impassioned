package egovframework.example.file.service;

import java.util.List;

public interface FileService {
    void insertFile(FileVO fileVO); // 파일 등록 (BLOB 포함)
    FileVO getFile(Long fileId); // 파일 1건 조회 (PK)
    List<FileVO> getFilesByBoardId(Long boardId); // 게시글별 첨부파일 목록 (예: BOARD_ID로)
    void deleteFile(Long fileId);    //  파일 삭제   
    void updateFile(FileVO fileVO);  //  파일 수정
    FileVO getProfileFileByMemberId(Long memberId); // 👈 회원 프로필 조회용
    
    // 7/11 민중 게시글삭제를위한 달려있는 모든 파일 삭제 기능
    void deleteAllByTargetIdAndType(Long targetId, String useType); 
 }

