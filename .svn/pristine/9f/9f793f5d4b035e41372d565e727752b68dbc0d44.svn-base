package com.cmcc.seed.service.impl;

import com.cmcc.seed.dao.CropGrowMapper;
import com.cmcc.seed.model.CropGrow;
import com.cmcc.seed.service.CacheService;
import com.cmcc.seed.service.CropGrowService;
import com.cmcc.seed.utils.Constants;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by cmcc on 16/1/17.
 */
@Service
public class CropGrowServiceImpl implements CropGrowService {

    @Autowired
    private CropGrowMapper cropGrowMapper;

    @Autowired
    private CacheService cacheService;

    public List<CropGrow> getGrowInfoByCropId(long cropId) {
        //从redis中查询作物的成长阶段信息
        List<CropGrow> list = (List<CropGrow>)cacheService.getObj(Constants.REDIS_CROPGROWTH_KEY + cropId);

        //如果redis中查不到，则查询数据库，并存入redis
        if (list == null) {
            list = cropGrowMapper.selectByCropId(cropId);
            if (list != null && list.size() > 0) {
                cacheService.set(Constants.REDIS_CROPGROWTH_KEY + cropId, list);
            }
        }
        return list;
    }

    public long getMatureTimeByCropId(long cropId) {
        //从redis中查询作物成熟所需时间
        String timeStr = cacheService.get(Constants.REDIS_CROP_MATURE_TIME_KEY + cropId);

        long time = 0;
        if (StringUtils.isNotBlank(timeStr)) {
            time = Long.parseLong(timeStr);
        } else {
            //如果redis中查不到，则查询数据库，并存入redis
            //查询作物的成长阶段信息，并计算成熟时间
            List<CropGrow> list = getGrowInfoByCropId(cropId);
            for (CropGrow cropGrow : list) {
                time += cropGrow.getTime();
            }

            //存入redis
            cacheService.set(Constants.REDIS_CROP_MATURE_TIME_KEY + cropId, time + "");
        }
        return time;
    }
}
