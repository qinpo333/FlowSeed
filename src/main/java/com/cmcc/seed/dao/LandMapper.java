package com.cmcc.seed.dao;

import com.cmcc.seed.model.Land;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LandMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Land record);

    int insertSelective(Land record);

    Land selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Land record);

    int updateByPrimaryKey(Land record);

    List<Land> selectByUserId(Long userId);

    List<Land> selectByUserIdAndCropId(@Param("userId")Long userId, @Param("cropId")Long cropId);

    int addYieldById(Long id, double amount);

    boolean updateAllStatus(int status);
}