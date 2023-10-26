package com.security.springboot.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Optional;

@Slf4j
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
            // [STEP.01] http header에서 AuthConstants.AUTH_HEADER를 가져오고 null 이면 에러
            String header = Optional.ofNullable(request.getHeader(AuthConstants.AUTH_HEADER)).orElseThrow(() -> new Exception("Token is null"));

            // [STEP.02] Header에서 Token 추출하는데 null 이면 에러
            String token = Optional.ofNullable(JWTProvider.getTokenFromHeader(header)).orElseThrow(() -> new Exception("Token type is invalid"));

            // [STEP.03] Token 유효성 검사
            if (!JWTProvider.isValidToken(token)) {
                throw new Exception("Token is invalid");
            }

            // [STEP.04] Token에서 Email 추출
            String userEmail = Optional.ofNullable(JWTProvider.getUserEmailFromToken(token)).orElseThrow(() -> new Exception("Token isn't userEmail"));

            // [STEP.05] Token에서 Role 추출
            String userRole = Optional.ofNullable(JWTProvider.getUserRoleFromToken(token)).orElseThrow(() -> new Exception("Token isn't userRole"));

            // [STEP.06] JWT에서 가져온 정보로 인증 완료된 객체 만들기
            Authentication authentication = new UsernamePasswordAuthenticationToken(userEmail, null, Collections.singleton(new SimpleGrantedAuthority(userRole)));

            // [STEP.07] context에 저장하여 나머지 필터에서 해당 객체를 통해 검사할 수 있도록 함. stateless 설정을 하면 로직 종료 후 저장된 객체는 삭제가 된다.
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // [STEP.08] 다음 필터로 넘기기
            filterChain.doFilter(request, response);


        } catch (Exception e) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            PrintWriter printWriter = response.getWriter();

            HashMap<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("status", 403);
            jsonMap.put("code", "9999");
            jsonMap.put("message", e.getMessage());
            JSONObject jsonObject = new JSONObject(jsonMap);

            printWriter.println(jsonObject);
            printWriter.flush();
            printWriter.close();
        }
    }
}
