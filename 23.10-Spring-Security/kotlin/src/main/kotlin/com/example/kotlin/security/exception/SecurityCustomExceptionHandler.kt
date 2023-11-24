package com.example.kotlin.security.exception

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class SecurityCustomExceptionHandler {

    /**
     * Controller에서 SecurityCustomException 발생한 경우 실행 되는 메소드
     * @param securityCustomException SecurityCustomException
     * @return ResponseEntity<Map<String, Any>>
     */
    @ExceptionHandler(SecurityCustomException::class)
    fun securityCustomException(securityCustomException: SecurityCustomException): ResponseEntity<Map<String, Any>> {
        // 출력 메시지 (Response 객체를 만들어 한번에 관리하는 것이 좋으나 넘어가겠음)
        val map = mapOf("code" to securityCustomException.securityCustomErrorCode.code,"msg" to securityCustomException.securityCustomErrorCode.msg)
        return ResponseEntity(map, securityCustomException.securityCustomErrorCode.httpStatus)
    }

}