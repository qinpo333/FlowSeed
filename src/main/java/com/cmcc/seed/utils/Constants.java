package com.cmcc.seed.utils;

/**
 * Created by cmcc on 16/1/17.
 */
public class Constants {

    //默认作物（暂时只有一个作物）
    public static final Long DEFAULT_CROP_ID = 1L;

    //浇水一次种子产量增加值
    public static final double WATER_FLOW = 0.3;

    //redis中用户信息key
    public static final String REDIS_USERINFO_KEY = "USERINFO_OPENID_";

    //redis中作物信息key
    public static final String REDIS_CROPINFO_KEY = "CROPINFO_ID_";

    //redis中作物成长阶段信息key
    public static final String REDIS_CROPGROWTH_KEY = "CROPGROWTH_ID_";

    //redis中作物成熟所需时间key
    public static final String REDIS_CROP_MATURE_TIME_KEY = "CROP_MATURE_TIME_ID_";

    //redis中土地中的作物种植时间key
    public static final String REDIS_LAND_PLANT_TIME_KEY = "LAND_PLANT_TIME_ID_";

    //redis中土地中的作物最后浇水时间key
    public static final String REDIS_LAND_LAST_WATER_TIME_KEY = "LAND_LAST_WATER_TIME_ID_";

    //redise中用户的好友列表key
    public static final String REDIS_FRIEND_LIST_KEY = "FRIEND_LIST_OPENID_";

    public static final String REDIS_CROPIDS = "CROPIDS";

    //收获获得经验值
    public static final int HAVEST_EXPERIENCE = 6;

    //浇水获得经验值
    public static final int WATER_EXPERIENCE = 1;

    //帮好友浇水获得经验值
    public static final int WATER_FRIEND_EXPERIENCE = 1;

    //邀请好友浇水获得经验值
    public static final int INVITE_FRIEND_WATER_EXPERIENCE = 2;

    //连续不浇水就枯萎的天数
    public static final int WITHERED_WATER_DAYS = 3;

    //测试代码关闭
    public static final boolean ISTEST = false;
}
