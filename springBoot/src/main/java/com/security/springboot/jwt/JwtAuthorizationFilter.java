package com.security.springboot.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class JwtAuthorizationFilter extends OncePerRequestFilter {
    // BasicAuthenticationFilter를 상속 받아도 됨.(BasicAuthenticationFilter 이 OncePerRequestFilter를 상속하고 있어서 상관없음.)
    // 다만, BasicAuthenticationFilter는 기본적으로 Basic 타입 인증을 사용함.
    // 따라서, BasicAuthenticationFilter 보단 OncePerRequestFilter를 상속 받는 경우가 많음.
    // 대신, BasicAuthenticationFilter는 권한이 필요한 경로만 자동으로 필터링함. OncePerRequestFilter는 이를 구현해야 함.

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 1. Token이 필요하지 않은 API URL. (필요 없음. securityConfig에서 설정하면 됨.)
//        List<String> list = Arrays.asList(
//                "/api/v1/user/login",
//                "/api/v1/user/generateToken"
//        );

        // 2. 토큰이 필요하지 않은 API URL 다음 필터로 넘기기 (필요 없음. securityConfig에서 설정하면 됨.)
//        if (list.contains(request.getRequestURI())) {
//            filterChain.doFilter(request, response);
//        }

        // 3. OPTIONS 요청일 경우 다음 필터로 넘기기 (필요 없음. securityConfig에서 설정하면 됨.)
//        if (request.getMethod().equalsIgnoreCase("OPTIONS")) {
//            filterChain.doFilter(request, response);
//        }

        try {
            // [STEP.01] http header에서 AuthConstants.AUTH_HEADER를 가져옴 없으면 ""
            String header = Optional.ofNullable(request.getHeader(AuthConstants.AUTH_HEADER)).orElseThrow(()->new Exception("Token is null"));
            logger.info(header);

            // [STEP.02] Header에서 지정한 Token Type 검사 (Token이 가지는 타입과 다른 거임.)
            if(!header.startsWith(AuthConstants.TOKEN_TYPE)){
                throw new Exception("Token type is invalid");
            }

            // [STEP.03] Header에서 Token 추출
            String token = JWTProvider.getTokenFromHeader(header);

            // [STEP.04] Token 유효성 검사
            if (!JWTProvider.isValidToken(token)) {
                throw new Exception("Token is invalid");
            }

            // [STEP.05] Token에서 Email 추출
            String userEmail = Optional.ofNullable(JWTProvider.getUserEmailFromToken(token)).orElseThrow(()->new Exception("Token isn't userEmail"));

            // [STEP.06] 다음 필터로 넘기기
            filterChain.doFilter(request, response);

        } catch (Exception e) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            PrintWriter printWriter = response.getWriter();

            HashMap<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("status", 401);
            jsonMap.put("code", "9999");
            jsonMap.put("message", e.getMessage());
            JSONObject jsonObject = new JSONObject(jsonMap);

            printWriter.println(jsonObject);
            printWriter.flush();
            printWriter.close();
        }
    }
}
