package com.example.kotlin.security.provider

import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.stereotype.Component

@Component
class SecurityProvider {
    /**
     * url 형 변환
     * @param urls Array<out String>
     * @return Array<AntPathRequestMatcher>
     */
    fun createRequestMatchers(vararg urls: String): Array<AntPathRequestMatcher> {
        return urls.map { AntPathRequestMatcher(it) }.toTypedArray()
    }
}