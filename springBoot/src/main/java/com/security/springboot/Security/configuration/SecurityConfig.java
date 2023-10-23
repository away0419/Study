package com.security.springboot.Security.configuration;

import com.security.springboot.Security.Provider.CustomAuthenticationProvider;
import com.security.springboot.Security.filter.CustomAuthenticationFilter;
import com.security.springboot.Security.handler.CustomLoginFailureHandler;
import com.security.springboot.Security.handler.CustomLoginSuccessHandler;
import com.security.springboot.jwt.JwtAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    // 패스워드 인코더
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // [STEP.01] customAuthenticationProvider 생성
    @Bean
    public CustomAuthenticationProvider customAuthenticationProvider() {
        return new CustomAuthenticationProvider();
    }

    // [STEP.02] authenticationManager 생성
    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(customAuthenticationProvider());
    }

    // [STEP.03] CustomLoginFailureHandler 생성
    @Bean
    public CustomLoginFailureHandler customLoginFailureHandler() {
        return new CustomLoginFailureHandler();
    }

    // [STEP.04] CustomLoginFailureHandler 생성
    @Bean
    public CustomLoginSuccessHandler customLoginSuccessHandler() {
        return new CustomLoginSuccessHandler();
    }

    // [STEP.05] customAuthenticationFilter 생성
    @Bean
    public CustomAuthenticationFilter customAuthenticationFilter() {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManager());
        customAuthenticationFilter.setFilterProcessesUrl("/api/v1/user/login");     // 접근 URL
        customAuthenticationFilter.setAuthenticationSuccessHandler(customLoginSuccessHandler());    // '인증'(로그인) 성공 시 해당 핸들러로 처리를 전가한다.
        customAuthenticationFilter.setAuthenticationFailureHandler(customLoginFailureHandler());    // '인증'(로그인) 실패 시 해당 핸들러로 처리를 전가한다.
        customAuthenticationFilter.afterPropertiesSet();
        return customAuthenticationFilter;
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter(){
        return new JwtAuthorizationFilter();
    }

    // [STEP.06] filterChain 생성
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable) // csrf 공격 보호 옵션 끄기. (rest api 에서는 필요 없기 때문)
                .cors(AbstractHttpConfigurer::disable) // cors 예방 옵션 끄기
                .headers(AbstractHttpConfigurer::disable) // h2 접근을 위해 사용. 다른 db 사용시 제거
                .formLogin(AbstractHttpConfigurer::disable) // form bases authentication 비활성화 (기본 로그인 페이지 비활성화, UsernamePasswordAuthenticationFilter 비활성화, rest api만 작성하기 때문에 필요없음.)
                .httpBasic(AbstractHttpConfigurer::disable) // http basic authentication 비활성화 (기본 로그인 인증창 비활성화, BasicAuthenticationFilter 비활성화, rest api만 작성하기 때문에 필요없음.)
                .authorizeHttpRequests(request->
                    request.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll() // 정적 자원 경로 인증, 권한 상관없이 누구나 접근 허용
                            .requestMatchers(new AntPathRequestMatcher("/"),new AntPathRequestMatcher("/swagger-ui/**"),new AntPathRequestMatcher("/h2-console/**")).permitAll() // 해당 페이지 인증, 권한 상관 없이 누구나 접근 허용
                            .requestMatchers(new AntPathRequestMatcher("/api/v1/admin/**")).hasRole("ADMIN") // 해당 페이지는 인증된 사람 중 ADMIN 권한이 있는 자만 접근 허용. 여기서 Enum엔 ROLE_ADMIN으로 되어있는데 ROLE_이 자동으로 앞에 붙기 때문에 Enum에서 ROLE_을 앞에 붙힌것이다.
                            .requestMatchers(new AntPathRequestMatcher("/api/v1/user/**")).hasAnyRole("ADMIN", "USER") // 해당 페이지 인증된 사랑 중 ADMIN 또는 USER 권한이 있는 자만 접근 허용.
                            .anyRequest().authenticated() // 나머지 페이지는 권한 상관없이 인증된 사람만 접근 가능.
                ) // 특정 페이지 접근 시 사용자 권한 확인 설정
                .sessionManagement(session -> session // session 기반이 아닌 jwt token 기반일 경우 stateless 설정
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class) // UsernamePasswordAuthenticationFilter 실행 전 순서에 커스텀 필터 추가
                .addFilterBefore(jwtAuthorizationFilter(), BasicAuthenticationFilter.class) // JWT 필터 추가
                .build();
    }



}
