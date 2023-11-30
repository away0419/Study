package com.example.kotlin.domain.member.controller

import com.example.kotlin.domain.member.Member
import com.example.kotlin.domain.member.MemberDTO
import com.example.kotlin.domain.member.service.MemberService
import com.fasterxml.jackson.databind.ObjectMapper
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.spring.SpringExtension
import io.mockk.every
import io.mockk.mockk
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.util.*

internal class MemberControllerTest : DescribeSpec({
    val memberService = mockk<MemberService>()
    val memberController = MemberController(memberService)
    val mockMvc: MockMvc = MockMvcBuilders.standaloneSetup(memberController).build()

    extensions(SpringExtension)

    describe("MemberControllerTest") {
        context("회원 가입 요청") {
            it("email, name 주어진 경우") {
                // Given
                val request = MemberDTO(email = "hong.gmail@com", name = "홍길동")
                val response = Member(UUID.fromString("4a9e5e6b-0b68-4eaa-9e38-53590a0332d4"), "홍길동", "hong@gmail.com")

                every { memberService.save(ofType(MemberDTO::class)) } returns response

                // When
                val result = mockMvc.post("/save") {
                    contentType = MediaType.APPLICATION_JSON
                    content = ObjectMapper().writeValueAsString(request)
                }

                // Then
                result.andExpect {
                    status { isOk() }
                    jsonPath("id") { exists() }
                    jsonPath("name") { exists() }
                    jsonPath("email") { exists() }
                }
            }
        }

        context("회원 조회") {
            it("찾는 유저가 없는 경우") {

                // Given
                val request = "4a9e5e6b-0b68-4eaa-9e38-53590a0332d4"

                every { memberService.findById(ofType(UUID::class)) } returns null

                // When
                val result = mockMvc.get("/find/$request") {
                    contentType = MediaType.APPLICATION_JSON
                }

                // Then
                result.andExpect {
                    status { isOk() }
                }
            }

            it("찾는 유저가 있는 경우") {

                // Given
                val request = "4a9e5e6b-0b68-4eaa-9e38-53590a0332d4"
                val response = Member(UUID.fromString("4a9e5e6b-0b68-4eaa-9e38-53590a0332d4"), "홍길동", "hong@gmail.com")

                every { memberService.findById(ofType(UUID::class)) } returns response

                // When
                val result = mockMvc.get("/find/$request") {
                    contentType = MediaType.APPLICATION_JSON
                }

                // Then
                result.andExpect {
                    status { isOk() }
                    jsonPath("id") { exists() }
                    jsonPath("name") { exists() }
                    jsonPath("email") { exists() }
                }
            }
        }
    }
})
