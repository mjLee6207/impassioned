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
}