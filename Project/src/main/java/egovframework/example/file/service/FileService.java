package egovframework.example.file.service;

import java.util.List;

public interface FileService {
    void insertFile(FileVO fileVO);
    FileVO getFile(Long fileId);
    List<FileVO> getFilesByBoardId(Long boardId);
    void deleteFile(Long fileId);       
    void updateFile(FileVO fileVO);  
}