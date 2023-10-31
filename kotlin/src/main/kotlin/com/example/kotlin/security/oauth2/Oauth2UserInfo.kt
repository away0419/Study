package com.example.kotlin.security.oauth2

import com.example.kotlin.member.Member
import com.example.kotlin.member.Role

class Oauth2UserInfo(
    val id: String?,
    val name: String?,
    val email: String?,
    val picture: String?
){
    fun toEntity(): Member {
        return Member(id=null, name=name, email=email, picture=picture, role = Role.ROLE_USER)
    }
}