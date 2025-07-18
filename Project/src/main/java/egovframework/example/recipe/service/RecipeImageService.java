package egovframework.example.recipe.service;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import egovframework.example.recipe.service.impl.RecipeMapper;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class RecipeImageService {

    @Autowired
    private RecipeMapper recipeMapper;

    // 이클립스 기준 저장 경로
    private final String basePath = "src/main/webapp/images/recipes/";

    public void downloadAndCacheAllImages() {
        List<RecipeVO> recipeList = recipeMapper.selectAllRecipeThumb();

        File dir = new File(basePath);
        if (!dir.exists()) dir.mkdirs();

        log.info("🔁 총 {}개의 레시피 썸네일 처리 시작", recipeList.size());

        for (RecipeVO recipe : recipeList) {
            String recipeId = recipe.getRecipeId();
            String imageUrl = recipe.getThumbnail();

            log.info("📌 [{}] 처리 중 - 원본 썸네일 URL: [{}]", recipeId, imageUrl);

            if (imageUrl == null || imageUrl.trim().isEmpty()) {
                log.warn("❌ [{}] 무시됨 - URL이 null 또는 빈 문자열", recipeId);
                continue;
            }

            imageUrl = imageUrl.trim();

            if (!imageUrl.startsWith("http")) {
                log.warn("❌ [{}] 무시됨 - 유효하지 않은 URL (http로 시작하지 않음): [{}]", recipeId, imageUrl);
                continue;
            }

            // 확장자 추출
            String extension = ".jpg";
            String lowerUrl = imageUrl.toLowerCase();
            if (lowerUrl.endsWith(".png")) extension = ".png";
            else if (lowerUrl.endsWith(".jpeg")) extension = ".jpeg";
            else if (lowerUrl.endsWith(".jpg")) extension = ".jpg";

            String savePath = basePath + recipeId + extension;
            String newPath = "/images/recipes/" + recipeId + extension;

            log.info("📥 [{}] 다운로드 시도 → 저장경로: [{}]", recipeId, savePath);

            try {
                ImageDownloader.downloadImage(imageUrl, savePath);

                // DB에 저장 경로 업데이트
                recipe.setThumbnail(newPath);
                recipeMapper.updateThumbnailPath(recipe);

                log.info("✅ [{}] 처리 완료 - DB에 썸네일 경로 업데이트: {}", recipeId, newPath);
            } catch (Exception e) {
                log.error("❌ [{}] 다운로드 실패 - URL: {}, 저장경로: {}", recipeId, imageUrl, savePath, e);
            }
        }

        log.info("✅ 전체 썸네일 처리 완료");
    }
}
