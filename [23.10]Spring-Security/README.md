> ## Spring Security

- 스프링 기반 보안을 (인증, 권한, 인가) 담당하는 스프링 하위 프레임워크.
- 보안과 관련된 많은 옵션을 제공. (개발 비용 감소)
- 주로 서블릿 필터와 이들로 구성된 위임 모델 사용.

- 인증 : 검증된 사람인지 확인. (주로 사용자 Request와 동일한 정보가 DB에 있는지 확인)
- 인가 : 인증된 사람에게 권한이 있는지 확인.
- 권한 : 요청에 대한 응답을 받을 수 있는 자격.

<br/>
<br/>

> ## 스프링 시큐리티 특징

- 보안과 관련 된 체계적인 옵션 제공.
- Filter 기반으로 MVC와 분리하여 관리하고 동작함.
- 어노테이션을 통한 간단한 설정 가능.
- 기본적으로 세션 & 쿠키 방식. 추가적으로 JWT 방식
- 인증 관리자(UsenamePasswordAuthenticationFilter)와 접근 결정 관리자(FilterSecurityInterceptor)를 통해 리소스(DB) 접근 관리.

<br/>
<br/>

> ## 인증 플로우

![Alt text](image/image-0.png)

1. [Client] 로그인 정보 담아 Request.

2. [UsernamePasswordAuthenticationFilter] 클라이언트가 요청한 로그인 정보를 인터셉트. 해당 정보 각각 유효성 검사 후 UseNamePasswordAuthenticationToken(인증 되지 않은 Token, 로그인 정보를 포함한 객체) 생성.

3. [UsernamePasswordAuthenticationFilter] AuthenticationManager 인터페이스를 구현한 ProviderManager(Authentication 전달) 호출.

4. [ProviderManager] 실제 사용자인지 확인 하기 위해 AuthenticationProvider(Authentication 전달) 호출.

5. [AuthenticationProvider] 인증 확인을 위해 UserDetailsService(Authentication 전달) 호출.

6. [UserDetailsService] 전달 받은 Authentication 정보 저장소에 있는지 확인. (대부분 ID 여부만 확인)

7. [UserDetailsService] 정보가 있는 경우 UserDetails 생성.

8. [UserDetailsService] 생성한 UserDetails AuthenticationProvider에 반환.

9. [AuthenticationProvider] UserDetails Credential과 Authentication Credential 동일 여부 확인(인증 확인) 완료되면 UserDetails 정보에서 Principal, Credential, List<GrantedAuthority>(권한) 추출 후 UseNamePasswordAuthenticationToken 생성. (인증 완료된 Token)

10. [AuthenticationProvider] UseNamePasswordAuthenticationToken ProviderManager에 반환.

11. [ProviderManager] UseNamePasswordAuthenticationToken UsernamePasswordAuthenticationFilter에 반환.

12. [UsernamePasswordAuthenticationFilter] UseNamePasswordAuthenticationToken을 SecurityContextHolder 안에 있는 SecurityContext에 저장. (기본적으로 SecurityContext에 저장된 정보는 SecurityContextPersistenceFilter를 통해 HttpSession에 저장되어 인증 상태 유지됨 -> Session 방식)

<br/>
<br/>

> ## 모듈

<details>
  <summary>Authentication</summary>

- 현재 접근하는 주체의 정보와 권한을 담은 인터페이스.

  ```java
  public interface authentication extends Principal, Serializable {
      // 현재 사용자의 권한 목록
      Collection<? extends GrantedAuthority> getAuthorities();

      // credentials (주로 비밀번호)
      Object getCredentials();

      Object getDetails();

      // Principal 객체
      Object getPrincipal();

      // 인증 여부를 가져옴
      boolean isAuthenticated();

      // 인증 여부 설정
      void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException;

  }
  ```

  </details>

<details>
  <summary>UsernamePasswordAuthenticationToken</summary>

- Authentication 상속받은 AbstractAuthenticationToken의 하위 클래스.
- 인증 완료 전 또는 인증 완료 후의 정보를 담은 객체.
  - 첫 번째 생성자는 인증 전의 객체를 생성.
  - 두 번째 생성자는 인증 완료된 객체를 생성함.
