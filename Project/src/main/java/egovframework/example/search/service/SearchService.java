package egovframework.example.search.service;

import java.util.List;

import egovframework.example.common.Criteria;

public interface SearchService {
	List<SearchVO> searchAll(Criteria criteria);
}
