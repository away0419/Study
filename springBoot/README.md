최종 작성일 : 2023.07.17.</br>
# Spring-Security 

## 0. 들어가기 앞서
- spring Security는 버전에 따라 Deprecated 된 클래스, 함수들이 존재함. 따라서 버전을 잘 맞추고 사용해야 하는 메서드가 무엇인지 판단하기 바랍니다.
- [docs](https://docs.spring.io/spring-security/reference/index.html)가 가장 정확합니다.
- 흐름도를 참고하여 무엇을 먼저 작성해야 하는지 확인하기 바랍니다.
  ![Alt text](image.png)
  

<br/>

## 1. 라이브러리 추가 및 H2 설정

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
    implementation 'org.springframework.boot:spring-boot-starter-thymeleaf'
    annotationProcessor 'org.projectlombok:lombok'
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
    testImplementation 'org.springframework.security:spring-security-test'
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
```

</details>



<br/>

## 2. User Domain

- 로그인을 위한 유저 도메인.
- 회원 Entity, Service, Controller, Role 작성. (security는 UserDetail, UserDetailsService를 제공하며 이를 이용함. 따라서, UserDetail를 상속 받는 UserDetailVO를 만들고 이 안에 @Delegate로 UserEntity 필드를 만들어야함. 또한 UserDetailsService를 상속 받는 UserDetailsServiceImpl를 따로 구현해야함. User과 UserDetail를 따로 만드는 이유는 기능상 분리하는 것)

    <details>
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

    <details>
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

        @CreationTimestamp
        @Column(nullable = false, length = 20, updatable = false)
        private LocalDateTime createdAt;

        @UpdateTimestamp
        @Column(length = 20)
        private LocalDateTime updateAt;

        @Setter
        @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT true")
        private Boolean IsEnable = true;
    }

    ```

    </details>

    <details>
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

    <details>
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

    <details>
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

        Optional<UserEntity> findByUserEmail(String userId);
    }

    ```
    
    </details>

    <details>
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
        public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
            return userRepository.findByUserEmail(userId)
                    .map(user -> new UserDetailsVO(user, Collections.singleton(new SimpleGrantedAuthority(user.getRole().getPosition()))))
                    .orElseThrow(()-> new UsernameNotFoundException(userId));
        }
    }
    ```
    
    </details>

    <details>
        <summary>UserVO</summary>

    ```java
    package com.security.springboot.domain.User.Model;


    import com.security.springboot.domain.User.Role.UserRole;
    import lombok.Data;

    @Data
    public class UserVO {
        private String userEmail;
        private String userPw;
        private UserRole role;

        public UserVO(UserEntity userEntity) {
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

    <details>
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

    <details>
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

## 3. Config
- 인증과 인가를 담당.
- 사용자가 만든 필터, 어드바이저, 핸들러를 등록하거나, 추가적인 설정을 등록할 수 있는 클래스. 
- 클래스명은 상관 없음.

    <details>
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


## 기타 활용


<details> 
    <summary> Filter 적용하기</summary>

  - 해당 필터에서 아이디나 비밀번호 유효성 검사도 해주면 좋음.
  - 커스텀 필터는 UsernamePasswordAuthenticationFilter 이전에 적용해야 함.
  - 커스텀 필터 수행 이후 처리될 Handler 역시 Bean 등록하고 CustomAuthenticationFilter 핸들러로 추가해 주어야 함.

  ```java
  public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

      public CustomAuthenticationFilter(AuthenticationManager authenticationManager){
          super.setAuthenticationManager(authenticationManager);
      }

      @Override
      public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
          UsernamePasswordAuthenticationToken authRequest // request로 받은 유저 이메일과 비밀번호를 Toekn으로 만든다.
                  = new UsernamePasswordAuthenticationToken(request.getParameter("userEmail"), request.getParameter("userPW"));
          return this.getAuthenticationManager().authenticate(authRequest); // 해당 토큰을 검사한 뒤 인증된 사용자면 정상적으로 리턴. 아니라면 예외 발생
      }

  }
  ```

</details>


<details>
    <summary>Handler</summary>

  - AuthenticationProvider를 통해 인증이 성공될 경우 처리되는 핸들러.
  - 현재 JWT를 사용하지 않고 세션을 사용하고 있으므로 SecurityContextHolder에 사용자 정보 저장.


  ```java
  // 인증이 성공할 경우 처리.
  public class CustomLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

      @Override
      public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
          SecurityContextHolder.getContext().setAuthentication(authentication); // 인증이 완료된 객체를 저장해 두고 다른 서비스에서 사용자 정보를 사용할 때 꺼내 쓴다.
          response.sendRedirect("/about"); // 이후 
      }
  }
  ```
</details>


<details>
    <summary>Provider</summary>
    
  - 전달 받은 usernamePasswordToken을 순차적으로 Provider들에게 전달.
  - 실제 인증 과정을 수행. (Authenticate 함수에 작성)
  - 해당 Provider를 SecurityConfig에서 Bena 등록해주어야 함.

  ```java
  @RequiredArgsConstructor
  public class CustomAuthenticationProvider implements AuthenticationProvider {

      private final UserDetailsService userDetailsService;
      private final BCryptPasswordEncoder passwordEncoder;

      @Override
      public Authentication authenticate(Authentication authentication) throws AuthenticationException {
          UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;

          // 아이디를 가져온다.
          String userEmail = token.getName();
          //비밀번호를 가져온다.
          String userPw = (String) token.getCredentials();
          // 아이디로 사용자 조회
          UserDetailsVO userDetailsVO = (UserDetailsVO) userDetailsService.loadUserByUsername(userEmail);

          // 찾은 사용자의 비밀번호가 일치하지 않는 경우 예외 처리
          if(!passwordEncoder.matches(userPw, userDetailsVO.getPassword())) {
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






