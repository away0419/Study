package com.example.kotlin.domain.member.repository

import com.example.kotlin.domain.member.Member
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface MemberRepository: JpaRepository<Member,UUID> {
    fun findMemberById(id: UUID): Member?
    fun save(member:Member) : Member

}