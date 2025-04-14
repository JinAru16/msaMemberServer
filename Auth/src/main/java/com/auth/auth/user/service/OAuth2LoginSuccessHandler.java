package com.auth.auth.user.service;

import com.auth.auth.security.jwt.JwtTokenProvider;
import com.auth.auth.user.domain.response.LoginResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        Object principal = authentication.getPrincipal();

        String token = jwtTokenProvider.generateAuthToken(authentication);

        ResponseCookie jwtCookie = ResponseCookie.from("jwt", token)
                .httpOnly(true)// js에서 접근 불가능(xss 방어)
                .secure(false) // true면 https에서만 사용 가능. 운영은 true로
                .path("/") //모든 경로에서 사용가능
                .maxAge(3600)
                .sameSite("Strict") //Csrf 방어
                .build();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, jwtCookie.toString());

        response.addHeader(jwtCookie.getName(), jwtCookie.getValue());
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"response\":\"SUCCESS\"}");
        response.setHeader("jwt", jwtCookie.toString());


    }

}
