package com.security.springboot.domain.User.Controller;


import com.security.springboot.Security.exception.CustomErrorCode;
import com.security.springboot.Security.exception.CustomException;
import com.security.springboot.domain.User.Model.UserDetailsVO;
import com.security.springboot.jwt.AuthConstants;
import com.security.springboot.jwt.JWTProvider;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/jwt")
@RequiredArgsConstructor
public class JWTController {
    private final UserDetailsService userDetailsService;
    @PostMapping("/token/update")
    public String udateToken(HttpServletRequest request, HttpServletResponse response){

        Cookie[] cookies= request.getCookies(); // 쿠키 찾기
        String refreshToken = JWTProvider.getRefreshToken(cookies); // refresh token 가져오기
        JWTProvider.isValidToken(refreshToken); // token 검증
//
//        if(JWTProvider.isNeedToUpdateRefreshToken(refreshToken)){ refresh token 기간 만료 7일 전이면 업데이트
//            refreshToken = JWTProvider.generateRefreshToken();
//        }

        String header = Optional.ofNullable(request.getHeader(AuthConstants.AUTH_HEADER)).orElseThrow(()-> new CustomException(CustomErrorCode.AUTH_HEADER_NULL));
        String accessToken = Optional.ofNullable(JWTProvider.getTokenFromHeader(header)).orElseThrow(() -> new CustomException(CustomErrorCode.TOKEN_NULL));
        String userEmail = Optional.ofNullable(JWTProvider.getUserEmailFromToken(accessToken)).orElseThrow(() -> new CustomException(CustomErrorCode.USER_INFO_NULL));
        UserDetailsVO userDetailsVO = (UserDetailsVO) Optional.ofNullable(userDetailsService.loadUserByUsername(userEmail)).orElseThrow(() -> new CustomException(CustomErrorCode.USER_INFO_NULL));

        accessToken = JWTProvider.generateJwtToken(userDetailsVO);
        refreshToken = JWTProvider.generateRefreshToken(); // RTR 전략 적용
        ResponseCookie responseCookie  =JWTProvider.generateRefreshTokenCookie(refreshToken); // refresh token 담은 cookie 생성
        response.addHeader(AuthConstants.AUTH_HEADER, AuthConstants.TOKEN_TYPE +accessToken);
        response.addHeader(AuthConstants.COOKIE_HEADER, responseCookie.toString());

        return "발급 성공";
    }


}
