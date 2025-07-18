package egovframework.example.like.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import egovframework.example.like.service.LikeService;
import egovframework.example.like.service.LikeVO;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
public class LikeController {

    @Autowired
    private LikeService likeService;

    /** 👍 좋아요 등록 */
    @PostMapping("/addLike.do")
    @ResponseBody
    public int addLike(@RequestBody LikeVO vo) {
        log.info("📥 addLike.do 요청: {}", vo);

        try {
            boolean exists = false;

            if ("BOARD".equalsIgnoreCase(vo.getLikeType())) {
                exists = likeService.existsLike(vo);
            } else if ("RECIPE".equalsIgnoreCase(vo.getLikeType())) {
                exists = likeService.existsRecipeLike(vo);
            }

            if (!exists) {
                likeService.addLike(vo);
                log.info("✅ 좋아요 등록 완료");
            } else {
                log.info("⚠️ 이미 좋아요 누름");
            }

            return likeService.countLikes(vo);
        } catch (Exception e) {
            log.error("💥 좋아요 등록 중 에러: " + e.getMessage(), e);
            return -1;
        }
    }

    /** ❌ 좋아요 취소 */
    @PostMapping("/cancelLike.do")
    @ResponseBody
    public int removeLike(@RequestBody LikeVO vo) {
        log.info("📥 cancelLike.do 요청: {}", vo);

        try {
            boolean exists = false;

            if ("BOARD".equalsIgnoreCase(vo.getLikeType())) {
                exists = likeService.existsLike(vo);
            } else if ("RECIPE".equalsIgnoreCase(vo.getLikeType())) {
                exists = likeService.existsRecipeLike(vo);
            }

            if (exists) {
                likeService.removeLike(vo);
                log.info("✅ 좋아요 취소 완료");
            } else {
                log.info("⚠️ 취소 요청했지만 좋아요 안 되어 있음");
            }

            return likeService.countLikes(vo);
        } catch (Exception e) {
            log.error("💥 좋아요 취소 중 에러: " + e.getMessage(), e);
            return -1;
        }
    }

    /** 📊 좋아요 수 조회 */
    @GetMapping("/countLike.do")
    @ResponseBody
    public int getLikeCount(@RequestParam(required = false) Integer boardId,
                            @RequestParam(required = false) String recipeId,
                            @RequestParam String likeType) {
        log.info("📊 countLike.do 호출: boardId={}, recipeId={}, likeType={}", boardId, recipeId, likeType);
        try {
            LikeVO vo = new LikeVO();
            vo.setLikeType(likeType);
            vo.setBoardId(boardId);
            vo.setRecipeId(recipeId);
            return likeService.countLikes(vo);
        } catch (Exception e) {
            log.error("💥 좋아요 수 조회 중 에러: " + e.getMessage(), e);
            return -1;
        }
    }

    /** 🔍 좋아요 여부 확인 */
    @GetMapping("/checkLike.do")
    @ResponseBody
    public boolean checkLike(@RequestParam Long memberIdx,
                             @RequestParam String likeType,
                             @RequestParam(required = false) Integer boardId,
                             @RequestParam(required = false) String recipeId) {
        LikeVO vo = new LikeVO();
        vo.setMemberIdx(memberIdx);
        vo.setLikeType(likeType);
        vo.setBoardId(boardId);
        vo.setRecipeId(recipeId);
        return likeService.existsLike(vo);
    }

    /** 🌐 좋아요 JSP 페이지 */
    @GetMapping("/like.do")
    public String likePage() {
        return "like/like";
    }

    /** 🧪 에러 테스트용 뷰 */
    @GetMapping("/testErrorView.do")
    public String testErrorView() {
        return "cmmn/egovError";
    }

    /** 🏠 메인 페이지 */
    @GetMapping("/index.do")
    public String index() {
        return "sample/index";
    }
}
