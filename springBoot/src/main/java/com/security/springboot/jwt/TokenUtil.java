package com.security.springboot.jwt;

import com.security.springboot.domain.User.Model.UserVO;
import io.jsonwebtoken.*;
import jakarta.xml.bind.DatatypeConverter;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class TokenUtil {
    //    @Value(value = "${jwt-secret-key}")
    private static final String jwtSecretKey = "exampleSecretKey";


    /**
     * JWT Header 생성 후 반환
     *
     * @return
     */
    private static Map<String, Object> createHeader() {
        Map<String, Object> header = new HashMap<>();

        header.put("typ", "JWT");
        header.put("alg", "HS256");
        header.put("regDate", System.currentTimeMillis());

        return header;
    }


    /**
     * 사용자 정보를 Claims로 생성 후 반환.
     *
     * @param userVO
     * @return
     */
    private static Map<String, Object> createClaims(UserVO userVO) {
        Map<String, Object> claims = new HashMap<>();

        // 공개 클레임으로 사용자 이메일만 넣음.
        claims.put("userEmail", userVO.getUserEmail());

        return claims;
    }


    /**
     * JWT Signature 생성 후 반환
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
        c.add(Calendar.HOUR, 8); // 8시간
        return c.getTime();
    }


    /**
     * 사용자 정보를 기반으로 토큰 생성 후 반환
     *
     * @param userVO
     * @return
     */
    public static String generateJwtToken(UserVO userVO) {
        // 사용자 시퀀스를 기준으로 JWT 토큰 발급.
        return Jwts.builder()
                .setHeader(createHeader())  // Header
                .setClaims(createClaims(userVO))    // claims
                .setSubject(String.valueOf(userVO.getId())) // claims
                .signWith(SignatureAlgorithm.HS256, createSignature())  // Signature
                .setExpiration(createExpiredDate()) // Expired Date
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
            log.info("expireTime : {}", claims.getExpiration());
            log.info("userEmail : {}", claims.get("userId"));
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
    public static String getUserIdFromToken(String token) {
        return getClaimsFormToken(token).get("userEmail").toString();
    }


}
