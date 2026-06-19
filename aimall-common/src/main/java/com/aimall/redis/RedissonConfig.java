package com.aimall.redis;

import io.lettuce.core.RedisConnectionException;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RedissonConfig {

    @Value("${spring.data.redis.host:}")
    private String redisHost;

    @Value("${spring.data.redis.port:}")
    private Integer redisPort;

    @Bean(name = "redissonClient", destroyMethod = "shutdown")
    public RedissonClient redissonClient() {
        try {
            // 鍒涘缓閰嶇疆 鎸囧畾redis鍦板潃鍙婅妭鐐逛俊鎭?
            Config config = new Config();
            config.useSingleServer().setAddress("redis://" + redisHost + ":" + redisPort);
            // 鏍规嵁config鍒涘缓鍑篟edissonClient瀹炰緥
            RedissonClient redissonClient = Redisson.create(config);
            return redissonClient;
        } catch (RedisConnectionException e) {
            log.error("redis閰嶇疆閿欒锛岃妫€鏌edis閰嶇疆");
        }
        return null;
    }
}

