package com.auth.auth.user.service;

import com.auth.auth.security.jwt.JwtTokenProvider;
import com.auth.auth.user.domain.request.LoginRequest;
import com.auth.auth.user.domain.response.LoginResponse;
import com.auth.auth.user.domain.response.Nickname;
import com.auth.auth.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    private final UserRepository userRepository;

    public LoginResponse login(LoginRequest loginRequest) {


        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String nickname = userRepository.findUsersByUsername(loginRequest.getUsername()).get().getNickname();

        // JWT 발급
        String jwt = jwtTokenProvider.generateAuthToken(authentication);
        ResponseCookie jwtCookie = ResponseCookie.from("jwt", jwt)
                .httpOnly(true)// js에서 접근 불가능(xss 방어)
                .secure(false) // true면 https에서만 사용 가능. 운영은 true로
                .path("/") //모든 경로에서 사용가능
                .maxAge(3600)
                .sameSite("Strict") //Csrf 방어
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, jwtCookie.toString());

        return new LoginResponse(headers, new Nickname(nickname));

    }

    public ResponseCookie logout(String jwt) {
        Date expirationDate = jwtTokenProvider.getExpirationTime(jwt);
        long expirationMillis = expirationDate.getTime() - System.currentTimeMillis();

        if (expirationMillis > 0) {
            redisTemplate.opsForValue().set(jwt, "blacklisted", expirationMillis, TimeUnit.MILLISECONDS);
        }

        // ✅ 쿠키 삭제 (클라이언트에서 JWT 제거)
        ResponseCookie jwtCookie = ResponseCookie.from("jwt", "")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0)  // 쿠키 즉시 삭제
                .sameSite("Strict")
                .build();

        return jwtCookie;
    }
}
