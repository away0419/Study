package com.security.springboot.jwt;

import com.security.springboot.domain.User.Model.UserDetailsVO;
import com.security.springboot.domain.User.Model.UserEntity;
import com.security.springboot.domain.User.Model.UserVO;
import io.jsonwebtoken.*;
import jakarta.xml.bind.DatatypeConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JWTProvider {

    //    @Value(value = "${jwt-secret-key}")
    private static final String jwtSecretKey = "exampleSecretKeyExampleSecretKeyExampleSecretKeyExampleSecretKey"; // HS256 알고리즘을 사용할 경우 256비트 보다 커야하므로 32글자 이상이어야 한다.


    /**
     * JWT의 Header 생성 후 반환
     *
     * @return
     */
    private static Map<String, Object> createHeader() {
        Map<String, Object> header = new HashMap<>();

        header.put("typ", "JWT"); // 토큰 타입
        header.put("alg", "HS256"); // signature(서명) 알고리즘

        return header;
    }


    /**
     * JWT의 Payload UserVO 정보로 Claims 생성 후 반환.
     *
     * @param userDetailsVO
     * @return
     */
    private static Map<String, Object> createClaims(UserDetailsVO userDetailsVO) {
        Map<String, Object> claims = new HashMap<>();

        // 비공개 클레임
        claims.put("userEmail", userDetailsVO.getUserEmail());
        claims.put("userRole", userDetailsVO.getRole());
//        claims.put("authority", userDetailsVO.getAuthorities()); 권한 목록인데 JWT에서 뽑아서 ObjectMapping 하는 것보다 위에처럼 바로 권한을 주는게 좋을 듯함. 이건 상황에 따라 구현 해야 함.

        // 공개 클레임
        claims.put("https://github.com/away0419/spring-security/tree/main/springBoot", true);

        return claims;
    }


    /**
     * JWT Signature 사용하는 시크릿키와 알고리즘을 이용하여 생성 후 반환
     *
     * @return
     */
    private static Key createSignature() {
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(jwtSecretKey);
        return new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());
    }


    /**
     * 토큰 만료 기간을 지정
     *
     * @return
     */
    private static Date createExpiredDate() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MINUTE, 30); // 30분
        return c.getTime();
    }


    /**
     * 사용자 정보를 기반으로 토큰 생성 후 반환
     *
     * @param userDetailsVO
     * @return
     */
    public static String generateJwtToken(UserDetailsVO userDetailsVO) {

        // 사용자 시퀀스를 기준으로 JWT 토큰 발급.
        return Jwts.builder()
                .setHeader(createHeader())  // JWT Header
                .setClaims(createClaims(userDetailsVO))    // JWT Payload 공개, 비공개 클레임 (사용자 정보)
                .setSubject(String.valueOf(userDetailsVO.getId())) // JWT Payload 등록 클레임
                .setExpiration(createExpiredDate()) // JWT Payload 등록 클레임
                .setIssuedAt(new Date()) // JWT Payload claims 등록 클레임
                .signWith(createSignature(),SignatureAlgorithm.HS256)  // JWT Signature 매개변수 순서는 바뀌어도 상관 없는듯
                .compact();
    }


    /**
     * 요청의 Header에 있는 토킅 추출 후 반환
     *
     * @param header
     * @return
     */
    public static String getTokenFromHeader(String header) {
        return header.split(" ")[1];
    }


    /**
     * 토큰 유효성 검사 후 반환
     *
     * @param token
     * @return
     */
    public static boolean isValidToken(String token) {
        try {
            Claims claims = getClaimsFormToken(token);
            log.info("userEmail : {}", claims.get("userEmail"));
            log.info("userRole : {}", claims.get("userRole"));
            log.info("토큰 발급자 : {}", claims.getSubject());
            log.info("토큰 만료 시간 : {}", claims.getExpiration());
            log.info("토큰 발급 시간 : {}", claims.getIssuedAt());
            return true;
        } catch (ExpiredJwtException e) {
            log.error("Token Expired");
            return false;
        } catch (JwtException e) {
            log.error("Token Tampered");
            return false;
        } catch (NullPointerException e) {
            log.error("Token is null");
            return false;
        }
    }


    /**
     * 토큰을 기반으로 Claims(정보) 반환
     *
     * @param token
     * @return Claims
     */
    private static Claims getClaimsFormToken(String token) {
        return Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(jwtSecretKey))
                .parseClaimsJws(token).getBody();
    }


    /**
     * 토큰의 Claims에서 사용자 이메일을 반환
     *
     * @param token
     * @return 사용자 아이디
     */
    public static String getUserEmailFromToken(String token) {
        return getClaimsFormToken(token).get("userEmail").toString();
    }


    /**
     * 토큰의 Claims에서 사용자 권한을 반환
     *
     * @param token
     * @return 사용자 권한
     */
    public static String getUserRoleFromToken(String token) {
        return getClaimsFormToken(token).get("userRole").toString();
    }

    /**
     *
     * @return 만료 시간
     */
    private static Date createRefreshTokenExpiredDate() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONDAY, 30); // 30일
        return c.getTime();
    }

    /**
     *
     * @return refresh token
     */
    public static String generateRefreshToken(){
        return Jwts.builder()
                .setHeader(createHeader())  // JWT Header
                .setExpiration(createRefreshTokenExpiredDate()) // JWT Payload 등록 클레임
                .setIssuedAt(new Date()) // JWT Payload claims 등록 클레임
                .signWith(createSignature(),SignatureAlgorithm.HS256)  // JWT Signature 매개변수 순서는 바뀌어도 상관 없는듯
                .compact();
    }

    /**
     *
     * @param refreshToken
     * @return 쿠키
     */
    public static ResponseCookie generateRefreshTokenCookie(String refreshToken){
        return ResponseCookie.from("refresh_token", refreshToken)
                .httpOnly(true)  // 클라이언트 측 JavaScript에서 쿠키에 접근 불가
//                .secure(true)    // HTTPS 연결에서만 쿠키 전송
                .sameSite("None") // SameSite 속성 설정 (크로스 사이트 요청 위조 방지)
                .path("/refresh-token")
                .maxAge(60 * 60 * 24 * 30) // 쿠키의 수명 (예: 30일)
                .build();
    }


}
