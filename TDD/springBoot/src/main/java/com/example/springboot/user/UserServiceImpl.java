package com.example.springboot.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRep;

    @Override
    public UserDTO signUp(UserDTO userDTO) {
        return (UserDTO.of(userRep.save(userDTO.transforUser())));
    }

    @Override
    public UserDTO select(Long ID) {
        return null;
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO) {
        return null;
    }

}
