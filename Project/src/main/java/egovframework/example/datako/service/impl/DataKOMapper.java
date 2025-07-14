package egovframework.example.datako.service.impl;

import org.apache.ibatis.annotations.Param;
import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

import egovframework.example.datako.service.DataKOVO;

@Mapper
public interface DataKOMapper {
	boolean checkRecipe(@Param("recipeId") String recipeId);
	void insertDataKO(DataKOVO datako);
}
