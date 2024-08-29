> ## 라이브러리 및 설정

<details>
    <summary>Gradle</summary>

- 라이브러리 추가.

    ```gradle 
    plugins {
        id 'java'
        id 'org.springframework.boot' version '3.3.2'
        id 'io.spring.dependency-management' version '1.1.6'
    }
    
    group = 'com.example'
    version = '0.0.1-SNAPSHOT'
    
    java {
        toolchain {
            languageVersion = JavaLanguageVersion.of(21)
        }
    }
    
    repositories {
        mavenCentral()
    }
    
    dependencies {
    
    
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
    
    }
    
    tasks.named('test') {
        useJUnitPlatform()
    }
        
    ```

</details>

<details>
    <summary>configuration.yaml</summary>

- DB 설정

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
          data-locations: classpath:data.sql
    
    mybatis:
      mapper-locations: classpath:mapper/*.xml
    
    ```

</details>


<br/>
<br/>

> ## Domain

<details>
    <summary>UserDTO</summary>

```java
package com.example.encryption.user;

import com.example.encryption.encryption.EncryptedField;
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
    private String phone;
    @EncryptedField
    private String email;
    private String etc;

}

```

</details>

<details>
    <summary>Mapper</summary>

```java
package com.example.encryption.user.mapper;

import com.example.encryption.user.UserDTO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface UserMapper {
    int insertUser(UserDTO userDTO);
    List<UserDTO> selectAllUser();
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

    <insert id="insertUser" parameterType="com.example.encryption.user.UserDTO">
        INSERT INTO USERS (NAME, ADDRESS, PHONE, EMAIL, ETC)
        VALUES (#{name}, #{address}, #{phone}, #{email}, #{etc})
    </insert>
</mapper>

```

</details>

<details>
    <summary>UserService</summary>

```java
package com.example.encryption.user;


import com.example.encryption.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;

    public List<UserDTO> getAllUsers() {
        return userMapper.selectAllUser();
    }

    public int addUser(UserDTO userDTO) {
        return userMapper.insertUser(userDTO);
    }
}
```
</details>

<details>
    <summary>UserController</summary>

```java
package com.example.encryption.user;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

}

```

</details>


<br/>
<br/>


> ## 암복호화

<details>
    <summary>CryptoMode</summary>

- 암복화 enum

```java
package com.example.encryption.encryption;

import lombok.Getter;

@Getter
public enum CryptoMode {
    ENCRYPTION("암호화"),
    DECRYPTION("복호화");

    private final String mdoe;

    private CryptoMode(String mode) {
        this.mdoe = mode;
    }

}

```

</details>

<details>
    <summary>EncryptedField</summary>

- 암호화 또는 복호화 하려는 필드에 해당 어노테이션을 걸어주면 DB 접근 시 제어 가능.

    ```java
    package com.example.encryption.encryption;
    
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

</details>


<details>
    <summary>SaltUtil</summary>

- Salt로 만들었으나 IV임.
- Salt는 기본적으로 12바이트를 사용한다 함.

    ```java
    package com.example.encryption.encryption;
    
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
    <summary>EncryptionUtil</summary>

- 암복화 기능 구현
- 클래스 타입 체크 및 필드 어노테이션 확인 후 암복화 실행.

    ```java
    package com.example.encryption.encryption;
    
    import com.example.encryption.user.UserDTO;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.stereotype.Component;
    
    import javax.crypto.BadPaddingException;
    import javax.crypto.Cipher;
    import javax.crypto.IllegalBlockSizeException;
    import javax.crypto.NoSuchPaddingException;
    import javax.crypto.spec.IvParameterSpec;
    import javax.crypto.spec.SecretKeySpec;
    import java.lang.reflect.Field;
    import java.nio.charset.StandardCharsets;
    import java.security.InvalidAlgorithmParameterException;
    import java.security.InvalidKeyException;
    import java.security.NoSuchAlgorithmException;
    import java.util.*;
    
    @Slf4j
    @Component
    public class EncryptionUtil {
        private final SaltUtil saltUtil;
        private final SecretKeySpec secretKeySpec;
        private final String ALGORITHM = "AES";
        private final String TRANSFORMATION = "AES/CBC/PKCS5Padding";
        private final String EMPTY = "";
    
        public EncryptionUtil(@Value("secretKeysecretk") String encryptionKey, SaltUtil saltUtil) {
            this.saltUtil = saltUtil;
            this.secretKeySpec = new SecretKeySpec(encryptionKey.getBytes(StandardCharsets.UTF_8), ALGORITHM);
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
    
                        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
                        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, iv);
    
                        byte[] encrypted = cipher.doFinal(d.getBytes(StandardCharsets.UTF_8));
                        byte[] combined = new byte[salt.length + encrypted.length];
    
                        System.arraycopy(salt, 0, combined, 0, salt.length);
                        System.arraycopy(encrypted, 0, combined, salt.length, encrypted.length);
    
                        return Base64.getEncoder().encodeToString(combined);
                    } catch (BadPaddingException |
                             IllegalBlockSizeException |
                             InvalidAlgorithmParameterException |
                             InvalidKeyException |
                             NoSuchPaddingException |
                             NoSuchAlgorithmException e) {
                        log.info(e.getLocalizedMessage());
                        return EMPTY;
                    }
                }).orElseGet(() -> {
                    log.info("encrypt: encryptData is NULL");
                    return EMPTY;
                });
    
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
                        byte[] dataBytes = Base64.getDecoder().decode(data);
                        byte[] salt = new byte[saltUtil.getSALT_LENGTH()];
                        byte[] encrypted = new byte[dataBytes.length - salt.length];
    
                        System.arraycopy(dataBytes, 0, salt, 0, saltUtil.getSALT_LENGTH());
                        System.arraycopy(dataBytes, saltUtil.getSALT_LENGTH(), encrypted, 0, encrypted.length);
    
                        IvParameterSpec iv = new IvParameterSpec(salt);
                        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
                        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, iv);
    
                        return new String(cipher.doFinal(encrypted), StandardCharsets.UTF_8);
                    } catch (InvalidAlgorithmParameterException |
                             NoSuchPaddingException |
                             NoSuchAlgorithmException |
                             InvalidKeyException |
                             IllegalBlockSizeException |
                             BadPaddingException e) {
                        return EMPTY;
                    }
                }).orElseGet(() -> {
                    log.info("decrypt: decryptData is NULL");
                    return EMPTY;
                });
        }
    
        /**
         * 객체의 클래스 확인 후 재귀 또는 cryptoDataFields 메소드 실행 또는 아무것도 안함.
         * @param cryptoData 암호화 또는 복호화할 객체
         * @param cryptoMode 암호화 또는 복호화 모드
         */
        public void validateAndCrypto(Object cryptoData, CryptoMode cryptoMode) {
            if (cryptoData == null) {
                return;
            }
    
            if (cryptoData instanceof Optional<?>) {
                Optional<?> optionalResult = (Optional<?>) cryptoData;
                if (optionalResult.isPresent()) {
                    validateAndCrypto(optionalResult.get(), cryptoMode);
                }
            } else if (cryptoData instanceof List<?>) {
                List<?> listResult = (List<?>) cryptoData;
                listResult.forEach(v -> {
                    validateAndCrypto(v, cryptoMode);
                });
            } else if (cryptoData instanceof Set<?>) {
                Set<?> setResult = (Set<?>) cryptoData;
                setResult.forEach(v -> {
                    validateAndCrypto(v, cryptoMode);
                });
            } else if (cryptoData instanceof Map<?, ?>) {
                Map<?, ?> mapResult = (Map<?, ?>) cryptoData;
                mapResult.forEach((k, v) -> {
                    validateAndCrypto(v, cryptoMode);
                });
            } else if (isClassCryptoPossible(cryptoData)) {
                cryptoDataFields(cryptoData, cryptoMode);
            }
        }
    
        /**
         * 객체의 필드를 탐색하여 @EncryptedField 있는 필드를 암호화 또는 복호화
         * @EncryptedField 없는 경우 validateAndCrypto 메소드 실행
         * @param cryptoData 암호화 또는 복호화할 객체
         * @param cryptoMode 암호화 또는 복호화 모드
         */
        public void cryptoDataFields(Object cryptoData, CryptoMode cryptoMode) {
            Field[] fields = cryptoData.getClass().getDeclaredFields();
    
            for (Field field : fields) {
                try {
                    field.setAccessible(true);
                    Object value = field.get(cryptoData);
                    if (field.isAnnotationPresent(EncryptedField.class)) {
    
                        if (!(value instanceof String)) {
                            log.info("cryptoDataFields: value is not String");
                            continue;
                        }
    
                        String text = (String) value;
                        String processedValue = cryptoMode == CryptoMode.ENCRYPTION ? encrypt(text) : decrypt(text);
                        field.set(cryptoData, processedValue);
                    } else {
                        validateAndCrypto(value, cryptoMode);
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
         * 객체가 암호화 가능한 타입인지 확인
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
    <summary>EncryptionAspect</summary>

- DB 접근 시 파라미터 혹은 리턴 객체 확인하여 커스텀 어노테이션 있는 경우 암복호화 진행.

    ```java
    package com.example.encryption.encryption;
    
    import lombok.extern.slf4j.Slf4j;
    import org.aspectj.lang.annotation.AfterReturning;
    import org.aspectj.lang.annotation.Aspect;
    import org.aspectj.lang.annotation.Before;
    import org.springframework.stereotype.Component;
    
    /**
     * DB 접근 시, 해당 객체의 필드에 @EncryptedField 어노테이션 확인 후 암복화 실행.
     */
    @Slf4j
    @Aspect
    @Component
    public class EncryptionAspect {
    
        private final EncryptionUtil encryptionUtil;
    
        public EncryptionAspect(EncryptionUtil encryptionUtil) {
            this.encryptionUtil = encryptionUtil;
        }
    
        /**
         * com.example.encryption 하위 폴더 중 Mapper 맨 뒤에 붙은 파일에서 해당 명으로 시작하는 메소드명 확인. 매개변수로 활용 된 객체를 가져온다.
         * insert로 시작하는 메소드 혹은 update로 시작하는 메소드가 실행 되기 전 AOP 실행. 이때 entity 값은 AOP 메소드의 매개변수 타입인 Object.
         *
         * @param entity
         */
        @Before("(execution(* com.example.encryption..*Mapper.insert*(..)) || execution(* com.example.encryption..*Mapper.update*(..))) && args(entity)")
        public void encryptEntity(Object entity) {
            encryptionUtil.validateAndCrypto(entity, CryptoMode.ENCRYPTION);
        }
    
        /**
         * select, get, findBy 등등 메소드 실행 완료 후 결과 값이 있다면 받아와 AOP 실행.
         * @param entity
         */
        @AfterReturning(pointcut = "execution(* com.example.encryption..*Mapper.select*(..)) || execution(* com.example.encryption..*Mapper.get*(..)) || execution(* com.example.encryption..*Mapper.findBy*(..))", returning = "entity")
        public void decryptEntity(Object entity) {
            encryptionUtil.validateAndCrypto(entity, CryptoMode.DECRYPTION);
        }
    
    
    }
    
    ```

</details>


<br/>
<br/>

> ## 발견된 주의사항
<details>
<summary>transactional</summary>

- MyBatis 사용 기준 SQL 트랜잭션을 걸 경우, 동일한 SQL 요청이 들어온다면, 캐싱된 결과 값을 리턴함. 이로 인해 문제가 발생함.
- 복호화 진행 시, 첫번째 SQL 실행 후 결과 값 캐싱하고 리턴하며 이를 AOP가 잡아냄.
- AOP는 해당 객체의 필드를 확인하여 복호화 후 해당 객체 필드에 SET함. 따라서 캐싱된 값도 복호화 된 상태로 SET됨.
- 두번째 SQL 실행 시 캐싱된 객체를 리턴하며 이를 AOP가 잡아냄.
- 이미 복호화 된 내용을 다시 복호화 시도하니 에러 발생. (복호화 시 base64 형태의 String을 디코딩하는데 이미 복호화 된 String 형태이므로 디코딩 문제 발생.)
- 이를 해결하기 위해 캐싱을 확인 후 복호화 하거나, 복호화 에러 발생 시 현재 값을 리턴하는 식으로 로직을 수정해야함.
- 해결 방법
  - 인터셉터를 이용해 SQL에서 캐싱이 발생했는지 잡아내고 이를 AOP에서 확인하고 처리하는 방법.
  - AOP 실행 시 반환 받은 값의 HashCode를 캐싱하여 관리하는 방법. (System.identityHashCode() 사용.-> 거의 항상 유니크) 이때, Caching 사용하여 시간 지나면 삭제되도록 짜는게 효율적.
  - System.identityHashCode() 사용하여 캐시 구현할 때 주의점이 있음. AOP가 Object를 받아올 때 프록시를 활용하여 실제 객체의 프록시 객체를 만들어 가져옴.
  - 프록시 객체를 AOP 실행할 때마다 새로 만들기 때문에 System.identityHashCode() 사용할 경우 매번 다름. (hashcode()는 동일할 수도 다를 수도 있음. 유니크 보장이 안됨)
  - 따라서 프록시 객체의 타겟 객체를 가져와 실제 타겟 객체의 System.identityHashCode()로 캐싱을 활용해야 함.
  - 프록시 객체로 받아와서 값을 변경해도 실제 타겟 객체 값이 변경되는 이유는 참조 객체이기 때문임.
  - 결국 AOP로 작업시 프록시 객체와 관련된 추가적인 설정이 필요하다는 뜻.
  - [AOP정리참고](https://github.com/away0419/Study_2023-2024/tree/main/%5B23.06%5DAOP/spring_aop/springboot_java)

</details>
