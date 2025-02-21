package com.auth.auth.user.controller;


import com.auth.auth.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final UserService loginService;

    @PostMapping("/login")
    public ResponseEntity<?> login(){
        return null;
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(){
        return null;
    }
}
