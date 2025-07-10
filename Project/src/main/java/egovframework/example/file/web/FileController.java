package egovframework.example.file.web;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import egovframework.example.file.service.FileService;
import egovframework.example.file.service.FileVO;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    // 파일(이미지) 다운로드 또는 브라우저에 바로 보여주기
    @GetMapping("/file/download.do")
    public void downloadFile(
            @RequestParam("fileId") Long fileId,
            HttpServletResponse response) throws IOException {

        FileVO fileVO = fileService.getFile(fileId);

        if (fileVO == null || fileVO.getFileData() == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "파일을 찾을 수 없습니다.");
            return;
        }

        // Content-Type 설정 (예: image/jpeg, image/png, application/octet-stream 등)
        String contentType = fileVO.getFileType();
        if (contentType == null || contentType.isEmpty()) {
            contentType = "application/octet-stream";
        }
        response.setContentType(contentType);

        // 파일명 인코딩 (다운로드 시)
        String fileName = fileVO.getFileName();
        String encodedFileName = java.net.URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-Disposition",
                "inline; filename=\"" + encodedFileName + "\"");

        // 파일 바이너리 전송
        response.getOutputStream().write(fileVO.getFileData());
        response.getOutputStream().flush();
    }
    @DeleteMapping("/file/delete.do")
    public void deleteFile(@RequestParam("fileId") Long fileId, HttpServletResponse response) throws IOException {
        fileService.deleteFile(fileId);
        response.setStatus(HttpServletResponse.SC_OK); // 200
    }
//    * [회원 프로필 사진 업로드/변경]
//    	     * - memberId(회원 PK), file(이미지파일) 전송 필요
//    	     * - 기존 프로필이 있으면 삭제 후, 새 파일로 업로드
//    	     * - 실제로는 MemberController에서 처리하는 것이 더 자연스럽지만,
//    	     *   만약 파일 업로드만 따로 처리하고 싶다면 아래 예시처럼 구현할 수 있음
//    	     */
    	    @PostMapping("/file/profile-upload.do")
    	    public String uploadProfileImage(
    	            @RequestParam("memberId") Long memberId,       // 폼에서 전달받음
    	            @RequestPart("profileImage") MultipartFile file // 프로필 이미지
    	    ) throws IOException {
    	        // 1. 기존 프로필 이미지 조회
    	        FileVO oldProfile = fileService.getProfileFileByMemberId(memberId);
    	        if (oldProfile != null) {
    	            fileService.deleteFile(oldProfile.getFileId());
    	        }
    	        // 2. 새 프로필 파일 저장
    	        FileVO newFile = new FileVO();
    	        newFile.setFileName(file.getOriginalFilename());
    	        newFile.setFileType(file.getContentType());
    	        newFile.setUseType("MEMBER");            // 회원 프로필 용도
    	        newFile.setUseTargetId(memberId);        // 회원 PK
    	        newFile.setUploaderId(memberId);         // 업로더 = 회원 PK
    	        newFile.setFilePath("/profile/" + file.getOriginalFilename());
    	        newFile.setFileData(file.getBytes());

    	        fileService.insertFile(newFile);

    	        // 3. 필요하다면, 회원 DB의 profile 필드에 "/file/download.do?fileId=xxx" 경로 저장 (MemberService에서 update)
    	        // ... (여기서는 파일 저장만 처리)

    	        return "ok";
    	    }
}