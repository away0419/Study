package com.example.springboot.user;

public interface UserService {
    public UserDTO signUp(UserDTO userDTO);

    public UserDTO select(Long ID);

    public UserDTO updateUser(UserDTO userDTO);
}
