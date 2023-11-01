package com.example.kotlin.member

enum class Role(
    val key: String,
    val title: String
) {
    ROLE_ADMIN("ADMIN", "관리자"),
    ROLE_USER("USER", "사용자")
}