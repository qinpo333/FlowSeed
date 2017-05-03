package com.cmcc.seed.service.impl;

import com.cmcc.seed.dao.FriendsMapper;
import com.cmcc.seed.model.Friends;
import com.cmcc.seed.model.User;
import com.cmcc.seed.service.CacheService;
import com.cmcc.seed.service.ExchangeService;
import com.cmcc.seed.service.FriendsService;
import com.cmcc.seed.service.UserService;
import com.cmcc.seed.utils.Constants;
import com.cmcc.seed.utils.SerializerUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by cmcc on 16/1/25.
 */
@Service
public class FriendsServiceImpl implements FriendsService {

    private Logger logger = Logger.getLogger(FriendsServiceImpl.class);

    @Autowired
    private FriendsMapper friendsMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private CacheService cacheService;

    public boolean insert(Friends friends) {
        return friendsMapper.insert(friends) == 1;
    }

    public List<Friends> getFriends(String openId, String friendOpenId) {
        return friendsMapper.selectByOpenIdAndFriendOpenId(openId, friendOpenId);
    }

    public List<Friends> getFriends(String openId) {
        /*
        //从redis中查询用户的好友列表
        List<byte[]> list = cacheService.lrange(Constants.REDIS_FRIEND_LIST_KEY + openId, 0, -1);

        List<Friends> friendsList = new ArrayList<Friends>();

        if (list != null && list.size() > 0) {
            try{
                for (byte[] b : list) {
                    Friends friends = (Friends) SerializerUtil.deserialize(b);
                    friendsList.add(friends);
                }
            }catch(Exception e){
            }
        }

        //如果redis中查不到，则查询数据库，并存入redis
        if (list == null || list.size() == 0) {
            friendsList = friendsMapper.selectByOpenId(openId);
            if (friendsList != null && friendsList.size() > 0) {
                for (Friends friend : friendsList) {
                    cacheService.rpush(Constants.REDIS_FRIEND_LIST_KEY + openId, friend);
                }
            }
        }
        */
        List<Friends> friendsList = friendsMapper.selectByOpenId(openId);
        return friendsList;
    }

    public void createFriend(String openId, String fromOpenId) {
        //需先校验是否已是朋友关系
        //TODO:需从redis中判断是否为好友
        List<Friends> list = getFriends(openId, fromOpenId);
        if (list == null || list.size() == 0) {

            User user = userService.getUserByOpenId(openId);
            User friend = userService.getUserByOpenId(fromOpenId);

            Friends friends = new Friends();
            friends.setUserId(user.getId());
            friends.setOpenId(user.getOpenId());
            friends.setFriendUserId(friend.getId());
            friends.setFriendOpenId(friend.getOpenId());
            friends.setFriendNickName(friend.getNickname());
            friends.setFriendImgUrl(friend.getImgUrl());
            friends.setCreateTime(new Date());
            friends.setUpdateTime(new Date());

            Friends reverseFriends = new Friends();
            reverseFriends.setUserId(friend.getId());
            reverseFriends.setOpenId(friend.getOpenId());
            reverseFriends.setFriendUserId(user.getId());
            reverseFriends.setFriendOpenId(user.getOpenId());
            reverseFriends.setFriendNickName(user.getNickname());
            reverseFriends.setFriendImgUrl(user.getImgUrl());
            reverseFriends.setCreateTime(new Date());
            reverseFriends.setUpdateTime(new Date());

            if(insert(friends) && insert(reverseFriends)) {
                //存入redis
//                cacheService.rpush(Constants.REDIS_FRIEND_LIST_KEY + openId, friends);
//                cacheService.rpush(Constants.REDIS_FRIEND_LIST_KEY + fromOpenId, reverseFriends);
                logger.info(openId + "和" + fromOpenId + "建立好友关系成功！");
            } else {
                logger.info(openId + "和" + fromOpenId + "建立好友关系失败！");
            }
        } else {
            logger.info(openId + "和" + fromOpenId + "已是好友，无需再次建立好友关系！");
        }
    }

}
