package com.example.kotlin.security.oauth2.handler

import com.example.kotlin.security.AuthConstants
import com.example.kotlin.security.jwt.provider.JWTProvider
import com.example.kotlin.security.oauth2.CustomOAuth2User
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.json.simple.JSONObject
import org.slf4j.LoggerFactory
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler

class CustomSuccessHandler(
    private val jwtProvider: JWTProvider
) :AuthenticationSuccessHandler  {
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
        val customOAuth2User = principal as? CustomOAuth2User // 일단 DefaultOAuth2User로 형변환
        val attributes = customOAuth2User?.attributes // DefaultOAuth2User에 있는 attributes 즉, Oauth2 로그인 시 해당 서비스가 보낸 정보들
        val memberPrincipal = customOAuth2User?.memberPrincipal // 인증 완료한 유저 정보
        val accessToken = memberPrincipal?.let { jwtProvider.generateJwtToken(it) }
        val refreshToken = jwtProvider.generateRefreshToken()
        val responsecookie = jwtProvider.generateRefreshTokenCookie(refreshToken)
        val data = mapOf("msg" to "login success", "code" to "200")
        val jsonObject = JSONObject(data)
        val print = response?.writer

        response?.addHeader(AuthConstants.AUTH_HEADER, AuthConstants.TOKEN_TYPE + accessToken)
        response?.addHeader(AuthConstants.COOKIE_HEADER, responsecookie.toString())
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

        // 아래의 경우 보낼 수는 있는데 이러면 jwt filter를 한번 타게 된다. 왜그럴까?
        // handler가 실행 되면 따로 jwt filter가 작동 하지 않는것으로 알고 있는데 밑에 구문을 하면 왜 타는지 모르겠다.
        // 성공 시 요청 경로는 누구나 접근할 수 있게 설정 했지만, 현재 요청을 그대로 가져가는 것인지 헤더 값이 없다며 오류가 발생한다.
        // 추측컨데, 리다이렉트와 디스페쳐를 할 경우 미상의 api 요청이 발생하는 것 같다. 미상의 API는 따로 누구나 접근할 수 있게 설정하지 않아 JWT가 돌아 가는것 아닐까 하는 생각이 든다.

//        val requestDispatcher = request?.getRequestDispatcher("/api/v1/member/login/success")
//        requestDispatcher?.forward(request, response)
//        response?.sendRedirect("/api/v1/member/login/success")

    }
}