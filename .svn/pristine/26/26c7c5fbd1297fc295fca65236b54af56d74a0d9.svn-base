package com.cmcc.seed.service.impl;

import com.cmcc.seed.dao.CropMapper;
import com.cmcc.seed.model.Crop;
import com.cmcc.seed.service.CacheService;
import com.cmcc.seed.service.CropService;
import com.cmcc.seed.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by cmcc on 16/1/15.
 */
@Service
public class CropServiceImpl implements CropService {

    @Autowired
    private CropMapper cropMapper;

    @Autowired
    private CacheService cacheService;

    public Crop getCropById(Long id) {
        //从redis中查询作物信息
        Object object = cacheService.getObj(Constants.REDIS_CROPINFO_KEY + id);

        //如果redis中查不到，则查询数据库，并存入redis
        if (object == null) {
            Crop crop = cropMapper.selectByPrimaryKey(id);
            if (crop != null) {
                cacheService.set(Constants.REDIS_CROPINFO_KEY + id, crop);
            }
            return crop;
        }
        return (Crop)object;
    }
}
