package egovframework.example.file.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import egovframework.example.file.service.FileVO;

@Mapper
public interface FileMapper {
    // 파일 등록 (BLOB 포함)
    void insertFile(FileVO fileVO);

    // 파일 1건 조회 (PK)
    FileVO selectFileById(Long fileId);

    // 게시글별 첨부파일 목록 (예: BOARD_ID로)
    List<FileVO> selectFilesByBoardId(Long boardId);

    //  파일 삭제
    void deleteFile(Long fileId);
    //  파일 수정
    void updateFile(FileVO fileVO);
    // 👈 회원 프로필 조회용
    FileVO selectProfileFileByMemberId(Long memberId);
    
    // 7/11 민중 게시글삭제를위한 달려있는 모든 파일 삭제 기능
    void deleteByTargetIdAndType(@Param("targetId") Long targetId, @Param("useType") String useType);
}