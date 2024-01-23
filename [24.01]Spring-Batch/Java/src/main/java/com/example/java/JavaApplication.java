package com.example.java;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
//@EnableAspectJAutoProxy // Batch 기능 활성화. Batch Config 클래스가 여러개 존재 한다면 Main에 적용하는 것이 좋음. (SpringBoot 3.0.0 이상 버전은 사용하면 에러 남)
public class JavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaApplication.class, args);
    }

}
