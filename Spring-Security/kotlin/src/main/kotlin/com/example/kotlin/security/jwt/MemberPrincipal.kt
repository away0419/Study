package com.example.kotlin.security.jwt

import com.example.kotlin.member.Member
import com.example.kotlin.member.Role
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

/**
 * Access token에 담고 싶은 정보들. 또는 JWT 로직 간 주고 받는 데이터를 가지는 객체.
 * @property id String?
 * @property name String?
 * @property email String?
 * @property picture String?
 * @property role String?
 * @constructor
 */
data class MemberPrincipal(
    // JsonProperty -> access token 정보 교환을 할 때 필요한 직렬화, 역직렬화 를 위한 어노테이션
    @JsonProperty("id") val id: String?,
    @JsonProperty("name") val name: String?,
    @JsonProperty("email") val email: String?,
    @JsonProperty("picture") val picture: String?,
    @JsonProperty("role") val role: String?
){
    constructor(member: Member) : this(
        id = member.id.toString(),
        name = member.name,
        email = member.email,
        picture = member.picture,
        role = member.role.toString()
    )
}