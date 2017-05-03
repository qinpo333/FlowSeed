package com.cmcc.seed.service.impl;

import com.cmcc.seed.dao.ChargeMapper;
import com.cmcc.seed.dao.OpLogMapper;
import com.cmcc.seed.dao.UserMapper;
import com.cmcc.seed.enems.ChargeProductEnum;
import com.cmcc.seed.enems.OpType;
import com.cmcc.seed.mall_cp.CPClient;
import com.cmcc.seed.mall_cp.PresentReq;
import com.cmcc.seed.model.Charge;
import com.cmcc.seed.model.OpLog;
import com.cmcc.seed.model.User;
import com.cmcc.seed.service.CacheService;
import com.cmcc.seed.service.ExchangeService;
import com.cmcc.seed.service.OpLogService;
import com.cmcc.seed.utils.Constants;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by cmcc on 16/1/19.
 */
@Service
public class ExchangeServiceImpl implements ExchangeService {

    private Logger logger = Logger.getLogger(ExchangeServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private OpLogMapper opLogMapper;

    @Autowired
    private ChargeMapper chargeMapper;

    @Autowired
    private CacheService cacheService;

    ExecutorService executorService = Executors.newFixedThreadPool(300);

    @Transactional
    public boolean exchange(long userId, String tel, double flow) {

        try {
            User user = userMapper.selectByPrimaryKey(userId);
            //判断用户流量余额是否充足
            if (user.getFlow() >= flow) {
                //更新用户的流量余额
                int count = userMapper.decreaseFlow(userId, user.getFlow(), flow);
                if (count == 1) {
                    //更新redis中用户的信息
                    BigDecimal oldFlow = new BigDecimal(Double.toString(user.getFlow()));
                    BigDecimal changeFlow = new BigDecimal(Double.toString(flow));
                    double result = oldFlow.subtract(changeFlow).doubleValue();

                    user.setFlow(result);
                    cacheService.set(Constants.REDIS_USERINFO_KEY + user.getOpenId(), user);

                    //发送充值请求并记录日志
                    executorService.execute(new ChargeThread(tel, flow, user));

                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

            return false;
        }
        return false;
    }

    //TODO:事务处理
    public boolean insertExchangeLog(User user, PresentReq presentReq, long rechrNo) {
        try {
            //新增操作日志
            OpLog opLog = new OpLog();
            opLog.setOpTime(new Date());
            opLog.setOpUserId(user.getId());
            opLog.setOpType(OpType.EXCHANGE.getValue());
            opLog.setFlowChange((-1d) * ChargeProductEnum.getFlow(presentReq.getGid()));
            opLog.setMemo(presentReq.getTel());
            opLog.setCreateTime(new Date());
            opLog.setUpdateTime(new Date());

            opLogMapper.insertSelective(opLog);

            //新增充值记录
            Charge charge = new Charge();
            charge.setOpenId(user.getOpenId());
            charge.setUserId(user.getId());
            charge.setPhone(presentReq.getTel());
            charge.setGid(presentReq.getGid());
            charge.setFlow(ChargeProductEnum.getFlow(presentReq.getGid()));
            charge.setTransNo(presentReq.getTransNo());
            charge.setRechrNo(rechrNo);
            charge.setCreateTime(new Date());
            charge.setUpdateTime(new Date());

            chargeMapper.insert(charge);

        } catch (Exception e) {
            return false;
        }

        return true;
    }

    class ChargeThread extends Thread {

        private String tel;
        private double flow;
        private User user;

        ChargeThread(String tel, double flow, User user) {
            this.tel = tel;
            this.flow = flow;
            this.user = user;
        }

        @Override
        public void run() {
            long charNo = 0;
            PresentReq presentReq = new PresentReq();
            //TODO:充值请求参数值设置
            try {
                CPClient cpClient = new CPClient();
                presentReq.setGid(ChargeProductEnum.getGid(flow))
                        .setTel(tel).setTransNo(UUID.randomUUID().toString().replaceAll("-", ""));

                charNo = cpClient.present(presentReq);

                logger.info("调用10086充值接口充值成功：tel = " + tel + ",gid = " + ChargeProductEnum.getGid(flow));
            } catch (Exception e) {
                logger.error("调用10086充值接口充值失败：tel = " + tel + ",gid = " + ChargeProductEnum.getGid(flow));
            } finally {
                insertExchangeLog(user, presentReq, charNo);
            }
        }
    }
}
