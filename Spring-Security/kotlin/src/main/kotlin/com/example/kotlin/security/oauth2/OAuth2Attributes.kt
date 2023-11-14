package com.example.kotlin.security.oauth2

import com.example.kotlin.security.exception.SecurityCustomErrorCode
import com.example.kotlin.security.exception.SecurityCustomException

enum class OAuth2Attributes(
    val nameAttributeKey: String,
    val oauth2UserInfo: (Map<String, Any>) -> OAuth2UserInfo
) {
    GOOGLE("google", { map ->
        OAuth2UserInfo(
            map["name"]?.toString()
                ?: throw SecurityCustomException(SecurityCustomErrorCode.OAUTH2_USER_INFO_NAME_IS_NULL),
            map["email"]?.toString()
                ?: throw SecurityCustomException(SecurityCustomErrorCode.OAUTH2_USER_INFO_EMAIL_IS_NULL),
            map["picture"]?.toString(),
            map["sub"]?.toString()
                ?: throw SecurityCustomException(SecurityCustomErrorCode.OAUTH2_USER_INFO_KEY_IS_NULL),
            "google"
        )
    }),

    NAVER("naver", { attributes ->
        val content =
            attributes["response"] ?: throw SecurityCustomException(SecurityCustomErrorCode.OAUTH2_CONTENT_IS_NOT_FOUND)
        val map: Map<String, Any> =
            content as? Map<String, Any>
                ?: throw SecurityCustomException(SecurityCustomErrorCode.OAUTH2_CONTENT_TYPE_MISMATCH)
        OAuth2UserInfo(
            map["nickname"]?.toString()
                ?: throw SecurityCustomException(SecurityCustomErrorCode.OAUTH2_USER_INFO_NAME_IS_NULL),
            map["email"]?.toString()
                ?: throw SecurityCustomException(SecurityCustomErrorCode.OAUTH2_USER_INFO_EMAIL_IS_NULL),
            map["profile_image"]?.toString(),
            map["id"]?.toString()
                ?: throw SecurityCustomException(SecurityCustomErrorCode.OAUTH2_USER_INFO_KEY_IS_NULL),
            "naver"
        )
    }),

    KAKAO("kakao", { attributes ->
        val properties = attributes["properties"]
            ?: throw SecurityCustomException(SecurityCustomErrorCode.OAUTH2_CONTENT_IS_NOT_FOUND)
        val kakao_account = attributes["kakao_account"]
            ?: throw SecurityCustomException(SecurityCustomErrorCode.OAUTH2_CONTENT_IS_NOT_FOUND)
        val map: Map<String, Any> =
            properties as? Map<String, Any>
                ?: throw SecurityCustomException(SecurityCustomErrorCode.OAUTH2_CONTENT_TYPE_MISMATCH)
        val map2: Map<String, Any> =
            kakao_account as? Map<String, Any>
                ?: throw SecurityCustomException(SecurityCustomErrorCode.OAUTH2_CONTENT_TYPE_MISMATCH)
        OAuth2UserInfo(
            map["nickname"]?.toString()
                ?: throw SecurityCustomException(SecurityCustomErrorCode.OAUTH2_USER_INFO_NAME_IS_NULL),
            map2["email"]?.toString()
                ?: throw SecurityCustomException(SecurityCustomErrorCode.OAUTH2_USER_INFO_EMAIL_IS_NULL),
            map["profile_image"]?.toString(),
            attributes["id"]?.toString()
                ?: throw SecurityCustomException(SecurityCustomErrorCode.OAUTH2_USER_INFO_KEY_IS_NULL),
            "kakao"
        )
    });

    companion object {
        fun extract(registrationId: String, attributes: Map<String, Any>): OAuth2UserInfo {
            return values().find { it.nameAttributeKey == registrationId }?.oauth2UserInfo?.invoke(attributes)
                ?: throw SecurityCustomException(SecurityCustomErrorCode.OAUTH2_SERVICE_IS_NOT_FOUND)
        }
    }
}