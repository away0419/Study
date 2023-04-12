최종 작성일 : 23.04.07

## H2 database & JPA 의존성 추가

- maven

    ```
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

- gradle

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

<br>
<br>

## H2 & JPA 설정

- properties

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

- yml

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
<br>
<br>

## Mockito
- 사용법
  - @ExtendWith(MockitoExtension.class)를 테스트 클래스에 추가


- @Mock
  - 가짜 객체를 만들어 반환


- @Spy
  - stub 하지 않은 메소드들은 원본 메소드 그대로 사용
  - doReturn() : 가짜 객체가 특정한 값을 반환해야 하는 경우
  - doNothing() : 가짜 객체가 아무 것도 반환하지 않는 경우
  - doThrow() : 가짜 객체가 예외를 발생시키는 경우


- @InjectMocks
  - @Mock 또는 @Spy로 생성된 가짜 객체를 주입