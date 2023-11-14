package user;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    // 가짜 객체(Mock) 생성
    @Mock
    private UserService userService;

    // 가짜 객체 주입
    @InjectMocks
    private UserController userController;

    // 테스트용 HTTP 호출
    private MockMvc mockMvc;

    // 각 @Test, @RepeatedTest, @ParameterizedTest 또는 @TestFactory 메소드보다 먼저 메소드가 실행되어야 함을 의미
    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @DisplayName("회원 가입 성공")
    @Test
    void signUpSuccess() throws Exception {
        //given
        UserDTO userRequest = signRequest();
        UserDTO userResponse = signResponse();

        //userService의 signUp 메서드에 매개변수로 UserDTO.class를 넣었을 때, 리턴 값으로 userResponse와 동일한 값을 반환해야 한다는 뜻.
        // 여기서 any는
        doReturn(userResponse).when(userService).signUp(any(UserDTO.class));
//        doReturn(userResponse).when(userService).signUp(userRequest);

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/user/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(userRequest)) // request를 Gson 라이브러리를 통해 Json으로 변환하여 넘긴다.
        );

        //then
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