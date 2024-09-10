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