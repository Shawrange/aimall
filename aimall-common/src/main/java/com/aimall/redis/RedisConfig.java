package com.aimall.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
public class RedisConfig<V> {
    @Bean("redisTemplate")
    public RedisTemplate<String, V> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, V> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        // 璁剧疆key鐨勫簭鍒楀寲鏂瑰紡
        template.setKeySerializer(RedisSerializer.string());
        // 璁剧疆value鐨勫簭鍒楀寲鏂瑰紡
        template.setValueSerializer(RedisSerializer.json());
        // 璁剧疆hash鐨刱ey鐨勫簭鍒楀寲鏂瑰紡
        template.setHashKeySerializer(RedisSerializer.string());
        // 璁剧疆hash鐨剉alue鐨勫簭鍒楀寲鏂瑰紡
        template.setHashValueSerializer(RedisSerializer.json());
        template.afterPropertiesSet();
        return template;
    }
}
