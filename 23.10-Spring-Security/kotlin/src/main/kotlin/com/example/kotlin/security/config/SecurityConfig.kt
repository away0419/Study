package com.example.kotlin.security.config

import com.example.kotlin.member.Role
import com.example.kotlin.security.jwt.filter.JWTAuthorizationFilter
import com.example.kotlin.security.oauth2.handler.OAuth2CustomSuccessHandler
import com.example.kotlin.security.oauth2.service.OAuth2CustomMemberService
import com.example.kotlin.security.provider.SecurityProvider
import com.example.kotlin.security.handler.SecurityCustomAccessDeniedHandler
import com.example.kotlin.security.handler.SecurityCustomAuthenticationEntryPoint
import lombok.RequiredArgsConstructor
import org.springframework.boot.autoconfigure.security.StaticResourceLocation
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter

@Configuration
@RequiredArgsConstructor
class SecurityConfig(
    private val oAuth2CustomMemberService: OAuth2CustomMemberService,
    private val oAuth2CustomSuccessHandler: OAuth2CustomSuccessHandler,
    private val jwtAuthorizationFilter: JWTAuthorizationFilter,
    private val securityProvider: SecurityProvider,
    private val securityCustomAuthenticationEntryPoint: SecurityCustomAuthenticationEntryPoint,
    private val securityCustomAccessDeniedHandler: SecurityCustomAccessDeniedHandler

) {
    private val staticResourcePatterns: Array<String> = StaticResourceLocation.values() // 정적 파일 경로
        .flatMap { it.patterns.toList() }
        .toTypedArray()
    private val anyoneUrls =
        securityProvider.createRequestMatchers("/", "/h2-console/**", "/login/oauth2/code/**", *staticResourcePatterns) // 누구나 접근 가능 url
    private val adminUrls = securityProvider.createRequestMatchers("/api/admin/**") // admin 접근 가능 url
    private val memberUrls = securityProvider.createRequestMatchers("/api/member/**") // member 접근 가능 url

    @Bean
    fun filterChain(http: HttpSecurity) = http
        .headers { it.frameOptions { it.disable() } }
        .csrf { it.disable() } // csrf off
        .cors { it.disable() } // cors off
        .formLogin { it.disable() } // security login page (UsernamePasswordAuthenticationFilter) off
        .httpBasic{it.disable()} // security Authentication (BasicAuthenticationFilter) off
        .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.NEVER) } //필요하다면 세션 생성. (API는 session이 필요 없지만 google 계정 정보를 가져오기 위해 session이 필요함)
        .authorizeHttpRequests { // 경로가 겹칠 경우 먼저 작성 된 룰을 따른다.
            it.requestMatchers(*anyoneUrls).permitAll() // 누구나 접근 가능. 단, 이는 인증이 필요 없을 뿐, JWT Filter를 거치므로 JWT Filter에서 해당 Url 처리를 해야 함.
                .requestMatchers(*memberUrls).hasAnyRole(Role.ROLE_USER.key, Role.ROLE_ADMIN.key) // 인증 된 가입자 중 해당 권한을 하나라도 가진 멤버만 접근 가능
                .requestMatchers(*adminUrls).hasRole(Role.ROLE_ADMIN.key) // 인증 된 가입자 중 관리자만 접근 가능
                .anyRequest().authenticated()
        } // 나머지 api 호출은 인증 받아야함
        .oauth2Login {
            it.userInfoEndpoint { point -> point.userService(oAuth2CustomMemberService) } // oauth2Login는 loadUser라는 함수를 호출하는게 기본임. 이를 custom하여 사용하는 것.
            it.successHandler(oAuth2CustomSuccessHandler) // 성공 시 핸들러
//            it.defaultSuccessUrl("/myspace") // 성공시 이동 페이지
//            it.failureUrl("/fail") // 실패시
        }
        .addFilterBefore(jwtAuthorizationFilter, BasicAuthenticationFilter::class.java)
        .exceptionHandling {
            it.authenticationEntryPoint(securityCustomAuthenticationEntryPoint) // 인증 관련 에러 처리
            it.accessDeniedHandler(securityCustomAccessDeniedHandler) // 인가 관련 에러 처리
        }
        .build()!!
}