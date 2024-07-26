package com.example.encryption.user;


import com.example.encryption.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;

    public List<UserDTO> getAllUsers() {
        return userMapper.selectAllUser();
    }

    public int addUser(UserDTO userDTO) {
        return userMapper.insertUser(userDTO);
    }
}
