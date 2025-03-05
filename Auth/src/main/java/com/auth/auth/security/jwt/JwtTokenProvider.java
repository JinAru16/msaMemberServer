package com.auth.auth.security.jwt;

import com.auth.auth.common.exception.UserException;
import com.auth.auth.config.JwtConfig;
import com.auth.auth.user.domain.entity.Users;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


import java.nio.charset.StandardCharsets;
import java.util.Date;


@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    private final JwtConfig jwtConfig;
    private final RedisTemplate redisTemplate;

    public String generateAuthToken(Authentication authentication) {
        Users user = (Users) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim("role", user.getRole())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getExpirationTime()))
                .signWith(SignatureAlgorithm.HS256, jwtConfig.getSecretKey())
                .compact();
    }

    public String generateNicknameToken(String nickname) {
        return Jwts.builder()
                .setSubject(nickname)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getExpirationTime()))
                .signWith(SignatureAlgorithm.HS256, jwtConfig.getSecretKey())
                .compact();
    }

    public String getUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(jwtConfig.getSecretKey().getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public Date getExpirationTime(String token) {
        return getClaims(token).getExpiration();
    }

    public boolean validateToken(String token) {
        try {
            if(redisTemplate.hasKey(token)){
                throw new UserException("로그아웃된 사용자");
            }
            Jwts.parser().setSigningKey(jwtConfig.getSecretKey()).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            throw new UserException(e.getMessage());
        }
    }

    // ✅ JWT에서 Claims(페이로드) 추출하는 메서드
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtConfig.getSecretKey())  // 서명 검증을 위한 키 설정
                .build()
                .parseClaimsJws(token)  // JWT 파싱 및 검증
                .getBody();  // Claims (페이로드) 반환
    }
}