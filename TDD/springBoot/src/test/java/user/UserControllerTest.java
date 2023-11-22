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

    // @Mock으로 만든 가짜 객체를 주입 받은 객체 생성. (@Mock userService 주입된 userController)
    @InjectMocks
    private UserController userController;

    // 테스트용 HTTP 호출 (가짜 객체를 주입 받은 userController 등록 하기 위한 테스트용 MVC)
    private MockMvc mockMvc;

    // 각 @Test, @RepeatedTest, @ParameterizedTest 또는 @TestFactory 메소드보다 먼저 메소드가 실행되어야 함을 의미
    // 가짜 객체 userService가 주입 된 UserController를 적용 하겠다는 뜻이다.
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

        // userService는 가짜 객체이므로 반환 값이 무엇인지 설정해야한다.
        // 즉, 가짜 객체 userService의 메소드 signUp()에 UserDTO.class로 변환 가능한 객체 any를 매개변수로 주었을 경우 userResponse를 반환하도록 설정하는 것이다.
        doReturn(userResponse).when(userService).signUp(any(UserDTO.class));

        //when
        // userRequest를 콘텐트 내용으로 보냈을 때, 결과 값이 resultActions 에 저장된다.
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