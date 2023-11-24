package com.example.springboot.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {

    // bean에 등록 된 객체를 그대로 가져와 쓰겠다.
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("회원가입")
    void signUp(){
        //given
        UserDTO request = UserDTO.builder().name("홍길동").age(13).build();

        //when
        UserEntity result = userRepository.save(request.transforUser());

        //then
        assertThat(result.getAge()).isEqualTo(request.getAge());
        assertThat(result.getName()).isEqualTo(request.getName());
    }

}
