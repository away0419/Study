package com.example.kotlin.security.jwt.provider

import com.example.kotlin.common.logger
import com.example.kotlin.security.AuthConstants
import com.example.kotlin.security.exception.SecurityCustomErrorCode
import com.example.kotlin.security.exception.SecurityCustomException
import com.example.kotlin.security.jwt.MemberPrincipal
import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.*
import jakarta.servlet.http.Cookie
import jakarta.xml.bind.DatatypeConverter
import org.springframework.http.ResponseCookie
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*
import javax.crypto.spec.SecretKeySpec

@Component
class JWTProvider {
    //    @Value("\${jwt.secret}")
    private val jwtSecretKey: String = "exampleSecretKeyExampleSecretKeyExampleSecretKeyExampleSecretKey"
    private val log = logger()
    private val objectMapper = ObjectMapper()

    /**
     * token 헤더 만들기
     * @return Map<String, Any>
     */
    private fun createHeader(): Map<String, Any> {
        val header: MutableMap<String, Any> = HashMap()
        header["typ"] = "JWT" // 토큰 타입
        header["alg"] = "HS256" // signature(서명) 알고리즘
        return header
    }

    /**
     * token 공개, 비공개 claims 만들기
     * @param memberPrincipal MemberPrincipal
     * @return Map<String, Any>
     */
    private fun createClaims(memberPrincipal: MemberPrincipal): Map<String, Any> {
        val claims: MutableMap<String, Any> = HashMap()
        claims["email"] = memberPrincipal.email as Any
        claims["role"] = memberPrincipal.role as Any
        claims["memberPrincipal"] = objectMapper.writeValueAsString(memberPrincipal) // 객체를 json 형식으로 만들기
        return claims
    }

    /**
     * token 사인 만들기
     * @return Key
     */
    private fun createSignature(): Key {
        val apiKeySecretBytes = DatatypeConverter.parseBase64Binary(jwtSecretKey)
        return SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.jcaName)
    }

    /**
     * access token 유효 기간 설정
     * @return Date
     */
    private fun createExpiredDate(): Date {
        val c = Calendar.getInstance()
        c.add(Calendar.MINUTE, 30) // 30분
        return c.time
    }

    /**
     * access token 생성
     * @param memberPrincipal MemberPrincipal
     * @return String
     */
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

    /**
     * 헤더에서 token 추출
     * @param header String
     * @return String
     */
    fun getTokenFromHeader(header: String): String {
        // 만약 개발자가 지정한 토큰 타입이 아닌 경우 에러 발생
        if (!header.startsWith(AuthConstants.TOKEN_TYPE)) {
            throw SecurityCustomException(SecurityCustomErrorCode.JWT_TOKEN_TYPE_MISMATCH)
        }
        return header.split(" ")[1]
    }

    /**
     * token claims 추출
     * @param token String
     * @return Claims
     */
    fun getClaimsFromToken(token: String): Claims {

        return try {
            Jwts.parserBuilder()
                .setSigningKey(DatatypeConverter.parseBase64Binary(jwtSecretKey))
                .build()
                .parseClaimsJws(token)
                .body
        } catch (e: MalformedJwtException) {
            throw SecurityCustomException(SecurityCustomErrorCode.JWT_TOKEN_MALFORMED)
        } catch (e: ExpiredJwtException) {
            throw SecurityCustomException(SecurityCustomErrorCode.JWT_TOKEN_EXPIRED)
        } catch (e: UnsupportedJwtException) {
            throw SecurityCustomException(SecurityCustomErrorCode.JWT_TAMPERED_INVALID)
        } catch (e: IllegalArgumentException) {
            throw SecurityCustomException(SecurityCustomErrorCode.JWT_TOKEN_ILLEGAL_ARGUMENT)
        } catch (e: NullPointerException) {
            throw SecurityCustomException(SecurityCustomErrorCode.JWT_TOKEN_IS_NULL)
        }
    }

    /**
     * token에서 email 추출
     * @param token String
     * @return String
     */
    fun getUserEmailFromToken(token: String): String {
        return getClaimsFromToken(token)["email"].toString()
    }

    /**
     * token에서 role 추출
     * @param token String
     * @return String
     */
    fun getUserRoleFromToken(token: String): String {
        return getClaimsFromToken(token)["role"].toString()
    }

    /**
     * token에서 memberPrincipal 추출
     * @param token String
     * @return MemberPrincipal
     */
    fun getMemberPrincipalFromToken(token: String): MemberPrincipal {
        val memberPrincipalInfo = getClaimsFromToken(token)["memberPrincipal"] ?: throw SecurityCustomException(
            SecurityCustomErrorCode.OAUTH2_USER_INFO_IS_NULL
        )
        return objectMapper.readValue(memberPrincipalInfo.toString(), MemberPrincipal::class.java)
    }

    /**
     * refresh token 유효 기간 설정
     * @return Date
     */
    private fun createRefreshTokenExpiredDate(): Date {
        val c = Calendar.getInstance()
        c.add(Calendar.MONTH, 1) // 1개월
        return c.time
    }

    /**
     * refresh token 생성
     * @return String
     */
    fun generateRefreshToken(): String {
        return Jwts.builder()
            .setHeader(createHeader())
            .setExpiration(createRefreshTokenExpiredDate())
            .setIssuedAt(Date())
            .signWith(createSignature(), SignatureAlgorithm.HS256)
            .compact()
    }

    /**
     * cookie 생성
     * @param refreshToken String
     * @return ResponseCookie
     */
    fun generateRefreshTokenCookie(refreshToken: String): ResponseCookie {
        return ResponseCookie.from(AuthConstants.REFRESH_TOKEN_PREFIX, refreshToken)
            .httpOnly(true)
            .sameSite("None")
            .path("/")
            .maxAge(60 * 60 * 24 * 30) // 30일
            .build()
    }

    /**
     * cookie에서 refresh token 추출
     * @param cookies Array<Cookie>
     * @return String
     */
    fun getRefreshToken(cookies: Array<Cookie>): String {
        return cookies.firstOrNull { it.name == AuthConstants.REFRESH_TOKEN_PREFIX }?.value
            ?: throw SecurityCustomException(SecurityCustomErrorCode.JWT_COOKIE_IS_NOT_FOUND)
    }

    /**
     * refresh token 남은 유효 기간이 7일 이하 여부 판단.
     * @param refreshToken String
     * @return Boolean
     */
    fun isNeedToUpdateRefreshToken(refreshToken: String): Boolean {
        val expiresAt = getClaimsFromToken(refreshToken).expiration
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        calendar.add(Calendar.DATE, 7)
        return expiresAt.before(calendar.time)
    }
}