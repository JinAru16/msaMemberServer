package com.auth.auth.user.service;

import com.auth.auth.common.exception.GoogleUserException;
import com.auth.auth.common.exception.UserException;
import com.auth.auth.security.auth.UserDetailsImpl;
import com.auth.auth.user.repository.UserRepository;
import com.msa.common.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // 여기서 userRequest.getClientRegistration().getRegistrationId() 로 구글인지 네이버인지 구분 가능
        // 사용자 정보 추출 및 DB 저장 로직 등 처리


//        System.out.println("getClientRegistration: "+userRequest.getClientRegistration());
//        System.out.println("getAccessToken: "+userRequest.getAccessToken());
//        System.out.println("getAttributes: "+super.loadUser(userRequest).getAttributes());

        String email = oAuth2User.getAttribute("email");



        OAuth2User oAuth2User2 = super.loadUser(userRequest);
        Optional<Users> users = userRepository.findUsersByEmail(email);

        if (users.isPresent()) {
            // 토큰발급은 OAuth2LoginSuccessHandler에서 진행.
            return new UserDetailsImpl(users.get(),oAuth2User2.getAttributes());
        }
        else{
            throw new GoogleUserException("회원가입이 필요합니다. 관리자에게 문의하세요");
        }

    }
}