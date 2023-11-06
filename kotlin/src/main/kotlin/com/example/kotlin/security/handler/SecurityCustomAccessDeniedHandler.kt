package com.example.kotlin.security.handler

import com.example.kotlin.common.logger
import com.example.kotlin.security.exception.SecurityCustomErrorCode
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.json.simple.JSONObject
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component

/**
 * access token 인증은 되었으나 접근 권한이 없어 발생하는 AccessDeniedException 여기서 처리함.
 * @property log Logger
 */
@Component
class SecurityCustomAccessDeniedHandler () : AccessDeniedHandler{
    private val log = logger();

    override fun handle(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        accessDeniedException: AccessDeniedException?
    ) {
        log.info("=========AccessDeniedHandler===========")

        val jsonObject: JSONObject // response로 내보려는 정보를 담은 Json 객체
        val responseMap = HashMap<String?, Any?>() // response 할 데이터를 담기 위한 맵

        responseMap["msg"] = SecurityCustomErrorCode.JWT_TOKEN_ACCESS_DENIED.msg
        responseMap["code"] = SecurityCustomErrorCode.JWT_TOKEN_ACCESS_DENIED.code
        jsonObject = JSONObject(responseMap)

        response?.characterEncoding = "UTF-8"
        response?.contentType = "application/json"
        response?.status = SecurityCustomErrorCode.JWT_TOKEN_ACCESS_DENIED.httpStatus.value()

        val printWriter = response?.writer
        printWriter?.print(jsonObject)
        printWriter?.flush()
        printWriter?.close()

    }
}