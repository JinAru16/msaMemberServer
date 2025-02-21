package com.auth.auth.user.service;

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
       if(userRepository.findUsersByUserName(request.getUsername()).isPresent()){
           System.out.println("유저가 존재");
        }
       Users users = Users.builder()
               .userName(request.getUsername())
               .password(passwordEncoder.encode(request.getPassword()))
               .email(request.getEmail())
               .role(Role.USER)
               .build();
        return userRepository.save(users);
    }
}
