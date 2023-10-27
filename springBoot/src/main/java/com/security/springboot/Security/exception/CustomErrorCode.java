package com.security.springboot.Security.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CustomErrorCode {
    TOKEN_NULL(401, "Token is null"),
    AUTH_HEADER_NULL(401, "Auth-Header is null"),
    TOKEN_INVALID(401, "Token is invalid"),
    USER_INFO_NULL(401, "UserInfo is null");



    private int state;
    private String message;
}
