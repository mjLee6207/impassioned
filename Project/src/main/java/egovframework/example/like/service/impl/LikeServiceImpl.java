package egovframework.example.like.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import egovframework.example.common.Criteria;
import egovframework.example.like.service.LikeService;
import egovframework.example.like.service.LikeVO;

@Service("likeService")
@Transactional
public class LikeServiceImpl implements LikeService {

    @Autowired
    private LikeMapper likeMapper;

    @Override
    public List<LikeVO> selectLikeListByBoardId(int boardId) {
        return likeMapper.selectLikeListByBoardId(boardId);
    }

    @Override
    public boolean existsLike(LikeVO likevo) {
        return likeMapper.existsLike(likevo) > 0;
    }

    @Override
    public void addLike(LikeVO likevo) {
        likeMapper.insertLike(likevo);
        likeMapper.increaseLikeCount(likevo.getBoardId());
    }

    @Override
    public void removeLike(LikeVO likevo) {
        likeMapper.deleteLike(likevo);
        likeMapper.decreaseLikeCount(likevo.getBoardId());
    }

    @Override
    public List<?> selectLikeList(Criteria criteria) {
        return likeMapper.selectLikeList(criteria);
    }

    @Override
    public int selectLikeListTotCnt(Criteria criteria) {
        return likeMapper.selectLikeListTotCnt(criteria);
    }

    @Override
    public int countLikes(int boardId) {
        Integer count = likeMapper.countLikes(boardId);
        return count != null ? count : 0;
    }

    @Override
    public boolean checkLike(LikeVO likeVO) {
        return likeMapper.checkLike(likeVO) > 0;
    }

	@Override
	public void increaseLikeCount(int boardId) {
		likeMapper.increaseLikeCount(boardId);
		// TODO Auto-generated method stub
		
	}

	@Override
	public void decreaseLikeCount(int boardId) {
		likeMapper.decreaseLikeCount(boardId);
		// TODO Auto-generated method stub
		
	}


}
