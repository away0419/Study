package com.example.kotlin.security.oauth2.handler

import com.example.kotlin.common.logger
import com.example.kotlin.security.AuthConstants
import com.example.kotlin.security.exception.SecurityCustomErrorCode
import com.example.kotlin.security.exception.SecurityCustomException
import com.example.kotlin.security.jwt.provider.JWTProvider
import com.example.kotlin.security.oauth2.OAuth2CustomUser
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.json.simple.JSONObject
import org.slf4j.LoggerFactory
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component

@Component
class OAuth2CustomSuccessHandler(
    private val jwtProvider: JWTProvider
) :AuthenticationSuccessHandler  {
    private val log = logger()

    override fun onAuthenticationSuccess(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authentication: Authentication?
    ) {
        log.info("========CustomSuccessHandelr=========")

        val principal = authentication?.principal ?: throw SecurityCustomException(SecurityCustomErrorCode.SECURITY_PRINCIPAL_IS_NULL) // 인증 완료한 객체의 principal을 가져옴. 이때 OAuth2의 principal에는 DefaultOAuth2User가 들어간다. (우리는 현재 개발자가 만든 OAuth2CustomUser가 들어 있음.)
        val oAuth2CustomUser = principal as OAuth2CustomUser // OAuth2CustomUser 형변환
        val memberPrincipal = oAuth2CustomUser.memberPrincipal // 인증 완료한 유저 정보
        val accessToken = jwtProvider.generateJwtToken(memberPrincipal) // access token 발급
        val refreshToken = jwtProvider.generateRefreshToken() // refresh token 발급
        val responseCookie = jwtProvider.generateRefreshTokenCookie(refreshToken) // refresh token 담은 쿠키 생성
        val data = mapOf("msg" to "login success", "code" to "200") // 로그인 성공 메시지
        val jsonObject = JSONObject(data) // Json 형태로 변환
        val print = response?.writer

        val authorization = authentication.authorities // 권한 목록
        val details = authentication.details // 기타 정보 (sessionID 등)
        val name = authentication.name // userNameAttributeName이 내부 로직을 통해 name에 들어간다.
        val attributes = oAuth2CustomUser.attributes // OAuth2CustomUser 있는 attributes 즉, Oauth2 로그인 시 해당 서비스가 보낸 정보들

        response?.addHeader(AuthConstants.AUTH_HEADER, AuthConstants.TOKEN_TYPE + accessToken)
        response?.addHeader(AuthConstants.COOKIE_HEADER, responseCookie.toString())
        response?.characterEncoding = "UTF-8";
        response?.contentType = "application/json";
        print?.print(jsonObject)
        print?.flush()
        print?.close()

        log.info("========CustomSuccessHandelr=========")
        log.info("authentication : {}", authentication)
        log.info("principal : {} ",principal)
        log.info("details : {} ",details)
        log.info("name : {} ",name)
        log.info("authorization : {} ",authorization)
        log.info ("authorities : {}", attributes)
        log.info ("memberPrincipal : {}", memberPrincipal)
        log.info ("accessToken : {}", accessToken)
        log.info ("refreshToken : {}", refreshToken)

        // CustomSuccessHandler 실행 되면 로그인 완료로 끝나야 하는데 JWT Filter 실행 됨.
        // 성공 시 요청 경로는 누구나 접근할 수 있게 설정 했지만, 현재 요청을 그대로 가져가는 것인지 헤더 값이 없다며 오류가 발생한다.
        // 추측컨데, 리다이렉트와 디스페쳐를 할 경우 미상의 api 요청이 발생하는 것 같다. 미상의 API는 따로 누구나 접근할 수 있게 설정하지 않아 JWT가 돌아 가는것 아닐까 하는 생각이 든다.
        // 찾아보니 성공 이후 http/localhost:8080/favicon.ico을 요청함. 따라서 JWT Filter가 발생하는데 이때, Header에 값이 없어 오류가 발생하는 거였음.
//        val requestDispatcher = request?.getRequestDispatcher("/api/v1/member/login/success")
//        requestDispatcher?.forward(request, response)
//        response?.sendRedirect("/api/v1/member/login/success")

    }
}