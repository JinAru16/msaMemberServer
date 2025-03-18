package com.auth.auth.config;

import com.auth.auth.security.jwt.JwtAuthenticationFilter;
import com.auth.auth.security.jwt.JwtTokenProvider;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Configuration
@Getter
@Setter
public class JwtConfig {
    @Value("${jwt.secretKey}")
    private String secretKey;

    @Value("${jwt.expirationTime}")
    private long expirationTime; // 밀리초 단위 (예: 3600000 = 1시간)

    @Value("${jwt.tokenPrefix}")
    private String tokenPrefix;

    @Value("${jwt.header}")
    private String header;

    @PostConstruct
    public void init() {
        this.secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8));
    }
}