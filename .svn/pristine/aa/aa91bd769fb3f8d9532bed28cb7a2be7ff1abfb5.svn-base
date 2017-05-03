package com.cmcc.seed.service;

import com.cmcc.seed.model.Land;
import com.cmcc.seed.model.User;

/**
 * Created by cmcc on 16/1/8.
 */
public interface UserService {

    int insert(User user);

    int updateUser(User user);

    User getUserById(Long id);

    User getUserByOpenId(String openId);

    /**
     * 浇水
     * @param landId
     * @param userId
     * @return
     */
    boolean doWater(Long landId, Long userId);

    /**
     * 收获
     * @param landId
     * @param userId
     * @return 收获失败返回-1
     */
    double doHarvest(Long landId, Long userId);

    /**
     * 铲除
     * @param landId
     * @param userId
     * @return
     */
    boolean doEradicate(Long landId, Long userId);

    /**
     * 修改用户的流量
     * @param userId
     * @param flow
     * @return
     */
    boolean addFlow(long userId, double flow);

    /**
     * 修改用户的经验
     * @param userId
     * @param experience
     * @return
     */
    boolean addExperience(long userId, int experience);

    /**
     * 种植作物
     * @param userId
     * @param landId
     * @param cropId
     * @return
     */
    Land doPlant(Long userId, Long landId, Long cropId);

    /**
     * 分享邀请好友浇水
     * @param user
     * @return
     */
    boolean doWaterShare(User user);

    /**
     * 偷取果实
     * @param user
     * @param land
     * @return
     */
    double doSteal(User user, Land land);

    /**
     * 偷取失败，只记录日志
     * @param user
     * @param land
     */
    void doStealFail(User user, Land land);

    /**
     * 帮好友的土地浇水
     * @param user
     * @param land
     * @return
     */
    boolean doWaterFriend(User user, Land land);

    /**
     * 异步插入用户，先插入redis
     * @param user
     * @return
     */
    int insertAsyn(User user);

    /**
     * 判断用户是否为新用户
     * @param user
     * @return
     */
    boolean isNewUser(User user);
}
