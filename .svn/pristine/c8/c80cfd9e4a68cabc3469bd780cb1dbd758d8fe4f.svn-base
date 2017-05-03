package com.cmcc.seed.dao;

import com.cmcc.seed.model.OpLog;
import com.cmcc.seed.model.OpLogDetail;

import java.util.List;

public interface OpLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OpLog record);

    int insertSelective(OpLog record);

    OpLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OpLog record);

    int updateByPrimaryKey(OpLog record);

    List<OpLog> getIncomeFlowList(long userId);

    List<OpLog> getOutFlowList(long userId);

    List<OpLog> getTodayOpLogListByType(int type, long userId);

    List<OpLog> getTodayOpLogListByTypeAndUserId(int type, long userId, long opUserId);

    List<OpLog> getTodayOpLogListByTypeAndLandId(int type, long landId);

    List<OpLogDetail> getMyOplog(long userId);
}