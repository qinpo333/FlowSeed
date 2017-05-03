package com.cmcc.seed.service.impl;

import com.cmcc.seed.dao.OpLogMapper;
import com.cmcc.seed.enems.OpType;
import com.cmcc.seed.model.OpLog;
import com.cmcc.seed.pojo.FlowChangeDetail;
import com.cmcc.seed.model.OpLogDetail;
import com.cmcc.seed.pojo.MyOplog;
import com.cmcc.seed.service.OpLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cmcc on 16/1/16.
 */
@Service
public class OplogServiceImpl implements OpLogService {

    @Autowired
    private OpLogMapper opLogMapper;

    public boolean addOplog(OpLog log) {
        return opLogMapper.insert(log) == 1;
    }

    public boolean addSelectiveOplog(OpLog log) {
        return opLogMapper.insertSelective(log) == 1;
    }

    public List<FlowChangeDetail> getIncomeFlowList(long userId) {
        List<OpLog> opLogs = opLogMapper.getIncomeFlowList(userId);

        return transFrom(opLogs);
    }

    public List<FlowChangeDetail> getOutFlowList(long userId) {
        List<OpLog> opLogs = opLogMapper.getOutFlowList(userId);

        return transFrom(opLogs);
    }

    public List<OpLog> getTodayOpLogListByType(int type, long opUserId) {
        return opLogMapper.getTodayOpLogListByType(type, opUserId);
    }

    public List<OpLog> getTodayOpLogListByType(int type, long userId, long opUserId) {
        return opLogMapper.getTodayOpLogListByTypeAndUserId(type, userId, opUserId);
    }

    public List<OpLog> getTodayOpLogListByTypeAndLand(int type, long landId) {
        return opLogMapper.getTodayOpLogListByTypeAndLandId(type, landId);
    }

    public List<MyOplog> getMyOplog(long userId) {
        List<OpLogDetail> opLogDetails = opLogMapper.getMyOplog(userId);
        return transFromOpLogDetails(opLogDetails);
    }

    private FlowChangeDetail transFrom(OpLog opLog) {
        FlowChangeDetail detail = new FlowChangeDetail();

        if (opLog != null) {
            detail.setPhone(opLog.getMemo());

            SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            detail.setOpTime(df.format(opLog.getOpTime()));
            if (opLog.getFlowChange() < 0) {
                detail.setFlowChange(opLog.getFlowChange() * (-1d));
            } else {
                detail.setFlowChange(opLog.getFlowChange());
            }
            detail.setOpType(OpType.getName(opLog.getOpType()));
        }

        return detail;
    }

    private List<FlowChangeDetail> transFrom(List<OpLog> logs) {
        List<FlowChangeDetail> details = new ArrayList<FlowChangeDetail>();

        if (logs != null) {
            for (OpLog opLog : logs) {
                FlowChangeDetail detail = transFrom(opLog);
                details.add(detail);
            }
        }

        return details;
    }

    private MyOplog transFrom(OpLogDetail opLogDetail) {
        MyOplog myOplog = new MyOplog();

        if (opLogDetail != null) {
            myOplog.setOpUserOpenId(opLogDetail.getOpUserOpenId());
            myOplog.setOpUserNickName(opLogDetail.getOpUserNickName());
            myOplog.setOpUserImgUrl(opLogDetail.getOpUserImgUrl());
            myOplog.setOpTypeName(OpType.getName(opLogDetail.getOpType()));
            if (opLogDetail.getOpType().intValue() == OpType.WATER_FRIEND.getValue()) {
                myOplog.setOpTypeName("好友浇水");
            } else if (opLogDetail.getOpType().intValue() == OpType.STEAL.getValue()) {
                myOplog.setOpTypeName("好友偷取");
            }

            SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            myOplog.setOpTime(df.format(opLogDetail.getOpTime()));
        }

        return myOplog;
    }

    private List<MyOplog> transFromOpLogDetails(List<OpLogDetail> logs) {
        List<MyOplog> myOplogs = new ArrayList<MyOplog>();

        if (logs != null) {
            for (OpLogDetail opLogDetail : logs) {
                MyOplog myOplog = transFrom(opLogDetail);
                myOplogs.add(myOplog);
            }
        }

        return myOplogs;
    }
}
