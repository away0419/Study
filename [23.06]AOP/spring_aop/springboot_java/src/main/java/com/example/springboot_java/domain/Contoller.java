package com.example.springboot_java.domain;

import com.example.springboot_java.annotation.LogAOP;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class Contoller {
    private final CalculateService calculateService;

    @PostMapping("/add")
    public int add(@RequestBody Map<String,String> map) throws Exception {
        log.info("add");
        return calculateService.doAdd(map.get("str1"), map.get("str2"));
    }

    @PostMapping("/minus")
    public int minus(@RequestBody Map<String,String> map) throws Exception {
        log.info("minus");
        return calculateService.doMinus(map.get("str1"), map.get("str2"));
    }
}
