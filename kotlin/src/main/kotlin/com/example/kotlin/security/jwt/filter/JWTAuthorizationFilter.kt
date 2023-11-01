package com.example.kotlin.security.jwt.filter

import com.example.kotlin.security.AuthConstants
import com.example.kotlin.security.jwt.provider.JWTProvider
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import java.util.*

class JWTAuthorizationFilter (
    private val jwtProvider: JWTProvider
): OncePerRequestFilter() {
    private val log = LoggerFactory.getLogger(this.javaClass)!!
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        log.info("===========JWTAuthorizationFilter============")

        val header = request.getHeader(AuthConstants.AUTH_HEADER)

        val token = jwtProvider.getTokenFromHeader(header)

        jwtProvider.isValidToken(token)

        val userEmail = jwtProvider.getUserEmailFromToken(token)

        val userRole = jwtProvider.getUserRoleFromToken(token)

        val autentication = UsernamePasswordAuthenticationToken(userEmail, null, Collections.singleton(SimpleGrantedAuthority(userRole)))

        SecurityContextHolder.getContext().authentication=autentication

        log.info("userEmail : {}", userEmail)
        log.info("userRole : {}", userRole)

        filterChain.doFilter(request, response)

    }
}