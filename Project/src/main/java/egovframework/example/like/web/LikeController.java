package egovframework.example.like.web;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public int addLike(@RequestBody Map<String, Object> map) {
        log.info("📥 addLike.do 요청: " + map);

        int boardId = (int) map.get("boardId");
        int memberIdx = (int) map.get("memberIdx");

        LikeVO vo = new LikeVO();
        vo.setBoardId(boardId);
        vo.setMemberIdx(memberIdx);

        try {
            if (!likeService.existsLike(vo)) {
                likeService.addLike(vo);
                log.info("✅ 좋아요 등록 완료");
            } else {
                log.info("⚠️ 이미 좋아요 누름");
            }

            return likeService.countLikes(boardId);

        } catch (Exception e) {
            log.error("💥 좋아요 등록 중 에러: " + e.getMessage(), e);
            return -1;
        }
        
    }

    /** ❌ 좋아요 취소 */
    @PostMapping("/cancelLike.do")
    @ResponseBody
    public int removeLike(@RequestBody Map<String, Object> map) {
        log.info("📥 cancelLike.do 요청: " + map);

        int boardId = (int) map.get("boardId");
        int memberIdx = (int) map.get("memberIdx");

        LikeVO vo = new LikeVO();
        vo.setBoardId(boardId);
        vo.setMemberIdx(memberIdx);

        try {
            if (likeService.existsLike(vo)) {
                likeService.removeLike(vo);
                log.info("✅ 좋아요 취소 완료");
            } else {
                log.info("⚠️ 취소 요청했지만 좋아요 안 되어 있음");
            }

            return likeService.countLikes(boardId);
        } catch (Exception e) {
            log.error("💥 좋아요 취소 중 에러: " + e.getMessage(), e);
            return -1;
        }
    }

    /** 📊 좋아요 수 조회 */
    @RequestMapping(value = "/countLike.do", method = RequestMethod.GET)
    @ResponseBody
    public int getLikeCount(@RequestParam int boardId) {
        log.info("📊 countLike.do 호출 - boardId: " + boardId);
        try {
            return likeService.countLikes(boardId);
        } catch (Exception e) {
            log.error("💥 좋아요 수 조회 중 에러: " + e.getMessage(), e);
            return -1;
        }
    }

    /** 🌐 좋아요 JSP 페이지 */
    @GetMapping("/like.do")
    public String likePage() {
        return "like/like";  // /WEB-INF/jsp/like/like.jsp
    }

    /** 🧪 에러 테스트용 뷰 */
    @GetMapping("/testErrorView.do")
    public String testErrorView() {
        return "cmmn/egovError";
    }

    /** 🏠 메인 페이지 */
    @GetMapping("/index.do")
    public String index() {
        return "sample/index"; // /WEB-INF/jsp/sample/index.jsp
    }
 // 좋아요 여부 확인 (프론트에서 등록/취소 여부 판단용)
    @GetMapping("/checkLike.do")
    @ResponseBody
    public boolean checkLike(@RequestParam int boardId, @RequestParam int memberIdx) {
        LikeVO vo = new LikeVO();
        vo.setBoardId(boardId);
        vo.setMemberIdx(memberIdx);
        return likeService.existsLike(vo);
    }
}
