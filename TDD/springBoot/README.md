## 테스트 도구
- spring-boot-starter-test 안에 포함 되어 있음.
  - JUnit : Java 단위 테스트 프레임워크
  - AssertJ : 유연한 검증 라이브러리
  - Spring Boot Test & Spring Test : 스프링 부트에 대한 유틸리티 및 통합 테스트 지원
  - Hamcrest : 객체 Matcher를 위한 라이브러리
  - Mockito : 자바 모킹 프레임워크
  - JSONassert : JSON 검증 도구
  - JsonPath : Json용 xPath

<br/>

## 통합 테스트 설정 어노테이션
- 해당 클래스가 테스트 코드를 위한 클래스라는 것을 알리고, 설정한다고 생각하면 됨.
- 여러 형태가 있는데 대표적으로 사용되는 어노테이션은 아래와 같음.

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

- 통합 테스트는 모두 애플리케이션 컨텍스트를 구성해주어야 함. 
- 모든 테스트마다 이를 구성하려면 비용이 커짐. 이 때문에 내부적으로 스프링 컨텍스트를 캐싱해두고 동일한 설정이라면 재사용함.
- 따라서, 설정에 변경이 생기면 새로운 컨텍스트를 생성해야 함. 만약 테스트 속도가 느릴 경우 확인해 보는 것이 좋음.
  - @MockBean, @SpyBean
  - @TestPropertySource
  - @ConditionalOnX
  - @WebMvcTest에 컨트롤러 지정
  - @Import
  - 기타 등등

<br/>

## 통합 테스트 작성시 주의사항
- 테스트는 조건에 맞는 어노테이션만 찾아 빈에 등록함. 따라서 무분별한 설정은 불필요한 빈을 등록하게 됨.
- @SpringBootApplication에 특정 기능을 위한 어노테이션 설정이 있는 경우 해당 기능도 활성화 됨. 아래 예제는 Batch가 활성화 되어버림.
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
