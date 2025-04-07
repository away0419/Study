package com.example.java.api.service;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Service;

import com.example.java.api.code.OmErrorMessage;
import com.example.java.api.dto.ScheduleRequest;
import com.example.java.api.jobs.QuartzJobRunner;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuartzJobService {

    private final Scheduler scheduler;

    // Job 등록
    public void createScheduledJob(String jobName) {
        JobKey jobKey = JobKey.jobKey(jobName);

        try {
            if (scheduler.checkExists(jobKey)) {
                return;
            }

            JobDetail jobDetail = JobBuilder.newJob(QuartzJobRunner.class)
                .withIdentity(jobKey)
                .usingJobData("jobName", jobName)
                .storeDurably()
                .build();

            scheduler.addJob(jobDetail, true);
            log.info("Job 등록 완료: {}", jobName);
        } catch (SchedulerException e) {
            throw new RuntimeException(OmErrorMessage.QUARTZ_JOB_CREATION_FAILED.getDesc(), e);
        }
    }

    // Job 삭제 (트리거 포함)
    public void deleteScheduledJob(ScheduleRequest request) {
        JobKey jobKey = JobKey.jobKey(request.getJobName());

        try {
            validateQuartzJobExists(jobKey);
            scheduler.deleteJob(jobKey);
            log.info("Job 삭제 완료: {}", request.getJobName());
        } catch (SchedulerException e) {
            throw new RuntimeException(OmErrorMessage.QUARTZ_JOB_DELETE_FAILED.getDesc(), e);
        }
    }

    // Job 정지
    public void pauseScheduledJob(ScheduleRequest request) {
        JobKey jobKey = JobKey.jobKey(request.getJobName());

        try {
            validateQuartzJobExists(jobKey);
            scheduler.pauseJob(jobKey);
            log.info("Job 정지 완료: {}", request.getJobName());
        } catch (SchedulerException e) {
            throw new RuntimeException(OmErrorMessage.QUARTZ_JOB_PAUSE_FAILED.getDesc(), e);
        }
    }

    // Job 재시작
    public void resumeScheduledJob(ScheduleRequest request) {
        JobKey jobKey = JobKey.jobKey(request.getJobName());

        try {
            validateQuartzJobExists(jobKey);
            scheduler.resumeJob(jobKey);
            log.info("Job 재시작 완료: {}", request.getJobName());
        } catch (SchedulerException e) {
            throw new RuntimeException(OmErrorMessage.QUARTZ_JOB_RESUME_FAILED.getDesc(), e);
        }
    }

    // Job 존재 여부 검증 (외부 호출용)
    public void validateQuartzJobExists(String jobName) {
        validateQuartzJobExists(JobKey.jobKey(jobName));
    }

    // Job 존재 여부 검증 (내부 호출용)
    private void validateQuartzJobExists(JobKey jobKey) {
        try {
            if (!scheduler.checkExists(jobKey)) {
                throw new RuntimeException(OmErrorMessage.QUARTZ_JOB_NOT_FOUND.getDesc());
            }
        } catch (SchedulerException e) {
            throw new RuntimeException(OmErrorMessage.QUARTZ_JOB_CHECK_FAILED.getDesc(), e);
        }
    }
}
