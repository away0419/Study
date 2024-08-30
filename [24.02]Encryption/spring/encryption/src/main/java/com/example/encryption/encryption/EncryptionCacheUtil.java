package com.example.encryption.encryption;

import java.util.Optional;
import java.util.function.Consumer;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class EncryptionCacheUtil {
    private final EncryptionCache encryptionCache;

    public EncryptionCacheUtil(EncryptionCache encryptionCache) {
        this.encryptionCache = encryptionCache;
    }

    /**
     * identityHashCode 추출.
     * @param entity
     * @return
     */
    private String generateCacheKey(Object entity) {
        return Optional.ofNullable(entity)
            .map(data -> String.valueOf(System.identityHashCode(data)))
            .orElseThrow(() -> new RuntimeException("cache param null"));
    }

    /**
     * 캐시 확인 후 consumer 실행
     * @param target
     * @param result
     * @param consumer
     */
    public void ifAbsent(Object target, Object result, Consumer<Object> consumer) {
        if (target == null || result == null || consumer == null) {
            return;
        }

        String cacheKey = generateCacheKey(target);

        if (!encryptionCache.getCache(cacheKey)) {
            consumer.accept(result);
            encryptionCache.setCache(cacheKey);
            return;
        }

        log.info("Using cached entity for decryption. cacheKey {}", cacheKey);
    }
}
