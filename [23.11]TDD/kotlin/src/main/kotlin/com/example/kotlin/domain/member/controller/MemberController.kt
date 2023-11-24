package com.example.kotlin.domain.member.controller

import com.example.kotlin.domain.member.Member
import com.example.kotlin.domain.member.MemberDTO
import com.example.kotlin.domain.member.service.MemberService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
class MemberController(
    private val memberService: MemberService
) {
    @PostMapping("/save")
    fun memberSave(memberDTO: MemberDTO):Member{
        return memberService.save(memberDTO)
    }
    @GetMapping("/find/{id}")
    fun memberFindById(@PathVariable var id:UUID):Member?{
        return memberService.findById(id)
    }


}