package com.cmcc.seed.service;

import com.cmcc.seed.model.CropGrow;

import java.util.List;

/**
 * Created by cmcc on 16/1/8.
 */
public interface CropGrowService {

    /**
     * 查询作物的成才阶段信息
     * @param cropId
     * @return
     */
    List<CropGrow> getGrowInfoByCropId(long cropId);

    /**
     * 查询作物成熟所需时间
     * @param cropId
     * @return
     */
    long getMatureTimeByCropId(long cropId);
}
