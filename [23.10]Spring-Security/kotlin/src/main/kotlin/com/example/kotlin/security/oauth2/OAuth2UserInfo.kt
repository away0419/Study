package com.example.kotlin.security.oauth2

import com.example.kotlin.member.Member
import com.example.kotlin.member.Role

class OAuth2UserInfo(
    val name: String,
    val email: String,
    val picture: String?,
    val oAuth2Key : String,
    val oAuth2Provider : String
){
    fun toEntity(): Member {
        return Member(id=null, name=name, email=email, picture=picture, oAuth2Key = oAuth2Key, oAuth2Provider=oAuth2Provider, role = Role.ROLE_USER)
    }
}