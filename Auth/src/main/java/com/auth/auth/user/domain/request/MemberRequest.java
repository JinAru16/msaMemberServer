package com.auth.auth.user.domain.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MemberRequest {
    private String username;
    private String password;
    private String nickname;
    private String email;
}
