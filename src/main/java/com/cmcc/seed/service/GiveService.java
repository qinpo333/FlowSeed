package com.cmcc.seed.service;

import com.cmcc.seed.model.Give;
import com.cmcc.seed.model.User;
import com.cmcc.seed.pojo.ReceiveResult;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by cmcc on 16/2/23.
 */
public interface GiveService {

    /**
     * 发起赠送
     * @param give
     * @param user
     * @return
     */
    boolean startGive(Give give, User user);

    /**
     * 领取赠送
     * @param code
     * @param user
     * @return
     */
    ReceiveResult receive(String code, User user);

    /**
     * 查询所有过期的赠送
     * @return
     */
    List<Give> getExpireInfos(String dateStr);

    /**
     * 回收过期赠送
     * @param give
     */
    void rollbackExpire(Give give);
}
