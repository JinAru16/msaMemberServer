package com.auth.auth.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@RequiredArgsConstructor

public class RedisConfig {
    private final RedisConnectionFactory redisConnectionFactory;


    @Value("${spring.data.redis.database}")
    private int authRedisIndex;

    @Bean
    @Primary  // ✅ 기본 RedisConnectionFactory로 사용
    public LettuceConnectionFactory redisConnectionFactory() {
        // ✅ 기존 `RedisStandaloneConfiguration`을 가져와서 DB Index만 변경
        LettuceConnectionFactory factory = (LettuceConnectionFactory) redisConnectionFactory;
        factory.setDatabase(authRedisIndex);
        return factory;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory()); // ✅ 위에서 설정한 Factory 사용
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        return redisTemplate;
    }
}