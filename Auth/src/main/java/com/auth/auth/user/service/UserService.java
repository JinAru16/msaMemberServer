package com.auth.auth.user.service;

import com.auth.auth.common.exception.UserException;
import com.auth.auth.user.domain.Role;
import com.auth.auth.user.domain.entity.Users;
import com.auth.auth.user.domain.request.MemberRequest;
import com.auth.auth.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Users register(MemberRequest request) {
       if(userRepository.findUsersByUsername(request.getUsername()).isPresent()){
           throw new UserException("존재하는 사용자입니다.");
        }
       Users users = Users.builder()
               .username(request.getUsername())
               .password(passwordEncoder.encode(request.getPassword()))
               .email(request.getEmail())
               .role(Role.USER)
               .build();
        return userRepository.save(users);
    }
}
