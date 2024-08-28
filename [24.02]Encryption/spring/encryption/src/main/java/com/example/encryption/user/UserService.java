package com.example.encryption.user;


import com.example.encryption.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserMapper userMapper;

    public List<UserDTO> getAllUsers() {
        return userMapper.selectAllUser();
    }

    public int addUser(UserDTO userDTO) {
        return userMapper.insertUser(userDTO);
    }

    @Transactional
    public void repeat(){
        for (int i = 0; i < 10; i++) {
            log.info("반복: {}",i);
            userMapper.selectUserByName("홍길동");
        }
    }
}
