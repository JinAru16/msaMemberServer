package com.auth.auth.config;
import com.auth.auth.security.auth.UserDetailsImpl;
import com.auth.auth.security.jwt.JwtAuthenticationFilter;
import com.auth.auth.security.jwt.JwtTokenProvider;
import com.auth.auth.user.repository.UserRepository;
import com.auth.auth.user.service.PrincipalOauth2UserService;
import com.msa.common.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserRepository repository;
    private final CorsFilter corsFilter; // 🔥 CORS 필터 주입 (Spring Security 6.x 이후 방식)
    private final JwtTokenProvider jwtTokenProvider; // JWT 토큰 관리
    private final RedisTemplate<String, Object> redisTemplate;

    @Bean
    public UserDetailsService userDetailsService() { // ✅ UsersRepository 주입
        return username -> {
            Users user = repository.findUsersByUsername(username);
            if (user == null) {
                throw new UsernameNotFoundException("해당 사용자가 존재하지 않습니다: " + username);
            }
            return new UserDetailsImpl(user);
        };
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtTokenProvider, userDetailsService(), redisTemplate); // ✅ 직접 생성
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, PrincipalOauth2UserService principalOauth2UserService) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // CSRF 비활성화
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class) // CORS 필터 추가
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/oauth2/**", "/login/**").permitAll() // ✅ 추가
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/user/**").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo.userService(principalOauth2UserService))
                        .defaultSuccessUrl("/", true))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class); // JWT 필터 추가

        return http.build();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService()); // ✅ UserDetailsService 등록
        authProvider.setPasswordEncoder(passwordEncoder()); // ✅ 비밀번호 암호화 방식 등록
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}