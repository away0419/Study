package com.example.encryption.user.mapper;

import com.example.encryption.user.UserDTO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface UserMapper {
    int insertUser(UserDTO userDTO);
    List<UserDTO> selectAllUser();
    UserDTO selectUserByName(String name);
}
