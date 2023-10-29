package com.security.springboot.Security.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CustomErrorCode {
    AUTH_HEADER_NULL(401, "인증 헤더가 없음."),

    USER_INFO_NULL(401, "유저 정보가 없음."),
    USER_ROLE_NULL(401, "유저 권한 정보가 없음."),

    TOKEN_NULL(401, "토큰이 없음."),
    TOKEN_SIGNATURE(401, "토큰 서명 오류."),
    TOKEN_MALFORMED(401, "토큰 형식 오류."),
    TOKEN_TYPE_WRONG(401, "토큰 타입 불일치"),
    TOKEN_UNSUPPORTED(401, "지원 하지 않는 토큰"),
    TOKEN_EXPIRED(401, "토큰 기간 만료"),
    TOKEN_ILLEGALARGUMENT(401, "잘못된 토큰");

    private int state;
    private String message;
}
