package com.example.springboot_java.domain;

import com.example.springboot_java.annotation.LogAOP;

public interface CalculateService {
    public Integer doAdd(String str1, String str2) throws Exception;

    public Integer doMinus(String str1, String str2) throws Exception;
}
