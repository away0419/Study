package com.example.encryption.user;


import com.example.encryption.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserMapper userMapper;

    public List<UserDTO> getAllUsers() {
        return userMapper.selectAllUser();
    }

    @Transactional
    public int addUser(UserDTO userDTO) {
        for (int i = 0; i < 10; i++) {
            userMapper.insertUser(userDTO);
            String str = userDTO.getName()+ i;
            log.info(str);
            userDTO.setName(str);
        }

        return 1;
    }

    public void repeat(){
        for (int i = 0; i < 10; i++) {
            log.info("1. 반복: {}",i);
            userMapper.selectOptionaltUserByName("홍길동");
        }
    }

    @Transactional
    public void repeat2(){
        repeat();

        for (int i = 0; i < 10; i++) {
            log.info("2. 반복: {}",i);
            userMapper.selectOptionaltUserByName("홍길동");
        }
    }

    @Transactional
    public void test(){
        this.repeat();
        this.repeat2();
    }

}
