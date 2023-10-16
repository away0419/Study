```
kotlin : 1.8
spring boot : 3.1.3
spring security : 6.1.4
```


## Oauth2

- google, naver, kakao 연동 로그인 구현

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
          clientAuthenticationMethod: post
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
          clientAuthenticationMethod: post
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
          user_name_attribute: id
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