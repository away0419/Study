package com.example.kotlin.security.jwt

import com.example.kotlin.member.Member
import com.example.kotlin.member.Role
import java.util.*


class MemberPrincipal(
    val id: UUID?,
    val name: String?,
    val email: String?,
    val picture: String?,
    val role: Role?
){
    constructor(member: Member) : this(
        id = member.id,
        name = member.name,
        email = member.email,
        picture = member.picture,
        role = member.role
    )

    override fun toString(): String {
        return "MemberPrincipal(id=$id, name=$name, email=$email, picture=$picture, role=$role)"
    }

}