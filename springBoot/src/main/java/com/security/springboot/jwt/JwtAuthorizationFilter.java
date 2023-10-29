package com.security.springboot.jwt;

import com.security.springboot.Security.exception.CustomErrorCode;
import com.security.springboot.Security.exception.CustomException;
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
            String header = Optional.ofNullable(request.getHeader(AuthConstants.AUTH_HEADER)).orElseThrow(() -> new CustomException(CustomErrorCode.AUTH_HEADER_NULL));

            // [STEP.02] Header에서 Token 추출하는데 null 이면 에러
            String token = Optional.ofNullable(JWTProvider.getTokenFromHeader(header)).orElseThrow(() -> new CustomException(CustomErrorCode.TOKEN_NULL));

            // [STEP.03] Token 유효성 검사.
            JWTProvider.isValidToken(token);

            // [STEP.04] Token에서 Email 추출
            String userEmail = Optional.ofNullable(JWTProvider.getUserEmailFromToken(token)).orElseThrow(() -> new CustomException(CustomErrorCode.USER_INFO_NULL));

            // [STEP.05] Token에서 Role 추출
            String userRole = Optional.ofNullable(JWTProvider.getUserRoleFromToken(token)).orElseThrow(() -> new CustomException(CustomErrorCode.USER_INFO_NULL));

            // [STEP.06] JWT에서 가져온 정보로 인증 완료된 객체 만들기
            Authentication authentication = new UsernamePasswordAuthenticationToken(userEmail, null, Collections.singleton(new SimpleGrantedAuthority(userRole)));

            // [STEP.07] context에 저장하여 나머지 필터에서 해당 객체를 통해 검사할 수 있도록 함. stateless 설정을 하면 로직 종료 후 저장된 객체는 삭제가 된다.
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // [STEP.08] 다음 필터로 넘기기
            filterChain.doFilter(request, response);


            // 만약 CustomException이 발생할 경우 여기서 바로 처리할 것인지 또는 필터를 거친 뒤 RestControllerAdvice가 처리하게 할지 정하면 됨.
            // 또한 CustomException이 아닌 [인증, 인가] 예외의 경우  [CustomAuthenticationEntryPoint, CustomAccessDeniedHandler] 만들고 이를 SecurityConfig에 등록하여 처리.
            // 여기서 주의할 점은 JWT는 Security의 AuthenticationException를 상속 받지 않으므로 이 부분에서 처리해야함. 즉, CsutomException 처럼 만들어서 처리해야한다.
        } catch (CustomException e) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            PrintWriter printWriter = response.getWriter();

            HashMap<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("status", e.getCustomErrorCode().getState());
            jsonMap.put("code", e.getCustomErrorCode().getState());
            jsonMap.put("message", e.getCustomErrorCode().getMessage());
            JSONObject jsonObject = new JSONObject(jsonMap);

            printWriter.println(jsonObject);
            printWriter.flush();
            printWriter.close();
        }
    }
}
