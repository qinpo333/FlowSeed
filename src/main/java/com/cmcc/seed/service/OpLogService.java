package com.cmcc.seed.service;

import com.cmcc.seed.model.OpLog;
import com.cmcc.seed.pojo.FlowChangeDetail;
import com.cmcc.seed.model.OpLogDetail;
import com.cmcc.seed.pojo.MyOplog;

import java.util.List;

/**
 * Created by cmcc on 16/1/8.
 */
public interface OpLogService {

    boolean addOplog(OpLog log);

    boolean addSelectiveOplog(OpLog log);

    /**
     * 查询流量收入明细
     * 按时间倒序，
     * TODO:暂不实现分页，默认最多查出10条
     * @param userId
     * @return
     */
    List<FlowChangeDetail> getIncomeFlowList(long userId);

    /**
     * 查询流量支出明细
     * 按时间倒序，
     * TODO:暂不实现分页，默认最多查出10条
     * @param userId
     * @return
     */
    List<FlowChangeDetail> getOutFlowList(long userId);

    /**
     * 根据操作类型查询当天日志
     * @param type
     * @param opUserId
     * @return
     */
    List<OpLog> getTodayOpLogListByType(int type, long opUserId);

    /**
     * 查询当天日志
     * @param type
     * @param userId
     * @param opUserId
     * @return
     */
    List<OpLog> getTodayOpLogListByType(int type, long userId, long opUserId);

    /**
     * 查询当天土地的操作日志
     * @param type
     * @param landId
     * @return
     */
    List<OpLog> getTodayOpLogListByTypeAndLand(int type, long landId);

    /**
     * 查询与我有关的日志，暂时只包括被偷和被浇水
     * @param userId
     * @return
     */
    List<MyOplog> getMyOplog(long userId);
}
