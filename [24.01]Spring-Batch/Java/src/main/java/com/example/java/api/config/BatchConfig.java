package com.example.java.api.config;

import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class BatchConfig implements ApplicationRunner {
    private final JobRegistry jobRegistry;

    @Override
    public void run(ApplicationArguments args) {
        try {
            System.out.println("=== 등록된 Job 목록 ===");
            jobRegistry.getJobNames().forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}