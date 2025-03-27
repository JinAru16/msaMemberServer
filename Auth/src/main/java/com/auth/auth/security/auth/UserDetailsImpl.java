package com.auth.auth.security.auth;

import com.msa.common.entity.Role;
import com.msa.common.entity.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Collection;
import java.util.List;

public class UserDetailsImpl implements UserDetails {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String nickname;
    private Role role;

    public UserDetailsImpl(Users usersByUsername) {
            this.id = usersByUsername.getId();
            this.username = usersByUsername.getUsername();
            this.password = usersByUsername.getPassword();
            this.email = usersByUsername.getEmail();
            this.nickname = usersByUsername.getNickname();
            this.role = usersByUsername.getRole();
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
