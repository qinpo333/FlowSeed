package com.cmcc.seed.service;

import com.cmcc.seed.model.Land;
import com.cmcc.seed.pojo.PlantPojo;

import java.util.List;

/**
 * Created by cmcc on 16/1/8.
 */
public interface LandService {

    /**
     * 新增土地
     * @param land
     * @return
     */
    int insert(Land land);

    /**
     * 查询用户的土地列表
     * @param userId
     * @return
     */
    List<Land> getLandsByUserId(long userId);


    /**
     *
     * @param userId
     * @param cropId
     * @return
     */
    List<Land> getLandsByUserId(Long userId, Long cropId);

    /**
     * 查询指定土地的生长阶段
     * @param landId
     * @return
     */
    int getGrowStage(long landId);

    /**
     * 土地信息转换成PlantPojo
     * @param land
     * @return
     */
    PlantPojo transFrom(Land land);

    /**
     * 土地信息列表转换成PlantPojo
     * @param lands
     * @return
     */
    List<PlantPojo> transFrom(List<Land> lands);

    /**
     * 修改状态
     * @param landId
     * @param status
     */
    boolean updateStatus(long landId, int status);

    /**
     * 浇水后更新状态和最后浇水时间
     * @param landId
     * @return
     */
    boolean updateWaterStatus(long landId);
    /**
     * 增加实际产量
     * @param landId
     * @param yield
     * @return
     */
    boolean addYield(long landId, double yield);

    /**
     * 查询土地信息
     * @param id
     * @return
     */
    Land getLandById(long id);

    /**
     * 查询作物成熟剩余时间（小时）
     * @param id
     * @return
     */
    int getMatureHours(long id);

    /**
     * 收获后更新土地信息
     * @param land
     * @return
     */
    boolean updateHarvestLand(Land land);

    /**
     * 判断作物是否成熟
     * @param land
     * @return
     */
    boolean matured(Land land);

    /**
     * 更新所有作物的状态
     * @param status
     * @return
     */
    boolean updateAllStatus(int status);

    /**
     * 查询土地上作物的种植时间
     * @param landId
     * @return
     */
    long getPlantTime(long landId);

    /**
     * 更新土地信息
     * @param land
     * @return
     */
    boolean updateLand(Land land);
}