- 플로우 흐름 별 사용자 정보를 편하게 주고 받기 위한 객체.
- 사용자의 아이디가 Principal, 비밀번호가 Credential로 매칭됨.

  - Principal(사용자 정보) : 저장소에 저장되어 있는 사용자 정보. (개발자가 클라이언트에게 보내고 싶은 정보만 담은 객체)
  - Credential(민감한 정보) : 사용자 검증을 할 때 사용 되는 정보, 저장소에 저장되어 있는 민감한 정보. (대부분 비밀번호)
  - List&lt;GrantedAuthority&gt;(권한 목록) : Principal이 가지고 있는 권한 목록. (prefix ROLE -> ROLE_ADMIN, ROLE_USER 등)

  ```java
  public class UsernamePasswordAuthenticationToken extends AbstractAuthenticationToken {
      // 주로 사용자의 ID
      private final Object principal;

      // 주로 사용자의 비밀번호
      private Object credentials;

      // 인증 완료 전의 객체 생성
      public UsernamePasswordAuthenticationToken(Object principal, Object credentials){
          super(null):
          this.principal = principal;
          this.credentials = credentials;
          setAuthenticated(false);
      }

      // 인증 완료 후의 객체 생성
      public UsernamePasswordAuthenticationToken(Object principal, Object credentials, Collection<? extemds GramtedAuthority> authorities) {
          super(authorities);
          this.principal = principal;
          this.credentials = credentials;
          super.setAuthenticated(ture);
      }
  }

  public abstract class AbstractAuthenticationToken implements Authentication, CredentialsContainer {}
  ```

  </details>

<details>
  <summary>AuthenticationManager</summary>

- 인증 처리를 지시하는 매니저 인터페이스.
- AuthenticationProvider이 등록되어 있음.

</details>

<details>
  <summary>ProviderManager</summary>

- AuthenticationManager 상속받은 클래스.
- 인증이 성공적으로 이루어지면 Credentials 제거. (인증 완료 후 더이상 필요 없고 민감 정보이므로 제거)

</details>

<details>
  <summary>AuthenticationProvider</summary>

- 인증된 사용자인지 판단하는 인터페이스.
- AuthenticationManager 지시를 받는 현장 담당자 역할.
</details>

<details>
  <summary>UserDetails</summary>

- 저장소에 저장된 사용자 정보를 담을 객체.
- UserDetails 인터페이스의 경우 직접 구현한 UserVO 클래스에 UserDetails를 상속하여 처리하거나 UserDetailsVO에 UserDetails를 상속하여 처리 가능.

  ```java
  public interface UserDetails extends Serializable {
      Collection<? extends GrantedAuthority> getAuthorities();
      String getPassword();
      String getUserName();
      boolean isAccountNonExpired();
      boolean isAccountNonLocked();
      boolean isCredentialExpired();
      boolean isEnabled();
  }
  ```

  </details>

<details>
  <summary>UserDetailsService</summary>

- UserDetails 반환하는 단 하나의 메소드를 가지고 있는 인터페이스.
- 분리를 위해 UserService를 따로 만들고, UserDetailsService를 상속받은 클래스에서 UserService를 호출하여 UserDetails에 넣을 사용자 정보를 가져오는 것이 일반적.

  ```java
  public interface UserDetailService {
      UserDetails loadUserByUsername(String var1) throws UsernameNotFountException;
  }
  ```

  </details>

<details>
  <summary>PasswordEncoding</summary>

- 패스워드 암호화 설정 인터페이스.
- AuthenticationManagerBuilder.userDetailsService().passwordEncoder() 통해 패스워드 암호화에 사용될 PassEncoder 구현체 지정 가능.

  ```java
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
      auth.userDetailService(userDetailsService).passwordEncoder(passwordEncoder());
  }

  @Bean
  public PasswordEncoder passwordEncoder(){
      return new BcryptPasswordEncoder();
  }
  ```

  </details>

<details>
  <summary>SecurityContextHolder</summary>

- 인증된 객체를 SecurityContext에 저장하기 위한 객체.

</details>

<details>
  <summary>SecurityContext</summary>

- 인증된 객체를 보관하는 객체
- SecurityContext를 통해 Authentication 객체를 꺼내올 수 있음.

