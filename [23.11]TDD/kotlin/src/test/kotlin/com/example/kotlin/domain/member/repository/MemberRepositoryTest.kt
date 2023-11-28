package com.example.kotlin.domain.member.repository

import com.example.kotlin.domain.member.Member
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNot
import io.kotest.matchers.shouldNotHave
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import java.util.*

@DataJpaTest
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

                response shouldBe null
            }
        }
    }
})