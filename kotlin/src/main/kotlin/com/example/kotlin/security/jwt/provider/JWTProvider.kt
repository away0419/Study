package com.example.kotlin.security.jwt.provider

import com.example.kotlin.security.AuthConstants
import com.example.kotlin.security.jwt.MemberPrincipal
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import jakarta.servlet.http.Cookie
import jakarta.xml.bind.DatatypeConverter
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseCookie
import org.springframework.security.oauth2.jwt.JwtException
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*

@Component
class JWTProvider {
    private val jwtSecretKey = "exampleSecretKeyExampleSecretKeyExampleSecretKeyExampleSecretKey"
    private val log = LoggerFactory.getLogger(this.javaClass)!!

    private fun createHeader(): Map<String, Any> {
        val header: MutableMap<String, Any> = HashMap()
        header["typ"] = "JWT" // 토큰 타입
        header["alg"] = "HS256" // signature(서명) 알고리즘
        return header
    }

    private fun createClaims(memberPrincipal: MemberPrincipal): Map<String, Any> {
        val claims: MutableMap<String, Any> = HashMap()
        claims["email"] = memberPrincipal.email as Any
        claims["role"] = memberPrincipal.role as Any
        claims["https://github.com/away0419/spring-security/tree/main/springBoot"] = true
        return claims
    }

    private fun createSignature(): Key {
        val apiKeySecretBytes = DatatypeConverter.parseBase64Binary(jwtSecretKey)
        return Keys.hmacShaKeyFor(apiKeySecretBytes)
    }

    private fun createExpiredDate(): Date {
        val c = Calendar.getInstance()
        c.add(Calendar.MINUTE, 30) // 30분
        return c.time
    }

    fun generateJwtToken(memberPrincipal: MemberPrincipal): String {
        return Jwts.builder()
            .setHeader(createHeader())
            .setClaims(createClaims(memberPrincipal))
            .setSubject(memberPrincipal.id.toString())
            .setExpiration(createExpiredDate())
            .setIssuedAt(Date())
            .signWith(createSignature(), SignatureAlgorithm.HS256)
            .compact()
    }

    fun getTokenFromHeader(header: String): String? {
        if (!header.startsWith(AuthConstants.TOKEN_TYPE)) {
            return null
        }
        return header.split(" ")[1]
    }

    fun isValidToken(token: String): Boolean {
        return try {
            val claims = getClaimsFromToken(token)
            log.info("email : {}", claims["email"])
            log.info("role : {}", claims["role"])
            log.info("토큰 발급자 : {}", claims.subject)
            log.info("토큰 만료 시간 : {}", claims.expiration)
            log.info("토큰 발급 시간 : {}", claims.issuedAt)
            true
        } catch (e: ExpiredJwtException) {
            log.error("Token Expired")
            false
        } catch (e: JwtException) {
            log.error("Token Tampered or Invalid")
            false
        } catch (e: NullPointerException) {
            log.error("Token is null")
            false
        }
    }

    private fun getClaimsFromToken(token: String): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(createSignature())
            .build()
            .parseClaimsJws(token)
            .body
    }

    fun getUserEmailFromToken(token: String): String {
        return getClaimsFromToken(token)["email"].toString()
    }

    fun getUserRoleFromToken(token: String): String {
        return getClaimsFromToken(token)["role"].toString()
    }

    private fun createRefreshTokenExpiredDate(): Date {
        val c = Calendar.getInstance()
        c.add(Calendar.MONTH, 1) // 1개월
        return c.time
    }

    fun generateRefreshToken(): String {
        return Jwts.builder()
            .setHeader(createHeader())
            .setExpiration(createRefreshTokenExpiredDate())
            .setIssuedAt(Date())
            .signWith(createSignature(), SignatureAlgorithm.HS256)
            .compact()
    }

    fun generateRefreshTokenCookie(refreshToken: String): ResponseCookie {
        return ResponseCookie.from(AuthConstants.REFRESH_TOKEN_PREFIX, refreshToken)
            .httpOnly(true)
            .sameSite("None")
            .path("/")
            .maxAge(60 * 60 * 24 * 30) // 30일
            .build()
    }

    fun getRefreshToken(cookies: Array<Cookie>): String? {
        return cookies.firstOrNull { it.name == AuthConstants.REFRESH_TOKEN_PREFIX }?.value
    }

    fun isNeedToUpdateRefreshToken(refreshToken: String): Boolean {
        val expiresAt = getClaimsFromToken(refreshToken).expiration
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        calendar.add(Calendar.DATE, 7)
        return expiresAt.before(calendar.time)
    }
}