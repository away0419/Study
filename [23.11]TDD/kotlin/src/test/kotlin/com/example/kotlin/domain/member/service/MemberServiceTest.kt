package com.example.kotlin.domain.member.service

import com.example.kotlin.domain.member.Member
import com.example.kotlin.domain.member.MemberDTO
import com.example.kotlin.domain.member.repository.MemberRepository
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import org.hibernate.annotations.processing.SQL
import org.springframework.test.context.TestPropertySource
import java.util.UUID

@TestPropertySource(properties = ["spring.config.location=classpath:application-test.yaml"])
internal class MemberServiceTest: DescribeSpec({
    val memberRepository = spyk<MemberRepository>()
    val memberService = MemberService(memberRepository)

    context("회원 가입"){
        it("성공"){
            val request = MemberDTO(email = "hong.gmail@com", name = "홍길동")
            val response = Member(email = "hong.gmail@com", name = "홍길동")

            every { memberRepository.save(ofType(Member::class)) } returns response
            val result = memberService.save(request)

            result.name shouldBe response.name
            result.email shouldBe response.email
        }
    }

    context("회원 찾기"){
        it("없다면 null 반환"){
            val request = UUID.randomUUID()

            val result = memberService.findById(request)

            result?.shouldBeNull()
        }

        it("멤버가 있다면 찾은 멤버 반환"){
            val request = UUID.fromString("4a9e5e6b-0b68-4eaa-9e38-53590a0332d4")
            val response = Member(UUID.fromString("4a9e5e6b-0b68-4eaa-9e38-53590a0332d4"), "홍길동", "hong@gmail.com")

            every { memberRepository.findMemberById(ofType(UUID::class)) } returns response
            val result = memberService.findById(request)

            result?.id shouldBe response.id
            result?.name shouldBe response.name
            result?.email shouldBe response.email
        } // 동일한 context 안에서 spyk<>()가 공유 되므로, 아래에 있는 다른 it도  stubbing 적용 됨을 주의 하자.

    }
})