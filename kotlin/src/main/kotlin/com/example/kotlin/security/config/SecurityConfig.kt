package com.example.kotlin.security.config

import com.example.kotlin.member.Role
import com.example.kotlin.security.jwt.filter.JWTAuthorizationFilter
import com.example.kotlin.security.jwt.provider.JWTProvider
import com.example.kotlin.security.oauth2.handler.CustomSuccessHandler
import com.example.kotlin.security.oauth2.service.CustomOAuth2MemberService
import lombok.RequiredArgsConstructor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

@Configuration
@RequiredArgsConstructor
class SecurityConfig(
    private val customOAuth2MemberService: CustomOAuth2MemberService,
    private val jwtProvider: JWTProvider
) {
    private val urls = arrayOf(
        AntPathRequestMatcher("/"),
        AntPathRequestMatcher("/h2-console/**"),
        AntPathRequestMatcher("/api/v1/member/signup"),
        AntPathRequestMatcher("/api/v1/member/login/success"),
        AntPathRequestMatcher("/api/member/oauth2/**"),
        AntPathRequestMatcher("/login/oauth2/code/**")
    )
    private val memberUrls = arrayOf(AntPathRequestMatcher("/api/v1/member/**"))
    private val adminUrls = arrayOf(AntPathRequestMatcher("/api/v1/admin/**"))

    fun customSuccessHandler(): CustomSuccessHandler {
        return CustomSuccessHandler(jwtProvider)
    }

    fun jwtAuthorizationFilter(): JWTAuthorizationFilter {
        return JWTAuthorizationFilter(jwtProvider)
    }

    @Bean
    fun filterChain(http: HttpSecurity) = http
        .headers { it.frameOptions { it.disable() } }
        .csrf { it.disable() } // csrf off
        .cors { it.disable() } // cors off
        .formLogin { it.disable() } // security login page (UsernamePasswordAuthenticationFilter) off
        .httpBasic{it.disable()} // security Authentication (BasicAuthenticationFilter) off
        .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.NEVER) } //필요하다면 세션 생성. (API는 session이 필요 없지만 google 계정 정보를 가져오기 위해 session이 필요함)
        .authorizeHttpRequests { // 경로가 겹칠 경우 먼저 작성 된 룰을 따른다.
            it.requestMatchers(*urls).permitAll() // [signup, login] 누구나 접근 가능
                .requestMatchers(*memberUrls).hasAnyRole(Role.ROLE_USER.key, Role.ROLE_ADMIN.key) // 가입자만 접근 가능
                .requestMatchers(*adminUrls).hasRole(Role.ROLE_ADMIN.key) // 관리자만 접근 가능
                .anyRequest().authenticated()
        } // 나머지 api 호출은 인증 받아야함
        .oauth2Login {
            it.userInfoEndpoint { point -> point.userService(customOAuth2MemberService) } // oauth2Login는 loadUser라는 함수를 호출하는게 기본임. 이를 custom하여 사용하는 것.
            it.successHandler(customSuccessHandler()) // 성공 시 핸들러
//            it.defaultSuccessUrl("/myspace") // 성공시 이동 페이지
//            it.failureUrl("/fail") // 실패시
        }
        .addFilterBefore(jwtAuthorizationFilter(), BasicAuthenticationFilter::class.java)
        .exceptionHandling { it.authenticationEntryPoint(LoginUrlAuthenticationEntryPoint("/login")) } // 인증 되지 않은 사용자가 접근시 login으로 이동
        .build()!!
}