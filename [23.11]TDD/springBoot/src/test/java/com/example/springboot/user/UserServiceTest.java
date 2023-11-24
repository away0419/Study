package com.example.springboot.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    // 가짜 객체 생성
    @Mock
    private UserRepository userRepository;

    // 가짜 객체 주입
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    @DisplayName("회원가입")
    void signup() {
        //given (입력 값, 예상 되는 출력값, 가짜 객체 실행 시 내보낼 값 설정)
        UserEntity userEntity = UserEntity.builder().name("홍길동").age(13).build();
        UserDTO request = UserDTO.builder().name("홍길동").age(13).build();
        UserDTO response = UserDTO.of(userEntity);
        doReturn(userEntity).when(userRepository).save(any(UserEntity.class));

        // when (서비스 실행 시 결과 값)
        UserDTO result = userService.signUp(request);

        // then (예상 값과 결과 값 비교)
        assertThat(result.getName()).isEqualTo(response.getName());
        assertThat(result.getAge()).isEqualTo(response.getAge());

        // verity (실제 해당 메소드가 1번 실행 되었는지 검증)
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }
}
