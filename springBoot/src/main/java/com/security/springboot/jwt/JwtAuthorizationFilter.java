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
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 1. Token이 필요하지 않은 API URL
        List<String> list = Arrays.asList(
                "/api/v1/user/login",
                "/api/v1/user/generateToken"
        );

        // 2. 토큰이 필요하지 않은 API URL 다음 필터로 넘기기
        if (list.contains(request.getRequestURI())) {
            filterChain.doFilter(request, response);
        }

        // 3. OPTIONS 요청일 경우 다음 필터로 넘기기
        if (request.getMethod().equalsIgnoreCase("OPTIONS")) {
            filterChain.doFilter(request, response);
        }

        try {
            String header = Optional.ofNullable(request.getHeader(AuthConstants.AUTH_HEADER)).orElseGet(() -> "");

            // header 유무 검사
            if ("".equals(header)) {
                throw new Exception("Token is null");
            }

            // token 추출
            String token = TokenUtil.getTokenFromHeader(header);

            // 토큰 유효성 검사
            if (!TokenUtil.isValidToken(token)) {
                throw new Exception("Token is invalid");
            }

            // userEmail 추출
            String userEmail = Optional.ofNullable(TokenUtil.getUserIdFromToken(token)).orElseGet(() -> "");

            // userEmail 유무 검사
            if ("".equals(userEmail)) {
                throw new Exception("Token isn't userEmail");
            }

            // 다음 필터로 넘기기
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
