package com.example.kotlin.domain.member.repository

import com.example.kotlin.domain.member.Member
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import java.util.*

@DataJpaTest(properties = ["spring.config.location=classpath:application-test.yaml"])
//@TestPropertySource(properties = ["spring.config.location=classpath:application-test.yaml"])
internal class MemberRepositoryTest(
    @Autowired
    private val memberRepository: MemberRepository
) : DescribeSpec({
    describe("MemberRepository") {
        context("회원 가입") {
            it("성공") {
                val member = Member(name = "홍길동", email = "hong@gmail.com")
                val response = memberRepository.save(member)

                member.email shouldBe response.email
                member.name shouldBe response.name
            }
        }

        context("회원 ID로 조회할 때") {
            it("찾지 못한 경우 null을 반환한다.") {
                val response = memberRepository.findMemberById(UUID.randomUUID())

                response.shouldBeNull()
            }

            it("찾은 경우 member 반환한다."){
                val request = UUID.fromString("4a9e5e6b-0b68-4eaa-9e38-53590a0332d4")
                val response = Member(UUID.fromString("4a9e5e6b-0b68-4eaa-9e38-53590a0332d4"), "홍길동", "hong@gmail.com")

                val result = memberRepository.findMemberById(request)

                result?.id shouldBe response.id
                result?.name shouldBe response.name
                result?.email shouldBe response.email
            }
        }
    }
})