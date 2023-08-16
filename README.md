최종 작성일 : 2023.06.19.</br>
# Spring-Security 

### 스프링 시큐리티란
스프링 기반 보안(인증, 권한, 인가)을 담당하는 스프링 하위 프레임워크.
보안과 관련된 많은 옵션을 제공. (개발 비용 감소)
주로 서블릿 필터와 이들로 구성된 위임 모델 사용.
- 인증 : 검증된 사람인지 확인
- 인가 : 인증 이후 해당 기능에 접근할 수 있는지 확인 
- 권한 : 인가 과정에서 해당 리소스에 접근할 수 있는 최소 권한을 가졌는지 확인 

<br/>

### 스프링 시큐리티 특징과 구조
- 보안과 관련하여 체계적으로 많은 옵션 제공
- filter 기반으로 동작하여 MVC와 분리하여 관리 및 동작
- 어노테이션을 통한 간단한 설정
- Spring Security는 기본적으로 세션 & 쿠키 방식으로 인증
- 인증 관리자(UsenamePasswordAuthenticationFilter)와 접근 결정 관리자(FilterSecurityInterceptor)를 통해 사용자의 리소스 접근을 관리
- Principal(접근 주체) : 보호 받는 Resource에 접근하는 대상 (ID)
- Credential(비밀번호) : Resource에 접근하는 대상의 비밀번호

<br>

### 인증 아키텍쳐 (Form 로그인 플로우)
<img src="https://blog.kakaocdn.net/dn/Svk8p/btqEIKlEbTZ/vXKzokudAYZT9kRGXNHJe1/img.png" />

1. 사용자가 Form을 통해 로그인 정보를 입력하고 인증 요청을 보냄.
2. AuthenicationFilter가 HttpServletRequest에서 사용자가 보낸 아이디, 패스워드를 인터셉트. 아이디와 패스워드 유효성 검사 후 AuthenticationManager 인터페이스에 인증용 객체(UseNamePasswordAuthenticationToken)를 만들어 위임.
3. 실제 인증을 할 AuthenticationProvider에게 인증용 객체를 다시 전달.
4. 해당 인증 객체를 UserDetailService에 넘기고, 해당 서비스가 DB에서 UserDetails 객체를 만들어줌. (이때, 인증용 객체와 도메인 객체를 분리하지 않기 위해서 실제 사용되는 도메인 객체에 UserDetails를 상속하기도 함.)
5. AuthenticationProvider는 UserDetils 객체를 전달 받은 이후 실제 사용자의 입력정보와 UserDetails 객체를 가지고 인증 시도.
6. 인증이 완료되면 사용자 정보를 가진 Authentication 객체를 SecurityContextHolder에 담은 이후 AuthenticationSuccessHandle 실행. (실패 시 AuthenticationFailureHandler)

<br>

### 필터별 기능 설명
|필터명|설명|
|--|--|
|SecurityContextPersistenceFilter|SecurityContext를 로드하고 저장|
|LogoutFilter|로그아웃 URL로 지정된 가상 URL에 대한 요청을 감시하고 매칭되는 요청이 있으면 사용자를 로그아웃 시킴|
|UsenamePasswordAuthenticationFilter|사용자명과 비밀번호로 이루어진 폼 기반 인증에 사용하는 가상 URL 요청을 감시하고 요청이 있으면 사용자의 인증을 진행|
|DefaultLogInPageGeneratingFilter|폼 기반 또는 OpenID 기반 인증에 사용하는 가상 URL에 대한 요청을 감시하고 로그인 폼 기능을 수행하는데 필요한 HTML 생성|
|BasicAuthenticationFilter|HTTP 기본 인증 헤더를 감시하고 이를 처리|
|RequestCacheAwareFilter|이 필터가 호출되는 시점까지 사용자가 아직 인증을 받지 못했다면 요청 관련 인증 토큰에서 사용자가 익명 사용자로 나타나게 됨.|
|SessionManagementFilter|인증된 주체를 바탕으로 세션 트래킹을 처리해 단일 주체와 관련한 모든 세션들이 트래킹되도록 도움|
|ExceptionTranslationFilter|이 필터는 보호된 요청을 처리하는 동안 발생할 수 있는 기대한 예외의 기본 라우팅과 위임을 처리함|
|FilterSecurityInterceptor|이 필터는 권한부여와 관련한 결정을 AccessDecisionManager에게 위임해 권한 부여 결정 및 접근 제어 결정을 쉽게 만들어 줌|


