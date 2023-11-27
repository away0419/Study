package com.example.kotlin.domain.member.controller

import com.example.kotlin.domain.member.service.MemberService
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class MemberControllerTest(
    @Mock
    val memberService : MemberService,

    @InjectMocks
    val memberController: MemberController,
) {

}