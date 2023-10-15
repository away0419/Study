최종 작성일 : 2023.08.16.</br>
# Spring-Security

## 0. 들어가기 앞서
- spring Security는 버전에 따라 Deprecated 된 클래스, 함수들이 존재함. 따라서 버전을 확인한 뒤, 사용해야 하는 메서드가 무엇인지 판단하기 바랍니다.
- [docs](https://docs.spring.io/spring-security/reference/index.html)가 가장 정확합니다.
- 흐름도를 참고하여 무엇을 먼저 작성해야 하는지 확인하기 바랍니다.
  ![Alt text](image/image.png)
  

<br/>

## 1. 라이브러리 추가 및 설정

<details> 
  <summary>gradle</summary>

  ```gradle
  dependencies {
      implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
	implementation 'org.springframework.boot:spring-boot-starter-security'
	implementation 'org.springframework.boot:spring-boot-starter-web'
	compileOnly 'org.projectlombok:lombok'
	developmentOnly 'org.springframework.boot:spring-boot-devtools'
	runtimeOnly 'com.h2database:h2'
	annotationProcessor 'org.projectlombok:lombok'
	testImplementation 'org.springframework.boot:spring-boot-starter-test'
	testImplementation 'org.springframework.security:spring-security-test'

	implementation 'com.fasterxml.jackson.datatype:jackson-datatype-jsr310'			// LocalDateTime 역직렬화 해결 패키지
	implementation "com.googlecode.json-simple:json-simple:1.1.1"                   // Google Simple JSON
	implementation 'com.fasterxml.jackson.core:jackson-databind'           // Jackson Databind
	implementation "io.jsonwebtoken:jjwt:0.9.1"                                     // Spring Json-Web-Token                                  // Spring Json-Web-Token
  }
  ```
</details>

<details>
  <summary>properties</summary>

  ```properties
  spring.h2.console.enabled=true
  spring.h2.console.path=/h2-console

  spring.datasource.driverClassName=org.h2.Driver
  spring.datasource.url=jdbc:h2:mem:test
  spring.datasource.username=sa
  spring.datasource.password=

  spring.sql.init.mode=always

  spring.jpa.hibernate.ddl-auto=create 
  spring.jpa.properties.hibernate.format_sql=true 
  spring.jpa.show-sql=true

  logging.level.com.security.springboot=debug
  ```

</details>



<br/>

## 2. User Domain

- 로그인을 위한 유저 도메인.
- 회원 Entity, Service, Controller, Role 작성. (security는 UserDetail, UserDetailsService를 제공하며 이를 이용함. 따라서, UserDetail를 상속 받는 UserDetailVO를 만들고 이 안에 @Delegate로 UserEntity 필드를 만들어야함. 또한 UserDetailsService를 상속 받는 UserDetailsServiceImpl를 따로 구현해야함. User과 UserDetail를 따로 만드는 이유는 기능상 분리하는 것)

- <details>
    <summary>UserRole</summary>
    
    ```java
    package com.security.springboot.domain.User.Role;

    import lombok.AllArgsConstructor;
    import lombok.Getter;

    @AllArgsConstructor
    @Getter
    public enum UserRole {
        USER("ROLE_USER"),
        ADMIN("ROLE_ADMIN");

        private final String position;
    }    
    ```
  </details>
- <details>
    <summary>CommonEntity</summary>

    ```java
    package com.security.springboot.domain.Common;
  
    import jakarta.persistence.*;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;
    import org.hibernate.annotations.CreationTimestamp;
    import org.hibernate.annotations.UpdateTimestamp;
  
    import java.io.Serializable;
    import java.time.LocalDateTime;
  
    @MappedSuperclass // JPA에서 공통 매핑 정보 클래스를 사용할 때, 공통 속성만 받아서 사용하게 해주는 어노테이션
    @Getter
    @NoArgsConstructor
    public abstract class CommonEntity implements Serializable {
  
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column
        private Long id;
  
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        @CreationTimestamp
        @Column(nullable = false, length = 20, updatable = false)
        private LocalDateTime createdAt;
  
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        @UpdateTimestamp
        @Column(length = 20)
        private LocalDateTime updateAt;
  
        @Setter
        @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT true")
        private Boolean IsEnable = true;
    }
    ```
  
  </details>
- <details>
    <summary>UserEntity</summary>
  
    ```java
    package com.security.springboot.domain.User.Model;

    import com.security.springboot.Security.Role.UserRole;
    import com.security.springboot.domain.Common.CommonEntity;
    import jakarta.persistence.*;
    import lombok.Builder;
    import lombok.Getter;
    import lombok.NoArgsConstructor;

    import java.io.Serializable;

    @NoArgsConstructor
    @Entity
    @Table(name = "users")
    @Getter
    public class UserEntity extends CommonEntity implements Serializable{

        @Column(nullable = false, unique = true, length = 50)
        private String userEmail;

        @Column(nullable = false)
        private String userPw;

        @Column(nullable = false, length =50)
        @Enumerated(EnumType.STRING)
        private UserRole role;

        @Builder
        public UserEntity(String userEmail, String userPw){
            this.userEmail = userEmail;
            this.userPw = userPw;
        }

    }
    ```
  
  </details>
- <details>
    <summary>UserDetailsVO</summary>

    ```java
    package com.security.springboot.domain.User.Model;
  
    import lombok.Getter;
    import lombok.RequiredArgsConstructor;
    import lombok.experimental.Delegate;
    import org.springframework.security.core.GrantedAuthority;
    import org.springframework.security.core.userdetails.UserDetails;
  
    import java.util.Collection;
  
    @RequiredArgsConstructor
    @Getter
    public class UserDetailsVO implements UserDetails {
  
        // UserEntity 메소드를 UserDetailVO 객체로 위임 시키는 어노테이션
        // 즉, UserEntity의 메소드를 자신이 바로 사용할 수 있음.
        @Delegate
        private final UserEntity userEntity;
        private final Collection<? extends GrantedAuthority> authorities;
  
        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return authorities;
        }
  
        @Override
        public String getPassword() {
            return userEntity.getUserPw();
        }
  
        @Override
        public String getUsername() {
            return userEntity.getUserEmail();
        }
  
        @Override
        public boolean isAccountNonExpired() {
            return userEntity.getIsEnable();
        }
  
        @Override
        public boolean isAccountNonLocked() {
            return userEntity.getIsEnable();
        }
  
        @Override
        public boolean isCredentialsNonExpired() {
            return userEntity.getIsEnable();
        }
  
        @Override
        public boolean isEnabled() {
            return userEntity.getIsEnable();
        }
    }
  
    ```
  
  </details>
- <details>
    <summary>UserRepository</summary>

    ```java
    package com.security.springboot.domain.User.repository;
    
    import com.security.springboot.domain.User.Model.UserEntity;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.stereotype.Repository;
    
    import java.util.Optional;
    
    @Repository
    public interface UserRepository extends JpaRepository<UserEntity, Long> {
    
        UserEntity findByUserEmailAndUserPw(String userId, String userPw);
    
        Optional<UserEntity> findByUserEmail(String userEmail);
    }
    ```

  </details>
- <details>
    <summary>UserDetailsServiceImpl</summary>

    ```java
    package com.security.springboot.domain.User.Service;
    
    import com.security.springboot.domain.User.Model.UserDetailsVO;
    import com.security.springboot.domain.User.repository.UserRepository;
    import lombok.RequiredArgsConstructor;
    import org.springframework.security.core.authority.SimpleGrantedAuthority;
    import org.springframework.security.core.userdetails.UserDetails;
    import org.springframework.security.core.userdetails.UserDetailsService;
    import org.springframework.security.core.userdetails.UsernameNotFoundException;
    import org.springframework.stereotype.Service;
    
    import java.util.Collections;
    
    @Service
    @RequiredArgsConstructor
    public class UserDetailsServiceImpl implements UserDetailsService {
    
        private final UserRepository userRepository;
    
        @Override
        public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
            return userRepository.findByUserEmail(userEmail)
                    .map(user -> new UserDetailsVO(user, Collections.singleton(new SimpleGrantedAuthority(user.getRole().getPosition()))))
                    .orElseThrow(()-> new UsernameNotFoundException(userEmail));
        }
    }
    ```
  
  </details>
- <details>
    <summary>UserVO</summary>

    ```java
    package com.security.springboot.domain.User.Model;
    
    import com.security.springboot.domain.User.Role.UserRole;
    import lombok.Data;
    
    @NoArgsConstructor
    @Data
    public class UserVO {
        private Long id;
        private String userEmail;
        private String userPw;
        private UserRole role;
    
        public UserVO(UserEntity userEntity) {
            this.id = userEntity.getId();
            this.userEmail = userEntity.getUserEmail();
            this.userPw = userEntity.getUserPw();
            this.role = userEntity.getRole();
        }
    
        public UserEntity makeUserEntity() {
            return UserEntity.builder().userEmail(this.userEmail).userPw(this.userPw).build();
        }
    }
    
    ```
  
  </details>
- <details>
    <summary>UserService</summary>

    ```java
    package com.security.springboot.domain.User.Service;
    
    import com.security.springboot.domain.User.Model.UserVO;
    
    // 유저 서비스
    public interface UserService  {
        // 로그인
        UserVO login(UserVO userVO);
    
        // 회원가입
        UserVO createUser(UserVO userVO);
    
        // 아이디로 유저 찾기
        UserVO findUserByUserEmail(String userId);
    }    
    ```

  </details>
- <details>
    <summary>UserServiceImpl</summary>

    ```java
    package com.security.springboot.domain.User.Service;
    import com.security.springboot.domain.User.Model.UserEntity;
    import com.security.springboot.domain.User.Model.UserVO;
    import com.security.springboot.domain.User.repository.UserRepository;
    import lombok.RequiredArgsConstructor;
    import org.springframework.security.core.userdetails.UsernameNotFoundException;
    import org.springframework.stereotype.Service;
    
    import java.util.Optional;
    
    @RequiredArgsConstructor
    @Service
    public class UserServiceImpl implements UserService {
    
        private final UserRepository userRepository;
    
        @Override
        public UserVO login(UserVO userVO) {
            UserEntity userEntity = userRepository.findByUserEmailAndUserPw(userVO.getUserEmail(), userVO.getUserPw());
            return new UserVO(userEntity);
        }
    
        @Override
        public UserVO createUser(UserVO userVO) {
            return new UserVO(userVO.makeUserEntity());
        }
    
        @Override
        public UserVO findUserByUserEmail(String userId) {
            Optional<UserEntity> userEntityOptional = userRepository.findByUserEmail(userId);
            UserEntity userEntity = userEntityOptional.orElseThrow(() -> new UsernameNotFoundException(userId)); // user을 찾지 못했을 경우 예외 처리
            return new UserVO(userEntity);
        }
    }
    ```
  </details>

<br/>

## 3. SecurityConfig
- 인증과 인가를 담당.
- 사용자가 만든 필터, 어드바이저, 핸들러를 등록하거나, 추가적인 설정을 등록할 수 있는 클래스. 
- 클래스명은 상관 없음.
- <details>
    <summary>SecurityConfig</summary>

    ```java
    package com.security.springboot.Security.configuration;

    import jakarta.servlet.DispatcherType;
    import lombok.RequiredArgsConstructor;
    import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.security.config.annotation.web.builders.HttpSecurity;
    import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
    import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
    import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
    import org.springframework.security.config.http.SessionCreationPolicy;
    import org.springframework.security.core.userdetails.UserDetailsService;
    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
    import org.springframework.security.web.SecurityFilterChain;

    @Configuration
    @EnableWebSecurity
    @RequiredArgsConstructor
    public class SecurityConfig {

        private final UserDetailsService userDetailsService;

        // 정적 자원 경로는 security 적용 하지 않음.
        @Bean
        public WebSecurityCustomizer configure() {
            return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
        }

        // security 설정
        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            return http
                    .csrf(AbstractHttpConfigurer::disable) // csrf 공격 보호 옵션 끄기. (rest api 에서는 필요 없기 때문)
                    .cors(AbstractHttpConfigurer::disable) // cors 예방 옵션 끄기
                    .formLogin(AbstractHttpConfigurer::disable) // form bases authentication 비활성화 (기본 로그인 페이지 비활성화, rest api만 작성하기 때문에 필요없음.)
                    .httpBasic(AbstractHttpConfigurer::disable) // http basic authentication 비활성화 (기본 로그인 인증창 비활성화, rest api만 작성하기 때문에 필요없음.)
                    .sessionManagement(session -> session // session 기반이 아닌 jwt token 기반일 경우 stateless 설정
                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .authorizeHttpRequests(request -> request // HTTP 요청에 대한 인증을 구성
                            .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll() // DispatcherType.FORWARD 유형의 모든 요청을 허용
                            .anyRequest().authenticated()) // 다른 요청은 인증을 받아야 함.
                    .build();
        }

        // 패스워드 인코더
        @Bean
        public BCryptPasswordEncoder bCryptPasswordEncoder() {
            return new BCryptPasswordEncoder();
        }

    }
    ```
  </details>


<br/>


## 기타 

<details>
  <summary>커스텀 로직 추가</summary>

  - <details> 
      <summary> Filter </summary>

      - 해당 필터에서 아이디나 비밀번호 유효성 검사도 해주면 좋음.
      - 커스텀 필터는 UsernamePasswordAuthenticationFilter 이전에 적용해야 함.
      - 커스텀 필터 수행 이후 처리될 Handler 역시 Bean 등록하고 CustomAuthenticationFilter 핸들러로 추가해 주어야 함.
      - 해당 메서드 내에서 AuthenticationManager를 호출하여 전달함.
      - 인증 성공 시 CustomAuthSuccessHandler를 호출하고, 실패 시 CustomAuthFailureHandler 호출
  
      ```java
      package com.security.springboot.Security.filter;
    
      import com.fasterxml.jackson.core.JsonParser;
      import com.fasterxml.jackson.databind.ObjectMapper;
      import com.security.springboot.domain.User.Model.UserVO;
      import jakarta.servlet.http.HttpServletRequest;
      import jakarta.servlet.http.HttpServletResponse;
      import lombok.NoArgsConstructor;
      import lombok.extern.slf4j.Slf4j;
      import org.springframework.security.authentication.AuthenticationManager;
      import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
      import org.springframework.security.core.Authentication;
      import org.springframework.security.core.AuthenticationException;
      import org.springframework.security.core.userdetails.UsernameNotFoundException;
      import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
      
      @NoArgsConstructor
      @Slf4j
      public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
      
        public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
          super.setAuthenticationManager(authenticationManager);
        }
      
        @Override
        public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
          UsernamePasswordAuthenticationToken authRequest; // request로 받은 유저 이메일과 비밀번호를 Toekn으로 만든다.
          try {
              authRequest= getAuthRequest(request);
              setDetails(request, authRequest);
          } catch (Exception e) {
              throw new RuntimeException(e);
          }
          return this.getAuthenticationManager().authenticate(authRequest); // 해당 토큰을 검사한 뒤 인증된 사용자면 정상적으로 리턴. 아니라면 예외 발생
        }
      
        private UsernamePasswordAuthenticationToken getAuthRequest(HttpServletRequest request) throws Exception {
          try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
            UserVO userVO = objectMapper.readValue(request.getInputStream(), UserVO.class);
            log.debug("1.CustomAuthenticationFilter :: user Email : " + userVO.getUserEmail() + " userPw:" + userVO.getUserPw());
      
            return new UsernamePasswordAuthenticationToken(userVO.getUserEmail(), userVO.getUserPw());
          } catch (UsernameNotFoundException ae) {
            throw new UsernameNotFoundException(ae.getMessage());
          } catch (Exception e) {
            throw new Exception(e.getMessage(), e.getCause());
          }
        }
      }
    
      ```
  
    </details>
  - <details>
      <summary>Provider</summary>
    
      - Filter 후 생성된 토큰을 받아 비즈니스 로직 처리
      - 전달 받은 usernamePasswordToken을 순차적으로 Provider들에게 전달.
      - 실제 인증 과정을 수행. (Authenticate 함수에 작성)
      - 해당 Provider를 SecurityConfig에서 Bena 등록해주어야 함.
    
      ```java
      package com.security.springboot.Security.Provider;
    
      import com.security.springboot.domain.User.Model.UserDetailsVO;
      import lombok.RequiredArgsConstructor;
      import lombok.extern.slf4j.Slf4j;
      import org.springframework.security.authentication.AuthenticationProvider;
      import org.springframework.security.authentication.BadCredentialsException;
      import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
      import org.springframework.security.core.Authentication;
      import org.springframework.security.core.AuthenticationException;
      import org.springframework.security.core.userdetails.UserDetailsService;
      import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
  
      @Slf4j
      @RequiredArgsConstructor
      public class CustomAuthenticationProvider implements AuthenticationProvider {
  
        @Autowired
        private UserDetailsService userDetailsService;
        @Autowired
        private BCryptPasswordEncoder passwordEncoder;
  
        @Override
        public Authentication authenticate(Authentication authentication) throws AuthenticationException {
          UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication; // Filter를 거쳐 생성된 토큰
          String userEmail = token.getName(); // 아이디를 가져온다.
          String userPw = (String) token.getCredentials(); //비밀번호를 가져온다.
          UserDetailsVO userDetailsVO = (UserDetailsVO) userDetailsService.loadUserByUsername(userEmail); // 아이디로 사용자 조회
  
          log.debug("2.CustomAutenticationProvider userEmail = {}, UserPw = {}",userEmail, userPw);
            
          // 찾은 사용자의 비밀번호가 일치하지 않는 경우 예외 처리
          // 현재 암호화해서 db에 넣지 않았으므로 넘어간다.
          // if(!passwordEncoder.matches(userPw, userDetailsVO.getPassword())) {
          //    throw new BadCredentialsException(userDetailsVO.getUsername());
          // }
          if(!userPw.equals(userDetailsVO.getPassword())) {
              throw new BadCredentialsException(userDetailsVO.getUsername());
          }
  
          return new UsernamePasswordAuthenticationToken(userDetailsVO, userPw, userDetailsVO.getAuthorities());
        }
  
        @Override
        public boolean supports(Class<?> authentication) {
          return authentication.equals(UsernamePasswordAuthenticationToken.class);
        }
      }
      ```
    </details>
  - <details>
      <summary>Handler</summary>

      - AuthenticationProvider를 통해 인증이 성공/실패될 때 실행할 비즈니스 로직.
      - JWT를 사용하지 않고 세션을 사용할 경우 SecurityContextHolder에 사용자 정보 저장.
      - successHandler
    
        ```java
        package com.security.springboot.Security.handler;
    
        import com.security.springboot.domain.User.Model.UserDetailsVO;
        import com.security.springboot.domain.User.Model.UserEntity;
        import com.security.springboot.domain.User.Role.UserRole;
        import com.security.springboot.utils.ConvertUtil;
         import jakarta.servlet.FilterChain;
        import jakarta.servlet.ServletException;
        import jakarta.servlet.http.HttpServletRequest;
        import jakarta.servlet.http.HttpServletResponse;
        import lombok.extern.slf4j.Slf4j;
        import org.json.simple.JSONObject;
        import org.springframework.security.core.Authentication;
        import org.springframework.security.core.context.SecurityContextHolder;
        import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
    
        import java.io.IOException;
        import java.io.PrintWriter;
        import java.util.HashMap;
  
        // 인증이 성공할 경우 처리.
        @Slf4j
        public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {
          @Override
          public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
            log.debug("3.CustomLoginSuccessHandler");
  
            JSONObject jsonObject; // response로 내보려는 정보를 담은 Json 객체
            HashMap<String, Object> responseMap = new HashMap<>(); // response 할 데이터를 담기 위한 맵
            UserEntity userEntity = ((UserDetailsVO) authentication.getPrincipal()).getUserEntity(); // 사용자와 관련된 정보 조회
            JSONObject userEntityJson = (JSONObject) ConvertUtil.convertObjectToJsonObject(userEntity); // 사용자 정보 Json 객체로 변환
    
            if(userEntity.getRole()==UserRole.ADMIN) {
              responseMap.put("userInfo", userEntityJson); // 유저 정보 Json 형식으로 넣기
              responseMap.put("msg", "관리자 인증 성공");
            }else{
              responseMap.put("userInfo", userEntityJson); // 유저 정보 Json 형식으로 넣기
              responseMap.put("msg", "일반 사용자 인증 성공");
            }
  
            jsonObject = new JSONObject(responseMap);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            PrintWriter printWriter = response.getWriter();
            printWriter.print(jsonObject);
            printWriter.flush();
            printWriter.close();
  
            SecurityContextHolder.getContext().setAuthentication(authentication); // 인증이 완료된 객체를 저장해 두고 다른 서비스에서 사용자 정보를 사용할 때 꺼내 쓴다. (jwt를 안쓰므로 세션에 저장하는 것)
  
          }
        }
        ```
    - failHander

      ```java
      package com.security.springboot.Security.handler;
  
      import jakarta.servlet.ServletException;
      import jakarta.servlet.http.HttpServletRequest;
      import jakarta.servlet.http.HttpServletResponse;
      import org.json.simple.JSONObject;
      import org.springframework.security.core.AuthenticationException;
      import org.springframework.security.web.authentication.AuthenticationFailureHandler;
  
      import java.io.IOException;
      import java.io.PrintWriter;
      import java.util.HashMap;
  
      public class CustomLonginFailureHandler implements AuthenticationFailureHandler {
        @Override
        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
          log.debug("3.CustomLoginFailsHandler");
  
          response.setCharacterEncoding("UTF-8");
          response.setContentType("application/json");
          PrintWriter printWriter = response.getWriter();

          JSONObject jsonObject; // response로 내보려는 정보를 담은 Json 객체
          HashMap<String, Object> responseMap = new HashMap<>(); // response 할 데이터를 담기 위한 맵
          responseMap.put("msg", "로그인 실패");

          jsonObject = new JSONObject(responseMap);
          printWriter.print(jsonObject);
          printWriter.flush();
          printWriter.close();
        }
      }
    
  
      ```
    </details>
  - <details>
      <summary>config 등록</summary>
    
      - 먼저 Provider를 빈으로 등록하고 커스텀 프로바이저를 가지는 authenticationManagerManager 생성
      - handler bean 등록
      - handler와 생성한 authenticationManagerManager를 커스텀 필터에 추가하여 반환
      - 해당 필터를 실행하고자 하는 시기에 맞춰 등록

      ```java
      package com.security.springboot.Security.configuration;
    
      import com.security.springboot.Security.Provider.CustomAuthenticationProvider;
      import com.security.springboot.Security.filter.CustomAuthenticationFilter;
      import com.security.springboot.Security.handler.CustomLoginFailureHandler;
      import com.security.springboot.Security.handler.CustomLoginSuccessHandler;
      import jakarta.servlet.DispatcherType;
      import lombok.RequiredArgsConstructor;
      import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
      import org.springframework.context.annotation.Bean;
      import org.springframework.context.annotation.Configuration;
      import org.springframework.security.authentication.AuthenticationManager;
      import org.springframework.security.authentication.ProviderManager;
      import org.springframework.security.config.annotation.web.builders.HttpSecurity;
      import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
      import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
      import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
      import org.springframework.security.config.http.SessionCreationPolicy;
      import org.springframework.security.core.userdetails.UserDetailsService;
      import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
      import org.springframework.security.web.SecurityFilterChain;
      import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
    
      @Configuration
      @EnableWebSecurity
      @RequiredArgsConstructor
      public class SecurityConfig {
    
          private final UserDetailsService userDetailsService;
    
          // 정적 자원 경로는 security 적용 하지 않음.
          @Bean
          public WebSecurityCustomizer configure() {
              return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
          }
    
          // 패스워드 인코더
          @Bean
          public BCryptPasswordEncoder bCryptPasswordEncoder() {
              return new BCryptPasswordEncoder();
          }
    
          @Bean
          public CustomAuthenticationProvider customAuthenticationProvider() {
              return new CustomAuthenticationProvider();
          }
    
          @Bean
          public AuthenticationManager authenticationManager() {
              return new ProviderManager(customAuthenticationProvider());
          }
    
          @Bean
          public CustomLoginFailureHandler customLoginFailureHandler() {
              return new CustomLoginFailureHandler();
          }
    
          @Bean
          public CustomLoginSuccessHandler customLoginSuccessHandler() {
              return new CustomLoginSuccessHandler();
          }
      
          @Bean
          public CustomAuthenticationFilter customAuthenticationFilter() {
              CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManager());
              customAuthenticationFilter.setFilterProcessesUrl("/api/v1/user/login");     // 접근 URL
              customAuthenticationFilter.setAuthenticationSuccessHandler(customLoginSuccessHandler());    // '인증'(로그인) 성공 시 해당 핸들러로 처리를 전가한다.
              customAuthenticationFilter.setAuthenticationFailureHandler(customLoginFailureHandler());    // '인증'(로그인) 실패 시 해당 핸들러로 처리를 전가한다.
              customAuthenticationFilter.afterPropertiesSet();
              return customAuthenticationFilter;
          }
    
          // security 설정
          @Bean
          public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
              return http
                      .csrf(AbstractHttpConfigurer::disable) // csrf 공격 보호 옵션 끄기. (rest api 에서는 필요 없기 때문)
                      .cors(AbstractHttpConfigurer::disable) // cors 예방 옵션 끄기
                      .formLogin(AbstractHttpConfigurer::disable) // form bases authentication 비활성화 (기본 로그인 페이지 비활성화, rest api만 작성하기 때문에 필요없음.)
                      .httpBasic(AbstractHttpConfigurer::disable) // http basic authentication 비활성화 (기본 로그인 인증창 비활성화, rest api만 작성하기 때문에 필요없음.)
                      .sessionManagement(session -> session // session 기반이 아닌 jwt token 기반일 경우 stateless 설정
                              .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                      .authorizeHttpRequests(request -> request // HTTP 요청에 대한 인증을 구성
                              .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll() // DispatcherType.FORWARD 유형의 모든 요청을 허용
                              .anyRequest().authenticated()) // 다른 요청은 인증을 받아야 함.
                      .addFilterBefore(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class) // 커스텀 필터 추가
                      .build();
          }
      }
      ```
    </details>
  - <details>
    <summary>결과</summary>
    
    ![img.png](image/img.png)
    ![img_1.png](image/img_1.png)
  
    </details>

</details>

<details>
    <summary>H2 사용시 설정</summary>

- Security 적용 후 Web에서 확인하려면 추가 설정이 필요함.
    
    ```java
    package com.security.springboot.Security.configuration;
    
    import com.security.springboot.Security.Provider.CustomAuthenticationProvider;
    import com.security.springboot.Security.filter.CustomAuthenticationFilter;
    import com.security.springboot.Security.handler.CustomLoginFailureHandler;
    import com.security.springboot.Security.handler.CustomLoginSuccessHandler;
    import lombok.RequiredArgsConstructor;
    import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.security.authentication.AuthenticationManager;
    import org.springframework.security.authentication.ProviderManager;
    import org.springframework.security.config.annotation.web.builders.HttpSecurity;
    import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
    import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
    import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
    import org.springframework.security.config.http.SessionCreationPolicy;
    import org.springframework.security.core.userdetails.UserDetailsService;
    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
    import org.springframework.security.web.SecurityFilterChain;
    import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
    
    @Configuration
    @EnableWebSecurity
    @RequiredArgsConstructor
    public class SecurityConfig {
    
        private final UserDetailsService userDetailsService;
    
        // 정적 자원 경로, h2 서버는 security 적용 하지 않음.
        @Bean
        public WebSecurityCustomizer configure() {
            return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations()).requestMatchers("/h2-console/**");
        }
    
        // 패스워드 인코더
        @Bean
        public BCryptPasswordEncoder bCryptPasswordEncoder() {
            return new BCryptPasswordEncoder();
        }
    
        @Bean
        public CustomAuthenticationProvider customAuthenticationProvider() {
            return new CustomAuthenticationProvider();
        }
    
        @Bean
        public AuthenticationManager authenticationManager() {
            return new ProviderManager(customAuthenticationProvider());
        }
    
        @Bean
        public CustomLoginFailureHandler customLoginFailureHandler() {
            return new CustomLoginFailureHandler();
        }
    
        @Bean
        public CustomLoginSuccessHandler customLoginSuccessHandler() {
            return new CustomLoginSuccessHandler();
        }
    
        @Bean
        public CustomAuthenticationFilter customAuthenticationFilter() {
            CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManager());
            customAuthenticationFilter.setFilterProcessesUrl("/api/v1/user/login");     // 접근 URL
            customAuthenticationFilter.setAuthenticationSuccessHandler(customLoginSuccessHandler());    // '인증'(로그인) 성공 시 해당 핸들러로 처리를 전가한다.
            customAuthenticationFilter.setAuthenticationFailureHandler(customLoginFailureHandler());    // '인증'(로그인) 실패 시 해당 핸들러로 처리를 전가한다.
            customAuthenticationFilter.afterPropertiesSet();
            return customAuthenticationFilter;
        }
    
        // security 설정
        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            return http
                    .csrf(AbstractHttpConfigurer::disable) // csrf 공격 보호 옵션 끄기. (rest api 에서는 필요 없기 때문)
                    .cors(AbstractHttpConfigurer::disable) // cors 예방 옵션 끄기
                    .headers(AbstractHttpConfigurer::disable) // h2 접근을 위해 사용. 다른 db 사용시 제거
                    .formLogin(AbstractHttpConfigurer::disable) // form bases authentication 비활성화 (기본 로그인 페이지 비활성화, rest api만 작성하기 때문에 필요없음.)
                    .httpBasic(AbstractHttpConfigurer::disable) // http basic authentication 비활성화 (기본 로그인 인증창 비활성화, rest api만 작성하기 때문에 필요없음.)
                    .sessionManagement(session -> session // session 기반이 아닌 jwt token 기반일 경우 stateless 설정
                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
    //                .authorizeHttpRequests(request -> request // HTTP 요청에 대한 인증을 구성
    //                        .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll() // DispatcherType.FORWARD 유형의 모든 요청을 허용
    //                        .anyRequest().authenticated()) // 다른 요청은 인증을 받아야 함.
                    .addFilterBefore(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class) // 커스텀 필터 추가
                    .build();
        }
    }
    ```
  
    ```properties
    # JPA 설정
    spring.jpa.hibernate.ddl-auto=create
    spring.jpa.properties.hibernate.format_sql=true
    spring.jpa.show-sql=true
    
    # H2 설정
    spring.h2.console.enabled=true
    spring.h2.console.path=/h2-console
    spring.datasource.driverClassName=org.h2.Driver
    spring.datasource.url=jdbc:h2:mem:test
    spring.datasource.username=sa
    spring.datasource.password=
    spring.sql.init.mode=always
    # 바로 위 구문이 먼저 있어야 JPA로 먼저 테이블 생성
    # spring.sql.init.data-locations=classpath:data.sql (h2 dependencies Implementation 했을 경우 사용. data.sql는 dml, schema.sql은 ddl 여기서 경로 설정한 data.sql 파일을 data(dml용) sql로 설정하겠다는 뜻 )

    # log 출력 설정
    logging.level.com.security.springboot=debug
    ```
</details>

<br/>

# JWT

## 0. 들어가기 앞서

- JWT는 Security와 함께 적용하지 않고 따로 사용 가능합니다.
- 해당 실습은 Security + JWT로 진행했습니다.

<br/>

## 1. 라이브러리 추가 및 설정

- Security와 동일
- java 11 이상일 경우 해당 라이브러리 추가
  ```properties
  implementation 'com.sun.xml.bind:jaxb-impl:4.0.1'
  implementation 'com.sun.xml.bind:jaxb-core:4.0.1'
  implementation 'javax.xml.bind:jaxb-api:2.4.0-b180830.0359'
  ```
 

<br/>

## 2. JWT

- <details>
    <summary>AuthConstants</summary>

    - 상수 파일로 JWT Header에 키 값인 authorization과 클라이언트에서 JWT로 전송할 때 사용하는 BEARER 값을 상수로 정의함.
  
    ```JAVA
    public final class AuthConstants {
        public static final String AUTH_HEADER = "Authorization";
        public static final String TOKEN_TYPE = "BEARER";
    }
    ```

    </details>
- <details>
    <summary>TokenUtil</summary>    

    - JWT 생성, 유효성 체크 등, 전반적인 기능을 모아둔 클래스.
    - private static final String jwtSecretKey는 따로 관리.

    </details>




