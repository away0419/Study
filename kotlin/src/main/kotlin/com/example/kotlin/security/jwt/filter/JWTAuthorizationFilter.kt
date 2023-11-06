package com.example.kotlin.security.jwt.filter

import com.example.kotlin.common.logger
import com.example.kotlin.security.AuthConstants
import com.example.kotlin.security.exception.SecurityCustomErrorCode
import com.example.kotlin.security.exception.SecurityCustomException
import com.example.kotlin.security.jwt.provider.JWTProvider
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.util.*

@Component
class JWTAuthorizationFilter(
    private val jwtProvider: JWTProvider
) : OncePerRequestFilter() {
    private val log = logger()

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        log.info("===========JWTAuthorizationFilter============")
        try {
            val header = request.getHeader(AuthConstants.AUTH_HEADER) // 개발자가 지정한 헤더 추출

            if (header == null) { // 개발자가 지정한 헤더가 없는 경우 넘김. (인증 정보가 저장 되지 않아서 넘겨도 상관 없음)
                log.info(SecurityCustomErrorCode.JWT_AUTH_HEADER_IS_NOT_FOUND.msg);
                filterChain.doFilter(request, response)
                return
            }

            val token = jwtProvider.getTokenFromHeader(header)
            val userEmail = jwtProvider.getUserEmailFromToken(token)
            val userRole = jwtProvider.getUserRoleFromToken(token)
            val authentication = UsernamePasswordAuthenticationToken(   // 인증 완료 된 객체 생성
                userEmail, null, Collections.singleton(
                    SimpleGrantedAuthority(userRole)
                )
            )

            SecurityContextHolder.getContext().authentication = authentication // 인증 완료 된 객체 저장

            log.info("userEmail : {}", userEmail)
            log.info("userRole : {}", userRole)
        } catch (e: SecurityCustomException) { // 만약 에러 발생한 경우 request에 담아 넘김. 이후 AuthenticationEntryPoint에서 확인함.
            request.setAttribute("securityCustomException", e)
        }

        filterChain.doFilter(request, response)
    }
}