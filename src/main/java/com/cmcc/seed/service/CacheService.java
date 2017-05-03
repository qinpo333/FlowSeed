package com.cmcc.seed.service;

import java.util.List;
import java.util.Map;

public interface CacheService {

    /**
     * 添加key value
     *
     * @param key
     * @param value
     */
    public boolean set(String key, String value);


    /**
     * 获取redis value (String)
     *
     * @param key
     * @return
     */
    public String get(String key);



    /**
     * 取key对应的map中的某一属性
     * @param key cache的key名
     * @param field map中的key名字
     * @return
     */
    public String hget(String key, String field);

    public Map<String, String> hgetAll(String key);

    /**
     * set map型缓存
     * @param key
     * @param
     * @return
     */
    public boolean set(String key, Object obj);

    public boolean hset(String key, String fieldname, String value);

    public boolean hmset(String key, Map<String, String> hash);


    public Object getObj(String key);

    public Long incr(String key);

    public Long decr(String key);

    public Long del(String key);

    public boolean exists(String key);

    /**
     *
     * @param key
     * @param obj  需要序列化的对象
     * @param seconds 过期时间(秒)
     * @return
     */
    public boolean set(String key, Object obj, int seconds);

    Long rpush(String key, Object obj);

    String lindex(String key, int index);

    String lpop(String key);

    List lrange(String key, long start, long stop);
}
