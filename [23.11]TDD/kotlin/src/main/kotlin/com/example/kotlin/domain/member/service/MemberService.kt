package com.example.kotlin.domain.member.service

import com.example.kotlin.domain.member.Member
import com.example.kotlin.domain.member.MemberDTO
import com.example.kotlin.domain.member.repository.MemberRepository
import org.springframework.stereotype.Service

@Service

class MemberService(
    private val memberRepository: MemberRepository
) {
    fun save(memberDTO: MemberDTO): Member {
        return memberRepository.save(memberDTO.toEntity())
    }

    fun findById(memberDTO: MemberDTO): Member? {
        return memberRepository.findMemberById(memberDTO.id)
    }
}