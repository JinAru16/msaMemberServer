package com.auth.auth.member.controller;

import com.auth.auth.member.domain.request.MemberRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/register")
public class MemberController {

    @PostMapping("/add/member")
    public ResponseEntity addMember(@RequestBody MemberRequest request) {
        return null;
    }
}
