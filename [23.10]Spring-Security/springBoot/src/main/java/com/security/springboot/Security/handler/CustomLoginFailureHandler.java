package com.security.springboot.Security.handler;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

@Slf4j
public class CustomLoginFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.debug("3.CustomLoginFailsHandler");
        String msg = "알수 없는 이유로 로그인 실패";

        if(exception instanceof BadCredentialsException || exception instanceof InternalAuthenticationServiceException){
            msg = "아이디 또는 비밀번호 오류";
        }

        JSONObject jsonObject; // response로 내보려는 정보를 담은 Json 객체
        HashMap<String, Object> responseMap = new HashMap<>(); // response 할 데이터를 담기 위한 맵
        responseMap.put("msg", msg);
        jsonObject = new JSONObject(responseMap);

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        PrintWriter printWriter = response.getWriter();
        printWriter.print(jsonObject);
        printWriter.flush();
        printWriter.close();
    }
}
