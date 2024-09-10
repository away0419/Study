> ## 라이브러리 및 설정

<details>

<summary>yaml</summary>

- h2, mybatis 설정.

  ```yaml
  spring:
    datasource:
      url: jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
      driverClassName: org.h2.Driver
      username: sa
      password:
  
    h2:
      console:
        enabled: true
        path: /h2-console
  
    sql:
      init:
        mode: always
        data-locations: classpath:data.sql
  
  
  mybatis:
    mapper-locations: classpath:mapper/*.xml
  ```
</details>

<details>
<summary>gradle</summary>

- h2, mybatis, cache 등...

  ```gradle
  implementation 'org.springframework.boot:spring-boot-starter-web'
  developmentOnly 'org.springframework.boot:spring-boot-devtools'
  testImplementation 'org.springframework.boot:spring-boot-starter-test'
  
  compileOnly 'org.projectlombok:lombok'
  annotationProcessor 'org.projectlombok:lombok'
  implementation 'com.h2database:h2'
  implementation 'org.springframework.boot:spring-boot-starter-aop'
  implementation 'org.mybatis.spring.boot:mybatis-spring-boot-starter:3.0.3'
  implementation 'org.mapstruct:mapstruct:1.4.2.Final'
  annotationProcessor 'org.mapstruct:mapstruct-processor:1.4.2.Final'
  compileOnly 'org.projectlombok:lombok'
  annotationProcessor 'org.projectlombok:lombok'
  
  implementation 'org.apache.logging.log4j:log4j-core:2.20.0'
  implementation 'org.apache.logging.log4j:log4j-api:2.20.0'
  implementation 'org.apache.logging.log4j:log4j-slf4j-impl:2.20.0'
  
  // Spring Cache 관련 의존성
  implementation 'org.springframework.boot:spring-boot-starter-cache'
  implementation 'com.github.ben-manes.caffeine:caffeine:3.1.8'
  implementation 'org.springframework:spring-context-support:6.1.2'
  
  ```

</details>


<br/>
<br/>

> ## Domain

<details>
<summary>UserDTO</summary>

- 기본 유저 정보

  ```java
  package com.example.encryption.user;
  
  import com.example.encryption.crypto.EncryptedField;
  import com.example.encryption.crypto.HashingField;
  
  import lombok.*;
  
  @AllArgsConstructor
  @Setter
  @Getter
  @NoArgsConstructor
  @Builder
  @ToString
  public class UserDTO {
      private String name;
      @EncryptedField
      private String address;
      @EncryptedField
      private String email;
      @EncryptedField
      private String phonee;
      @HashingField
      private String phoneh;
      private String etc;
  
  }
  
  ```

</details>

<details>
  <summary>UserMapper</summary>

