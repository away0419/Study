package com.example.springboot.user;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    // 가짜 객체 생성
    @Mock
    private UserRepository userRepository;

    // 가짜 객체 주입
    @InjectMocks
    private UserService userService;




}
