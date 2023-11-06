package com.example.kotlin.security.exception


class SecurityCustomException(
    val securityCustomErrorCode: SecurityCustomErrorCode
) : RuntimeException(securityCustomErrorCode.msg)