package com.example.kotlin.member.controller

import com.example.kotlin.common.logger
import com.example.kotlin.security.AuthConstants
import com.example.kotlin.security.exception.SecurityCustomErrorCode
import com.example.kotlin.security.exception.SecurityCustomException
import com.example.kotlin.security.jwt.provider.JWTProvider
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/login")
class LoginController(
    private val jwtProvider: JWTProvider
) {
    private val log = logger()

    @GetMapping("/success")
    fun login(): String? {
        return "login success"
    }

    @PostMapping("/token/issuance")
    fun issuance(request: HttpServletRequest, response: HttpServletResponse): String {
        log.info("========={}=========", request.requestURI)

        val cookies = request.cookies
        var refreshToken = jwtProvider.getRefreshToken(cookies)

        jwtProvider.getClaimsFromToken(refreshToken)

        val header = request.getHeader(AuthConstants.AUTH_HEADER) ?: throw SecurityCustomException(
            SecurityCustomErrorCode.JWT_AUTH_HEADER_IS_NOT_FOUND
        )
        var accessToken = jwtProvider.getTokenFromHeader(header)
        val memberPrincipal =
            jwtProvider.getMemberPrincipalFromToken(accessToken)

        accessToken = jwtProvider.generateJwtToken(memberPrincipal)
        refreshToken = jwtProvider.generateRefreshToken()
        val responseCookie = jwtProvider.generateRefreshTokenCookie(refreshToken)
        response.addHeader(AuthConstants.AUTH_HEADER, AuthConstants.TOKEN_TYPE + accessToken)
        response.addHeader(AuthConstants.COOKIE_HEADER, responseCookie.toString())
        return "issuance success"
    }
}