<br>

### 모듈

#### [SecurityContextHolder]
보안 주체의 세부 정부를 포함하여 응용 프로그램의 현재 보안 컨텍스트에 대한 세부 정보가 저장됨.
<br/>

#### [SecurityContext]
Authentication을 보관하는 역할, SecurityContext를 통해 Authentication 객체를 꺼내올 수 있음.
<br/>

#### [Authentication]
현재 접근하는 주체의 정보와 권한을 담는 인터페이스.
Authentication 객체는 Security Context에 저장되며, SecurityContextHolder를 통해 SecurityContext에 접근하고, SecurityContext를 통해 Authentication에 접근할 수 있음.

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
<br/>

#### [UsernamePasswordAuthenticationToken]
Authentication을 implements한 AbstractAuthenticationToken의 하위 클래스로, User의 ID가 Principal 역할을 하고, Password가 Credential의 역할을 함. UsernamePasswordAuthenticationToken의 첫 번째 생성자는 인증 전의 객체를 생성하고, 두번째 생성자는 인증이 완료된 객체를 생성함.

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
 <br/>

 #### [AuthenticationProvider]
 실제 인증에 대한 부분을 처리함. 인증 전의 Authentication 객체를 받아 인증이 완료 된 객체를 반환하는 역할. 인터페이스를 구현하소 AuthenticationManager에 등록하면 됨.

 ``` java
    public interface AuthenticationProvider {
        Authentication authenticate(Authentication var1) throws AuthenticationException;

        boolean supports(Class<?> var1);
    }
 ```
<br/>

 #### [Authentication Manager]
 인증에 대한 부분을 처리하는데 실질적으로 AuthenticationManager에 등록된 AuthenticationProvider이 처리함.
 인증이 성공하면 2번째 생성자를 이용해 인증이 성공한 객체를 생성하여 Security Context에 저장.
 인증 상태를 유지하기 위해 세션에 보관하며, 인증이 실패한 경우에는 AuthenticationException 발생시킴.

 AuthenticationManager를 implements한 ProviderManager는 실제 인증 과정에 대한 로직을 가지고 있는 AuthenticationProvider를 List로 가지고 있으며, ProviderManager는 for문을 통해 모든 provider를 조회하면서 Authenticate를 처리함.
 ```java
    public class ProviderManger implements AuthenticationManager, MessageSourceAware, InitializingBean {
        public List<AuthenticationProvider> getProviders(){
            return providers;
        }
        
        public Authentication authenticate(Authenticaiton authentication) throws AuthenticationException {
            Class<? extends Authentication> toTest = authentication.getClass();
            AuthenticationException lastException = null;
            Authentication result = null;
            boolean debug = logger.isDebugEnabled();

            // 모든 Provider 순회하여 처리하고 result 나올 때 까지 반복
            for(AuthenticationProvider provider : getProviders()){
                try {
                    result = provider.authenticate(authentication);

                    if (result != null) {
                        copyDetails (authentication, result);
                        break;
                    }
                }catch (AccountStatusException e){
                    prepareException(e, authentication);
                    throw e;
                }
            }

            throw lastException;
        }
    }
 ```

ProviderManager에 직접 구현한 Provider를 등록하는 방법은 WebSecurityConfigurerAdapter를 상속해 만든 SecurityConfig에서 할 수 있음.
WebSecurityConfigurerAdapter의 상위 클래스에서는 AuthenticaitnoManager를 가지고 있으므로 등록 가능.
```java
    @Configuration
    @EnableWebSecurity
    public class SecurityConfig extends WebSecurityConfigurerAdapter {
        
        @Bean
        public AuthenticationManager getAuthenticationManager() throws Exception {
            return super.authenticationManagerBean();
        }

        @Bean
        public CustomAuthenticationProvider customAuthenticationProvider() throws Exception {
            return new CustomAuthenticationProvider();
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exceptino {
            auth.authenticationProvider(customAuthenticationProvider());
        }
    }
```
<br/>

