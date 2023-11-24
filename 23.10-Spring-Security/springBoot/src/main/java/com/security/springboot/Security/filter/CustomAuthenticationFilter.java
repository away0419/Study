package com.security.springboot.Security.filter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.security.springboot.domain.User.Model.UserVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@NoArgsConstructor
@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        UsernamePasswordAuthenticationToken authRequest; // [STEP.01] 사용자가 보낸 값을 넣을 인증 판별 객체 생성

        try {
            authRequest= getAuthRequest(request); // [STEP.02] 사용자가 보낸 값 UsernamePasswordAuthenticationToken으로 변환
            setDetails(request, authRequest); // [STEP.03] UsernamePasswordAuthenticationToken에 추가 정보 설정 ex) ip, session ID...
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return this.getAuthenticationManager().authenticate(authRequest); // [STEP.05] manager에 인증 전 객체 전달하여 판단 위임 후 결과만 받아옴.
    }

    //사용자가 보낸 요청에서 유저 아이디와 비밀번호 추출 하는 메서드
    private UsernamePasswordAuthenticationToken getAuthRequest(HttpServletRequest request) throws Exception {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
            UserVO userVO = objectMapper.readValue(request.getInputStream(), UserVO.class);
            log.debug("1.CustomAuthenticationFilter :: user Email : " + userVO.getUserEmail() + " userPw:" + userVO.getUserPw());

            return new UsernamePasswordAuthenticationToken(userVO.getUserEmail(), userVO.getUserPw());
        } catch (UsernameNotFoundException ae) {
            throw new UsernameNotFoundException(ae.getMessage());
        } catch (Exception e) {
            throw new Exception(e.getMessage(), e.getCause());
        }
    }
}
