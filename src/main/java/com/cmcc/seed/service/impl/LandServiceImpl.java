package com.cmcc.seed.service.impl;

import com.cmcc.seed.dao.LandMapper;
import com.cmcc.seed.enems.LandStatus;
import com.cmcc.seed.model.CropGrow;
import com.cmcc.seed.model.Land;
import com.cmcc.seed.pojo.PlantPojo;
import com.cmcc.seed.service.CacheService;
import com.cmcc.seed.service.CropGrowService;
import com.cmcc.seed.service.LandService;
import com.cmcc.seed.utils.Constants;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by cmcc on 16/1/11.
 */
@Service
public class LandServiceImpl implements LandService {

    @Autowired
    private LandMapper landMapper;

    @Autowired
    private CropGrowService cropGrowService;

    @Autowired
    private CacheService cacheService;

    public int insert(Land land) {
        return landMapper.insert(land);
    }

    public List<Land> getLandsByUserId(long userId) {
        return landMapper.selectByUserId(userId);
    }

    public List<Land> getLandsByUserId(Long userId, Long cropId) {
        return landMapper.selectByUserIdAndCropId(userId, cropId);
    }

    public int getGrowStage(long landId) {
        //TODO:计算成长阶段
        return new Random().nextInt(5);
    }

    public PlantPojo transFrom(Land land) {
        PlantPojo plant = new PlantPojo();

        plant.setId(land.getId());
        plant.setGround(land.getNum());
        plant.setFlow(land.getExpectYield());

        int status = getStatus(land);
        plant.setStatus(status);

        //如果作物枯萎，则设置成长阶段为枯萎
        if (LandStatus.SHOVEL.getValue() == status) {
            plant.setGrowthStatus(5);
        } else {
            plant.setGrowthStatus(getGrowthStatus(land));
        }

        long matureTime = cropGrowService.getMatureTimeByCropId(land.getCropId());
        plant.setMatureDays((int)(matureTime/1000/3600/24));

        return plant;
    }

    /**
     * 获取作物的状态(可浇水、可收获、枯萎/可铲除)
     * 作物是否成熟和是否枯萎需要实时计算
     * @param land
     * @return
     */
    private int getStatus(Land land) {
        if (land.getCropId() == null) {
            return LandStatus.PLANT.getValue();
        }
        //查询作物的最后浇水时间，超过六天没有浇水，则枯萎/可铲除
        if (withered(land)) {
            return LandStatus.SHOVEL.getValue();
        }

        //判断作物是否已成熟
        if (matured(land)) {
            return LandStatus.HARVEST.getValue();
        }

        return land.getStatus();
    }

