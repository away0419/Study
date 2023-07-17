package com.security.springboot.domain.User.Service;

import com.security.springboot.domain.User.Model.UserVO;

// 유저 서비스
public interface UserService  {
    // 로그인
    UserVO login(UserVO userVO);

    // 회원가입
    UserVO createUser(UserVO userVO);

    // 아이디로 유저 찾기
    UserVO findUserByUserEmail(String userId);
}
