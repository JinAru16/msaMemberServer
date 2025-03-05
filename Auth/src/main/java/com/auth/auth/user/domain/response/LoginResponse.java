package com.auth.auth.user.domain.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpHeaders;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoginResponse {
    HttpHeaders headers;
    Nickname nickname;
}
