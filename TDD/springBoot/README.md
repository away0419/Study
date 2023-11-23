## 테스트 도구
- spring-boot-starter-test 안에 포함 되어 있음.
  - JUnit : Java 단위 테스트 프레임워크
  - AssertJ : 유연한 검증 라이브러리
  - Spring Boot Test & Spring Test : 스프링 부트에 대한 유틸리티 및 통합 테스트 지원 (여러 테스트 도구를 포함 하고 있음)
  - Hamcrest : 객체 Matcher를 위한 라이브러리
  - Mockito : 자바 모킹 프레임워크
  - JSONassert : JSON 검증 도구
  - JsonPath : Json용 xPath

<br/>

## 통합 테스트 설정 어노테이션
- 해당 클래스가 테스트 코드를 위한 클래스라는 것을 알리고, 설정한다고 생각하면 됨.
- 여러 형태가 있는데 대표적으로 사용되는 어노테이션은 아래와 같음.
- 슬라이드 테스트 클래스의 위치는 Spring 실행 클래스(@SpringBootApplication)와 동일한 경로 혹은, 하위 경로에 포함되어 있어야 함.

<details>
  <summary>@SpringBootTest</summary>

```java
@SpringBootTest(properties = { "mangkyu.blog=tistory" })
```
- 통합 테스트 환경 설정.
- 모든 빈을 스캔하고 애플리케이션 컨텍스트 생성.
  - 특정 계층만 테스트할 경우 불필요한 빈 스캔으로 시간이 오래 걸림.
  - 이를 해결하기 위해 특정 부분만 테스트할 수 있는 슬라이드 테스트를 위한 어노테이션을 제공함. (@WebMvcTest 등)
  - 슬라이스 테스트도 스프링 컨텍스트를 구성함. (단위 테스트가 아님.)
- 다양한 값을 줄 수 있음.
  - properties : 애플리케이션 실행에 필요한 properties를 Key=value 형태로 추가.
  - args : 애플리케이션의 arguments로 값 전달.
  - classes : 애플리케이션 로딩 시 사용되는 컴포넌트 클래스 정의.
  - webEnvironment : 테스트 환경 설정. (총 4가지)
    - MOCK
      - 웹 기반의 애플리케이션 컨텍스트를 생성하지만 MOCK 환경으로 제공하여 내장 서버 시작 되지 않음.
      - 웹 환경이 클래스패스에 없다면 웹이 아닌 애플리케이션 컨텍스틀를 생성함.
      - 웹 기반 MOCK 테스트를 위해 @AutoConfigureMockMVC 또는 @AutoConfigureWebTestClient와 함께 사용 가능.
    - RANDOM_PORT
      - 웹 기반의 애플리케이션 컨텍스트 생성하여 실제 웹 환경 제공.
      - 내장 서버도 실행되며 사용되지 않는 랜덤 포트를 listen 함.
    - DEFINED_PORT
      - 웹 기반의 애플리케이션 컨텍스트 생성하여 실제 웹 환경 제공.
      - 내장 서버도 실행되며 지정한 포트를 listen 함.
    - NONE
      - SpringApplication로 애플리케이션 컴텍스트 생성.
      - MOCK이나 다른 것들을 포함해 어떠한 웹 환경도 제공하지 않음.
    - 기본 값은 MOCK 이므로 실제 웹 서버 실행 안함.
    - @Transactional 설정 하면 테스트 끝난 후 DB 롤백함.
    - 만약 RANDOM_PORT, DEFINED_PORT 설정 시 별도 쓰레드에서 실제 서버가 구동되므로 @Transactional 설정 해도 롤백 불가. 또한, TestRestTemplate 의존성이 자동 추가 되므로 API 호출이 필요할 때 사용 가능.
    - 해결 방법은 TRUNCATE 명령어로 모든 데이터를 날려 버리는 것. (기본 데이터가 없다는 가정)
    - <details>
        <summary>테스트 격리 고도화하기</summary>
        
        - 테스트의 실행 주기에 개입할 수 있는 AbstractTestExecutionListener 구현체를 생성하여 등록.
        - beforeTestClass : 테스트 클래스 내의 어떠한 테스트도 실행되기 전에 테스트 클래스를 전처리하기 위해 사용.
        - prepareTestInstance : 테스트 객체를 생성하기 위한 전처리 작업.
        - beforeTestMethod : BeforeEach와 같은 Before 콜백들이 실행되기 전에 테스트를 전처리 할 때 사용.
        - beforeTestExecution : BeforeEach와 같은 Before 콜백들이 실행된 후에 테스트를 전처리 할 때 사용.
        - afterTestExecution : AfterEach와 같은 After 콜백들이 실행되기 전에 테스트를 후처리 할 때 사용.
        - afterTestMethod : AfterEach와 같은 After 콜백들이 실행된 후에 테스트를 후처리 할 때 사용.
        - afterTestClass : 모든 테스트의 실행이 끝나고, 테스트 클래스를 후처리할 때 사용.
          ```java
          public class AcceptanceTestExecutionListener extends AbstractTestExecutionListener {
            @Override
            public void afterTestMethod(final TestContext testContext) {
                final JdbcTemplate jdbcTemplate = getJdbcTemplate(testContext);
                final List<String> truncateQueries = getTruncateQueries(jdbcTemplate);
                truncateTables(jdbcTemplate, truncateQueries);
            }
          
            private List<String> getTruncateQueries(final JdbcTemplate jdbcTemplate) {
                return jdbcTemplate.queryForList("SELECT Concat('TRUNCATE TABLE ', TABLE_NAME, ';') AS q FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'PUBLIC'", String.class);
            }
          
            private JdbcTemplate getJdbcTemplate(final TestContext testContext) {
                return testContext.getApplicationContext().getBean(JdbcTemplate.class);
            }
          
            private void truncateTables(final JdbcTemplate jdbcTemplate, final List<String> truncateQueries) {
                execute(jdbcTemplate, "SET REFERENTIAL_INTEGRITY FALSE");
                truncateQueries.forEach(v -> execute(jdbcTemplate, v));
                execute(jdbcTemplate, "SET REFERENTIAL_INTEGRITY TRUE");
            }
            private void execute(final JdbcTemplate jdbcTemplate, final String query) {
                jdbcTemplate.execute(query);
            }
          }
          ```
          ```java
          @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
          @Retention(RetentionPolicy.RUNTIME)
          @TestExecutionListeners(value = {AcceptanceTestExecutionListener.class,}, mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
          public @interface AcceptanceTest {
          }
          ```
          ```java
          @AcceptanceTest
          class MyTest {
                  
          }
          ``` 
          </details>
  
