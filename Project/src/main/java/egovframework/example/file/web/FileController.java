package egovframework.example.file.web;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}