- 유저 Mapper

  ```java
  package com.example.encryption.user.mapper;
  
  import com.example.encryption.user.UserDTO;
  import org.apache.ibatis.annotations.Mapper;
  import java.util.List;
  import java.util.Optional;
  
  @Mapper
  public interface UserMapper {
      int insertUser(UserDTO userDTO);
      List<UserDTO> selectAllUser();
      UserDTO selectUserByName(String name);
      Optional<UserDTO> selectOptionaltUserByName(String name);
  }
  
  ```

  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
  
  <mapper namespace="com.example.encryption.user.mapper.UserMapper">
      <select id="selectAllUser" resultType="com.example.encryption.user.UserDTO">
          SELECT *
          FROM USERS
      </select>
      <select id="selectUserByName" resultType="com.example.encryption.user.UserDTO">
          SELECT *
          FROM USERS
          WHERE name = #{name}
      </select>
      <select id="selectOptionaltUserByName" resultType="com.example.encryption.user.UserDTO">
          SELECT *
          FROM USERS
          WHERE name = #{name}
      </select>
  
      <insert id="insertUser" parameterType="com.example.encryption.user.UserDTO">
          INSERT INTO USERS (NAME, ADDRESS, PHONEH, PHONEE, EMAIL, ETC)
          VALUES (#{name}, #{address}, #{phoneh}, #{phonee}, #{email}, #{etc})
      </insert>
  </mapper>
  
  ```

</details>

<details>
<summary>UserService</summary>

- 테스트를 위한 트랜잭션 기능 추가

  ```java
  package com.example.encryption.user;
  
  
  import com.example.encryption.user.mapper.UserMapper;
  import lombok.RequiredArgsConstructor;
  import lombok.extern.slf4j.Slf4j;
  
  import org.springframework.stereotype.Service;
  import org.springframework.transaction.annotation.Transactional;
  
  import java.util.List;
  import java.util.Optional;
  
  @Service
  @RequiredArgsConstructor
  @Slf4j
  public class UserService {
      private final UserMapper userMapper;
  
      public List<UserDTO> getAllUsers() {
          return userMapper.selectAllUser();
      }
  
      @Transactional
      public int addUser(UserDTO userDTO) {
          for (int i = 0; i < 10; i++) {
              userMapper.insertUser(userDTO);
              String str = userDTO.getName()+ i;
              log.info(str);
              userDTO.setName(str);
          }
  
          return 1;
      }
  
      public void repeat(){
          for (int i = 0; i < 10; i++) {
              log.info("1. {}반복: {} ",i, userMapper.selectOptionaltUserByName("홍길동"));
          }
      }
  
      @Transactional
      public void repeat2(){
          repeat();
  
          for (int i = 0; i < 10; i++) {
              log.info("2. {}반복: {} ",i, userMapper.selectOptionaltUserByName("홍길동"));
          }
      }
  
      @Transactional
      public void test(){
          this.repeat();
          this.repeat2();
      }
  
  }
  
  ```


</details>

<details>
  <summary>UserController</summary>

- api 작성

  ```java
  package com.example.encryption.user;
  
  import java.util.List;
  
  import org.springframework.web.bind.annotation.GetMapping;
  import org.springframework.web.bind.annotation.PostMapping;
  import org.springframework.web.bind.annotation.RequestBody;
  import org.springframework.web.bind.annotation.RequestMapping;
  import org.springframework.web.bind.annotation.RestController;
  
  import lombok.RequiredArgsConstructor;
  
  @RestController
  @RequestMapping("/user")
  @RequiredArgsConstructor
  public class UserController {
  
    private final UserService userService;
  
    @GetMapping("/selectAll")
    public List<UserDTO> getAllUsers() {
      return userService.getAllUsers();
    }
  
    @PostMapping("/insert")
    public int insertUser(@RequestBody UserDTO userDTO) {
      return userService.addUser(userDTO);
    }
  
    @GetMapping("/repeat1")
    public int getRepeat1Users() {
      userService.repeat();
      return 1;
    }
    @GetMapping("/repeat2")
    public int getRepeat2Users() {
      userService.repeat2();
      return 1;
    }
    @GetMapping("/test")
    public int getTestUsers() {
      userService.test();
      return 1;
    }
  
  }
  
  ```

</details>





<br/>
<br/>


> ## Cache

<details>
  <summary>CommonCacheName</summary>

- 상수 파일.

  ```java
  package com.example.encryption.cache;
  
  public interface CommonCacheName {
      String ENCRYPT = "ENCRYPT";
      String HASHING = "HASHING";
  }
  
  ```

</details>

<details>
  <summary>CommonCache</summary>

- 캐시 종류

  ```java
  package com.example.encryption.cache;
  
  import lombok.Getter;
  
  @Getter
  public enum CommonCache {
      ENCRYPT(CommonCacheName.ENCRYPT, 20000, 300),
      HASHING(CommonCacheName.HASHING, 20000, 300),
      ;
  
      private final String cacheName;      // 캐시명
      private final long maximumSize;      // 메모리 최대 적재 크기(Caffeine을 사용하는 경우에만 적용)
      private final long expireAfterWrite; // 캐시 유효시간(초)
  
      CommonCache(String cacheName, long maximumSize, long expireAfterWrite) {
          this.cacheName = cacheName;
          this.maximumSize = maximumSize;
          this.expireAfterWrite = expireAfterWrite;
      }
  }
  
  ```

</details>

<details>
  <summary>CacheConfig</summary>

- 캐시 설정

  ```java
  package com.example.encryption.cache;
  
  import java.util.Arrays;
  import java.util.concurrent.TimeUnit;
  
  import org.springframework.cache.CacheManager;
  import org.springframework.cache.annotation.EnableCaching;
  import org.springframework.cache.caffeine.CaffeineCacheManager;
  import org.springframework.context.annotation.Bean;
  import org.springframework.context.annotation.Configuration;
  
  import com.github.benmanes.caffeine.cache.Caffeine;
  
  import lombok.extern.slf4j.Slf4j;
  
  @Slf4j
  @EnableCaching
  @Configuration
  public class CacheConfig {
      @Bean
      public CacheManager cacheManager() {
          CaffeineCacheManager cacheManager = new CaffeineCacheManager();
          Arrays.stream(CommonCache.values())
              .forEach(
                  cache -> cacheManager.registerCustomCache(
                      cache.getCacheName(),
                      Caffeine.newBuilder()
                          .recordStats()
                          .expireAfterWrite(cache.getExpireAfterWrite(), TimeUnit.SECONDS)
                          .maximumSize(cache.getMaximumSize())
                          .removalListener(
                              (key, value, cause) ->
                                  log.debug("Key {}, Value : {}, Cause : {} expired", key, value, cause.name())
                          )
                          .evictionListener((key, value, cause) ->
                              log.debug("Key {}, Value : {}, Cause : {} eviction", key, value, cause.name())
                          )
                          .build()
                  )
              );
          return cacheManager;
      }
  }
  ```

</details>



<br/>
<br/>


> ## Crypto

<details>
  <summary>Crypto</summary>

- 암호학 종류

  ```java
  package com.example.encryption.crypto;
  
  import lombok.Getter;
  
  @Getter
  public enum Crypto {
      ENCRYPTION("암호화", "AES", "AES/CBC/PKCS5Padding"),
      DECRYPTION("복호화", "AES", "AES/CBC/PKCS5Padding"),
      HASHING("해싱", "SHA-256", "NULL");
  
      private final String type;
      private final String algorithm;
      private final String transformation;
  
      Crypto(String type, String algorithm, String transformation) {
          this.type = type;
          this.algorithm = algorithm;
          this.transformation = transformation;
      }
  
  }
  
  ```

</details>

<details>
  <summary>Annotation</summary>

- 어떤 필드에 적용할지 쓰려는 용도

  ```java
  package com.example.encryption.crypto;
  
  import java.lang.annotation.ElementType;
  import java.lang.annotation.Retention;
  import java.lang.annotation.RetentionPolicy;
  import java.lang.annotation.Target;
  
  /**
   * DB에 암호화하여 관리하려는 필드
   */
  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.FIELD)
  public @interface EncryptedField {
  }
  
  ```

  ```java
  package com.example.encryption.crypto;
  
  import java.lang.annotation.ElementType;
  import java.lang.annotation.Retention;
  import java.lang.annotation.RetentionPolicy;
  import java.lang.annotation.Target;
  
  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.FIELD)
  public @interface HashingField {
  }
  
  ```

</details>

<details>
  <summary>Cache</summary>

- 해싱, 복호화 각각 캐시 구현

  ```java
  package com.example.encryption.crypto;
  
  import org.springframework.cache.annotation.CacheEvict;
  import org.springframework.cache.annotation.CachePut;
  import org.springframework.cache.annotation.Cacheable;
  import org.springframework.stereotype.Component;
  
  import com.example.encryption.cache.CommonCacheName;
  
  import lombok.extern.slf4j.Slf4j;
  
  @Slf4j
  @Component
  public class EncryptionCache {
  
      /**
       * 캐시 조회. 저장 안함.
       * @param cacheKey
       * @return
       */
      @Cacheable(cacheNames = CommonCacheName.ENCRYPT, key = "#cacheKey", unless = "!#result")
      public boolean getCache(String cacheKey) {
          return false;
      }
  
      /**
       * 캐시 저장
       * @param cacheKey
       * @return
       */
      @CachePut(cacheNames = CommonCacheName.ENCRYPT, key = "#cacheKey", condition = "#cacheKey != null and #cacheKey.length() != 0", unless = "!#result")
      public boolean setCache(String cacheKey) {
          return true;
      }
  
      /**
       * 캐시 삭제
       * @param cacheKey
       */
      @CacheEvict(cacheNames = CommonCacheName.ENCRYPT, key = "#cacheKey")
      public void evictCache(String cacheKey) {
      }
  
  }
  
  ```

  ```java
  package com.example.encryption.crypto;
  
  import org.springframework.cache.annotation.CacheEvict;
  import org.springframework.cache.annotation.CachePut;
  import org.springframework.cache.annotation.Cacheable;
  import org.springframework.stereotype.Component;
  
  import com.example.encryption.cache.CommonCacheName;
  
  @Component
  public class HashingCache {
      /**
       * 캐시 조회. 저장 안함.
       * @param cacheKey
       * @return
       */
      @Cacheable(cacheNames = CommonCacheName.HASHING, condition = "#cacheKey != null and #cacheKey.length() != 0", key = "#cacheKey", unless = "#result.isEmpty()")
      public String getCache(String cacheKey) {
          return "";
      }
  
      /**
       * 캐시 저장
       * @param cacheKey
       * @return
       */
      @CachePut(cacheNames = CommonCacheName.HASHING, key = "#cacheKey", condition = "#cacheKey != null and #cacheKey.length() != 0", unless = "#result.isEmpty()")
      public String setCache(String cacheKey, String cacheValue) {
          return cacheValue;
      }
  
      /**
       * 캐시 삭제
       * @param cacheKey
       */
      @CacheEvict(cacheNames = CommonCacheName.HASHING, key = "#cacheKey")
      public void evictCache(String cacheKey) {
      }
  }
  
  ```

</details>

<details>
  <summary>SaltUtil</summary>

- 암호화에 사용하기 위한 IV 랜덤 생성 클래스.

  ```java
  package com.example.encryption.crypto;
  
  import org.springframework.stereotype.Component;
  
  import java.security.SecureRandom;
  
  @Component
  public class SaltUtil {
      private final SecureRandom secureRandom;
      private final int SALT_LENGTH = 16;
  
      public SaltUtil() {
          secureRandom = new SecureRandom();
      }
  
      /**
       * 암호화에 사용되는 무작위 바이트 배열 생성
       *
       * @return
       */
      public byte[] generateSalt() {
          byte[] salt = new byte[SALT_LENGTH];
          secureRandom.nextBytes(salt);
          return salt;
      }
  
      public int getSALT_LENGTH() {
          return SALT_LENGTH;
      }
  }
  
  ```

</details>

<details>
  <summary>CryptoUtil</summary>

- 실제 암호학 처리하는 비즈니스 클래스.

  ```java
  package com.example.encryption.crypto;
  
  import java.lang.reflect.Field;
  import java.nio.charset.StandardCharsets;
  import java.security.MessageDigest;
  import java.security.NoSuchAlgorithmException;
  import java.util.Base64;
  import java.util.List;
  import java.util.Map;
  import java.util.Optional;
  import java.util.Set;
  
  import javax.crypto.Cipher;
  import javax.crypto.spec.IvParameterSpec;
  import javax.crypto.spec.SecretKeySpec;
  
  import org.springframework.beans.factory.annotation.Value;
  import org.springframework.stereotype.Component;
  
  import com.example.encryption.user.UserDTO;
  
  import lombok.extern.slf4j.Slf4j;
  
  @Slf4j
  @Component
  public class CryptoUtil {
      private final SaltUtil saltUtil;
      private final SecretKeySpec secretKeySpec;
      private final String salt;
      private final HashingCache hashingCache;
      private final EncryptionCache encryptionCache;
      private Base64.Encoder base64Encoder;
      private Base64.Decoder base64Decoder;
      private MessageDigest md;
  
      /**
       * 생성자 주입
       * @param cryptoKey
       * @param salt
       * @param saltUtil
       */
      public CryptoUtil(@Value("secretKeysecretk") String cryptoKey, @Value("salt") String salt, SaltUtil saltUtil,
          HashingCache hashingCache, EncryptionCache encryptionCache) {
          this.saltUtil = saltUtil;
          this.secretKeySpec = new SecretKeySpec(cryptoKey.getBytes(StandardCharsets.UTF_8),
              Crypto.ENCRYPTION.getAlgorithm());
          this.salt = salt;
          this.hashingCache = hashingCache;
          this.encryptionCache = encryptionCache;
          this.base64Encoder = Base64.getEncoder();
          this.base64Decoder = Base64.getDecoder();
          try {
              this.md = MessageDigest.getInstance(Crypto.HASHING.getAlgorithm());
          } catch (NoSuchAlgorithmException e) {
              throw new RuntimeException(e);
          }
      }
  
      /**
       * 데이터를 AES 알고리즘으로 암호화
       * @param data 암호화할 데이터
       * @return 암호화된 데이터 (Base64 인코딩된 문자열)
       */
      public String encrypt(String data) {
          log.info("encrypt: data = {}", data);
  
          return Optional.ofNullable(data)
              .map(d -> {
                  try {
                      byte[] salt = saltUtil.generateSalt();
                      IvParameterSpec iv = new IvParameterSpec(salt);
  
                      Cipher cipher = Cipher.getInstance(Crypto.ENCRYPTION.getTransformation());
                      cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, iv);
  
                      byte[] encrypted = cipher.doFinal(d.getBytes(StandardCharsets.UTF_8));
                      byte[] combined = new byte[salt.length + encrypted.length];
  
                      System.arraycopy(salt, 0, combined, 0, salt.length);
                      System.arraycopy(encrypted, 0, combined, salt.length, encrypted.length);
  
                      return base64Encoder.encodeToString(combined);
                  } catch (Exception e) {
                      throw new RuntimeException(e);
                  }
              }).orElse(null);
  
      }
  
      /**
       * 데이터를 AES 알고리즘으로 복호화
       * @param data 복호화할 데이터 (Base64 인코딩된 문자열)
       * @return 복호화된 데이터
       */
      public String decrypt(String data) {
          log.info("decrypt: data = {}", data);
  
          return Optional.ofNullable(data)
              .map(d -> {
                  try {
                      byte[] dataBytes = base64Decoder.decode(d);
                      byte[] salt = new byte[saltUtil.getSALT_LENGTH()];
                      byte[] encrypted = new byte[dataBytes.length - salt.length];
  
                      System.arraycopy(dataBytes, 0, salt, 0, saltUtil.getSALT_LENGTH());
                      System.arraycopy(dataBytes, saltUtil.getSALT_LENGTH(), encrypted, 0, encrypted.length);
  
                      IvParameterSpec iv = new IvParameterSpec(salt);
                      Cipher cipher = Cipher.getInstance(Crypto.DECRYPTION.getTransformation());
                      cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, iv);
  
                      return new String(cipher.doFinal(encrypted), StandardCharsets.UTF_8);
                  } catch (Exception e) {
                      throw new RuntimeException(e);
                  }
              }).orElse(null);
      }
  
      /**
       * 데이터를 SHA-256 알고리즘으로 해싱.
       * @param data
       * @return
       */
  
      public String hashing(String data) {
          log.info("Hashing: data = {}", data);
  
          return Optional.ofNullable(data)
              .map(d -> {
                  String dataAddSalt = d + salt;
                  byte[] dataBytes = md.digest(dataAddSalt.getBytes(StandardCharsets.UTF_8));
  
                  return base64Encoder.encodeToString(dataBytes);
              }).orElse(null);
  
      }
  
      /**
       * 객체의 클래스 확인 후 재귀 또는 cryptoDataFields 메소드 실행 또는 아무것도 안함.
       * @param cryptoData 암호화 또는 복호화할 객체
       * @param crypto 암호화, 복호화 중 하나
       */
      public void validateAndCrypto(Object cryptoData, Crypto crypto) {
          if (cryptoData == null) {
              return;
          }
  
          if (cryptoData instanceof Optional<?>) {
              Optional<?> optionalResult = (Optional<?>)cryptoData;
              if (optionalResult.isPresent()) {
                  validateAndCrypto(optionalResult.get(), crypto);
              }
          } else if (cryptoData instanceof List<?>) {
              List<?> listResult = (List<?>)cryptoData;
              listResult.forEach(v -> {
                  validateAndCrypto(v, crypto);
              });
          } else if (cryptoData instanceof Set<?>) {
              Set<?> setResult = (Set<?>)cryptoData;
              setResult.forEach(v -> {
                  validateAndCrypto(v, crypto);
              });
          } else if (cryptoData instanceof Map<?, ?>) {
              Map<?, ?> mapResult = (Map<?, ?>)cryptoData;
              mapResult.forEach((k, v) -> {
                  validateAndCrypto(v, crypto);
              });
          } else if (isClassCryptoPossible(cryptoData)) {
              cryptoDataFields(cryptoData, crypto);
          }
      }
  
      /**
       * 객체의 필드를 탐색하여 암호화, 해싱 어노테이션 있는 필드인지 확인 후 해당 기능 실행.
       * @EncryptedField 없는 경우 validateAndCrypto 메소드 실행
       * @param cryptoData 대상 객체
       * @param crypto 해당 타입
       */
      public void cryptoDataFields(Object cryptoData, Crypto crypto) {
          Field[] fields = cryptoData.getClass().getDeclaredFields();
  
          for (Field field : fields) {
              try {
                  field.setAccessible(true);
                  Object value = field.get(cryptoData);
  
                  if (field.isAnnotationPresent(EncryptedField.class) && field.isAnnotationPresent(HashingField.class)) {
                      throw new RuntimeException("Hashing field annotated with @EncryptedField is not supported");
                  }
  
                  if (field.isAnnotationPresent(EncryptedField.class) || field.isAnnotationPresent(HashingField.class)) {
                      if (!(value instanceof String)) {
                          log.info("cryptoDataFields: value is not String");
                          continue;
                      }
  
                      String text = (String)value;
                      String processedValue = text;
  
                      if (field.isAnnotationPresent(EncryptedField.class)) {
                          if(crypto == Crypto.ENCRYPTION){
                              processedValue = encrypt(text);
                          }else if(!encryptionCache.getCache(text)){
                              processedValue = decrypt(text);
                              encryptionCache.setCache(processedValue);
                          }
                      }
                      else if (crypto == Crypto.ENCRYPTION && hashingCache.getCache(text).isEmpty()) {
                          processedValue = hashing(text);
                          hashingCache.setCache(processedValue, text);
                      } else if (crypto == Crypto.DECRYPTION && !hashingCache.getCache(text).isEmpty()) {
                          processedValue = hashingCache.getCache(text);
                          hashingCache.evictCache(text);
                      }
  
                      field.set(cryptoData, processedValue);
                  } else {
                      validateAndCrypto(value, crypto);
                  }
              } catch (IllegalAccessException e) {
                  log.info(e.getLocalizedMessage());
              }
          }
      }
  
      /**
       * 객체가 원시 타입 혹은 래퍼 타입인지 확인
       * @param object 확인할 객체
       * @return 원시 타입 혹은 래퍼 타입이면 true, 아니면 false
       */
      private boolean isPrimitiveOrWrapper(Object object) {
          Class<?> type = object.getClass();
  
          return
              type.isPrimitive() ||
                  type == String.class ||
                  type == Boolean.class ||
                  type == Byte.class ||
                  type == Character.class ||
                  type == Double.class ||
                  type == Float.class ||
                  type == Integer.class ||
                  type == Long.class ||
                  type == Short.class;
      }
  
      /**
       * 객체 타입 확인
       * @param object 확인할 객체
       * @return 암호화 가능한 타입이면 true, 아니면 false
       */
      private boolean isClassCryptoPossible(Object object) {
          return object instanceof UserDTO;
      }
  }
  
  ```

</details>

<details>
  <summary>CryptoAspect</summary>

- 암호학 AOP

  ```java
  package com.example.encryption.crypto;
  
  import org.aspectj.lang.ProceedingJoinPoint;
  import org.aspectj.lang.annotation.Around;
  import org.aspectj.lang.annotation.Aspect;
  import org.aspectj.lang.annotation.Pointcut;
  import org.springframework.stereotype.Component;
  
  import lombok.extern.slf4j.Slf4j;
  
  /**
   * DB 접근 시, 해당 객체의 필드에 @EncryptedField 어노테이션 확인 후 암복화 실행.
   */
  @Slf4j
  @Aspect
  @Component
  public class CryptoAspect {
  
      private final CryptoUtil encryptionUtil;
  
      public CryptoAspect(CryptoUtil encryptionUtil) {
          this.encryptionUtil = encryptionUtil;
      }
  
      @Pointcut("execution(* com.example.encryption..*Mapper.insert*(..))")
      private void insert() {
      }
  
      @Pointcut("execution(* com.example.encryption..*Mapper.update*(..))")
      private void update() {
      }
  
      @Pointcut("execution(* com.example.encryption..*Mapper.select*(..))"
          + "|| execution(* com.example.encryption..*Mapper.get*(..))"
          + "|| execution(* com.example.encryption..*Mapper.findBy*(..))")
      private void select() {
      }
  
      /**
       * SQL 호출 전 매개변수를 암호화 할 경우, SQL 호출 이후 해당 매개변수는 암호화가 되어 있으므로 이를 재사용하기 힘듬. 따라서 사용 후 복호화 해야함.
       *
       * @param joinPoint
       * @return
       * @throws Throwable
       */
      @Around("insert() || update() || select()")
      public Object encryptEntity(ProceedingJoinPoint joinPoint) throws Throwable {
          Object[] args = joinPoint.getArgs();
  
          for (Object arg : args) {
              encryptionUtil.validateAndCrypto(arg, Crypto.ENCRYPTION);
          }
  
          Object result = joinPoint.proceed();
  
          for (Object arg : args) {
              encryptionUtil.validateAndCrypto(arg, Crypto.DECRYPTION);
          }
  
          encryptionUtil.validateAndCrypto(result, Crypto.DECRYPTION);
  
          return result;
      }
  
      /**
       * select, get, findBy 등등 메소드 실행 완료 후 결과 값이 있다면 받아와 AOP 실행.
       * @param result
       */
      // @AfterReturning(pointcut = "select()", returning = "result")
      // public void decryptEntity(JoinPoint joinPoint, Object result) {
      //     log.info("sadfs {}", joinPoint);
      //     log.info("sadfs {}", AopProxyUtils.ultimateTargetClass(joinPoint));
      //     log.info("sadfs {}", joinPoint.getTarget());
      //     log.info("sadfs {}", joinPoint.getThis());
      //     encryptionCacheUtil.ifAbsent(AopProxyUtils.getSingletonTarget(result), result, t -> {
      //         encryptionUtil.validateAndCrypto(t, Crypto.DECRYPTION);
      //     });
      // }
  
  }
  
  ```

</details>

<br/>
<br/>

> ## 주의사항

<details>
<summary>복호화시, 하나의 트랜잭션에서 SQL 캐싱 발생</summary>

- 하나의 트랜잭션으로 묶어서 실행 시 2차 캐싱이 일어남.
- AOP의 프록시 객체 생성으로 인한 에러 발생.
  - 복호화 진행 시, 첫번째 SQL 실행 이후 결과 값을 캐싱함.
  - 첫번째 SQL 실행 이후 결과 값을 AOP에서 프록시 객체로 새로 만들고 이를 복호화함. (프록시 객체이지만 주소 참조로 인해 SQL 결과 객체도 복호화 진행됨.)
  - 두번째 SQL 실행 전 이미 캐싱된 결과 값이 있으므로 따로 SQL 실행하지 않고 해당 캐시 값을 리턴함.
  - 캐시된 값은 이미 복호화 된 상태인데 이를 또 복호화 하려고 하니 에러가 발생함. (이미 복호화 된 값은 Base64 형태가 아니므로 디코딩 에러 발생.)
- 해결 방법.
  - AOP에서 캐싱된 객체를 자체적으로 캐싱하여 판별하는 로직 추가하기.
- 해결 방법 적용하며 발생한 문제.
  - SQL 캐시로 인한 문제이니 AOP에서 리턴 받은 객체가 이미 복호화 되었는지 판별하면 되므로 System.identityHashCode() 방문 여부 판단만 하면 된다 생각함.  
  - 첫번째로 Map을 이용했으나 데이터가 계속 쌓여 낭비가 일어나고, System.identityHashCode()이 무조건 유니크 하지 않는점이 있어 중복 가능성이 올라감.
  - 두번째로 @CacheAble 활용하여 일정 시간 이후 데이터가 삭제 되도록 만들어 낭비를 줄이고 중복 가능성을 낮출 수 있어 이를 사용하기로 함.
  - AOP의 @AfterReturning 사용하여 받은 Return 객체로 판별하니, 해당 객체는 MapperProxy에서 새로운 객체로 만들어져 System.identityHashCode()이 매번 다른 문제 발생.
  - 이후 Return 받은 객체가 아닌 실제 타겟이 되는 객체. 즉, SQL 캐싱 객체를 JoinPoint.target()을 가져옴.
  - 그러나 JoinPoin.target()은 MapperProxy 객체였고, 해당 객체는 MapperMethod 캐싱만 판별하고 있었음. (해당 MapperMethod는 실제 SQL 실행과는 무관한 즉, 지금까지 실행된 적 있는 메서드인지 판별하는 것 뿐임. 따라서 이를 할경우 다른 매개변수를 넣어도 동일한 메소드여서 문제 발생)
  - 근본적인 문제 해결을 위해 SQL 1차 캐싱 유무를 AOP에서 판단하려 했으나 방법을 찾지 못함. (SQL이 실제 실행 될 때를 AOP로 잡아 보려 했으나 실패)
  - 차선책으로 데이터를 복호화 한 값이 캐싱에 있는지 없는지 판단하여 있다면 스킵하는 형식으로 변경함.
  - [AOP정리참고](https://github.com/away0419/Study_2023-2024/tree/main/%5B23.06%5DAOP/spring_aop/springboot_java)

</details>

<details>
  <summary>암호화시, 해당 객체의 값이 암호화 되어 문제 발생</summary>

- 암호화는 insert, update에서 사용됨. 이때, 해당 SQL 실행 전 매개변수 객체를 암호화한 뒤 SQL을 실행하는 방식.
- 만약, 해당 SQL 실행 후 매개변수 객체를 다시 사용하여 insert,update 하는 로직이 서비스에 있다면 해당 객체는 이미 암호화 된 상태이므로 문제 발생. (암호화가 2번 이루어짐.)
- 해결 방법
  - 첫번째로 직렬화, 역직렬화를 이용하여 기존 값을 직렬화 하여 가지고 있다가, 역직렬화하여 매개변수 객체에 넣는 방식.
  - 두번째로 AOP의 @Before 아닌 @Around를 이용하여 SQL 실행 전에 암호화 한 뒤, SQL 실행 이후 매개변수 객체를 복호화 하는 방식.
  - 세번쩨로 Clone() 메서드를 구현하여 동일한 값을 가진 객체를 새로 만드는 방식.
  - 3가지 방법 중 두번째를 채택함. 이유는 새로운 객체를 만드는 비용이 클 수 있다는 판단.

</details>