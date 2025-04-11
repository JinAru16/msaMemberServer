package com.auth.auth.security.auth;

import com.msa.common.entity.Role;
import com.msa.common.entity.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;


import java.util.Collection;
import java.util.List;
import java.util.Map;

public class UserDetailsImpl implements UserDetails, OAuth2User {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String nickname;
    private Role role;
    private Map<String, Object> attributes;

    // 일반 로그인용
    public UserDetailsImpl(Users usersByUsername) {
            this.id = usersByUsername.getId();
            this.username = usersByUsername.getUsername();
            this.password = usersByUsername.getPassword();
            this.email = usersByUsername.getEmail();
            this.nickname = usersByUsername.getNickname();
            this.role = usersByUsername.getRole();
    }

    // OAuth2를 이용한 구글로그인
    public UserDetailsImpl(Users user, Map<String, Object> attributes){
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.role = user.getRole();
        this.attributes = attributes;
    }


    @Override
    public String getName() {
        return this.username;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return Map.of();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.role));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    public String getRole(){
        return this.role.toString();
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }
}
