package com.example.kotlin.security.oauth2.handler

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component

@Component
class CustomSuccessHandler() :AuthenticationSuccessHandler  {
    private val log = LoggerFactory.getLogger(this.javaClass)!!
    override fun onAuthenticationSuccess(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authentication: Authentication?
    ) {

        val principal = authentication?.principal // 인증 완료한 객체의 principal을 가져옴. 이때 OAuth2의 principal에는 DefaultOAuth2User가 들어간다.
        val authorization = authentication?.authorities // 권한 목록
        val details = authentication?.details // 기타 정보
        val name = authentication?.name // DefaultOAuth2User 에 있는 userNameAttributeName 인듯
        val defaultOAuth2User = principal as? DefaultOAuth2User // 일단 DefaultOAuth2User로 형변환
        val authorities = defaultOAuth2User?.attributes // DefaultOAuth2User에 있는 attributes 즉, Oauth2 로그인 시 받아온 유저 정보

        log.info("========CustomSuccessHandelr=========")
        log.info("authentication : {}",authentication)
        log.info("principal : {} ",principal)
        log.info("details : {} ",details)
        log.info("name : {} ",name)
        log.info("authorization : {} ",authorization)
        log.info ("authorities : {}", authorities)



        response?.sendRedirect("/page")
    }
}