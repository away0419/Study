package com.security.springboot.Security.filter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@NoArgsConstructor
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager){
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        UsernamePasswordAuthenticationToken authRequest // request로 받은 유저 이메일과 비밀번호를 Toekn으로 만든다.
                = new UsernamePasswordAuthenticationToken(request.getParameter("userEmail"), request.getParameter("userPW"));
        return this.getAuthenticationManager().authenticate(authRequest); // 해당 토큰을 검사한 뒤 인증된 사용자면 정상적으로 리턴. 아니라면 예외 발생
    }

}