</details>

<br/>
<br/>

> ## 인가 권한 확인 플로우

![Alt text](image/image-4.png)

1. [AuthorizationFilter] SecurityContext에 저장되어 있는 인증 완료된 객체 획득.
2. [AuthorizationFilter] AuthorizationManager 인터페이스를 구현한 RequestMatcherDelegatingAuthorizationManager 호출. (Authentication, HttpServletRequest 전달)
3. [RequestMatcherDelegatingAuthorizationManager] 확인 결과 적절한 권한을 가졌다면 통과. 아니라면 예외 발생. 해당 에러는 ExceptionTranslationFilter가 처리.

<br>
<br>

> ## 인증 인가 예외 처리

- Security 인증 인가 로직에서 예외가 발생한 경우 처리 방법이 다양함.
- 필터 체인이기 때문에 처리 방법들 간 처리 순서가 정해져 있음.
- 따라서 앞에서 처리할 경우 뒤에 로직은 실행 안될 수 있음.

<br/>
<br/>

> ## 예외 처리 방법

<details>
  <summary>AuthenticationEntryPoint</summary>

- 인증 확인 시점에서 Exception 발생한 경우 도착하게 되는 지점.
- 어떤 예외가 발생한지 모르겠다면 여기서 일괄 처리 가능.
- 주로 로그인 페이지로 리다이렉션하거나 개발자 정의 오류 메시지 반환.
</details>

<details>
  <summary>AccessDeniedHandler</summary>

- 권한 확인 시점에서 Exception 발생할 경우 도착하게 되는 지점.
- 주로 권한 거부 페이지로 리다이렉션하거나 사용자 정의 오류 메시지 반환.
</details>

<details>
  <summary>ExceptionTranslationFilter</summary>

- 인증, 인가에서 발생한 예외를 통합 처리하는 지점.
- 앞서 설명한 두 방식을 합친 것으로 공통된 처리 방식일 경우 사용.
- 만약 위의 두 방법이 모두 설정되어 있다면 동작하지 않음.
</details>

<details>
  <summary>Global Exception Handling</summary>

- 개발자가 직접 예외, 처리 Handler 구현.
- @ControllerAdvice OR @RestControllerAdvice 사용.
- 인증, 인가에서 발생한 예외를 개발자가 만든 예외로 대체하여 예외 던짐.
- 가장 큰 틀에서 Try-Catch로 개발자가 만든 예외를 처리함. 이때, Request에 해당 예외를 저장.
- 기본적으로 인증, 인가에서 예외가 발생하면 각각 AccessDeniedHandler, EntryPoint를 타게 됨.
- 이때, Request에서 저장된 예외가 있는지 확인. 만약 존재할 경우 Spring Boot가 기본적으로 가지고 있는 resolver에 예외 처리 위임. 이 경우 Filter에서 예외가 발생 했지만, @ExceptionHandler로 처리가 가능해짐.
- 장점
  - 사용자가 만든 에러 처리 틀을 이용할 수 있으므로 Security 에러 뿐만 아니라 다른 에러와도 형식을(반환 값 등) 맞출 수 있음. (일관성)
  - 에러 코드를 한 곳에 모와 관리할 수 있음. (중앙 집중화)
  - 프로젝트에 맞는 보안 설계로 만들 수 있음.
- 단점
  - @ControllerAdvice 사용법을 숙지해야함.
  - 디버깅이 어려움.
  - 보안 설계를 신중하게 설계 해야 함.
  </details>

<br/>
<br/>

> ## SecurityFilterChain

