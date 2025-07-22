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
// 이 클래스는 REST API 컨트롤러임을 나타냅니다. (JSON 응답)
@RestController
// final 필드 자동 주입 (생성자 주입)
@RequiredArgsConstructor
public class FileController {
	// 파일 관련 서비스 (비즈니스 로직) 주입
    private final FileService fileService;

    // 파일(이미지) 다운로드 또는 브라우저에 바로 보여주기
    /**
     * [파일(이미지) 다운로드 및 바로 보기]
     * 파일 ID로 파일을 DB에서 조회한 뒤,
     * 브라우저에서 바로 보여주거나(이미지, PDF 등), 다운로드하도록 함
     */
    @GetMapping("/file/download.do")
    public void downloadFile(
    		 // 요청 파라미터로 파일 PK 받기
            @RequestParam("fileId") Long fileId,
            HttpServletResponse response) throws IOException {
    	// 1. DB에서 파일 정보와 데이터 조회
        FileVO fileVO = fileService.getFile(fileId);
        // 2. 파일이 존재하지 않으면 404 에러 응답
        if (fileVO == null || fileVO.getFileData() == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "파일을 찾을 수 없습니다.");
            return;
        }

        // 3. Content-Type 헤더 설정 (ex. image/jpeg, image/png, application/pdf 등)
        String contentType = fileVO.getFileType();
        if (contentType == null || contentType.isEmpty()) {
            contentType = "application/octet-stream";
        }
        response.setContentType(contentType);

        // 4. 파일명 인코딩 (한글 깨짐 방지)
        String fileName = fileVO.getFileName();
        String encodedFileName = java.net.URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
        // 5. Content-Disposition 헤더: inline(브라우저에 바로 보기), attachment(다운로드)
        response.setHeader("Content-Disposition",
                "inline; filename=\"" + encodedFileName + "\"");

        // 6. 파일 데이터(바이너리) 응답
        response.getOutputStream().write(fileVO.getFileData());
        response.getOutputStream().flush();
    }
    /**
     * [파일 삭제]
     * 파일 ID로 해당 파일을 삭제합니다.
     * 프론트엔드에서는 Ajax 등으로 호출
     */
    @DeleteMapping("/file/delete.do")
    public void deleteFile(@RequestParam("fileId") Long fileId, HttpServletResponse response) throws IOException {
    	 // 1. 파일 삭제 (DB, 파일시스템 등)
    	fileService.deleteFile(fileId);
    	// 2. 정상 응답(200)
        response.setStatus(HttpServletResponse.SC_OK);
    }
//    * [회원 프로필 사진 업로드/변경]
//    	     * - memberId(회원 PK), file(이미지파일) 전송 필요
//    	     * - 기존 프로필이 있으면 삭제 후, 새 파일로 업로드
//    	     * - 실제로는 MemberController에서 처리하는 것이 더 자연스럽지만,
//    	     *   만약 파일 업로드만 따로 처리하고 싶다면 아래 예시처럼 구현할 수 있음
//    	     */
    	    @PostMapping("/file/profile-upload.do")
    	    public String uploadProfileImage(
    	            @RequestParam("memberId") Long memberId,     // 폼에서 전달받음 회원 PK
    	            @RequestPart("profileImage") MultipartFile file // 업로드 파일
    	    ) throws IOException {
    	        // 1. 기존 프로필 파일 있는지 조회 (1인 1개만 허용)
    	        FileVO oldProfile = fileService.getProfileFileByMemberId(memberId);
    	        if (oldProfile != null) {
    	            fileService.deleteFile(oldProfile.getFileId());
    	        }
    	        // 2. 새 파일 정보 객체 생성 및 데이터 세팅
    	        FileVO newFile = new FileVO();
    	        newFile.setFileName(file.getOriginalFilename()); // 파일명
    	        newFile.setFileType(file.getContentType());	// MIME 타입
    	        newFile.setUseType("MEMBER");            // 용도 구분값 (회원 프로필)
    	        newFile.setUseTargetId(memberId);        // 어떤 회원 프로필인지 (회원 PK)
    	        newFile.setUploaderId(memberId);         // 업로더 (회원 PK)
    	        newFile.setFilePath("/profile/" + file.getOriginalFilename()); // 저장경로 (DB에만 저장)
    	        newFile.setFileData(file.getBytes()); // 실제 바이너리 데이터

    	        fileService.insertFile(newFile); // DB에 저장

    	        // 3. 실제로는 MemberService 등에서 member 테이블의 profile 컬럼을 갱신해야함
    	        // ex) profile = "/file/download.do?fileId=xxx"
    	        // 여기서는 파일 업로드(저장)만 처리


    	        return "ok"; // 성공시 문자열 반환 (프론트에서 사용)
    	    }
}