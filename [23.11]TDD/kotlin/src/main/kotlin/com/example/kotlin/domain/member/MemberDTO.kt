package com.example.kotlin.domain.member

import java.util.*

class MemberDTO(
    var id: UUID= UUID.randomUUID(),
    var name: String,
    var email: String,
) {
    fun toEntity(): Member {
        return Member(id, name, email)
    }
}