![Alt text](image/image-1.png)
| 필터명 | 설명 |
| ----------------------------------- | -------------------------------------------------------------------------------------------------------------------------- |
| SecurityContextPersistenceFilter | SecurityContext를 로드하고 저장 |
| LogoutFilter | 로그아웃 URL로 지정된 가상 URL에 대한 요청을 감시하고 매칭되는 요청이 있으면 사용자를 로그아웃 시킴 |
| UsernamePasswordAuthenticationFilter | 사용자명과 비밀번호로 이루어진 폼 기반 인증에 사용하는 가상 URL 요청을 감시하고 요청이 있으면 사용자의 인증을 진행 |
| DefaultLogInPageGeneratingFilter | 폼 기반 또는 OpenID 기반 인증에 사용하는 가상 URL에 대한 요청을 감시하고 로그인 폼 기능을 수행하는데 필요한 HTML 생성 |
| BasicAuthenticationFilter | HTTP 기본 인증 헤더를 감시하고 이를 처리 |
| RequestCacheAwareFilter | 이 필터가 호출되는 시점까지 사용자가 아직 인증을 받지 못했다면 요청 관련 인증 토큰에서 사용자가 익명 사용자로 나타나게 됨. |
| SessionManagementFilter | 인증된 주체를 바탕으로 세션 트래킹을 처리해 단일 주체와 관련한 모든 세션들이 트래킹되도록 도움 |
| ExceptionTranslationFilter | 이 필터는 보호된 요청을 처리하는 동안 발생할 수 있는 기대한 예외의 기본 라우팅과 위임을 처리함 |
| FilterSecurityInterceptor | 이 필터는 권한부여와 관련한 결정을 AccessDecisionManager에게 위임해 권한 부여 결정 및 접근 제어 결정을 쉽게 만들어 줌 |

- addFilter() : 해당 필터 실행 후 지점에 필터 추가.
- addFilterBefore() : 해당 필터 실행 전 지점에 필터 추가.
- Security를 적용하기 위해 여러가지 Filter를 상속 받아 로직을 구현함. 이때, 어떤 Filter을 상속 받아야 하는지 해당 표를 보면 됨.
- 해당 그림만으로 설명하긴 힘드므로 실습을 통해서 알아가는 것이 좋음.

<br/>
<br/>

> ## 인증 완료 후 사용자 정보 얻는 방법

<details>
  <summary>SecurityContextHolder Bean에서 사용자 정보 가져오기</summary>

- SecurityContextHolder를 토앻 SpringSecurityContext Bean에 등록된 인증 완료한 Authentication 객체를 가져와 사용자 정보 얻기.

  ```java
  Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  UserDetails userDetails = (UserDetails)principal;
  String username = principal.getUsername();
  String password = principal.getPassword();
  ```

  </details>

<details>
  <summary>Controller에서 사용자 정보 가져오기</summary>

- 위와 동일하게 SpringSecurityContext Bean에 등록되어 있는 Authentication 또는 Principal를 가져와 자동으로 매핑.

  ```java
  @Controller
  public class UserController{
    @GetMapping
    public String getMyInfo(Authentication authentication){
      JwtAuthenticationToken authentication = (JwtAuthenticationToken) authentication;
      User user = (User)authentication.getDetails();
      return user.toString();
    }
  }
  ```

  </details>

<details>
  <summary>@AuthenticationPrincipal</summary>

- SpringSecurity 3.2 부터 사용 가능.

```java
@Controller
public class SecurityController
{
  @GetMapping("/messages/inbox")
    public ModelAndView currentUserName(@AuthenticationPrincipal CustomUser customUser)
    {
      String username = customUser.getUsername();
        // .. find messages for this user and return them ...
    }
}
```

</details>

<br/>
<br/>

> ## JWT란

- JSON 객체를 통해 안정하게 정보를 전송할 수 있는 웹 표준.
- 세션이 유지 되지않는 다중 서버 환경에서 로그인 유지 가능.
- '.' 구분자로 세 부분으로 구분되어 있음.

<br/>
<br/>

> ## JWT 구조

![Alt text](image/image.png)

<details>
  <summary>Header</summary>

- 토큰의 타입과 해싱 알고리즘 정보를 포함하고 있음.
- 토큰의 타입은 항상 JWT로 고정되어 있음.
- 해싱 알고리즘은 토큰 생성 시 사용된 알고리즘 정보를 포함하고 있음.
- Header는 암호화 되지 않음.
- Header는 토큰의 무결성을 검증하는데 사용됨.
- 토큰의 무결성을 검증하는 방법은 토큰을 생성할 때 사용된 해싱 알고리즘을 이용해 토큰을 생성하고 토큰을 검증할 때도 동일한 해싱 알고리즘을 이용해 토큰을 검증함.

