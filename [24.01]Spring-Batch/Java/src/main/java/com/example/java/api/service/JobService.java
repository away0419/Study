package com.example.java.api.service;

import java.util.UUID;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
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
    public void runJob(String jobName) {
        try {
            Job job = jobRegistry.getJob(jobName);
            JobParameters jobParameters = new JobParametersBuilder()
                .addString("runId", UUID.randomUUID().toString())
                .toJobParameters();

            jobLauncher.run(job, jobParameters);
            log.info("Job 실행 완료: {}", jobName);

        } catch (NoSuchJobException e) {
            log.error("존재하지 않는 Job: {}", jobName, e);
            throw new RuntimeException("존재하지 않는 Job입니다: " + jobName, e);

        } catch (JobExecutionAlreadyRunningException |
                 JobRestartException |
                 JobInstanceAlreadyCompleteException |
                 JobParametersInvalidException e) {
            log.error("Job 실행 중 예외 발생: {}", jobName, e);
            throw new RuntimeException("Job 실행 중 예외 발생: " + jobName, e);
        }
    }
}
