package com.cmcc.seed.dao;

import com.cmcc.seed.model.CropGrow;

import java.util.List;

public interface CropGrowMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CropGrow record);

    int insertSelective(CropGrow record);

    CropGrow selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CropGrow record);

    int updateByPrimaryKey(CropGrow record);

    List<CropGrow> selectByCropId(Long cropId);
}