</details>

<details>
  <summary>@WebMvcTest</summary>

```java
@WebMvcTest(UserController.class)
class UserControllerTest {
}
```
- 슬라이드 테스트. (통합 테스트이며 부분 테스트임.)
- 컨트롤러 테스트용. 
- 컨트롤러와 연관된 빈들만 제한적으로 찾아서 등록. (@Component, @ConfigurationProperties 스캔 안함)
  - @Controller, @RestController
  - @ControllerAdvice, @RestControllerAdvice
  - @JsonComponent
  - Filter
  - WebMvcConfigurer
  - HandlerMethodArgumentResolver
  - 기타 등등
- 내장된 서블릿 컨테이너가 랜덤 포트로 실행.
- 내부에 @AutoConfigureMockMvc가 있기에 @Autowired로 MockMvc 주입 받을 수 있음.
- 컨트롤러가 의존하는 Bean이 있다면 @MockBean, @SpyBean을 사용해 주어야 함. 
  - 이 경우 새로운 애플리케이션 컨택스트를 필요로 하므로 주의 해야 함.
</details>

<details>
  <summary>@DataJpaTest</summary>

```java
@DataJpaTest
class MyRepositoryTests {
}
```
- 슬라이드 테스트. (통합 테스트이며 부분 테스트임.)
- JPA repository 테스트용.
- @Entity 스캔하고 테스트를 위한 TestEntityManager를 사용해 JPA repository 설정함. (@Component, @ConfigurationProperties 스캔 안함.)
- @Transactional이 포함되어 있어 테스트 끝나면 롤백 됨.
- 롤백을 원하지 않을 경우 @Rollback(false) 추가.
- 만약 properties에 DB 클래스 패스가 존재한다면 자동으로 해당 DB에 연결 됨. (default H2)
- 내장 DB를 사용하고 싶지 않다면 @AutoConfigureTestDatabase(replace = Replace.NONE) 추가.

</details>

<details>
  <summary>@RestClientTest</summary>

- 슬라이드 테스트. (통합 테스트이며 부분 테스트임.)
- RestTemplate 테스트용.
</details>

<details>
  <summary>@JsonTest</summary>

- 슬라이드 테스트. (통합 테스트이며 부분 테스트임.)
- json 관련 테스트를 위해 gson이나 objectMapper 등의 의존성이 필요한 경우 사용.
</details>

<details>
  <summary>@JdbcTest</summary>

- 슬라이드 테스트. (통합 테스트이며 부분 테스트임.)
- Datasource와 JdbcTemplate만 필요한 경우 사용.
</details>

<br/>

## 애플리케이션 컨텍스트 캐싱

- 통합 테스트는 모두 애플리케이션 컨텍스트를 구성해 주어야 함.
- 모든 테스트마다 이를 구성하려면 비용이 커짐. 이 때문에 내부적으로 스프링 컨텍스트를 캐싱해두고 동일한 설정이라면 재사용함.
- 따라서, 설정에 변경이 생기면 새로운 컨텍스트를 생성해야 함. 만약 테스트 속도가 느릴 경우 확인해 보는 것이 좋음. 아래는 사용시 컨텍스트를 새로 생성하는 것들임.
  - @MockBean, @SpyBean
  - @TestPropertySource
  - @ConditionalOnX
  - @WebMvcTest에 컨트롤러 지정
  - @Import
  - 기타 등등

