package com.cmcc.seed.dao;

import com.cmcc.seed.model.Friends;

import java.util.List;

public interface FriendsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Friends record);

    int insertSelective(Friends record);

    Friends selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Friends record);

    int updateByPrimaryKey(Friends record);

    List<Friends> selectByOpenIdAndFriendOpenId(String openId, String friendOpenId);

    List<Friends> selectByOpenId(String openId);
}