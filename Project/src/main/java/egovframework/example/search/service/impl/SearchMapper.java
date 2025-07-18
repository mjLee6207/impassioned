package egovframework.example.search.service.impl;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

import egovframework.example.common.Criteria;
import egovframework.example.search.service.SearchVO;

@Mapper
public interface SearchMapper {
	List<SearchVO> searchAll(Criteria criteria);
}
