최종 작성일 : 2023.06.19.</br>
# Spring-Security 

## 0. 들어가기 앞서
- spring Security는 버전에 따라 Deprecated 된 클래스, 함수들이 존재함. 따라서 버전을 잘 맞추고 사용해야 하는 메서드가 무엇인지 판단하기 바랍니다.

<br/>

## 1. 라이브러리 추가

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
<br/>

## 2. User Domain

- 공통 Entity 작성
- 회원 Entity, Service, Controller, Role 작성.

<br/>

## 2. Config 설정

정적 파일 설정
```java
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    // 정적 자원 경로
    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {"classpath:/static/", "classpath/public", "classpath:/",
            "classpath:/resources/", "classpath:/META-INF/resources/", "classpath:/META-INF/resources/webjars/" };

    // controller를 간단히 등록하는 방법
    @Override
    public void addViewControllers(ViewControllerRegistry registry){
        // 해당하는 url mapping을 /index로 froward 한다.
        registry.addViewController("/").setViewName("forward:/index");

        // 우선순위 가장 높게 측정
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }

    // 정적 자원 경로 추가
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("/**").addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
    }
}
```
<br/>

Security 설정. (현재 WebSecurityConfigurerAdapter 클래스는 공식적으로 Deprecated 이므로 SecurityFilterChain을 Bean 등록해야함.)

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig{

    // 정적 자원 경로는 security 적용 하지 않음.
    @Bean
    public WebSecurityCustomizer configure(){
        return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    // security 설정
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable) // csrf 공격 보호 옵션 끄기.
                .cors(AbstractHttpConfigurer::disable) // cors 예방 옵션 끄기
                .authorizeHttpRequests(request -> request // HTTP 요청에 대한 인증을 구성
                        .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll() // DispatcherType.FORWARD 유형의 모든 요청을 허용
                        .anyRequest().authenticated()) // 다른 요청은 인증을 받아야 함.
                .formLogin(login -> login // 로그인을 했을 때
                        .successForwardUrl("/index") // 성공 시 포워드 이동 페이지
                        .failureForwardUrl("/index") // 실패 시 포워드 이동 페이지
                        .permitAll()) // 이동을 위한 허용
                .logout(Customizer.withDefaults()) // 로그아웃을 기본으로 구성
                .addFilterBefore(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class) // 필터 추가하기
                .build();
    }

    // 패스워드 인코더
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
```
<br/>

## 3. Filter
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
<br/>

## 4. Handler

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
<br/>

## 5. Provider

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