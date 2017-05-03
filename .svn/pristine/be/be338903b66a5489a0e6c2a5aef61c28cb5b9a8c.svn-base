package com.cmcc.seed.dao;

import com.cmcc.seed.model.User;

public interface UserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User selectByOpenId(String openId);

    int addFlowById(Long id, double flow);

    int addExperienceById(Long id, int experience);

    int decreaseFlow(long id, double oldFlow, double flow);
}