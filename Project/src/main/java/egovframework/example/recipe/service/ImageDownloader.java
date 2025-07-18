package egovframework.example.recipe.service;

import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ImageDownloader {

    // ✅ 절대경로로 강제 지정 (사용자 환경에 맞게 수정 가능)
    private static final String ROOT_PATH = "C:/work/Github/impassioned/src/main/webapp/";

    public static void downloadImage(String imageUrl, String localRelativePath) throws Exception {
        if (imageUrl == null || imageUrl.trim().isEmpty()) {
            log.warn("❗ 무시됨 - 이미지 URL이 null 또는 빈 문자열입니다.");
            return;
        }

        imageUrl = imageUrl.trim();

        // ✅ http 또는 https로 시작하는 절대 URL만 허용
        if (!(imageUrl.startsWith("http://") || imageUrl.startsWith("https://"))) {
            log.warn("❗ 무시됨 - 유효하지 않은 이미지 URL (imageUrl): {}", imageUrl);
            return;
        }

        // ✅ 확장자 추출
        String extension = ".jpg";
        String lowerUrl = imageUrl.toLowerCase();
        if (lowerUrl.endsWith(".png")) extension = ".png";
        else if (lowerUrl.endsWith(".jpeg")) extension = ".jpeg";
        else if (lowerUrl.endsWith(".jpg")) extension = ".jpg";

        // ✅ 확장자 보장 (중복 방지)
        if (!(localRelativePath.toLowerCase().endsWith(".jpg") ||
              localRelativePath.toLowerCase().endsWith(".jpeg") ||
              localRelativePath.toLowerCase().endsWith(".png"))) {
            localRelativePath += extension;
        }

        // ✅ 절대경로 처리
        Path absolutePath = Paths.get(ROOT_PATH, localRelativePath);
        log.info("📁 절대 저장 경로: {}", absolutePath.toAbsolutePath());

        try (InputStream in = new URL(imageUrl).openStream()) {
            Files.createDirectories(absolutePath.getParent());
            Files.copy(in, absolutePath, StandardCopyOption.REPLACE_EXISTING);

            if (Files.exists(absolutePath)) {
                log.info("✅ 다운로드 완료: {}", absolutePath.toAbsolutePath());
            } else {
                log.error("❌ 파일 저장 실패: {}", absolutePath.toAbsolutePath());
            }
        } catch (Exception e) {
            log.error("❌ 이미지 다운로드 실패 - URL: {}, 경로: {}", imageUrl, absolutePath, e);
            throw e;
        }
    }
}
