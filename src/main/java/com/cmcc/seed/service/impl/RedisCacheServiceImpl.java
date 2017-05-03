package com.cmcc.seed.service.impl;

import com.cmcc.seed.service.CacheService;
import com.cmcc.seed.utils.SerializerUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.*;

import java.util.List;
import java.util.Map;

/**
 * 提供redis服务，使用Jedis的shard分片。
 */
@Service(value = "redisService")
public class RedisCacheServiceImpl implements CacheService {

    private Logger log = Logger.getLogger(RedisCacheServiceImpl.class);
    @Autowired
    private ShardedJedisPool shardedJedisPool ;


    private ShardedJedis getShardedJedis(){
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
        } catch (Exception e){
            log.error(e.getMessage(), e);
        }
        return jedis;
    }
    public boolean set(String key, String value) {
        ShardedJedis jedis = getShardedJedis();
        if (jedis == null) return false;
        try {
            jedis.set(key, (String) value);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        } finally {
            shardedJedisPool.returnResource(jedis);
        }

    }


    /**
     */
    public String get(String key) {
        ShardedJedis jedis = getShardedJedis();
        if (jedis == null) return null;
        try {
            return (String) jedis.get(key);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            shardedJedisPool.returnResource(jedis);
        }
        return null;
    }

    /**
     */

    public String hget(String key, String field) {
        String result = null;
        ShardedJedis jedis = getShardedJedis();
        if (jedis == null) {
            return result;
        }
        try {
            result = jedis.hget(key, field);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            shardedJedisPool.returnResource(jedis);
        }
        return result;
    }

    public boolean hset(String key, String fieldname, String value) {
        ShardedJedis jedis = getShardedJedis();
        if (jedis == null) return false;
        boolean ifSucc = true;
        try {
            jedis.hset(key, fieldname, value);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            ifSucc = false;
        } finally {
            shardedJedisPool.returnResource(jedis);
        }
        return ifSucc;
    }

    public boolean hmset(String key, Map<String, String> hash){
        ShardedJedis jedis = getShardedJedis();
        if (jedis == null) return false;
        boolean ifSucc = true;
        try {
            jedis.hmset(key, hash);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            ifSucc = false;
        } finally {
            shardedJedisPool.returnResource(jedis);

        }
        return ifSucc;
    }

    public Object getObj(String key){
        ShardedJedis jedis = getShardedJedis();
        if (jedis == null) return null;
        try {
            return SerializerUtil.deserialize(jedis.get(key.getBytes()));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally{
            shardedJedisPool.returnResource(jedis);
        }
        return null;
    }

    /**
     * 提取map中所有的值
     * @param key
     * @return
     */
    public Map<String, String> hgetAll(String key) {
        Map<String, String> result = null;
        ShardedJedis jedis = getShardedJedis();
        if (jedis == null) return null;
        try {
            result = jedis.hgetAll(key);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            shardedJedisPool.returnResource(jedis);
        }
        return result;
    }

    public boolean set(String key, Object obj){
        ShardedJedis jedis = getShardedJedis();
        if (jedis == null) return false;
        try {
            jedis.set(key.getBytes(), SerializerUtil.serialize(obj));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        } finally {
            shardedJedisPool.returnResource(jedis);
        }
        return true;
    }

    /**
     * @see CacheService#set(String, Object, int)
     */
    public boolean set(String key, Object obj, int seconds){
        ShardedJedis jedis = getShardedJedis();
        if (jedis == null) return false;
        try {
            jedis.set(key.getBytes(), SerializerUtil.serialize(obj));
            jedis.expire(key.getBytes(), seconds);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        } finally {
            shardedJedisPool.returnResource(jedis);
        }
        return true;
    }

    public Long incr(String key){
        ShardedJedis jedis = getShardedJedis();
        if (jedis == null) return null;
        try {
            Long num = jedis.incr(key);
            return num;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        } finally {
            shardedJedisPool.returnResource(jedis);
        }
    }

    public Long decr(String key){
        ShardedJedis jedis = getShardedJedis();
        if (jedis == null) return null;
        try {
            Long num = jedis.decr(key);
            return num;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        } finally {
            shardedJedisPool.returnResource(jedis);
        }
    }

    /**
     * key是否存在
     * @param key
     * @return
     */
    public boolean exists(String key) {
        Boolean result = false;
        ShardedJedis jedis = getShardedJedis();
        if (jedis == null) return false;
        try {
            result = jedis.exists(key);
            return result.booleanValue();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return result;
        } finally {
            shardedJedisPool.returnResource(jedis);
        }
    }

    public Long del(String key) {
        Long result = null;
        ShardedJedis jedis = getShardedJedis();
        if (jedis == null) return null;
        try {
            result = jedis.del(key);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            shardedJedisPool.returnResource(jedis);
        }
        return result;
    }

    public Long rpush(String key, Object obj) {
        ShardedJedis jedis = getShardedJedis();
        if (jedis == null) {
            return null;
        }

        Long tmp = null;
        try {
            tmp = jedis.lpush(key.getBytes(), SerializerUtil.serialize(obj));

        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            shardedJedisPool.returnResource(jedis);
        }
        return tmp;
    }

    public String lindex(String key, int index) {
        ShardedJedis jedis = getShardedJedis();
        if (jedis == null) {
            return null;
        }

        String tmp = null;
        try {
            tmp = jedis.lindex(key, index);

        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            shardedJedisPool.returnResource(jedis);
        }
        return tmp;
    }

    public String lpop(String key) {
        ShardedJedis jedis = getShardedJedis();
        if (jedis == null) {
            return null;
        }

        String tmp = null;
        try {
            tmp = jedis.lpop(key);

        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            shardedJedisPool.returnResource(jedis);
        }
        return tmp;
    }

    public List lrange(String key, long start, long stop) {
        ShardedJedis jedis = getShardedJedis();
        if (jedis == null) {
            return null;
        }

        List list = null;
        try {
            list = jedis.lrange(key, start, stop);

        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            shardedJedisPool.returnResource(jedis);
        }
        return list;
    }

    public void setShardedJedisPool(ShardedJedisPool shardedJedisPool) {
        this.shardedJedisPool = shardedJedisPool;
    }

    public ShardedJedisPool getShardedJedisPool() {
        return shardedJedisPool;
    }
}
