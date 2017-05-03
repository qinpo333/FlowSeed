package com.cmcc.seed.quartz.jobs;

import com.cmcc.seed.enems.LandStatus;
import com.cmcc.seed.model.Give;
import com.cmcc.seed.service.GiveService;
import com.cmcc.seed.service.LandService;
import com.cmcc.seed.service.OpLogService;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 每天零点检查土地的状态
 * Created by cmcc on 16/1/17.
 */
public class CheckWaterJob implements Job {

    private static Logger logger = Logger.getLogger(CheckWaterJob.class);

    @Autowired
    private LandService landService;

    @Autowired
    private GiveService giveService;

    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("更新作物的状态为可浇水任务开始...");

        //把状态为非枯萎的土地状态修改为可浇水
        landService.updateAllStatus(LandStatus.WATER.getValue());

        logger.info("更新作物的状态为可浇水任务结束...");

        /*logger.info("回收未领取赠送流量任务开始...");

        //赠送出去超过3天未领取的流量回收
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dataStr = format.format(new Date());
        List<Give> expireGives = giveService.getExpireInfos(dataStr + " 00:00:00");
        for (Give give : expireGives) {
            giveService.rollbackExpire(give);
        }

        logger.info("回收未领取赠送流量任务结束...");*/
    }
}
