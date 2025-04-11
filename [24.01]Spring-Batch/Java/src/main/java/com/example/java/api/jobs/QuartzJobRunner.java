package com.example.java.api.jobs;

import java.util.UUID;

import org.quartz.JobExecutionContext;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.stereotype.Component;

import com.example.java.api.code.OmErrorMessage;

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
            log.info("jobName: {}", jobName);
            Job job = jobRegistry.getJob(jobName);

            JobParameters jobParameters = new JobParametersBuilder()
                .addString("runId", UUID.randomUUID().toString()) // 새로운 인스턴스 실행
                .toJobParameters();

            jobLauncher.run(job, jobParameters);
        } catch (NoSuchJobException e) {
            throw new RuntimeException(OmErrorMessage.SPRING_BATCH_JOB_BEAN_NOT_FOUND.getDesc());
        } catch (Exception e) {
            throw new RuntimeException(OmErrorMessage.QUARTZ_JOB_EXECUTION_ERROR.getDesc());
        }

    }
}