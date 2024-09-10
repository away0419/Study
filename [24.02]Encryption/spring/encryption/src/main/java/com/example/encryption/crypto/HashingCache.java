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
