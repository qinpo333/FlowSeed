package com.cmcc.seed.service;

import com.cmcc.seed.model.Friends;

import java.util.List;

/**
 * Created by cmcc on 16/1/8.
 */
public interface FriendsService {

    boolean insert(Friends friends);

    /**
     * 查询两者的好友关系
     * @param openId
     * @param friendOpenId
     * @return
     */
    List<Friends> getFriends(String openId, String friendOpenId);

    /**
     * 查询用户的好友列表
     * @param openId
     * @return
     */
    List<Friends> getFriends(String openId);

    /**
     * 创建好友关系
     * @param openId
     * @param fromOpenId
     */
    void createFriend(String openId, String fromOpenId);
}