```json
{
  "typ": "JWT", // JWT 타입
  "alg": "HS256" // JWT에서 사용되는 알고리즘
}
```

</details>

<details>
  <summary>Payload</summary>

- 토큰에 담을 정보(claim)들을 JSON으로 만든 형태.
- claim은 key와 value가 한쌍으로 이루어진 형태.
- claim은 크게 세 가지로 나눌 수 있음.
  - 등록 클레임(Registered Claim): 필수로 사용되는 정보(데이터)는 아니지만 JWT가 기본적으로 가지는 정의된 key-value을 이용하여 Token 생성 정보를 만들 때 사용함.
    - iss : 토클 발급자
    - sub : 토큰 제목
    - aud : 토큰 대상자
    - exp : 토큰 만료 시간
    - nbf : 토큰 활성 날짜
    - iat : 토큰 발급 시간
    - jti : JWT 고유 식별자
  - 공개 클레임(Public Claim): 공개용 정보를 뜻하며 key에 URI 포맷을 이용함.
    - "https://github.comn/away0419" : true
  - 비공개 클레임(Private Claim): 실제 사용되는 정보(데이터)임.
    - "userId" : "away0419"
- 등록 클레임은 토큰에 포함되어 있어도 되지만 비공개 클레임은 포함되면 안됨.

```json
{
  "sub": "123456789", // 비공개 클레임
  "userId": "Jone Doe", // 비공개 클레임
  "https://github.comn/away0419": true // 공개 클레임
}
```

</details>

<details>
  <summary>Signature</summary>

- 인코딩된 헤더(Header)와 인코딩된 페이로드(payload)를 합친 후 비밀 키(Secret)와 알고리즘을 기반으로 백엔드에서 해싱하여 발급됨.
</details>

<br>
<br>

> ## 로그인 후 회원 검증 방식

<details>
  <summary>Session</summary>

- 사용자 정보를 서버에(SecurityContext) 저장. 이와 매칭 되는 세션 ID 발급.
- 클라이언트는 서버에 요청을 보낼 때 세션 ID를 Header에 담아 보냄.
- 세션 ID가 탈취 될 위험성이 있으며 서버가 세션 저장소를 필요로 하기 때문에 추가적인 저장 공간이 필요.
- 서버에서 사용자를 관리하기 때문에 서버에 부담을 줄 수 있음.

  ![Alt text](image/image-2.png)

</details>

<details>
  <summary>Access Token & Refresh Token (JWT)</summary>

- 흔히 JWT 라고 불리는 토큰 방식.
- 인증에 필요한 정보를 암호화하여 만든 토큰을 활용한 방법. (JWT 활용)
  - Access Token : 사용자 인증 정보를 암호화한 토큰.
  - Refresh Token : Access Token 만료 시 재발급 하는 용도.
- 사용자 인증 완료 시, Access Token 발급 후 HttpHeader에 담아 응답.
- 이후, 클라이언트는 Access Token을 Header에 담아 요청.
- 권한이 필요한 요청일 경우 Access Token에 있는 사용자 권한 확인.
  - 확인 결과 권한 있을 경우 요청에 대한 적절히 응답.
  - 만약, 권한이 없다면 권한 없음 응답.
  - 만약, Access Token이 유효하지 않다면 에러 응답.
- 기본적으로 DB 접근 과정이 로그인할 때만 있어 효율적.
- Refresh Token 추가 시 빨간색 과정 추가. (선택 사항)
  - Access Token 기간 만료 시 재발급 하는 용도. (보안성 향상이 목적)
  - 사용자 정보를 담고 있기 보단, 기간 만료 등 공개 클레임만 가짐.
- Security 미사용 시 로그인 결과 값을 반환하는 로직에서 JWT를 발급.

  ![Alt text](image/image-3.png)

</details>

<br/>
<br/>

> ## Security + JWT 프로세스

- Security 로그인 성공 시 실행되는 로직에서 JWT를 발급.
- SecurityConfig에서 세션을 stateless로 설정 해야 의미가 있음.

