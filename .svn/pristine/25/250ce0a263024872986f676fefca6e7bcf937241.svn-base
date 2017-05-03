package com.cmcc.seed.service.impl;

import com.cmcc.seed.dao.UserMapper;
import com.cmcc.seed.enems.LandStatus;
import com.cmcc.seed.enems.OpType;
import com.cmcc.seed.model.Crop;
import com.cmcc.seed.model.Land;
import com.cmcc.seed.model.OpLog;
import com.cmcc.seed.model.User;
import com.cmcc.seed.service.*;
import com.cmcc.seed.utils.Constants;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by cmcc on 16/1/11.
 */
@Service
public class UserServiceImpl implements UserService {

    private Logger logger = Logger.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private LandService landService;

    @Autowired
    private OpLogService opLogService;

    @Autowired
    private CacheService cacheService;

    @Autowired
    private CropService cropService;

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    public int insert(User user) {
        int count = 0;
        try {
            count = userMapper.insert(user);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getLocalizedMessage());
        }
        //如果新增成功，则存入redis
        if (count == 1) {
            cacheService.set(Constants.REDIS_USERINFO_KEY + user.getOpenId(), user);
        }
        return count;
    }

    public int updateUser(User user) {
        int count = 0;
        try {
            count = userMapper.updateByPrimaryKey(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //如果更新成功，则更新redis中数据
        if (count == 1) {
            cacheService.set(Constants.REDIS_USERINFO_KEY + user.getOpenId(), user);
        }
        return count;
    }

    public User getUserById(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }

    public User getUserByOpenId(String openId) {
        //从redis中查询
        Object object = cacheService.getObj(Constants.REDIS_USERINFO_KEY + openId);
        //redis中没有则从数据库中查询，并存入redis
        if (object == null) {
            User user = userMapper.selectByOpenId(openId);
            if (user != null) {
                cacheService.set(Constants.REDIS_USERINFO_KEY + openId, user);
            }
            return user;
        }
        return (User)object;
    }

    @Transactional
    public boolean doWater(Long landId, Long userId) {
        try {
            Land landInfo = landService.getLandById(landId);
            //校验今天是否已教过水，防止重复浇水
            if (landInfo.getStatus().intValue() == LandStatus.WATER.getValue()) {
                //更新土地状态和最后浇水时间
                landService.updateWaterStatus(landId);

                //增加操作记录
                OpLog log = new OpLog();
                log.setLandId(landInfo.getId());
                log.setNum(landInfo.getNum());
                log.setCropId(landInfo.getCropId());
                log.setUserId(landInfo.getUserId());
                log.setOpUserId(userId);
                log.setExperienceChange(Constants.WATER_EXPERIENCE);
                log.setOpType(OpType.WATER.getValue());
                log.setOpTime(new Date());
                log.setCreateTime(new Date());
                log.setUpdateTime(new Date());

                opLogService.addOplog(log);

                //更新实际产量(每浇水一次，增加产量0.3M)
//                landService.addYield(landId, Constants.WATER_FLOW);

                //增加用户经验
                if (addExperience(userId, Constants.WATER_EXPERIENCE)) {
                    User user = userMapper.selectByPrimaryKey(userId);
                    cacheService.set(Constants.REDIS_USERINFO_KEY + user.getOpenId(), user);
                } else {
                    throw new RuntimeException("更新用户经验失败");
                }

                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    @Transactional
    public double doHarvest(Long landId, Long userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        //先清除缓存
        cacheService.del(Constants.REDIS_USERINFO_KEY + user.getOpenId());

        double amount = -1;

        try {
            Land land = landService.getLandById(landId);

            //增加校验，防止重复收获
            if (landService.matured(land)) {
                //增加操作记录
                OpLog log = new OpLog();
                log.setLandId(land.getId());
                log.setNum(land.getNum());
                log.setCropId(land.getCropId());
                log.setUserId(land.getUserId());
                log.setOpUserId(userId);
                log.setOpType(OpType.HAVEST.getValue());
                log.setOpTime(new Date());
                log.setFlowChange(land.getYield());
                log.setExperienceChange(Constants.HAVEST_EXPERIENCE);
                log.setCreateTime(new Date());
                log.setUpdateTime(new Date());

                opLogService.addOplog(log);

                amount = land.getYield();

                //更新土地状态和种植时间
                landService.updateHarvestLand(land);

                //更新用户流量,如果更新成功则同时更新redis中用户信息
                //TODO：增加经验和修改级别
                if (addFlow(userId, amount) && addExperience(userId, Constants.HAVEST_EXPERIENCE)) {
                    logger.info("更新用户经验和流量成功");
                } else {
                    throw new RuntimeException("更新用户流量失败");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            amount = -1;
        }
        return amount;
    }

    @Transactional
    public boolean doEradicate(Long landId, Long userId) {
        Land land = landService.getLandById(landId);

        if (land != null && land.getUserId().longValue() == userId.longValue()){
            try {
                //增加操作日志
                OpLog log = new OpLog();
                log.setLandId(land.getId());
                log.setNum(land.getNum());
                log.setCropId(land.getCropId());
                log.setUserId(land.getUserId());
                log.setOpUserId(userId);
                log.setOpType(OpType.UPROOT.getValue());
                log.setOpTime(new Date());
                log.setCreateTime(new Date());
                log.setUpdateTime(new Date());

                opLogService.addOplog(log);

                //更新土地信息（清除作物的信息）
                land.setYield(null);
                land.setCropId(null);
                land.setExpectYield(null);
                land.setLastWaterTime(null);
                land.setUpdateTime(new Date());

                landService.updateLand(land);

                //更新redis中作物种植和最后浇水时间
                cacheService.set(Constants.REDIS_LAND_LAST_WATER_TIME_KEY + land.getId(), "");
                cacheService.set(Constants.REDIS_LAND_PLANT_TIME_KEY + land.getId(), "");

                return true;
            }catch (Exception e) {
                return false;
            }
        }

        return false;
    }

    public boolean addFlow(long userId, double flow) {
        return userMapper.addFlowById(userId, flow) == 1;
    }

    public boolean addExperience(long userId, int experience) {
        return userMapper.addExperienceById(userId, experience) == 1;
    }

    public Land doPlant(Long userId, Long landId, Long cropId) {
        //查询作物信息，暂时只有一种作物
        Crop crop = cropService.getCropById(cropId);

        //查询用户的作物列表
        List<Land> lands = landService.getLandsByUserId(userId);

        //如果没有指定landId,则在第一块没有种植的土地上种植
        if (landId == null) {
            for (Land land : lands) {
                Long cropId1Id = land.getCropId();
                if (cropId1Id == null) {
                    landId = land.getId();
                    break;
                }
            }
        }

        //没有指定landId，并且用户没有土地，则是在新的土地上种植，即新增土地信息；
        // 如果用户有土地但没种植，则在已有的土地上种植
        if (landId == null) {

            Land land = new Land();
            land.setUserId(userId);
            land.setCropId(crop.getId());
            land.setExpectYield(crop.getExpectYield());
            land.setYield(crop.getExpectYield());
            land.setNum(lands.size());
            land.setStatus(LandStatus.WATER.getValue());//刚种植的作物可以浇水
            land.setPlantTime(new Date());
            land.setCreateTime(new Date());
            land.setUpdateTime(new Date());

            if (landService.insert(land) == 1) {
                //增加操作日志
                OpLog log = new OpLog();
                log.setLandId(land.getId());
                log.setNum(land.getNum());
                log.setCropId(land.getCropId());
                log.setUserId(land.getUserId());
                log.setOpUserId(userId);
                log.setOpType(OpType.PLANT.getValue());
                log.setOpTime(new Date());
                log.setCreateTime(new Date());
                log.setUpdateTime(new Date());

                opLogService.addOplog(log);


                //作物的种植时间存入redis
                cacheService.set(Constants.REDIS_LAND_PLANT_TIME_KEY + land.getId(), land.getPlantTime().getTime() + "");
                return land;
            } else {
                return null;
            }

        } else {//指定了landid，则在指定的土地上种植，即更新土地信息
            Land land = landService.getLandById(landId);
            if (land != null && land.getUserId().longValue() == userId.longValue()) {

                land.setCropId(crop.getId());
                land.setExpectYield(crop.getExpectYield());
                land.setYield(crop.getExpectYield());
                land.setLastWaterTime(null);
                land.setStatus(LandStatus.WATER.getValue());//刚种植的作物可以浇水
                land.setPlantTime(new Date());
                land.setUpdateTime(new Date());

                if(landService.updateLand(land)) {
                    //增加操作日志
                    OpLog log = new OpLog();
                    log.setLandId(land.getId());
                    log.setNum(land.getNum());
                    log.setCropId(land.getCropId());
                    log.setUserId(land.getUserId());
                    log.setOpUserId(userId);
                    log.setOpType(OpType.PLANT.getValue());
                    log.setOpTime(new Date());
                    log.setCreateTime(new Date());
                    log.setUpdateTime(new Date());

                    opLogService.addOplog(log);

                    //作物的种植时间存入redis
                    cacheService.set(Constants.REDIS_LAND_PLANT_TIME_KEY + land.getId(), land.getPlantTime().getTime() + "");
                    //更新作物的最后浇水时间
                    cacheService.set(Constants.REDIS_LAND_LAST_WATER_TIME_KEY + land.getId(), "");
                    return land;
                }
            }
            return land;
        }

    }

    @Transactional
    public boolean doWaterShare(User user) {
        //先清除缓存
        cacheService.del(Constants.REDIS_USERINFO_KEY + user.getOpenId());

        try {
            //添加操作记录
            OpLog log = new OpLog();
            log.setUserId(user.getId());
            log.setOpUserId(user.getId());
            log.setOpType(OpType.SHARE.getValue());
            log.setExperienceChange(Constants.INVITE_FRIEND_WATER_EXPERIENCE);
            log.setOpTime(new Date());
            log.setCreateTime(new Date());
            log.setUpdateTime(new Date());

            opLogService.addSelectiveOplog(log);

            //增加流量
//            userMapper.addFlowById(user.getId(), 0.6);

            //更新redis中数据
//            BigDecimal oldFlow = new BigDecimal(Double.toString(user.getFlow()));
//            BigDecimal changeFlow = new BigDecimal(Double.toString(0.6));
//            double result = oldFlow.add(changeFlow).doubleValue();
//            user.setFlow(result);

            //增加经验
            if (addExperience(user.getId(), Constants.INVITE_FRIEND_WATER_EXPERIENCE)) {
                logger.info("更新用户经验成功");
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Transactional
    public double doSteal(User user, Land land) {
        //先清除缓存
        cacheService.del(Constants.REDIS_USERINFO_KEY + user.getOpenId());

        try {
            //生成随机偷取量（预期产量的1%~5%）
            BigDecimal steal = new BigDecimal(land.getYield()).multiply(
                    new BigDecimal(RandomUtils.nextInt(5) + 1)).divide(new BigDecimal(100));
            double stealAmount = steal.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
            //校验土地预期产量是否满足被偷(剩余量)
            // TODO:校验防止多次偷取放在前面校验
            if ((land.getYield() - stealAmount) > land.getExpectYield() * 0.5) {
                //添加操作记录
                OpLog log = new OpLog();
                log.setLandId(land.getId());
                log.setCropId(land.getCropId());
                log.setUserId(land.getUserId());
                log.setOpUserId(user.getId());
                log.setOpType(OpType.STEAL.getValue());
                log.setFlowChange(stealAmount);
                log.setOpTime(new Date());
                log.setCreateTime(new Date());
                log.setUpdateTime(new Date());

                opLogService.addSelectiveOplog(log);

                //修改土地的实际收获量
                land.setYield(land.getYield() - stealAmount);
                land.setUpdateTime(new Date());
                landService.updateLand(land);

                //修改用户的流量
                userMapper.addFlowById(user.getId(), stealAmount);
//                //更新redis中数据
//                BigDecimal oldFlow = new BigDecimal(Double.toString(user.getFlow()));
//                BigDecimal changeFlow = new BigDecimal(Double.toString(stealAmount));
//                double result = oldFlow.add(changeFlow).doubleValue();
//                user.setFlow(result);
//                cacheService.set(Constants.REDIS_USERINFO_KEY + user.getOpenId(), user);

                return stealAmount;
            } else {
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void doStealFail(User user, Land land) {
        OpLog log = new OpLog();
        log.setLandId(land.getId());
        log.setCropId(land.getCropId());
        log.setUserId(land.getUserId());
        log.setOpUserId(user.getId());
        log.setOpType(OpType.STEAL.getValue());
        log.setFlowChange(0d);
        log.setOpTime(new Date());
        log.setCreateTime(new Date());
        log.setUpdateTime(new Date());

        opLogService.addSelectiveOplog(log);
    }

    @Transactional
    public boolean doWaterFriend(User user, Land land) {
        //先清除缓存
        cacheService.del(Constants.REDIS_USERINFO_KEY + user.getOpenId());
        User friend = userMapper.selectByPrimaryKey(land.getUserId());
        cacheService.del(Constants.REDIS_USERINFO_KEY + friend.getOpenId());

        try {
            //TODO：帮好友浇水获取流量
//            double amount = 0.3;

            //今天帮好友浇水的次数
            List<OpLog> allWaterOplogs = opLogService.getTodayOpLogListByType(
                    OpType.WATER_FRIEND.getValue(), user.getId());

            //添加操作记录
            OpLog log = new OpLog();
            log.setLandId(land.getId());
            log.setCropId(land.getCropId());
            log.setUserId(land.getUserId());
            log.setOpUserId(user.getId());
            log.setOpType(OpType.WATER_FRIEND.getValue());
            log.setOpTime(new Date());
            log.setCreateTime(new Date());
            log.setUpdateTime(new Date());

            //帮好友浇水的前三次才获得经验
            if (allWaterOplogs == null || allWaterOplogs.size() < 3) {
//                log.setFlowChange(amount);
//
//                //修改用户的流量
//                userMapper.addFlowById(user.getId(), amount);
//                //更新redis中数据
//                BigDecimal oldFlow = new BigDecimal(Double.toString(user.getFlow()));
//                BigDecimal changeFlow = new BigDecimal(Double.toString(amount));
//                double result = oldFlow.add(changeFlow).doubleValue();
//                user.setFlow(result);
//                cacheService.set(Constants.REDIS_USERINFO_KEY + user.getOpenId(), user);

                //修改土地的实际收获量
//                land.setYield(land.getYield() + amount);
//                land.setUpdateTime(new Date());
//                landService.updateLand(land);

                log.setExperienceChange(Constants.WATER_FRIEND_EXPERIENCE);

                //修改用户的经验
                if (addExperience(user.getId(), Constants.WATER_FRIEND_EXPERIENCE)) {
                    logger.info("修改用户经验成功");
                }
            } else {
                //好友增加经验
                addExperience(land.getUserId(), Constants.WATER_FRIEND_EXPERIENCE);
                log.setExperienceChange(0);
            }

            opLogService.addSelectiveOplog(log);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public int insertAsyn(User user) {
        int count = 0;
        try {
            cacheService.set(Constants.REDIS_USERINFO_KEY + user.getOpenId(), user);
            count = 1;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getLocalizedMessage());
        }
        //如果新增成功，则存入数据库
        if (count == 1) {
            threadPoolTaskExecutor.execute(new InsertThread(user));
        }
        return count;
    }

    public boolean isNewUser(User user) {
        List<Land> lands = landService.getLandsByUserId(user.getId());
        //如果土地为0，则为新用户
        if (lands.size() == 0) {
            return true;
        } else {
            //如果土地数不为0
            int nullCropIds = 0;
            for (Land land : lands) {
                Long cropId = land.getCropId();
                if (cropId == null) {
                    nullCropIds ++;
                }
            }
            //如果所有土地都未种植，则为新用户
            if (nullCropIds == lands.size()){
                return true;
            } else {
                return false;
            }
        }
    }

    class InsertThread extends Thread {
        private User user;

        InsertThread(User user) {
            this.user = user;
        }

        @Override
        public void run() {
            userMapper.insert(user);
        }

    }
}
