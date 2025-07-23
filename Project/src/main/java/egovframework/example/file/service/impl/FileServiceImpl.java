package egovframework.example.file.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import egovframework.example.file.service.FileService;
import egovframework.example.file.service.FileVO;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    FileMapper fileMapper;
 // 파일 등록 (BLOB 포함)
    @Override
    public void insertFile(FileVO fileVO) {
        fileMapper.insertFile(fileVO);
    }
 // 파일 1건 조회 (PK)
    @Override
    public FileVO getFile(Long fileId) {
        return fileMapper.selectFileById(fileId);
    }
 // 게시글별 첨부파일 목록 (예: BOARD_ID로)
    @Override
    public List<FileVO> getFilesByBoardId(Long boardId) {
        return fileMapper.selectFilesByBoardId(boardId);
    }
//  파일 삭제  
    @Override
    public void deleteFile(Long fileId) {
        fileMapper.deleteFile(fileId);
    }
//  파일 수정
    @Override
    public void updateFile(FileVO fileVO) {
        fileMapper.updateFile(fileVO);
    }
 // 회원 프로필 조회용
    @Override
    public FileVO getProfileFileByMemberId(Long memberId) {
        return fileMapper.selectProfileFileByMemberId(memberId);
    }
    
    // 7/11 민중 게시글삭제를위한 달려있는 모든 파일 삭제 기능
    @Override
    public void deleteAllByTargetIdAndType(Long targetId, String useType) {
        fileMapper.deleteByTargetIdAndType(targetId, useType);
    }
}