- 로그인(인증) 프로세스 흐름
  ![Alt text](image/image-5.png)

  - 기본적인 흐름은 Security 로그인 플로우와 동일.
  - JwtAuthenticationFilter에서(UsernamePasswordAuthenticationFilter) Response에 JWT 추가 로직을 구현한 것.
  - 따라서 Security의 플로우를 확인하며 해당 프로세스 흐름을 보면 이해하기 쉬움.

- 자격 검증 프로세스 흐름
  ![Alt text](image/image-6.png)
  - 사용자가 Header에 보낸 JWT을 JwtVerificationFilter에서 검증.
  - 검증이 완료되면 인증된 객체를 만들어 SecurityContext에 저장. 이를 통해 이후 흐름에서 문제 없이 통과 가능. (저장하지 않을 경우, 다음 흐름에서 인증된 객체를 찾지 못해 통과 못함.)
  - 이때, SecurityContext에 저장된 정보는 모든 흐름 통과 후 SecurityContextPersistenceFilter가 삭제하여 세션 유지 되지 않도록 함.

<br/>
<br/>

> ## Refresh Token

<details>
  <summary>Refresh Token 생긴 이유</summary>

- Access Token 만료 시간이 클수록 탈취될 가능성 높아짐.
- 반대로 만료 시간이 작을수록 자주 로그아웃 됨.
- 이를 해결하고자 나온 Token.
</details>

<details>
  <summary>Refresh Token 흐름</summary>

- 사용자 인증 완료(로그인) 시, Access Token과 Refresh Token 발급.
- 사용자가 서비스 요청 했을 때, Access Token 기간 만료시 기간 만료 에러 응답.
- 사용자가 기간 만료 에러 코드 확인 후 Refresh Token을 담아 Access Token 재발급 요청.
  - 보안 강화 필요시 Access token 함께 보내도 되나 Jwts 라이브러리 사용 시 Access Token 검증에서 복호화 할 경우 기간만료 예외가 발생하므로 해당 예외를 처리한 뒤 Refresh Token 검증 로직 추가해야 하므로 복잡해짐.
- 서버에서 Refresh Token 확인 결과 유효하다면, Access Token 재발급 응답.
  - 만약, Refresh Token 남은 만료 시간이 1주일 이하라면 RefreshToken 함께 재발급.
- 사용자는 재발급 받은 Access Token으로 서비스 다시 요청.
</details>

<details>
  <summary>Refresh Token 단점</summary>

- Refresh Token를 사용할 경우 추가적인 요청/응답이 발생한다는 단점이 있음.
  - 또한, Refresh Token 유효성 검사를 위해 DB에 Refresh Token을 저장하는 경우가 있어 추가적인 DB 연결이 발생함. (Cache 이용하여 최소화 가능)
  - spring cache는 session과 별 차이 없고, Redis Cache 사용하는게 일반적.
- Refresh Token 역시 탈취 가능성 있음. 이를 막고자 여러 전략이 생김.
</details>

> ## Refresh Token 보안 전략

<details>
  <summary>RTR(Refresh Token Rotation) 전략</summary>

- Refresh Token 1회용 전략.
- Refresh Token 1회라도 사용되면 Refresh Token을 함께 재발급 하는 전략.
  - DB를 사용하는게 일반적임. (JWT 강점 약화)
  - Redis Cache로 DB 연결 최소화.
- Refresh 탈취 당하더라도, 한번 사용된 시점에 값이 바뀌어 DB에 저장됨.
  - 다음 Refresh 요청 시 DB에 저장된 Refresh 값과 다르다면 연결 거부.
  - 이후 DB에 저장된 Refresh 값을 임의로 변경.
  - 따라서 재로그인 해야 서비스 이용 가능.
  - 이때, 다시 로그인한 사용자에게 비정상 접근을 알려준다면 Best.
- XSS 공격에 취약.
</details>

<details>
  <summary>http-only 적용 전략</summary>

- http-only 적용한 쿠키에 Refresh Token 담아 넘기는 전략. (쿠키 전송 방지)
  - 쿠키도 원래 헤더에 저장됨.
  - 쿠키 설정에 HTTP-ONLY를 적용하는 것.
  - 설정할 경우 해당 쿠키는 JS에서 접근 불가. (XSS 공격 방어, 개발자 모드로는 확인 할 수 있음)
- csrf 공격 취약함.

</details>

