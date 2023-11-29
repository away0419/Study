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
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
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
        // DB에 연결하지 않았음에도 통과하는 이유는 다음과 같다.
        // spyk 이므로 실제 기능을 가져온다. 이때, 실제 기능은 DB가 연결 되어 있어야만 동작한다.
        // DB가 연결 되지 않은 현재, 실제 기능이 없는 null이 되어버린다.
        // 즉, stubbing 하지 않고 memberService.findByid()를 한다면 memberRepository 가 null이므로 null을 반환한다.
        // 현재는 service 자체만 확인하면 되기 때문에 굳이 db를 연결할 필요없다. 어차피 순서상 repository test를 먼저하기 때문이다.

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