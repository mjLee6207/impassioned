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

    @Override
    public void insertFile(FileVO fileVO) {
        fileMapper.insertFile(fileVO);
    }

    @Override
    public FileVO getFile(Long fileId) {
        return fileMapper.selectFileById(fileId);
    }

    @Override
    public List<FileVO> getFilesByBoardId(Long boardId) {
        return fileMapper.selectFilesByBoardId(boardId);
    }
    
    @Override
    public void deleteFile(Long fileId) {
        fileMapper.deleteFile(fileId);
    }

    @Override
    public void updateFile(FileVO fileVO) {
        fileMapper.updateFile(fileVO);
    }
    
    @Override
    public FileVO getProfileFileByMemberId(Long memberId) {
        return fileMapper.selectProfileFileByMemberId(memberId);
    }
    
    // 7/11 민중 게시글삭제를위한 달려있는 모든 파일 삭제 기능
    @Override
    public void deleteAllByTargetIdAndType(int targetId, String useType) {
        fileMapper.deleteByTargetIdAndType(targetId, useType);
    }
}