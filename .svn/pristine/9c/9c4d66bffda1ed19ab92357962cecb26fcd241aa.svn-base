package com.cmcc.seed.dao;

import com.cmcc.seed.model.Give;

import java.util.List;

public interface GiveMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Give record);

    int insertSelective(Give record);

    Give selectByPrimaryKey(Long id);

    Give selectByCode(String code);

    int updateByPrimaryKeySelective(Give record);

    int updateByPrimaryKey(Give record);

    List<Give> getExpireGives(String dateStr);
}