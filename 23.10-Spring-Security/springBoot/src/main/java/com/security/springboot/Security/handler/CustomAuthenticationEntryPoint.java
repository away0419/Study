package com.security.springboot.Security.handler;

import com.security.springboot.Security.exception.CustomException;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Comment;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final HandlerExceptionResolver resolver; // 예외 처리 핸들러

    //빈에 등록된 예외 처리 핸들러 가져오기
    public CustomAuthenticationEntryPoint(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.resolver = resolver;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.error("CustomAuthenticationEntryPoint");

        Exception exception = (Exception) request.getAttribute("exception");

        if (exception != null && exception instanceof CustomException) {
            CustomException customException= (CustomException) exception;
            resolver.resolveException(request, response, null,  customException);
        } else {
            JSONObject jsonObject; // response로 내보려는 정보를 담은 Json 객체
            HashMap<String, Object> responseMap = new HashMap<>(); // response 할 데이터를 담기 위한 맵
            responseMap.put("msg", "인증 실패.");
            jsonObject = new JSONObject(responseMap);

            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 응답 코드 설정
            PrintWriter printWriter = response.getWriter();
            printWriter.print(jsonObject);
            printWriter.flush();
            printWriter.close();
        }
    }
}
