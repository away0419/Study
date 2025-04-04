package com.example.java.api.service;

import java.util.UUID;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class JobService {
    private final JobLauncher jobLauncher;
    private final JobRegistry jobRegistry;

    // Job 단일 실행
    public void runJob(String jobName) throws Exception {
        Job job = jobRegistry.getJob(jobName);
        if (job == null) {
            log.error("Job 실행 실패: 존재하지 않는 Job -> {}", jobName);
            return;
        }
        JobParameters jobParameters = new JobParametersBuilder()
            .addString("runId", UUID.randomUUID().toString())
            .toJobParameters();

        jobLauncher.run(job, jobParameters);
    }

}
