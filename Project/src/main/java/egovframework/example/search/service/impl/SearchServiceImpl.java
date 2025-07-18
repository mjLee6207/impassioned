package egovframework.example.search.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import egovframework.example.common.Criteria;
import egovframework.example.search.service.SearchService;
import egovframework.example.search.service.SearchVO;

@Service
public class SearchServiceImpl implements SearchService {
	
	@Autowired
	SearchMapper searchMapper;

	@Override
	public List<SearchVO> searchAll(Criteria criteria) {
		return searchMapper.searchAll(criteria);
	}
}
