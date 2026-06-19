package com.aimall.redis;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component("redisUtils")
@Slf4j
public class RedisUtils<V> {

    @Resource
    private RedisTemplate<String, V> redisTemplate;

    /**
     * 鍒犻櫎缂撳瓨
     *
     * @param key 鍙互浼犱竴涓€?鎴栧涓?
     */
    public void delete(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete((Collection<String>) CollectionUtils.arrayToList(key));
            }
        }
    }

    public V get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 鏅€氱紦瀛樻斁鍏?
     *
     * @param key   閿?
     * @param value 鍊?
     * @return true鎴愬姛 false澶辫触
     */
    public boolean set(String key, V value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            log.error("璁剧疆redisKey:{},value:{}澶辫触", key, value);
            return false;
        }
    }

    /**
     * 鏅€氱紦瀛樻斁鍏ュ苟璁剧疆鏃堕棿
     *
     * @param key   閿?
     * @param value 鍊?
     * @param time  鏃堕棿(绉? time瑕佸ぇ浜? 濡傛灉time灏忎簬绛変簬0 灏嗚缃棤闄愭湡
     * @return true鎴愬姛 false 澶辫触
     */
    public boolean setex(String key, V value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            log.error("璁剧疆redisKey:{},value:{}澶辫触", key, value);
            return false;
        }
    }

    /**
     * set 鍏虫搷浣?
     */
    public void zsetAdd(String key, V value, double score) {
        redisTemplate.opsForZSet().add(key, value, score);
    }

    public Set<V> zsetRangeByScore(String key, double min, double max) {
        return redisTemplate.opsForZSet().rangeByScore(key, min, max);
    }

    public Long zsetAddRemove(String key, V v) {
        return redisTemplate.opsForZSet().remove(key, v);
    }

}

