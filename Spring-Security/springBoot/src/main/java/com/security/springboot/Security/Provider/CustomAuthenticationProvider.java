package com.security.springboot.Security.Provider;

import com.security.springboot.domain.User.Model.UserDetailsVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Slf4j
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication; // [STEP.01] Filter에서 받은 인증 전 객체를 형 변환.
        String userEmail = token.getName(); // [STEP.02] 토큰에서 아이디 추출.
        String userPw = (String) token.getCredentials(); // [STEP.03] 토큰에서 비밀번호를 추출.

        log.debug("2.CustomAutenticationProvider userEmail = {}, UserPw = {}",userEmail, userPw);

        UserDetailsVO userDetailsVO = (UserDetailsVO) userDetailsService.loadUserByUsername(userEmail); // [STEP.04] 이메일로 사용자 정보 Details로 추출

        // [STEP.05] DB에 있던 비밀번호와 사용자가 보낸 비밀번호가 다를 경우 에러.
        // 실제로 가입할 때 passwordEncoder로 변환하여 DB에 저장해야하나 더미 데이터를 변환하지 않았기 때문에 바로 비교함.
        if(!userPw.equals(userDetailsVO.getPassword())) {
            throw new BadCredentialsException(userDetailsVO.getUsername());
        }
//        if(!passwordEncoder.matches(userPw, userDetailsVO.getPassword())) {
//            throw new BadCredentialsException(userDetailsVO.getUsername());
//        }

        return new UsernamePasswordAuthenticationToken(userDetailsVO, userPw, userDetailsVO.getAuthorities()); // [STEP.06] 인증 완료한 객체(UsernamePasswordAuthenticationToken)로 만들고 반환
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
