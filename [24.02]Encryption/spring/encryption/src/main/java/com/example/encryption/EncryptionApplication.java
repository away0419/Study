package com.example.encryption;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@MapperScan("com.example.encryption.user.mapper")
public class EncryptionApplication {

    public static void main(String[] args) {
        SpringApplication.run(EncryptionApplication.class, args);
    }

}
