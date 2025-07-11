package egovframework.example.file.service;

import java.util.List;

public interface FileService {
    void insertFile(FileVO fileVO);
    FileVO getFile(Long fileId);
    List<FileVO> getFilesByBoardId(Long boardId);
    void deleteFile(Long fileId);       
    void updateFile(FileVO fileVO);  
    FileVO getProfileFileByMemberId(Long memberId); // 👈 회원 프로필 1개만 조회
    
    // 7/11 민중 게시글삭제를위한 달려있는 모든 파일 삭제 기능
    void deleteAllByTargetIdAndType(int targetId, String useType); 
 }

