package com.example.kotlin.security.oauth2

enum class OAuth2Attributes(
    val nameAttributeKey: String?,
    val oauth2UserInfo: (Map<String, Any>?) -> Oauth2UserInfo
) {
    GOOGLE("google", { map ->
        Oauth2UserInfo(
            map?.get("sub").toString(),
            map?.get("name").toString(),
            map?.get("email").toString(),
            map?.get("picture").toString()
        )
    }),

    NAVER("naver", { attributes ->
        val content = attributes?.get("response");
        val map: Map<String, Any>? = if (content is Map<*, *>) {
            content as? Map<String, Any>
        } else {
            emptyMap()
        }
        Oauth2UserInfo(
            map?.get("id").toString(),
            map?.get("nickname").toString(),
            map?.get("email").toString(),
            map?.get("profile_image").toString()
        )
    }),

    KAKAO("kakao", { attributes ->
        val content = attributes?.get("properties");
        val map: Map<String, Any>? = if (content is Map<*, *>) {
            content as? Map<String, Any>
        } else {
            emptyMap()
        }
        Oauth2UserInfo(
            map?.get("sub").toString(),
            map?.get("name").toString(),
            map?.get("account_email").toString(),
            map?.get("thumbnail_image").toString()
        )
    });

    companion object {
        fun extract(registrationId: String, attributes: Map<String, Any>?): Oauth2UserInfo? {
            return values().find { it.nameAttributeKey == registrationId }?.oauth2UserInfo?.invoke(attributes)
        }
    }
}