    /**
     * 判断作物是否枯萎
     * TODO:需判断是否已成熟
     * @param land
     * @return
     */
    private boolean withered(Land land) {
        //查询作物的最后浇水时间
        long lastWaterTime = getLastWaterTime(land);

        //没有查到最后浇水时间，即从未浇过水。则根据作物的种植时间判断
        if (lastWaterTime == -1) {
            lastWaterTime = land.getPlantTime().getTime();
        }
        //当前时间
        Date now = new Date();
        //时间差
        long diff = now.getTime() - lastWaterTime;

        long plantTime = land.getPlantTime().getTime();
        long matureTime = cropGrowService.getMatureTimeByCropId(land.getCropId());


//        return diff > 3600 * 1000 * 24 * 3;
        //如果超过3天没浇水，并且在成熟之前枯萎
        if((diff > 60 * 1000 * 60 * 24 * Constants.WITHERED_WATER_DAYS) &&
                ((lastWaterTime + 60 * 1000 * 60 * 24 * Constants.WITHERED_WATER_DAYS) < (plantTime + matureTime)
        )) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断作物是否成熟
     * TODO:需判断时候已枯萎
     * @param land
     * @return
     */
    public boolean matured(Land land) {
        //查询作物的最后浇水时间
        long lastWaterTime = getLastWaterTime(land);

        //没有查到最后浇水时间，即从未浇过水。则根据作物的种植时间判断
        if (lastWaterTime == -1) {
            lastWaterTime = land.getPlantTime().getTime();
        }

        //作物的种植时间
        Date plantTime = land.getPlantTime();
        if (plantTime == null) {
            return false;
        }

        //当前时间
        Date now = new Date();
        //时间差
        long diff = now.getTime() - plantTime.getTime();
        //作物成熟所需时间
        long matureTime = cropGrowService.getMatureTimeByCropId(land.getCropId());

        //达到成熟时间，并且在成熟之前3天内浇过水
        if((diff >= matureTime) && ((lastWaterTime + 60 * 1000 * 60 * 24 * Constants.WITHERED_WATER_DAYS
                > (plantTime.getTime() + matureTime)))){
            return true;
        } else {
            return false;
        }
    }

    public boolean updateAllStatus(int status) {
        return landMapper.updateAllStatus(status);
    }

    public long getPlantTime(long landId) {
        String plantTimeStr = cacheService.get(Constants.REDIS_LAND_PLANT_TIME_KEY + landId);

        long plantTime = 0;
        if (StringUtils.isNotBlank(plantTimeStr)) {
            plantTime = Long.parseLong(plantTimeStr);
        } else {
            Land land = getLandById(landId);
            if (land != null) {
                return land.getPlantTime().getTime();
            }
        }
        return plantTime;
    }

    public boolean updateLand(Land land) {
        return landMapper.updateByPrimaryKey(land) == 1;
    }

    /**
     * 查询作物的最后浇水时间
     * @param land
     * @return
     */
    private long getLastWaterTime(Land land) {
        String lastWaterTimeStr = cacheService.get(Constants.REDIS_LAND_LAST_WATER_TIME_KEY + land.getId());

        long lastWaterTime = -1;
        if (StringUtils.isNotBlank(lastWaterTimeStr)) {
            lastWaterTime = Long.parseLong(lastWaterTimeStr);
        } else {
            if (land.getLastWaterTime() != null) {
                lastWaterTime = land.getLastWaterTime().getTime();
            }
        }

        return lastWaterTime;
    }

    /**
     * 计算作物的成长阶段
     * @param land
     * @return
     */
    private int getGrowthStatus(Land land) {

        Date plantTime = land.getPlantTime();//作物的种植时间
        //如果土地的种植时间为空，或者cropid为空，说明作物未种植
        if (plantTime == null || land.getCropId() == null) {
            return -1;
        }
        Date now = new Date();//当前时间

        long diff = now.getTime() - plantTime.getTime();//时间差，即作物已成长时间

        //作物成熟所需时间
        long cropId = land.getCropId();
        List<CropGrow> growList = cropGrowService.getGrowInfoByCropId(cropId);

        long timeTemp = 0;
        for (CropGrow cropGrow : growList) {
            timeTemp += cropGrow.getTime();
            if (diff < timeTemp) {
                return cropGrow.getStage();
            }
        }
        //返回最后一个成长阶段
        return growList.get(growList.size() -1).getStage();
    }

    public List<PlantPojo> transFrom(List<Land> lands) {
        List<PlantPojo> plants = new ArrayList<PlantPojo>();
        for (Land land : lands) {
            if (land.getCropId() != null ) {
                plants.add(transFrom(land));
            }
        }
        return plants;
    }

    public boolean updateStatus(long landId, int status) {
        Land land = new Land();
        land.setId(landId);
        land.setStatus(status);
        land.setUpdateTime(new Date());
        return landMapper.updateByPrimaryKeySelective(land) == 1;
    }

    public boolean updateWaterStatus(long landId) {
        Land land = new Land();
        land.setId(landId);
        land.setStatus(LandStatus.NORMAL.getValue());
        land.setLastWaterTime(new Date());
        land.setUpdateTime(new Date());

        cacheService.set(Constants.REDIS_LAND_LAST_WATER_TIME_KEY + landId, land.getLastWaterTime().getTime() + "");
        return landMapper.updateByPrimaryKeySelective(land) == 1;
    }

    public boolean addYield(long landId, double yield) {
        return landMapper.addYieldById(landId, yield) == 1;
    }

    public Land getLandById(long id) {
        return landMapper.selectByPrimaryKey(id);
    }

    public int getMatureHours(long id) {
        //土地的种植时间需从redis中取
        long plantTime = getPlantTime(id);

        Date now = new Date();//当前时间

        long growTime = now.getTime() - plantTime;//时间差，即作物已成长时间

        //作物成熟所需时间
        Land land = landMapper.selectByPrimaryKey(id);
        long matureTime = cropGrowService.getMatureTimeByCropId(land.getCropId());

        //已成熟
        if (growTime >= matureTime) {
            return 0;
        } else {
            int hour = (int)(matureTime - growTime)/1000/3600;
            return hour + 1;
        }
    }

    public boolean updateHarvestLand(Land land) {
        land.setCropId(null);
        land.setStatus(LandStatus.PLANT.getValue());
        land.setExpectYield(0d);
        land.setYield(0d);
        land.setPlantTime(null);
        land.setLastWaterTime(null);
        land.setUpdateTime(new Date());

        //更新redis中土地的作物种植时间
        cacheService.set(Constants.REDIS_LAND_PLANT_TIME_KEY + land.getId(), "");

        return landMapper.updateByPrimaryKey(land) == 1;
    }
}
