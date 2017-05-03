package com.cmcc.seed.service.impl;

import com.cmcc.seed.dao.GiveMapper;
import com.cmcc.seed.dao.OpLogMapper;
import com.cmcc.seed.dao.UserMapper;
import com.cmcc.seed.enems.GiveStatus;
import com.cmcc.seed.enems.OpType;
import com.cmcc.seed.model.Give;
import com.cmcc.seed.model.OpLog;
import com.cmcc.seed.model.User;
import com.cmcc.seed.pojo.ReceiveResult;
import com.cmcc.seed.service.CacheService;
import com.cmcc.seed.service.GiveService;
import com.cmcc.seed.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by cmcc on 16/2/23.
 */
@Service
public class GiveServiceImpl implements GiveService {

    @Autowired
    private GiveMapper giveMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private OpLogMapper opLogMapper;

    @Autowired
    private CacheService cacheService;

    @Transactional
    public boolean startGive(Give give, User user) {

        try {
            if (user.getFlow() >= give.getAmount()) {
                //更新用户的流量余额
                int count = userMapper.decreaseFlow(user.getId(), user.getFlow(), give.getAmount());
                if (count == 1) {
                    //更新redis中用户的信息
                    BigDecimal oldFlow = new BigDecimal(Double.toString(user.getFlow()));
                    BigDecimal changeFlow = new BigDecimal(Double.toString(give.getAmount()));
                    double result = oldFlow.subtract(changeFlow).doubleValue();

                    user.setFlow(result);
                    cacheService.set(Constants.REDIS_USERINFO_KEY + user.getOpenId(), user);

                    //增加赠送记录
                    giveMapper.insert(give);

                    //并记录操作日志
                    OpLog opLog = new OpLog();
                    opLog.setOpTime(new Date());
                    opLog.setOpUserId(user.getId());
                    opLog.setOpType(OpType.GIVE.getValue());
                    opLog.setFlowChange((-1d) * give.getAmount());
                    opLog.setMemo("");
                    opLog.setCreateTime(new Date());
                    opLog.setUpdateTime(new Date());

                    opLogMapper.insertSelective(opLog);
                    return true;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return false;
    }

    @Transactional
    public ReceiveResult receive(String code, User user) {
        ReceiveResult result = new ReceiveResult();

        //根据赠送码查询赠送记录
        Give give = giveMapper.selectByCode(code);

        try {
            if (give != null) {
                if (give.getUserId().longValue() == user.getId().longValue()) {
                    result.setSuccess(false);
                    result.setMsg("不要太贪心，这是送给好友的哦");
                    return result;
                }

                if (give.getStatus() == GiveStatus.GIVE.getValue()) {
                   //修改赠送记录状态
                   give.setReceiveTime(new Date());
                   give.setReceiveUserId(user.getId());
                   give.setReceiveUserName(user.getNickname());
                   give.setStatus(GiveStatus.RECEIVE.getValue());

                   giveMapper.updateByPrimaryKeySelective(give);

                   //修改用户流量
                   userMapper.addFlowById(user.getId(), give.getAmount());
                   //更新redis中数据
                   user.setFlow(user.getFlow() + give.getAmount());
                   cacheService.set(Constants.REDIS_USERINFO_KEY + user.getOpenId(), user);

                   //操作记录
                    OpLog opLog = new OpLog();
                    opLog.setOpTime(new Date());
                    opLog.setOpUserId(user.getId());
                    opLog.setOpType(OpType.RECEIVE.getValue());
                    opLog.setFlowChange(give.getAmount());
                    opLog.setMemo("");
                    opLog.setCreateTime(new Date());
                    opLog.setUpdateTime(new Date());

                    opLogMapper.insertSelective(opLog);

                   result.setSuccess(true);
                   result.setMsg("恭喜你，你的好友" + give.getUserName() + "送给你" + give.getAmount() + "MB流量！");
               } else if (give.getStatus() == GiveStatus.EXPIRE.getValue()) {
                   result.setSuccess(false);
                   result.setMsg("该链接已失效！");
               } else if (give.getStatus() == GiveStatus.RECEIVE.getValue()) {
                   result.setSuccess(false);
                   result.setMsg("流量已经被其他人抢走了！");
               }
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMsg("网络异常！");
        }
        return result;
    }

    public List<Give> getExpireInfos(String dateStr){
        return giveMapper.getExpireGives(dateStr);
    }

    @Transactional
    public void rollbackExpire(Give give){
        try {

            //修改赠送记录状态
            give.setReceiveTime(new Date());
            give.setStatus(GiveStatus.EXPIRE.getValue());

            giveMapper.updateByPrimaryKeySelective(give);

            User user = userMapper.selectByPrimaryKey(give.getUserId());
            //修改用户流量
            userMapper.addFlowById(give.getUserId(), give.getAmount());
            //更新redis中数据
            user.setFlow(user.getFlow() + give.getAmount());
            cacheService.set(Constants.REDIS_USERINFO_KEY + user.getOpenId(), user);

            //操作记录
            OpLog opLog = new OpLog();
            opLog.setOpTime(new Date());
            opLog.setOpUserId(user.getId());
            opLog.setOpType(OpType.GIVE_EXPIRE.getValue());
            opLog.setFlowChange(give.getAmount());
            opLog.setMemo("");
            opLog.setCreateTime(new Date());
            opLog.setUpdateTime(new Date());

            opLogMapper.insertSelective(opLog);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
