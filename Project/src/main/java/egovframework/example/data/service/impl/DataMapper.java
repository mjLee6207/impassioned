package egovframework.example.data.service.impl;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

import egovframework.example.data.service.DataVO;

@Mapper
public interface DataMapper {
    int insertRecipe(DataVO vo);                  // 단건 삽입
    int existsRecipe(String recipeId);  // 중복 체크
}