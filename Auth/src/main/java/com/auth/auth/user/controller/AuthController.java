package com.auth.auth.user.controller;

import com.auth.auth.user.domain.request.LoginRequest;
import com.auth.auth.user.domain.response.LoginResponse;
import com.auth.auth.user.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Validated LoginRequest loginRequest){

        LoginResponse login = authService.login(loginRequest);

        return ResponseEntity
                .ok()
                .headers(login.getHeaders())
                .body("LOGIN_SUCCESS");
    }

    @PutMapping("/logout")
    public ResponseEntity<?> logout(@CookieValue(name="jwt", required=false) @Validated String jwt){
        // 블랙리스트에 추가하는 방식으로 로그아웃 구성.
        ResponseCookie cookie = authService.logout(jwt);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body("LOGOUT SUCCESS");

    }
}
