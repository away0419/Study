package com.example.java.api.jobs;

import java.util.UUID;

import org.quartz.JobExecutionContext;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class QuartzJobRunner implements org.quartz.Job {
    private final JobLauncher jobLauncher;
    private final JobRegistry jobRegistry;

    @Override
    public void execute(JobExecutionContext context) {
        try {
            String jobName = context.getJobDetail().getJobDataMap().getString("jobName");
            Job job = jobRegistry.getJob(jobName);
            if (job == null) {
                log.error("Quartz 실행 실패: 존재하지 않는 Job -> {}", jobName);
                return;
            }

            JobParameters jobParameters = new JobParametersBuilder()
                .addString("runId", UUID.randomUUID().toString()) // 실행 구분
                .toJobParameters();

            jobLauncher.run(job, jobParameters);
            log.info("Quartz 배치 실행 성공: {}", jobName);
        } catch (Exception e) {
            log.error("Quartz Job 실행 중 오류 발생", e);
        }
    }
}