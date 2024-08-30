package com.example.encryption.encryption;

import lombok.Getter;

@Getter
public enum CommonCache {
    ENCRYPT(CommonCacheConstants.ENCRYPT, 20000, 300),
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
