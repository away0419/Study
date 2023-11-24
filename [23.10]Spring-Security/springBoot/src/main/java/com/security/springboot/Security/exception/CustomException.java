package com.security.springboot.Security.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException{
    private final CustomErrorCode customErrorCode;


    public CustomException(CustomErrorCode customErrorCode) {
        this.customErrorCode = customErrorCode;
    }

    public CustomException(String message, CustomErrorCode customErrorCode) {
        super(message);
        this.customErrorCode = customErrorCode;
    }
}
