package com.auth.auth.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@RequiredArgsConstructor

public class RedisConfig {
    private final RedisConnectionFactory redisConnectionFactory;


    @Value("${spring.data.redis.database}")
    private int authRedisIndex;

    @Bean
    public RedisTemplate<String, String> redisTemplate() {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory(authRedisIndex));
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());

        return redisTemplate;
    }

    // ✅ 특정 DB Index를 사용하는 RedisConnectionFactory 생성
    private RedisConnectionFactory connectionFactory(int databaseIndex) {
        LettuceConnectionFactory factory = (LettuceConnectionFactory) redisConnectionFactory;

        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(factory.getHostName());
        config.setPort(factory.getPort());
        config.setDatabase(databaseIndex);

        return new LettuceConnectionFactory(config);
    }
}