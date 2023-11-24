package com.example.kotlin.security.handler

import com.example.kotlin.common.logger
import com.example.kotlin.security.exception.SecurityCustomException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.json.simple.JSONObject
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerExceptionResolver

/**
 * 주로 인증 단계에서 발생하는 AuthenticationException 처리
 * @property resolver HandlerExceptionResolver
 * @property log Logger
 * @constructor
 */
@Component
class SecurityCustomAuthenticationEntryPoint(
    @Qualifier("handlerExceptionResolver")
    private val resolver: HandlerExceptionResolver // Spring Boot가 기본적으로 가지고 있는 것. 해당 예외를 어떤 핸들러에 보낼 것인지 판단함.
) : AuthenticationEntryPoint {
    private val log = logger()

    override fun commence(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authException: AuthenticationException?
    ) {
        log.info("==========SecurityCustomAuthenticationEntryPoint==========")

        // 개발자가 만든 오류가 발생 했는지 확인.
        val securityCustomException = request?.getAttribute("securityCustomException") as? SecurityCustomException

        // 없을 경우 개발자가 예상치 못한 예외.
        if(securityCustomException == null){
            val jsonObject: JSONObject // response로 내보려는 정보를 담은 Json 객체
            val responseMap = HashMap<String, Any>() // response 할 데이터를 담기 위한 맵

            responseMap["msg"] = "알 수 없는 오류 발생."
            responseMap["code"] = "S---"
            jsonObject = JSONObject(responseMap)

            response?.characterEncoding = "UTF-8"
            response?.contentType = "application/json"
            response?.status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR

            val printWriter = response?.writer
            printWriter?.print(jsonObject)
            printWriter?.flush()
            printWriter?.close()
            return
        }

        // 개발자가 만든 예외 상황인 경우 예외 위임.
        resolver.resolveException(request, response!!, null, securityCustomException)
    }
}