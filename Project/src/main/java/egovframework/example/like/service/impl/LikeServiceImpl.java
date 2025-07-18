package egovframework.example.like.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import egovframework.example.common.Criteria;
import egovframework.example.like.service.LikeService;
import egovframework.example.like.service.LikeVO;

@Service("likeService")
@Transactional
public class LikeServiceImpl implements LikeService {

    @Resource(name = "likeMapper")
    private LikeMapper likeMapper;

    @Override
    public List<?> selectLikeList(Criteria criteria) {
        return likeMapper.selectLikeList(criteria);
    }

    @Override
    public int selectLikeListTotCnt(Criteria criteria) {
        return likeMapper.selectLikeListTotCnt(criteria);
    }

    @Override
    public int countLikes(LikeVO vo) {
        if ("RECIPE".equals(vo.getLikeType())) {
            return likeMapper.countRecipeLikes(vo.getRecipeId());
        } else {
            return likeMapper.countLikes(vo);  // 기존 BOARD_ID 전용 쿼리 사용
        }
    }

    @Override
    public boolean existsLike(LikeVO vo) {
        return likeMapper.existsLike(vo) > 0;
    }
    
    @Override
    public boolean existsRecipeLike(LikeVO likevo) {
        return likeMapper.existsRecipeLike(likevo) > 0;
    }
    
    @Override
    public boolean checkLike(LikeVO vo) {
        return likeMapper.checkLike(vo) > 0;
    }

    @Override
    public void addLike(LikeVO vo) {
        if ("RECIPE".equals(vo.getLikeType())) {
            likeMapper.insertRecipeLike(vo); // ✅ RECIPE_ID 기준 INSERT
            likeMapper.increaseRecipeLikeCount(vo); // ✅ 레시피 전용 수 증가
        } else {
            likeMapper.insertLike(vo);       // ✅ BOARD_ID 기준 INSERT
            likeMapper.increaseLikeCount(vo); // ✅ 게시판 전용 수 증가
        }
    }

    @Override
    public void removeLike(LikeVO vo) {
        if ("RECIPE".equals(vo.getLikeType())) {
            likeMapper.deleteRecipeLike(vo);
            likeMapper.decreaseRecipeLikeCount(vo);
        } else {
            likeMapper.deleteLike(vo);
            likeMapper.decreaseLikeCount(vo);
        }
    }

    @Override
    public void increaseLikeCount(LikeVO vo) {
        if ("RECIPE".equals(vo.getLikeType())) {
            likeMapper.increaseRecipeLikeCount(vo); // ✅ 레시피용 쿼리 호출
        } else {
            likeMapper.increaseLikeCount(vo); // ✅ 게시판용 쿼리 호출
        }
    }

    @Override
    public void decreaseLikeCount(LikeVO vo) {
        if ("RECIPE".equals(vo.getLikeType())) {
            likeMapper.decreaseRecipeLikeCount(vo); // ✅ 레시피용 쿼리 호출
        } else {
            likeMapper.decreaseLikeCount(vo); // ✅ 게시판용 쿼리 호출
        }
    }

    @Override
    public List<LikeVO> selectLikeListByTarget(LikeVO vo) {
        return likeMapper.selectLikeListByTarget(vo);  // ✅ 올바른 메서드 호출
    }


    @Override
    public void deleteAllByTarget(LikeVO vo) {
        likeMapper.deleteAllByTarget(vo);
    }
    
    @Override
    public void deleteAllByBoardId(int boardId) {
        LikeVO vo = new LikeVO();
        vo.setLikeType("BOARD");
        vo.setBoardId(boardId);
        likeMapper.deleteAllByTarget(vo);
    }
    
}
