package com.example.java.api.service;

import java.util.UUID;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Service;

import com.example.java.api.jobs.QuartzJobRunner;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BatchService {
    private final JobLauncher jobLauncher;
    private final JobRegistry jobRegistry;
    private final Scheduler scheduler;

    public void runJob(String jobName) throws Exception {
        Job job = jobRegistry.getJob(jobName);
        JobParameters jobParameters = new JobParametersBuilder()
            // .addLong("time", System.currentTimeMillis()) // Spring Batch는 내부적으로 밀리초 차이 정도는 동일한 Job Instance로 취급할 수 있으므로 UUID를 사용하자
            .addString("runId", UUID.randomUUID().toString()) // UUID 추가
            .toJobParameters();

        jobLauncher.run(job, jobParameters);
    }

    public void scheduleJob(String jobName, String cronExpression) throws Exception {
        JobDetail jobDetail = JobBuilder.newJob(QuartzJobRunner.class)
            .withIdentity(jobName)
            .usingJobData("jobName", jobName)
            .build();

        Trigger trigger = TriggerBuilder.newTrigger()
            .withIdentity(jobName + "Trigger")
            .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
            .build();

        scheduler.scheduleJob(jobDetail, trigger);
    }
}
