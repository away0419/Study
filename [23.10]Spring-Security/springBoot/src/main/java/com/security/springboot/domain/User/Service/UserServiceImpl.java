package com.security.springboot.domain.User.Service;

import com.security.springboot.domain.User.Model.UserEntity;
import com.security.springboot.domain.User.Model.UserVO;
import com.security.springboot.domain.User.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserVO login(UserVO userVO) {
        UserEntity userEntity = userRepository.findByUserEmailAndUserPw(userVO.getUserEmail(), userVO.getUserPw());
        return new UserVO(userEntity);
    }

    @Override
    public UserVO createUser(UserVO userVO) {
        return new UserVO(userVO.makeUserEntity());
    }

    @Override
    public UserVO findUserByUserEmail(String userId) {
        Optional<UserEntity> userEntityOptional = userRepository.findByUserEmail(userId);
        UserEntity userEntity = userEntityOptional.orElseThrow(() -> new UsernameNotFoundException(userId)); // user을 찾지 못했을 경우 예외 처리
        return new UserVO(userEntity);
    }
}
