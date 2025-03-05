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
    public String username;
    public String password;
    public String nickname;
    public String email;
}