<br/>

## 통합 테스트 작성시 주의사항
- 무분별한 어노테이션 설정은 불필요한 빈을 등록하게 됨.
  - 아래 예제에서 @EnableBatchProcessing를 적용 하였기 때문에 Test에서 배치가 필요 없더라도 배치와 관련된 bean이 함께 등록됨. 
    ```java
    @SpringBootApplication
    @EnableBatchProcessing
    public class MyApplication {
        public static void main(String[] args) {
            SpringApplication.run(MyApplication.class, args);
        }
    }
    ```
- @ComponentScan을 추가할 경우 해당 컴포넌트 스캔에 의하여 필요 없는 빈이 등록 될 수 있음.
- 해당 컴포넌트 스캔을 별도의 설정 클래스로 빼거나 테스트 패키지에 별도의 @SpringBootConfiguration을 만들거나 테스트에 대한 소스 위치를 지정하여 기본 설정을 비활성화 할 수 있음.
- 애플리케이션 컨텍스트 캐싱을 주의 해야 함.

<br/>


## Mockito
- 개발자가 동작을 직접 제어할 수 있는 가짜 객체를 지원하는 프레임워크.
- Spring 웹 개발 시, 객체들 간의 의존성이 생겨 단위 테스트 작성을 어렵게 함. 이를 가짜 객체로 주입하여 편하게 작성 가능.
- 필요 없다면 안쓰는게 가장 좋음.
- 크게 3가지 어노테이션을 사용함.
  - @Mock : 가짜 객체를 만들어 반환. (Stub)
  - @Spy : Stub 하지 않은 메소드들은 원본 메소드 그대로 사용. (특정 테스트에서만 stub 하고 싶은 경우 사용)
  - @InjectMocks : @Mock 또는 @Spy로 생성된 가짜 객체를 자동으로 주입.
  - @MockBean : 애플리케이션 컨텍스트로 동작할 때, 기존 Bean에 가짜 객체를 주입 하고 싶을 경우 사용.
  - @SpyBean : 애플리케이션 컨텍스트로 동작할 때, 기존 Bean에서 특정 테스트에서만 가짜 객체를 주입 하고 싶을 경우 사용.
- Stub 결과 처리를 위한 메소드를 제공함.
  - doReturn() : 가짜 객체가 특정한 값을 반환해야 하는 경우.
  - doNoting() : 가짜 객체가 아무 것도 반환하지 않는 경우. 
  - doThrow() : 가짜 객체가 예외를 발생시키는 경우.
- Junit과 결합하여 사용하기 위해선 @ExtendWith(MockitoExtension.class) 적용해야 함.
  - SpringBoot 2.2.0 전에는 @RunWith(MockitoJUnitRunner.class)

<br/>

## 의존성 추가 및 설정

<details>
  <summary>maven</summary>

```maven
<dependencies>
  <!-- H2 Database -->
  <dependency>
      <groupId>com.h2database</groupId>
      <artifactId>h2</artifactId>
      <scope>runtime</scope>
  </dependency>

  <!-- Spring Data JPA -->
  <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-data-jpa</artifactId>
  </dependency>
  
  <!-- MOCKITO -->
  <dependency>
      <groupId>org.mockito</groupId>
      <artifactId>mockito-core</artifactId>
      <version>4.1.0</version>
      <scope>test</scope>
  </dependency>
  
  <!-- GSON -->
  <dependency>
      <groupId>com.google.code.gson</groupId>
      <artifactId>gson</artifactId>
      <version>2.8.9</version>
  </dependency>
</dependencies>
```
</details>

<details>
  <summary>gradle</summary>

```
dependencies {
    // H2 Database
    runtimeOnly 'com.h2database:h2'

    // Spring Data JPA
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    
    // Mockito 
    testImplementation 'org.mockito:mockito-core:4.1.0'

    // Gson
    implementation 'com.google.code.gson:gson:2.8.9'
}
```
</details>

<details>
  <summary>properties</summary>

```
spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=update
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```
</details>

<details>
  <summary>yml</summary>

```
spring:
    datasource:
        url: jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
        driverClassName: org.h2.Driver
        username: sa
        password:

jpa:
    database-platform: org.hibernate.dialect.H2Dialect
    hibernate:
    ddl-auto: update

h2:
    console:
    enabled: true
    path: /h2-console
```
</details>

<br/>

## Controller 테스트
<details>
  <summary>단위 테스트</summary>

- 단위 테스트를 위해 MockMvc를 이용하여 controller 등록 하는 것이 포인트.

  ```java
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
  ```
</details>

<details>
  <summary>부분 테스트</summary>

- @WebMvcTest 적용하기.
- 만약 userController에 bean을 여러개 주입받고 있을 경우, 해당 빈들을 모두 가져 오고 다시 mockbean 확인 후 만들어 바꾸기 때문에 단위 테스트 보다 느리다.

  ```java
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
  ```
</details>