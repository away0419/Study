package com.example.encryption.encryption;

import java.util.concurrent.ConcurrentHashMap;

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
    private final ConcurrentHashMap<String, Object> cache;

    public EncryptionAspect(EncryptionUtil encryptionUtil) {
        this.encryptionUtil = encryptionUtil;
        this.cache = new ConcurrentHashMap<>();
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
        String cacheKey = generateCacheKey(entity);

        if (!cache.containsKey(cacheKey)) {
            encryptionUtil.validateAndCrypto(entity, CryptoMode.DECRYPTION);
            cache.put(cacheKey, entity);
        } else {
            log.info("Using cached entity for decryption");
        }
        log.info("hashcode: {}, identityHashCode: {}, entity: {}", entity.hashCode(), System.identityHashCode(entity), entity);
    }

    private String generateCacheKey(Object entity) {
        return String.valueOf(entity.hashCode());
    }

}
