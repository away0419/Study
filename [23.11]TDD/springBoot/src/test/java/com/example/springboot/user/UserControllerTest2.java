package com.example.springboot.user;

import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UserController.class)
class UserControllerTest2 {

    // 서버를 실행 하지 않고도 api 요청을 보낼 수 있는 객체
    @Autowired
    private MockMvc mockMvc;

    // bean에 등록 된 userService를 가짜 객체로 교체 한다.
    @MockBean
    private UserService userService;

    @Test
    @DisplayName("회원 가입 성공")
    void signUpSuccess() throws Exception {
        //given
        UserDTO userRequest = signRequest();
        UserDTO userResponse = signResponse();

        // userService는 가짜 객체이므로 반환 값이 무엇인지 설정해야한다.
        // 즉, 가짜 객체 userService의 메소드 signUp()에 UserDTO.class로 변환 가능한 객체 any를 매개변수로 주었을 경우 userResponse를 반환하도록 설정하는 것이다.
        doReturn(userResponse).when(userService).signUp(any(UserDTO.class));

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/user/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(userRequest)) // request를 Gson 라이브러리를 통해 Json으로 변환하여 넘긴다.
        );

        //then
        // 현재는 값이 존재 하는지만 확인 했지만 결과 값이 내가 예상하는 결과 값이랑 같은지 확인하면 된다.
        resultActions.andExpect(status().isCreated()) // 상태 결과 값이 created인지 확인
                .andExpect(jsonPath("id", userResponse.getId()).exists()) // id 값이 존재 하는지 확인
                .andExpect(jsonPath("name", userResponse.getName()).exists()) // name 값이 존재 하는지 확인
                .andExpect(jsonPath("age", userResponse.getAge()).exists()); // age 값이 존재 하는지 확인

    }

    // 테스트할 입력 값
    private UserDTO signRequest() {
        return UserDTO.builder()
                .id(null)
                .name("홍길동")
                .age(32)
                .build();
    }

    // 정해진 결과 값
    private UserDTO signResponse() {
        return UserDTO.builder()
                .id(0L)
                .name("홍길동")
                .age(32)
                .build();
    }

}