<details>
  <summary>Sliding Sessions 전략</summary>

- Access Token 기간 만료 직전 요청이 들어온 경우 해당 Access Token을 재발급 하여 응답 값과 함께 보냄.
- Access Token 기간 만료 시간 보다 이후에 완료되는 작업시 작업 도중에 기간 만료가 발생하는 불상사를 줄일 수 있음.
- 잦은 Access Token 발급으로 비용 증가.
</details>

<br/>
<br/>

> ## 인증 정보 헤더 전달 방식

<details>
    <summary>Authorization Header</summary>

- 일반적으로 토큰은 Authorization Header에 담아서 서버에 전송함.
- Authorization: `<type>` `<credentials>` 형식으로 우리가 흔히 사용하는 bearer는 type 형식에 해당함.
- type에는 여러 종류가 있음. (토큰 타입과는 별개)
  - basic : 아이디와 비밀번호를 Base64로 인코딩한 값 사용
  - bearer : JWT 또는 OAuth에 대한 토큰 사용
  - digest : 서버는 난수를, 클라이언트는 사용자 정보와 nonce를 포함하는 해시값 사용
  - HOBA : 전자 서명 사용
  - Mutual : 암호를 이용한 서버-클라이언트 상호 인증
  - AWS4-HMAC-SHA256 : AWS 전자 서명 사용
  </details>

<br/>
<br/>

> ## OAUTH2

- 인증을 위한 개방형 표준 프로토콜.
- 사용자 인증 확인을 다른 서비스에 위임하는 것.
- 다른 서비스에 저장 된 사용자 정보를 받아올 수 있음.

<br/>
<br/>

> ## Security OAuth2 제공 서비스

- Spring Security에서 지원하는 OAuth2 제공 서비스들은 구글, 페이스북, 깃허브 등이 있음.
- Naver와 Kakao의 경우 지원하지 않으므로 추가 설정이 필요함.
- 사용자 정보는 Json 형태이며, 서비스 별 제공해주는 사용자 정보가 다름. 또한, Json 필드 명도 다르기 때문에 해당 서비스에 가서 확인 해야 함.
  - 서비스1 ={"nickname" : "사용자"}, 서비스2 = {"name" : "사용자"} 처럼 다를 수 있음.

<br/>
<br/>

> ## 많이 사용되는 OAuth2 Flow

![Alt text](image/image-7.png)

- 사용자가 우리 서버에 간편 로그인 요청.
- 우리 서버는 OAuth2 제공 서비스의 로그인 창으로 사용자를 보냄.
- 사용자가 OAuth2 제공 서비스 로그인 창에서 로그인.
  - 사용자 인증 확인 처리는 해당 OAuth2 제공 서비스에서 진행. (자동)
  - 만약, 로그인 성공 시 Authorization code 사용자에게 응답. (자동)
  - 사용자는 Authorization code 우리 서버에 전달. (자동)
  - 우리 서버는 전달 받은 Authorization code를 OAuth2 제공 서비스에 전달. (자동)
  - OAuth2 제공 서비스가 전달 받은 Authorization code 확인 후 Access Token 우리 서버에 발급. (자동)
  - 우리 서버는 발급 받은 Access Token으로 OAuth2 제공 서비스에게 유저 정보 요청.
  - OAuth2 제공 서비스에서 이를 확인 후 요청한 유저 정보 우리 서버에 전달. (자동)
- 우리 서버는 해당 정보를 가지고 우리 DB에 가입한 유저 있는지 확인.
  - 우리 서비스에 회원가입이 따로 있을 경우, 회원가입으로 이동.
  - 없는 경우 로그인 성골 로직으로 이동.
- 로그인 성공 핸들러에서 우리 서버가 만든 Access Token, Refresh Token 발급. OR Session 저장.
- 이후 사용자의 요청은 JWT 방식과 동일.

<br/>
<br/>

> ## Authorization code 있는 이유

- 사용자가 OAuth2 제공 서비스에서 로그인 성공 한 경우 Redirect로 이동함.
- Redirect 이동의 경우 데이터를 URL에 담아서 보낼 수 밖에 없음.
- 따라서, Access Token이 노출될 수 있기 때문에 사용함.
