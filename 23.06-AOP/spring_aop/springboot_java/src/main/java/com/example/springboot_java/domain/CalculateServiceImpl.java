package com.example.springboot_java.domain;

import com.example.springboot_java.annotation.LogAOP;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CalculateServiceImpl implements CalculateService {

    @Override
    public Integer doAdd(String str1, String str2) throws Exception {
        return Integer.parseInt(str1) + Integer.parseInt(str2);
    }

    @LogAOP
    @Override
    public Integer doMinus(String str1, String str2) throws Exception {
        return Integer.parseInt(str1) - Integer.parseInt(str2);
    }

}
