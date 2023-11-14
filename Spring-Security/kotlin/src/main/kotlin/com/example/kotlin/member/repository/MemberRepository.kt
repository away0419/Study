package com.example.kotlin.member.repository

import com.example.kotlin.member.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long> {
    fun findByoAuth2Key(oAuth2Key: String): Member?
}