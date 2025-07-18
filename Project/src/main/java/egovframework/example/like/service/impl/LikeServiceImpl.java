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
        Integer count = likeMapper.countLikes(vo);
        return count != null ? count : 0;
    }

    @Override
    public boolean existsLike(LikeVO vo) {
        return likeMapper.existsLike(vo) > 0;
    }

    @Override
    public boolean checkLike(LikeVO vo) {
        return likeMapper.checkLike(vo) > 0;
    }

    @Override
    public void addLike(LikeVO vo) {
        likeMapper.insertLike(vo);
        likeMapper.increaseLikeCount(vo);
    }

    @Override
    public void removeLike(LikeVO vo) {
        likeMapper.deleteLike(vo);
        likeMapper.decreaseLikeCount(vo);
    }

    @Override
    public void increaseLikeCount(LikeVO vo) {
        likeMapper.increaseLikeCount(vo);
    }

    @Override
    public void decreaseLikeCount(LikeVO vo) {
        likeMapper.decreaseLikeCount(vo);
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
