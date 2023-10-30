package com.example.kotlin.member.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MemberController {

    @GetMapping("/myspace")
    fun login() : String?{
        return "login"
    }

}