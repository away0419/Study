```
kotlin : 1.8
spring boot : 3.1.3
spring security : 6.1.4
```


<details>
  <summary>OAuth2</summary>

- google, naver, kakao 연동 로그인 구현

  ### OAuth2 서비스 등록
  
  <details>
      <summary>google</summary>
  
  [구글 프로젝트](https://console.cloud.google.com/apis/dashboard?project=powerful-atlas-401406)
  
  ![img.png](image/google/img.png)
  ![img_1.png](image/google/img_1.png)
  ![img_2.png](image/google/img_2.png)
  ![img_3.png](image/google/img_3.png)
  ![img_4.png](image/google/img_4.png)
  ![img_5.png](image/google/img_5.png)
  ![img_6.png](image/google/img_6.png)
  ![img_7.png](image/google/img_7.png)
  ![img_8.png](image/google/img_8.png)
  ![img_9.png](image/google/img_9.png)
  ![img_10.png](image/google/img_10.png)
  ![img_11.png](image/google/img_11.png)
  ![img_12.png](image/google/img_12.png)
  ![img_13.png](image/google/img_13.png)
  ```text
  http://localhost:8080/login/oauth2/code/google
  ```
  ![img_14.png](image/google/img_14.png)
  ![img_15.png](image/google/img_15.png)
  
  </details>
  
  <details>
      <summary>naver</summary>
  
  [naver](https://developers.naver.com/main/)
  
  ![img.png](image/naver/img.png)
  ![img_1.png](image/naver/img_1.png)
  ![img_2.png](image/naver/img_2.png)
  ![img_3.png](image/naver/img_3.png)
  ```text
  서비스 URL: http://localhost:3000
  Callback URL: http://localhost:8080/login/ouath2/code/naver
  ```
  ![img_4.png](image/naver/img_4.png)
  ![img_5.png](image/naver/img_5.png)
  ![img_6.png](image/naver/img_6.png)
  </details>
  
  <details>
      <summary>kakao</summary>
  
  [kakao](https://developers.kakao.com/)
  ![img.png](image/kakao/img.png)
  ![img_1.png](image/kakao/img_1.png)
  ![img_2.png](image/kakao/img_2.png)
  ![img_3.png](image/kakao/img_3.png)
  ![img_4.png](image/kakao/img_4.png)
  ![img_5.png](image/kakao/img_5.png)
  ![img_6.png](image/kakao/img_6.png)
  ![img_7.png](image/kakao/img_7.png)
  ![img_8.png](image/kakao/img_8.png)
  ![img_9.png](image/kakao/img_9.png)
  ![img_10.png](image/kakao/img_10.png)
  ![img_11.png](image/kakao/img_11.png)
  ```text
  http://localhost:8080/login/oauth2/code/kakao
  ```
  ![img_12.png](image/kakao/img_12.png)
  
  </details>
  
  ### 라이브러리 및 yml
  
  <details>
      <summary>gradle</summary>
  
  ```gradle
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  implementation("org.springframework.boot:spring-boot-starter-data-redis")
  implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
  implementation("org.springframework.boot:spring-boot-starter-security")
  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
  implementation("org.jetbrains.kotlin:kotlin-reflect")
  compileOnly("org.projectlombok:lombok:1.18.24")
  developmentOnly("org.springframework.boot:spring-boot-devtools")
  runtimeOnly("com.h2database:h2")
  testImplementation("org.springframework.boot:spring-boot-starter-test")
  testImplementation("org.springframework.security:spring-security-test")
  testImplementation("com.h2database:h2:2.1.214")
  ```
  </details>
  
  <details>
      <summary>application.yaml</summary>
  
  ```yaml
  spring:
    profiles:
      active:
        - local
    # h2 설정
    h2:
      console:
        enabled: true
        path: /h2-console
    datasource:
      driver-class-name: org.h2.Driver
      url: jdbc:h2:mem:test
      username: admin
      password: admin
    # JPA 설정
    jpa:
      properties:
        hibernate:
          format_sql: true
          show_sql: true
    # H2에서 JPA 사용하기 위한 설정
    sql:
      init:
        mode: always
    # security 설정
    # 구글, 페북, 깃헙 등은 spring security에서 지원함.
    # 네이버, 카카오는 지원하지 않음. 따라서 추가 설정 필요함.
    security:
      oauth2.client:
        registration:
          google:
            clientId: ${oauth2.client.google.id}
            clientSecret: ${oauth2.client.google.secret}
            scope:
              - email
              - profile
          naver:
            clientId: ${oauth2.client.naver.id}
            clientSecret: ${oauth2.client.naver.secret}
            clientAuthenticationMethod: client_secret_post
            authorizationGrantType: authorization_code
            # application이 가지고 있는 기본 변수를 불러옴.
            redirectUri: "{baseUrl}/{action}/oauth2/code/{registrationId}"
            scope:
              - nickname
              - email
              - profile_image
            clientName: Naver
          kakao:
            clientId: ${oauth2.client.kakao.id}
            clientSecret: ${oauth2.client.kakao.secret}
            clientAuthenticationMethod: client_secret_post
            authorizationGrantType: authorization_code
            redirectUri: "{baseUrl}/{action}/oauth2/code/{registrationId}"
            scope:
              - profile_nickname
              - profile_image
              - account_email
            clientName: Kakao
        provider:
          kakao:
            authorization_uri: https://kauth.kakao.com/oauth/authorize
            token_uri: https://kauth.kakao.com/oauth/token
            user-info-uri: https://kapi.kakao.com/v2/user/me
            # 해당 서비스에 요청하여 받은 데이터 안에는 user 정보가 있는데 이 정보를 담음 필드 명이 무엇인지 설정하는 것.
            user_name_attribute: properties
          naver:
            authorization_uri: https://nid.naver.com/oauth2.0/authorize
            token_uri: https://nid.naver.com/oauth2.0/token
            user-info-uri: https://openapi.naver.com/v1/nid/me
            user_name_attribute: response
  
  logging.level:
    org.hibernate.SQL: debug
  
  --- #local
  jpa:
    hibernate:
      ddl-auto: create-drop
  
  
  --- #env
  spring:
    profiles.include:
      - env
  ```
  </details>
  
  <details>
      <summary>application-env.yaml</summary>
  
  ```yaml
  oauth2.client:
    google:
      id: google에서 받은 ID
      secret: google에서 받은 KEY
    naver:
      id: naver에서 받은 ID
      secret: naver에서 받은 KEY
    kakao:
      id: kakao에서 받은 ID
      secret: kakao에서 받은 KEY
  ```
  </details>
  
  ### 멤버
  
  - security 실습을 위한 도메인
  
  <details>
      <summary>Role</summary>
  
  - 사용자 권한
  
      ```kotlin
      package com.example.kotlin.member
      
      enum class Role(
          val key: String,
          val title: String
      ) {
          ROLE_ADMIN("ROLE_ADMIN", "관리자"),
          ROLE_USER("ROLE_USER", "사용자")
      }
      ```
  </details>
  
  <details>
      <summary>Member</summary>
  
  - db 저장 할 멤버 정보
  
      ```kotlin
      package com.example.kotlin.member
      
      import jakarta.persistence.*
      import java.util.*
      
      @Entity
      @Table(name = "MEMBER_TABLE")
      class Member(
          @Id
          @GeneratedValue(strategy = GenerationType.UUID)
          @Column(name = "member_id", nullable = false)
          var id: UUID?,
      
          @Column(name = "name", nullable = false)
          var name: String?,
      
          @Column(name = "email")
          var email: String?,
      
          @Column(name = "picture")
          var picture: String?,
      
          @Enumerated(EnumType.STRING)
          var role: Role?
      
      )
      ```
  </details>
  
  <details>
      <summary>MemberRepository</summary>
  
  - JPA를 이용한 멤버 등록
  
      ```kotlin
      package com.example.kotlin.member.repository
      
      import com.example.kotlin.member.Member
      import org.springframework.data.jpa.repository.JpaRepository
      
      interface MemberRepository : JpaRepository<Member, Long> {
          fun findByEmail(email: String): Member?
      }
      ```
  </details>
  <details>
      <summary>MemberController</summary>
  
  - 멤버 컨트롤러
  
  ```kotlin
  package com.example.kotlin.member.controller
  
  import org.springframework.web.bind.annotation.GetMapping
  import org.springframework.web.bind.annotation.PostMapping
  import org.springframework.web.bind.annotation.RequestMapping
  import org.springframework.web.bind.annotation.RestController
  
  @RestController
  @RequestMapping("/api/v1/member")
  class MemberController {
  
      @GetMapping("/login/success")
      fun login() : String?{
          return "login success"
      }
  
  }
  ```
  </details>
  
  ### OAuth2
  
  - security의 OAuth2를 사용하기 위한 설정 및 로직 구현
  
  <details>
      <summary>Oauth2UserInfo</summary>
  
  - OAuth2에서 가져온 사용자 정보를 담을 클래스.
    - 가져온 사용자 정보를 토대로 Member Entity 생성
    - 첫 로그인의 경우 관리자는 아닐 것이라 판단하여 ROLE_USER로 줌
      
        ```kotlin
        package com.example.kotlin.security.oauth2
      
        import com.example.kotlin.member.Member
        import com.example.kotlin.member.Role
      
        class Oauth2UserInfo(
            val id: String?,
            val name: String?,
            val email: String?,
            val picture: String?
        ){
            fun toEntity(): Member {
                return Member(id=null, name=name, email=email, picture=picture, role = Role.ROLE_USER)
            }
        }
        ```
  </details>
  
  <details>
      <summary>OAuth2Attribute</summary>
  
  - 사용자가 이용한 OAuth2 서비스가 무엇인지 판단하고, 해당 서비스의 정보를 알맞게 가져오기 위한 클래스
    - naver와 kakao는 Spring Security가 제공하지 않으므로 추가 설정이 필요함.
  
  ```kotlin
  package com.example.kotlin.security.oauth2
  
  enum class OAuth2Attributes(
      val nameAttributeKey: String?,
      val oauth2UserInfo: (Map<String, Any>?) -> Oauth2UserInfo
  ) {
      GOOGLE("google", { map ->
          Oauth2UserInfo(
              map?.get("sub").toString(),
              map?.get("name").toString(),
              map?.get("email").toString(),
              map?.get("picture").toString()
          )
      }),
  
      NAVER("naver", { attributes ->
          val content = attributes?.get("response");
          val map: Map<String, Any>? = if (content is Map<*, *>) {
              content as? Map<String, Any>
          } else {
              emptyMap()
          }
          Oauth2UserInfo(
              map?.get("id").toString(),
              map?.get("nickname").toString(),
              map?.get("email").toString(),
              map?.get("profile_image").toString()
          )
      }),
  
      KAKAO("kakao", { attributes ->
          val content = attributes?.get("properties");
          val map: Map<String, Any>? = if (content is Map<*, *>) {
              content as? Map<String, Any>
          } else {
              emptyMap()
          }
          Oauth2UserInfo(
              map?.get("sub").toString(),
              map?.get("name").toString(),
              map?.get("account_email").toString(),
              map?.get("thumbnail_image").toString()
          )
      });
  
      companion object {
          fun extract(registrationId: String, attributes: Map<String, Any>?): Oauth2UserInfo? {
              return values().find { it.nameAttributeKey == registrationId }?.oauth2UserInfo?.invoke(attributes)
          }
      }
  }
  ```
  </details>
  
  <details>
      <summary>CustomOAuth2MemberService</summary>
  
  - 사용자가 OAuth2 로그인 할 때 수행되는 비즈니스 로직
    - 서비스 별 가져온 사용자의 필드가 다르므로 개발자가 구현한 OAut2UserInfo 형식으로 매핑 후 반환.
    - 반환된 DefaultOAuth2User는 Authentication가 됨.
  
        ```kotlin
        package com.example.kotlin.security.oauth2.service
      
        import com.example.kotlin.member.Member
        import com.example.kotlin.member.repository.MemberRepository
        import com.example.kotlin.security.oauth2.OAuth2Attributes
        import com.example.kotlin.security.oauth2.Oauth2UserInfo
        import jakarta.servlet.http.HttpSession
        import org.springframework.security.core.authority.SimpleGrantedAuthority
        import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
        import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
        import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
        import org.springframework.security.oauth2.core.OAuth2AuthenticationException
        import org.springframework.security.oauth2.core.user.DefaultOAuth2User
        import org.springframework.security.oauth2.core.user.OAuth2User
        import org.springframework.stereotype.Service
      
        @Service
        class CustomOAuth2MemberService(
            private val memberRepository: MemberRepository,
            private val httpSession: HttpSession
        ): OAuth2UserService<OAuth2UserRequest, OAuth2User> {
            override fun loadUser(userRequest: OAuth2UserRequest?): OAuth2User {
                if (userRequest == null) throw OAuth2AuthenticationException("Oauth2 UserRequest Error")
      
                // userRequest에서 user 정보 가져오기
                val delegate = DefaultOAuth2UserService()
                val oAuth2User = delegate.loadUser(userRequest)
      
                // registrationId는 Oauth2 서비스 이름 (구글, 네이버, 카카오 등)
                val registrationId = userRequest.clientRegistration.registrationId
                // OAuth2 로그인 하면 서비스 별 유저가 가지는 고유 키가 있는 듯. 그 키의 필드 값.
                val userNameAttributeName = userRequest.clientRegistration.providerDetails.userInfoEndpoint.userNameAttributeName
                // OAuth2 서비스의 유저 정보들
                val attributes = oAuth2User.attributes;
                // 서비스의 유저 정보를 개발자가 만든 객체 형태로 매핑
                val oauth2UserInfo = OAuth2Attributes.extract(registrationId, attributes)
      
                // 전달받은 OAuth2User의 attribute를 이용하여 회원가입 및 수정의 역할을 한다.
                val member = oauth2UserInfo?.let { saveOrUpdate(it) }
      
                // session에 SessionUser(user의 정보를 담는 객체)를 담아 저장한다.
        //        httpSession.setAttribute("user", SessionUser(user))
      
                return DefaultOAuth2User(
                    setOf(SimpleGrantedAuthority(member?.role?.key)),
                    attributes,
                    userNameAttributeName
                )
            }
      
            fun saveOrUpdate(oauth2UserInfo: Oauth2UserInfo): Member {
                val member = memberRepository.findByEmail(oauth2UserInfo.email?:"")
                    ?: oauth2UserInfo.toEntity()
      
                return memberRepository.save(member)
            }
        }
        ```
  </details>
  
  <details>
      <summary>CustomSuccessHandler</summary>
  
  - OAuth2 로그인 성공 시 실행 되는 로직.
  
      ```kotlin
      package com.example.kotlin.security.oauth2.handler
      
      import jakarta.servlet.http.HttpServletRequest
      import jakarta.servlet.http.HttpServletResponse
      import org.slf4j.LoggerFactory
      import org.springframework.security.core.Authentication
      import org.springframework.security.oauth2.core.user.DefaultOAuth2User
      import org.springframework.security.web.authentication.AuthenticationSuccessHandler
      import org.springframework.stereotype.Component
      
      @Component
      class CustomSuccessHandler() :AuthenticationSuccessHandler  {
          private val log = LoggerFactory.getLogger(this.javaClass)!!
          override fun onAuthenticationSuccess(
              request: HttpServletRequest?,
              response: HttpServletResponse?,
              authentication: Authentication?
          ) {
      
              val principal = authentication?.principal // 인증 완료한 객체의 principal을 가져옴. 이때 OAuth2의 principal에는 DefaultOAuth2User가 들어간다.
              val authorization = authentication?.authorities // 권한 목록
              val details = authentication?.details // 기타 정보
              val name = authentication?.name // DefaultOAuth2User 에 있는 userNameAttributeName 인듯
              val defaultOAuth2User = principal as? DefaultOAuth2User // 일단 DefaultOAuth2User로 형변환
              val authorities = defaultOAuth2User?.attributes // DefaultOAuth2User에 있는 attributes 즉, Oauth2 로그인 시 서비스에서 보낸 정보
      
              log.info("========CustomSuccessHandelr=========")
              log.info("authentication : {}",authentication)
              log.info("principal : {} ",principal)
              log.info("details : {} ",details)
              log.info("name : {} ",name)
              log.info("authorization : {} ",authorization)
              log.info ("authorities : {}", authorities)
      
              
      
              response?.sendRedirect("/page")
          }
      }
      ```
  
  </details>
  
  <details>
      <summary>SecurityConfig</summary>
  
  - OAuth2를 적용하기 위한 설정 추가.
  
      ```kotlin
      package com.example.kotlin.security.config
      
      import com.example.kotlin.security.oauth2.handler.CustomSuccessHandler
      import com.example.kotlin.security.oauth2.service.CustomOAuth2MemberService
      import lombok.RequiredArgsConstructor
      import org.springframework.context.annotation.Bean
      import org.springframework.context.annotation.Configuration
      import org.springframework.security.config.annotation.web.builders.HttpSecurity
      import org.springframework.security.config.http.SessionCreationPolicy
      import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint
      import org.springframework.security.web.util.matcher.AntPathRequestMatcher
      
      @Configuration
      @RequiredArgsConstructor
      class SecurityConfig(
          private val customOAuth2MemberService: CustomOAuth2MemberService,
          private val customSuccessHandler: CustomSuccessHandler
      ) {
          private val urls = arrayOf(AntPathRequestMatcher("/h2-console/**"), AntPathRequestMatcher("/api/member/signup"), AntPathRequestMatcher("/api/member/login"), AntPathRequestMatcher("/api/member/oauth2/**"))
      
          @Bean
          fun filterChain(http: HttpSecurity) = http
              .headers {it.frameOptions{it.disable()}}
              .csrf { it.disable() } // csrf off
              .cors { it.disable() } // cors off
              .formLogin { it.disable() } // security login page off
              .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.NEVER) } //필요하다면 세션 생성. (API는 session이 필요 없지만 google 계정 정보를 가져오기 위해 session이 필요함)
              .authorizeHttpRequests {
                  it.requestMatchers(*urls).anonymous() // [signup, login] 누구나 접근 가능
                      .anyRequest().authenticated()
              } // 나머지 api 호출은 인증 받아야함
              .oauth2Login {
                  it.userInfoEndpoint { point -> point.userService(customOAuth2MemberService) } // oauth2Login는 loadUser라는 함수를 호출하는게 기본임. 이를 custom하여 사용하는 것.
                  it.successHandler(customSuccessHandler) // 성공 시 핸들러
                  // it.defaultSuccessUrl("/myspace") // 성공시 이동 페이지
                  it.failureUrl("/fail") // 실패시
              }
              .exceptionHandling { it.authenticationEntryPoint(LoginUrlAuthenticationEntryPoint("/login")) } // 인증 되지 않은 사용자가 접근시 login으로 이동
              .build()!!
      }
      ```
  </details>
</details>

<details>
  <summary>JWT</summary>

- OAuth2 로그인 후 이후 API 요청을 처리할 때 JWT를 이용하기 위한 로직 구현
  
  ### 라이브러리 추가
  
  <details>
    <summary>gradle</summary>
  
  - jwt 라이브러리 추가
  
    ```gradle
    import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
    
    plugins {
        id("org.springframework.boot") version "3.1.3"
        id("io.spring.dependency-management") version "1.1.3"
        kotlin("jvm") version "1.8.22"
        kotlin("plugin.spring") version "1.8.22"
        kotlin("plugin.jpa") version "1.8.22"
        kotlin("plugin.allopen") version "1.8.22"
        kotlin("plugin.noarg") version "1.8.22"
    }
    
    allOpen{
        annotation("jakarta.persistence.Entity")
        annotation("jakarta.persistence.Embeddable")
        annotation("jakarta.persistence.MappedSuperclass")
    }
    
    noArg{
        annotation("jakarta.persistence.Entity")
    }
    
    group = "com.example"
    version = "0.0.1-SNAPSHOT"
    
    java {
        sourceCompatibility = JavaVersion.VERSION_17
    }
    
    repositories {
        mavenCentral()
    }
    
    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-data-jpa")
        implementation("org.springframework.boot:spring-boot-starter-data-redis")
        implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
        implementation("org.springframework.boot:spring-boot-starter-security")
        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("io.jsonwebtoken:jjwt-api:0.11.2")
        implementation("io.jsonwebtoken:jjwt-impl:0.11.2")
        implementation("io.jsonwebtoken:jjwt-jackson:0.11.2")
        implementation("com.sun.xml.bind:jaxb-impl:4.0.1")
        implementation("com.sun.xml.bind:jaxb-core:4.0.1")
        implementation ("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")	// LocalDateTime 역직렬화 해결 패키지
        implementation ("com.googlecode.json-simple:json-simple:1.1.1")	// Google Simple JSON
        implementation ("com.fasterxml.jackson.core:jackson-databind")	// Jackson Databind
        compileOnly("org.projectlombok:lombok:1.18.24")
        developmentOnly("org.springframework.boot:spring-boot-devtools")
        runtimeOnly("com.h2database:h2")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testImplementation("org.springframework.security:spring-security-test")
        testImplementation("com.h2database:h2:2.1.214")
        runtimeOnly("com.oracle.database.jdbc:ojdbc8")
    }
    
    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs += "-Xjsr305=strict"
            jvmTarget = "17"
        }
    }
    
    tasks.withType<Test> {
        useJUnitPlatform()
    }
    ```
  </details>
  
  ### 로그인 시 JWT 발급
  
  <details>
    <summary>AuthConstants</summary>
  
  - security에서 사용하는 상수 파일
  
    ```kotlin
    package com.example.kotlin.security
    
    object AuthConstants {
        const val AUTH_HEADER = "Authorization"
        const val TOKEN_TYPE = "Bearer " // 띄어 쓰기가 있어야 한다.
        const val COOKIE_HEADER = "Set-Cookie"
        const val REFRESH_TOKEN_PREFIX = "refresh_token"
    }
    ```
  </details>
  
  <details>
    <summary>MemberPrincipal</summary>
  
  - 인증 완료 된 객체에 넣으려는 사용자의 Claims를 담은 객체
  
    ```kotlin
    package com.example.kotlin.security.jwt
    
    import com.example.kotlin.member.Member
    import com.example.kotlin.member.Role
    import java.util.*
    
    
    class MemberPrincipal(
        val id: UUID?,
        val name: String?,
        val email: String?,
        val picture: String?,
        val role: Role?
    ){
        constructor(member: Member) : this(
            id = member.id,
            name = member.name,
            email = member.email,
            picture = member.picture,
            role = member.role
        )
    
        override fun toString(): String {
            return "MemberPrincipal(id=$id, name=$name, email=$email, picture=$picture, role=$role)"
        }
    
    }
    ```
  </details>
  
  <details>
    <summary>JWTProvider</summary>
  
  - JWT에 필요한 기능 구현
  
    ```kotlin
    package com.example.kotlin.security.jwt.provider
    
    import com.example.kotlin.security.AuthConstants
    import com.example.kotlin.security.jwt.MemberPrincipal
    import io.jsonwebtoken.Claims
    import io.jsonwebtoken.ExpiredJwtException
    import io.jsonwebtoken.Jwts
    import io.jsonwebtoken.SignatureAlgorithm
    import io.jsonwebtoken.security.Keys
    import jakarta.servlet.http.Cookie
    import jakarta.xml.bind.DatatypeConverter
    import org.slf4j.LoggerFactory
    import org.springframework.http.ResponseCookie
    import org.springframework.security.oauth2.jwt.JwtException
    import org.springframework.stereotype.Component
    import java.security.Key
    import java.util.*
    
    @Component
    class JWTProvider {
        private val jwtSecretKey = "exampleSecretKeyExampleSecretKeyExampleSecretKeyExampleSecretKey"
        private val log = LoggerFactory.getLogger(this.javaClass)!!
    
        private fun createHeader(): Map<String, Any> {
            val header: MutableMap<String, Any> = HashMap()
            header["typ"] = "JWT" // 토큰 타입
            header["alg"] = "HS256" // signature(서명) 알고리즘
            return header
        }
    
        private fun createClaims(memberPrincipal: MemberPrincipal): Map<String, Any> {
            val claims: MutableMap<String, Any> = HashMap()
            claims["email"] = memberPrincipal.email as Any
            claims["role"] = memberPrincipal.role as Any
            claims["https://github.com/away0419/spring-security/tree/main/springBoot"] = true
            return claims
        }
    
        private fun createSignature(): Key {
            val apiKeySecretBytes = DatatypeConverter.parseBase64Binary(jwtSecretKey)
            return Keys.hmacShaKeyFor(apiKeySecretBytes)
        }
    
        private fun createExpiredDate(): Date {
            val c = Calendar.getInstance()
            c.add(Calendar.MINUTE, 30) // 30분
            return c.time
        }
    
        fun generateJwtToken(memberPrincipal: MemberPrincipal): String {
            return Jwts.builder()
                .setHeader(createHeader())
                .setClaims(createClaims(memberPrincipal))
                .setSubject(memberPrincipal.id.toString())
                .setExpiration(createExpiredDate())
                .setIssuedAt(Date())
                .signWith(createSignature(), SignatureAlgorithm.HS256)
                .compact()
        }
    
        fun getTokenFromHeader(header: String): String? {
            if (!header.startsWith(AuthConstants.TOKEN_TYPE)) {
                return null
            }
            return header.split(" ")[1]
        }
    
        fun isValidToken(token: String): Boolean {
            return try {
                val claims = getClaimsFromToken(token)
                log.info("email : {}", claims["email"])
                log.info("role : {}", claims["role"])
                log.info("토큰 발급자 : {}", claims.subject)
                log.info("토큰 만료 시간 : {}", claims.expiration)
                log.info("토큰 발급 시간 : {}", claims.issuedAt)
                true
            } catch (e: ExpiredJwtException) {
                log.error("Token Expired")
                false
            } catch (e: JwtException) {
                log.error("Token Tampered or Invalid")
                false
            } catch (e: NullPointerException) {
                log.error("Token is null")
                false
            }
        }
    
        private fun getClaimsFromToken(token: String): Claims {
            return Jwts.parserBuilder()
                .setSigningKey(createSignature())
                .build()
                .parseClaimsJws(token)
                .body
        }
    
        fun getUserEmailFromToken(token: String): String {
            return getClaimsFromToken(token)["email"].toString()
        }
    
        fun getUserRoleFromToken(token: String): String {
            return getClaimsFromToken(token)["role"].toString()
        }
    
        private fun createRefreshTokenExpiredDate(): Date {
            val c = Calendar.getInstance()
            c.add(Calendar.MONTH, 1) // 1개월
            return c.time
        }
    
        fun generateRefreshToken(): String {
            return Jwts.builder()
                .setHeader(createHeader())
                .setExpiration(createRefreshTokenExpiredDate())
                .setIssuedAt(Date())
                .signWith(createSignature(), SignatureAlgorithm.HS256)
                .compact()
        }
    
        fun generateRefreshTokenCookie(refreshToken: String): ResponseCookie {
            return ResponseCookie.from(AuthConstants.REFRESH_TOKEN_PREFIX, refreshToken)
                .httpOnly(true)
                .sameSite("None")
                .path("/")
                .maxAge(60 * 60 * 24 * 30) // 30일
                .build()
        }
    
        fun getRefreshToken(cookies: Array<Cookie>): String? {
            return cookies.firstOrNull { it.name == AuthConstants.REFRESH_TOKEN_PREFIX }?.value
        }
    
        fun isNeedToUpdateRefreshToken(refreshToken: String): Boolean {
            val expiresAt = getClaimsFromToken(refreshToken).expiration
            val calendar = Calendar.getInstance()
            calendar.time = Date()
            calendar.add(Calendar.DATE, 7)
            return expiresAt.before(calendar.time)
        }
    }
    ```
  
  </details>
  
  <details>
    <summary>CustomOAuth2User</summary>
  
  - OAuth2 서비스 별로 로그인 시 해당 서비스에서 우리 서버로 보내주는 정보가 다름.
    - 이러한 정보를 우리가 필요한 정보로만 매핑하여 사용해야함.
    - 이때 이 매핑한 정보를 주고 받기 위해서 필드를 추가해야함.
    - 따라서 DefaultOAuth2User를 상속 받아 우리가 보내고자 하는 매핑된 정보를 가진 객체가 필요하여 구현함.
  
      ```kotlin
      package com.example.kotlin.security.oauth2
    
      import com.example.kotlin.security.jwt.MemberPrincipal
      import org.springframework.security.core.GrantedAuthority
      import org.springframework.security.oauth2.core.user.DefaultOAuth2User
      import org.springframework.security.oauth2.core.user.OAuth2User
    
      class CustomOAuth2User(
          authorities: Collection<GrantedAuthority>,
          attributes: Map<String, Any>,
          userNameAttributeName: String,
          val memberPrincipal: MemberPrincipal // MemberVO 추가
      ) : DefaultOAuth2User(authorities, attributes, userNameAttributeName), OAuth2User
    
      ```
  
  </details>
  
  <details>
    <summary>CustomOAuth2MemberService</summary>
  
  - 서비스가 보내준 정보를 우리가 필요한 정보로 매핑.
    - CustomOAuth2User를 반환.
  
      ```kotlin
      package com.example.kotlin.security.oauth2.service
    
      import com.example.kotlin.member.Member
      import com.example.kotlin.member.repository.MemberRepository
      import com.example.kotlin.security.jwt.MemberPrincipal
      import com.example.kotlin.security.oauth2.CustomOAuth2User
      import com.example.kotlin.security.oauth2.OAuth2Attributes
      import com.example.kotlin.security.oauth2.Oauth2UserInfo
      import jakarta.servlet.http.HttpSession
      import org.springframework.security.core.authority.SimpleGrantedAuthority
      import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
      import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
      import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
      import org.springframework.security.oauth2.core.OAuth2AuthenticationException
      import org.springframework.security.oauth2.core.user.OAuth2User
      import org.springframework.stereotype.Service
    
      @Service
      class CustomOAuth2MemberService(
          private val memberRepository: MemberRepository,
          private val httpSession: HttpSession
      ): OAuth2UserService<OAuth2UserRequest, OAuth2User> {
          override fun loadUser(userRequest: OAuth2UserRequest?): CustomOAuth2User? {
              if (userRequest == null) throw OAuth2AuthenticationException("Oauth2 UserRequest Error")
    
              // userRequest에서 user 정보 가져오기
              val delegate = DefaultOAuth2UserService()
              val oAuth2User = delegate.loadUser(userRequest)
    
              // registrationId는 Oauth2 서비스 이름 (구글, 네이버, 카카오 등)
              val registrationId = userRequest.clientRegistration.registrationId
              // OAuth2 로그인 하면 서비스 별 유저가 가지는 고유 키가 있는 듯. 그 키의 필드 값.
              val userNameAttributeName = userRequest.clientRegistration.providerDetails.userInfoEndpoint.userNameAttributeName
              // OAuth2 서비스의 유저 정보들
              val attributes = oAuth2User.attributes;
              // 서비스의 유저 정보를 개발자가 만든 객체 형태로 매핑
              val oauth2UserInfo = OAuth2Attributes.extract(registrationId, attributes)
    
              // 전달받은 OAuth2User의 attribute를 이용하여 회원가입 및 수정의 역할을 한다.
              val member = oauth2UserInfo?.let { saveOrUpdate(it) }
    
              // 만들어낸 member entity로 principal 생성
              val memberPrincipal = member?.let { MemberPrincipal(it) }
    
              // session에 SessionUser(user의 정보를 담는 객체)를 담아 저장한다.
      //        httpSession.setAttribute("user", SessionUser(user))
    
              return memberPrincipal?.let {
                  CustomOAuth2User(
                      setOf(SimpleGrantedAuthority(member.role?.key)),
                      attributes,
                      userNameAttributeName,
                      it
                  )
              }
          }
    
          fun saveOrUpdate(oauth2UserInfo: Oauth2UserInfo): Member {
              val member = memberRepository.findByEmail(oauth2UserInfo.email?:"")
                  ?: oauth2UserInfo.toEntity()
    
              return memberRepository.save(member)
          }
      }
      ```
  
  </details>
  
  <details>
    <summary>CustomSuccessHandler</summary>
  
  - 로그인 성공 시 accessToken, refreshToken 발급
  
    ```kotlin
    package com.example.kotlin.security.oauth2.handler
    
    import com.example.kotlin.security.AuthConstants
    import com.example.kotlin.security.jwt.provider.JWTProvider
    import com.example.kotlin.security.oauth2.CustomOAuth2User
    import com.example.kotlin.security.oauth2.OAuth2Attributes
    import jakarta.servlet.http.HttpServletRequest
    import jakarta.servlet.http.HttpServletResponse
    import lombok.RequiredArgsConstructor
    import org.slf4j.LoggerFactory
    import org.springframework.security.core.Authentication
    import org.springframework.security.oauth2.core.user.DefaultOAuth2User
    import org.springframework.security.web.authentication.AuthenticationSuccessHandler
    import org.springframework.stereotype.Component
    
    @Component
    @RequiredArgsConstructor
    class CustomSuccessHandler(
        private val jwtProvider: JWTProvider
    ) :AuthenticationSuccessHandler  {
        private val log = LoggerFactory.getLogger(this.javaClass)!!
        override fun onAuthenticationSuccess(
            request: HttpServletRequest?,
            response: HttpServletResponse?,
            authentication: Authentication?
        ) {
    
            val principal = authentication?.principal // 인증 완료한 객체의 principal을 가져옴. 이때 OAuth2의 principal에는 DefaultOAuth2User가 들어간다.
            val authorization = authentication?.authorities // 권한 목록
            val details = authentication?.details // 기타 정보
            val name = authentication?.name // DefaultOAuth2User 에 있는 userNameAttributeName 인듯
            val customOAuth2User = principal as? CustomOAuth2User // 일단 DefaultOAuth2User로 형변환
            val attributes = customOAuth2User?.attributes // DefaultOAuth2User에 있는 attributes 즉, Oauth2 로그인 시 해당 서비스가 보낸 정보들
            val memberPrincipal = customOAuth2User?.memberPrincipal
            val accessToken = memberPrincipal?.let { jwtProvider.generateJwtToken(it) }
            val refreshToken = jwtProvider.generateRefreshToken()
    
            response?.addHeader(AuthConstants.AUTH_HEADER, AuthConstants.TOKEN_TYPE + accessToken)
            response?.addHeader(AuthConstants.COOKIE_HEADER, refreshToken)
    
            log.info("========CustomSuccessHandelr=========")
            log.info("authentication : {}", authentication)
            log.info("principal : {} ",principal)
            log.info("details : {} ",details)
            log.info("name : {} ",name)
            log.info("authorization : {} ",authorization)
            log.info ("authorities : {}", attributes)
            log.info ("memberPrincipal : {}", memberPrincipal)
            log.info ("accessToken : {}", accessToken)
            log.info ("refreshToken : {}", refreshToken)
    
            response?.sendRedirect("/api/v1/member/login/success")
        }
    }
    ```
  
  </details>
  
  ### 로그인 후 접근 시 인가 확인
  
  - 로그인 후 접근 권한 필요한 페이지 요청 시 처리 로직 구현
  
  <details>
    <summary>Role</summary>
  
  - 권한 수정
  
    ```kotlin
    package com.example.kotlin.member
    
    enum class Role(
        val key: String,
        val title: String
    ) {
        ROLE_ADMIN("ADMIN", "관리자"),
        ROLE_USER("USER", "사용자")
    }
    ```
  </details>
  
  <details>
    <summary>MemberController</summary>
  
  - 권한 필요한 요청 추가
  
    ```kotlin
    package com.example.kotlin.member.controller
    
    import org.springframework.web.bind.annotation.GetMapping
    import org.springframework.web.bind.annotation.PostMapping
    import org.springframework.web.bind.annotation.RequestMapping
    import org.springframework.web.bind.annotation.RestController
    
    @RestController
    @RequestMapping("/api/v1/member")
    class MemberController {
    
        @GetMapping("/login/success")
        fun login() : String?{
            return "login success"
        }
    
        @GetMapping("/space")
        fun getSpace() : String?{
            return "space"
        }
    
    }
    ```
  </details>
  
  <details>
    <summary>AdminController</summary>
  
  - 권한이 필요한 요청 추가
  
    ```kotlin
    package com.example.kotlin.member.controller
    
    import org.springframework.web.bind.annotation.GetMapping
    import org.springframework.web.bind.annotation.RequestMapping
    import org.springframework.web.bind.annotation.RestController
    
    @RestController
    @RequestMapping("/api/v1/admin")
    class AdminController {
    
        @GetMapping("/space")
        fun getSpace() : String?{
            return "space"
        }
    
    }
    ```
  
  </details>
  
  <details>
    <summary>JWTProvider</summary>
  
  - 일부 오류 수정
  
    ```kotlin
    package com.example.kotlin.security.jwt.provider
    
    import com.example.kotlin.security.AuthConstants
    import com.example.kotlin.security.jwt.MemberPrincipal
    import io.jsonwebtoken.Claims
    import io.jsonwebtoken.ExpiredJwtException
    import io.jsonwebtoken.Jwts
    import io.jsonwebtoken.SignatureAlgorithm
    import io.jsonwebtoken.security.Keys
    import jakarta.servlet.http.Cookie
    import jakarta.xml.bind.DatatypeConverter
    import org.slf4j.LoggerFactory
    import org.springframework.http.ResponseCookie
    import org.springframework.security.oauth2.jwt.JwtException
    import org.springframework.stereotype.Component
    import java.security.Key
    import java.util.*
    import javax.crypto.spec.SecretKeySpec
    
    @Component
    class JWTProvider {
        private val jwtSecretKey = "exampleSecretKeyExampleSecretKeyExampleSecretKeyExampleSecretKey"
        private val log = LoggerFactory.getLogger(this.javaClass)!!
    
        private fun createHeader(): Map<String, Any> {
            val header: MutableMap<String, Any> = HashMap()
            header["typ"] = "JWT" // 토큰 타입
            header["alg"] = "HS256" // signature(서명) 알고리즘
            return header
        }
    
        private fun createClaims(memberPrincipal: MemberPrincipal): Map<String, Any> {
            val claims: MutableMap<String, Any> = HashMap()
            claims["email"] = memberPrincipal.email as Any
            claims["role"] = memberPrincipal.role as Any
            claims["https://github.com/away0419/spring-security/tree/main/springBoot"] = true
            return claims
        }
    
        private fun createSignature(): Key {
            val apiKeySecretBytes = DatatypeConverter.parseBase64Binary(jwtSecretKey)
            return SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.jcaName)
        }
    
        private fun createExpiredDate(): Date {
            val c = Calendar.getInstance()
            c.add(Calendar.MINUTE, 30) // 30분
            return c.time
        }
    
        fun generateJwtToken(memberPrincipal: MemberPrincipal): String {
            return Jwts.builder()
                .setHeader(createHeader())
                .setClaims(createClaims(memberPrincipal))
                .setSubject(memberPrincipal.id.toString())
                .setExpiration(createExpiredDate())
                .setIssuedAt(Date())
                .signWith(createSignature(), SignatureAlgorithm.HS256)
                .compact()
        }
    
        fun getTokenFromHeader(header: String): String {
            if (!header.startsWith(AuthConstants.TOKEN_TYPE)) {
                throw Exception("${AuthConstants.TOKEN_TYPE} is not match")
            }
            return header.split(" ")[1]
        }
    
        fun isValidToken(token: String): Boolean {
            return try {
                val claims = getClaimsFromToken(token)
                log.info("email : {}", claims["email"])
                log.info("role : {}", claims["role"])
                log.info("토큰 발급자 : {}", claims.subject)
                log.info("토큰 만료 시간 : {}", claims.expiration)
                log.info("토큰 발급 시간 : {}", claims.issuedAt)
                true
            } catch (e: ExpiredJwtException) {
                throw Exception("Token Expired")
            } catch (e: JwtException) {
                throw Exception("Token Tampered or Invalid")
            } catch (e: NullPointerException) {
                throw Exception("Token is null")
            }
        }
    
        private fun getClaimsFromToken(token: String): Claims {
            return Jwts.parserBuilder()
                .setSigningKey(DatatypeConverter.parseBase64Binary(jwtSecretKey))
                .build()
                .parseClaimsJws(token)
                .body
        }
    
        fun getUserEmailFromToken(token: String): String {
            return getClaimsFromToken(token)["email"].toString()
        }
    
        fun getUserRoleFromToken(token: String): String {
            return getClaimsFromToken(token)["role"].toString()
        }
    
        private fun createRefreshTokenExpiredDate(): Date {
            val c = Calendar.getInstance()
            c.add(Calendar.MONTH, 1) // 1개월
            return c.time
        }
    
        fun generateRefreshToken(): String {
            return Jwts.builder()
                .setHeader(createHeader())
                .setExpiration(createRefreshTokenExpiredDate())
                .setIssuedAt(Date())
                .signWith(createSignature(), SignatureAlgorithm.HS256)
                .compact()
        }
    
        fun generateRefreshTokenCookie(refreshToken: String): ResponseCookie {
            return ResponseCookie.from(AuthConstants.REFRESH_TOKEN_PREFIX, refreshToken)
                .httpOnly(true)
                .sameSite("None")
                .path("/")
                .maxAge(60 * 60 * 24 * 30) // 30일
                .build()
        }
    
        fun getRefreshToken(cookies: Array<Cookie>): String? {
            return cookies.firstOrNull { it.name == AuthConstants.REFRESH_TOKEN_PREFIX }?.value
        }
    
        fun isNeedToUpdateRefreshToken(refreshToken: String): Boolean {
            val expiresAt = getClaimsFromToken(refreshToken).expiration
            val calendar = Calendar.getInstance()
            calendar.time = Date()
            calendar.add(Calendar.DATE, 7)
            return expiresAt.before(calendar.time)
        }
    }
    ```
  
  </details>
  
  <details>
    <summary>CustomSuccessHandler</summary>
  
  - 일부 오류 수정
  
    ```kotlin
    package com.example.kotlin.security.oauth2.handler
    
    import com.example.kotlin.security.AuthConstants
    import com.example.kotlin.security.jwt.provider.JWTProvider
    import com.example.kotlin.security.oauth2.CustomOAuth2User
    import jakarta.servlet.http.HttpServletRequest
    import jakarta.servlet.http.HttpServletResponse
    import org.json.simple.JSONObject
    import org.slf4j.LoggerFactory
    import org.springframework.security.core.Authentication
    import org.springframework.security.web.authentication.AuthenticationSuccessHandler
    
    class CustomSuccessHandler(
        private val jwtProvider: JWTProvider
    ) :AuthenticationSuccessHandler  {
        private val log = LoggerFactory.getLogger(this.javaClass)!!
        override fun onAuthenticationSuccess(
            request: HttpServletRequest?,
            response: HttpServletResponse?,
            authentication: Authentication?
        ) {
    
            val principal = authentication?.principal // 인증 완료한 객체의 principal을 가져옴. 이때 OAuth2의 principal에는 DefaultOAuth2User가 들어간다.
            val authorization = authentication?.authorities // 권한 목록
            val details = authentication?.details // 기타 정보
            val name = authentication?.name // DefaultOAuth2User 에 있는 userNameAttributeName 인듯
            val customOAuth2User = principal as? CustomOAuth2User // 일단 DefaultOAuth2User로 형변환
            val attributes = customOAuth2User?.attributes // DefaultOAuth2User에 있는 attributes 즉, Oauth2 로그인 시 해당 서비스가 보낸 정보들
            val memberPrincipal = customOAuth2User?.memberPrincipal // 인증 완료한 유저 정보
            val accessToken = memberPrincipal?.let { jwtProvider.generateJwtToken(it) }
            val refreshToken = jwtProvider.generateRefreshToken()
            val responsecookie = jwtProvider.generateRefreshTokenCookie(refreshToken)
            val data = mapOf("msg" to "login success", "code" to "200")
            val jsonObject = JSONObject(data)
            val print = response?.writer
    
            response?.addHeader(AuthConstants.AUTH_HEADER, AuthConstants.TOKEN_TYPE + accessToken)
            response?.addHeader(AuthConstants.COOKIE_HEADER, responsecookie.toString())
            response?.characterEncoding = "UTF-8";
            response?.contentType = "application/json";
            print?.print(jsonObject)
            print?.flush()
            print?.close()
    
            log.info("========CustomSuccessHandelr=========")
            log.info("authentication : {}", authentication)
            log.info("principal : {} ",principal)
            log.info("details : {} ",details)
            log.info("name : {} ",name)
            log.info("authorization : {} ",authorization)
            log.info ("authorities : {}", attributes)
            log.info ("memberPrincipal : {}", memberPrincipal)
            log.info ("accessToken : {}", accessToken)
            log.info ("refreshToken : {}", refreshToken)
    
            // 아래의 경우 보낼 수는 있는데 이러면 jwt filter를 한번 타게 된다. 왜그럴까?
            // handler가 실행 되면 따로 jwt filter가 작동 하지 않는것으로 알고 있는데 밑에 구문을 하면 왜 타는지 모르겠다.
            // 성공 시 요청 경로는 누구나 접근할 수 있게 설정 했지만, 현재 요청을 그대로 가져가는 것인지 헤더 값이 없다며 오류가 발생한다.
            // 추측컨데, 리다이렉트와 디스페쳐를 할 경우 미상의 api 요청이 발생하는 것 같다. 미상의 API는 따로 누구나 접근할 수 있게 설정하지 않아 JWT가 돌아 가는것 아닐까 하는 생각이 든다.
    
    //        val requestDispatcher = request?.getRequestDispatcher("/api/v1/member/login/success")
    //        requestDispatcher?.forward(request, response)
    //        response?.sendRedirect("/api/v1/member/login/success")
    
        }
    }
    ```
  
  </details>
  
  <details>
    <summary>JWTAuthorizationFilter</summary>
  
  - JWT 인가 필터 추가
  
    ```kotlin
    package com.example.kotlin.security.jwt.filter
    
    import com.example.kotlin.security.AuthConstants
    import com.example.kotlin.security.jwt.provider.JWTProvider
    import jakarta.servlet.FilterChain
    import jakarta.servlet.http.HttpServletRequest
    import jakarta.servlet.http.HttpServletResponse
    import org.slf4j.LoggerFactory
    import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
    import org.springframework.security.core.authority.SimpleGrantedAuthority
    import org.springframework.security.core.context.SecurityContextHolder
    import org.springframework.web.filter.OncePerRequestFilter
    import java.util.*
    
    class JWTAuthorizationFilter (
        private val jwtProvider: JWTProvider
    ): OncePerRequestFilter() {
        private val log = LoggerFactory.getLogger(this.javaClass)!!
        override fun doFilterInternal(
            request: HttpServletRequest,
            response: HttpServletResponse,
            filterChain: FilterChain
        ) {
            log.info("===========JWTAuthorizationFilter============")
    
            val header = request.getHeader(AuthConstants.AUTH_HEADER)
    
            val token = jwtProvider.getTokenFromHeader(header)
    
            jwtProvider.isValidToken(token)
    
            val userEmail = jwtProvider.getUserEmailFromToken(token)
    
            val userRole = jwtProvider.getUserRoleFromToken(token)
    
            val autentication = UsernamePasswordAuthenticationToken(userEmail, null, Collections.singleton(SimpleGrantedAuthority(userRole)))
    
            SecurityContextHolder.getContext().authentication=autentication
    
            log.info("userEmail : {}", userEmail)
            log.info("userRole : {}", userRole)
    
            filterChain.doFilter(request, response)
    
        }
    }
    ```
  
  </details>
  
  <details>
    SecurityConfig</summary>
  
  - 인가 필터 추가 (필터 bean 등록 시 자동으로 적용 되므로 이를 피하려면 Bean 등록 빼야함)
    - 권한 요청 URL 생성
  
      ```kotlin
      package com.example.kotlin.security.config
    
      import com.example.kotlin.member.Role
      import com.example.kotlin.security.jwt.filter.JWTAuthorizationFilter
      import com.example.kotlin.security.jwt.provider.JWTProvider
      import com.example.kotlin.security.oauth2.handler.CustomSuccessHandler
      import com.example.kotlin.security.oauth2.service.CustomOAuth2MemberService
      import lombok.RequiredArgsConstructor
      import org.springframework.context.annotation.Bean
      import org.springframework.context.annotation.Configuration
      import org.springframework.security.config.annotation.web.builders.HttpSecurity
      import org.springframework.security.config.http.SessionCreationPolicy
      import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint
      import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
      import org.springframework.security.web.util.matcher.AntPathRequestMatcher
    
      @Configuration
      @RequiredArgsConstructor
      class SecurityConfig(
          private val customOAuth2MemberService: CustomOAuth2MemberService,
          private val jwtProvider: JWTProvider
      ) {
          private val urls = arrayOf(
              AntPathRequestMatcher("/"),
              AntPathRequestMatcher("/h2-console/**"),
              AntPathRequestMatcher("/api/v1/member/signup"),
              AntPathRequestMatcher("/api/v1/member/login/success"),
              AntPathRequestMatcher("/api/member/oauth2/**"),
              AntPathRequestMatcher("/login/oauth2/code/**")
          )
          private val memberUrls = arrayOf(AntPathRequestMatcher("/api/v1/member/**"))
          private val adminUrls = arrayOf(AntPathRequestMatcher("/api/v1/admin/**"))
    
          fun customSuccessHandler(): CustomSuccessHandler {
              return CustomSuccessHandler(jwtProvider)
          }
    
          fun jwtAuthorizationFilter(): JWTAuthorizationFilter {
              return JWTAuthorizationFilter(jwtProvider)
          }
    
          @Bean
          fun filterChain(http: HttpSecurity) = http
              .headers { it.frameOptions { it.disable() } }
              .csrf { it.disable() } // csrf off
              .cors { it.disable() } // cors off
              .formLogin { it.disable() } // security login page (UsernamePasswordAuthenticationFilter) off
              .httpBasic{it.disable()} // security Authentication (BasicAuthenticationFilter) off
              .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.NEVER) } //필요하다면 세션 생성. (API는 session이 필요 없지만 google 계정 정보를 가져오기 위해 session이 필요함)
              .authorizeHttpRequests { // 경로가 겹칠 경우 먼저 작성 된 룰을 따른다.
                  it.requestMatchers(*urls).permitAll() // [signup, login] 누구나 접근 가능
                      .requestMatchers(*memberUrls).hasAnyRole(Role.ROLE_USER.key, Role.ROLE_ADMIN.key) // 가입자만 접근 가능
                      .requestMatchers(*adminUrls).hasRole(Role.ROLE_ADMIN.key) // 관리자만 접근 가능
                      .anyRequest().authenticated()
              } // 나머지 api 호출은 인증 받아야함
              .oauth2Login {
                  it.userInfoEndpoint { point -> point.userService(customOAuth2MemberService) } // oauth2Login는 loadUser라는 함수를 호출하는게 기본임. 이를 custom하여 사용하는 것.
                  it.successHandler(customSuccessHandler()) // 성공 시 핸들러
      //            it.defaultSuccessUrl("/myspace") // 성공시 이동 페이지
      //            it.failureUrl("/fail") // 실패시
              }
              .addFilterBefore(jwtAuthorizationFilter(), BasicAuthenticationFilter::class.java)
              .exceptionHandling { it.authenticationEntryPoint(LoginUrlAuthenticationEntryPoint("/login")) } // 인증 되지 않은 사용자가 접근시 login으로 이동
              .build()!!
      }
      ```
  
  </details>

</details>
