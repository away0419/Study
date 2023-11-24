package com.security.springboot.Security.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class CustomExcptionHandler {

    @ExceptionHandler(value = CustomException.class)
    public ResponseEntity<Map<String,String>> securityErrorHandler(CustomException exception){
        Map<String,String> response = new HashMap<>();
        response.put("msg",exception.getCustomErrorCode().getMessage());
        response.put("status", String.valueOf(exception.getCustomErrorCode().getState()));
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(exception.getCustomErrorCode().getState()));
    }
}
