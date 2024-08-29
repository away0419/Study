package com.example.encryption.user;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/selectAll")
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/insert")
    public int insertUser(@RequestBody UserDTO userDTO) {
        return userService.addUser(userDTO);
    }

    @GetMapping("/repeat1")
    public int getRepeat1Users() {
        userService.repeat();
        return 1;
    }
    @GetMapping("/repeat2")
    public int getRepeat2Users() {
        userService.repeat2();
        return 1;
    }
    @GetMapping("/test")
    public int getTestUsers() {
        userService.test();
        return 1;
    }

}