#### [UserDetails]
인증에 성공하여 생성된 UserDetails 객체는 Authentication 객체를 구현한 UsernamepasswordAuthenticatinoToken을 생성하기 위해 사용됨.
UserDetails 인터페이스를 살펴보면 아래와 같이 정보를 반환하는 메소드를 가지고 있음. 
UserDetails 인터페이스의 경우 직접 개발한 UserVO 모델에 UserDetails를 implements하여 이를 처리하거나 userDetailsVO에 UserDetails를 implements하여 처리 가능.

```java
    public interface UserDetails extends Serializable {
        Collection<? extends GrantedAutority> getAuthorities();
        String getPassword();
        String getUserName();
        boolean isAccountNonExpired();
        boolean isAccountNonLocked();
        boolean isCredentialExpired();
        boolean isEnabled();
    }
```
<br/>

#### [UserDetailsService]
UserDetails 객체를 반환하는 단 하나의 메소드를 가지고 있음. 일반적으로 이를 구현한 클래스의 내부에 UserRepository를 주입받아 DB와 연결하여 처리. 기능 분리를 위해 UserService를 따로 만들어 주는 것이 일반적.
UserDetails 인터페이스는 아래와 같음.
```java
    public interface UserDetailService {
        UserDetails loadUserByUsername(String var1) throws UsernameNotFountException;
    }
```
<br/>

#### [Password Encoding]
AuthenticationManagerBuilder.userDetailsService().passwordEncoder() 통해 패스워드 암호화에 사용될 PassEncoder 구현체 지정 가능
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
<br/>

#### [GrantedAuthority]
현재 사용자(principal)가 가지고 있는 권한을 의미. ROLE_ADMIN, ROLE_USER와 같이 ROLE_* 형태로 사용.
GrantedAuthority 객체는 UserDetailsService로 불러올 수 있고, 특정 자원에 대한 권한이 있는지 검사하여 접근 허용 여부를 결정함.






<br>
<br>
<br>

# JWT

### 로그인 후 회원 검증 방식

- 세션 
    - 사용자 정보를 세션에 저장. 이와 연결 되는 세션 ID 발급.
    - 클라이언트는 서버에 요청을 보낼 때 세션 ID를 Header에 담아 보냄.
    - 세션ID가 탈취 될 위험성이 있으며 서버가 세션 저장소를 필요로 하기 때문에 추가적인 저장 공간이 필요.

        ![Alt text](image/image-2.png)
    


<br/>

- JWT
    - 인증에 필요한 정보를 암호화하여 만든 토큰을 활용한 방법.
    - 사용자가 로그인하면 해당 정보를 AccessToken으로 발급해 클라이언트에 줌.
    - 클라이언트는 서버에 요청을 보낼 때 AccessToken을 Header에 담아 보냄.
    - AcessToken의 유효 기간을 짧게 만들어 탈취에 최대한 대응하고자 RefreshToken을 만듬.
    - RefreshToken은 AccessToken이 만료 되었을 때 사용 되는 토큰. 즉, AccessToken을 새로 발급해주기 위한 토큰.
    - RefreshToken도 유효 기간이 있으며, 한번 사용되면 폐기 되고 다시 발급 됨. 이유는 RefreshToken 역시 탈취 가능성이 있기 때문임.
    - AccessToken이 탈취 되더라도 기간이 짧으므로 악용 될 시간을 줄일 수 있음.
    - RefreshToken이 탈취 되더라도 기간이 정해져 있으며, 한번이라도 사용된 이력이 있으면 해당 토큰은 사용할 수 없기 때문에, 실제 사용자가 RefreshToekn을 사용하면 탈취자는 RefreshToken을 사용할 수 없음. 반대로 탈취자가 RefreshToken을 이미 사용하여 사용자가 RefreshToken을 쓸 수 없을 경우 사용자에게 경고 메시지를 전달할 수도 있음. (사용한 RefreshToken으로 접근했을 때, 경고 메시지를 보여주는 형식)
    - Token 정보를 저장하는 저장소가 필요함. 또한, 토큰이 만료되기 전까지 대처 방법이 없고 구현이 복잡하며 AccessToken이 만료될 때마다 새롭게 발급하기 때문에 서버의 자원 낭비가 발생됨.
      

      ![Alt text](image/image-3.png)
      