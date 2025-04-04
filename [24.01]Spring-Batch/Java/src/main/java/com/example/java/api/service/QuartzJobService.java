package com.example.java.api.service;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Service;

import com.example.java.api.dto.ScheduleRequest;
import com.example.java.api.jobs.QuartzJobRunner;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuartzJobService {

    private final Scheduler scheduler;

    // Job 삭제 (연결된 트리거 포함)
    public void deleteScheduledJob(ScheduleRequest scheduleRequest) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(scheduleRequest.getJobName());

        if (scheduler.checkExists(jobKey)) {
            scheduler.pauseJob(jobKey); // 정지
            boolean result = scheduler.deleteJob(jobKey);
            if (result) {
                log.info("Job 삭제 완료: {}", jobKey);
            } else {
                log.warn("Job 삭제 실패: {}", jobKey);
            }
        } else {
            log.warn("삭제하려는 Job이 존재하지 않음: {}", jobKey);
        }
    }

    // Job 정지 (연결된 트리거 포함)
    public void pauseScheduledJob(ScheduleRequest scheduleRequest) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(scheduleRequest.getJobName());

        if (scheduler.checkExists(jobKey)) {
            scheduler.pauseJob(jobKey);
            log.info("Job 정지: {}", scheduleRequest.getJobName());
        } else {
            log.warn("정지하려는 Job이 존재하지 않음: {}", scheduleRequest.getJobName());
        }
    }

    // Job 재시작 (연결된 트리거 포함)
    public void resumeScheduledJob(ScheduleRequest scheduleRequest) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(scheduleRequest.getJobName());

        if (scheduler.checkExists(jobKey)) {
            scheduler.resumeJob(jobKey);
            log.info("Job 재시작: {}", scheduleRequest.getJobName());
        } else {
            log.warn("재시작하려는 Job이 존재하지 않음: {}", scheduleRequest.getJobName());
        }
    }

    // Job 없으면 예외 발생
    public void validateJobExists(String jobName) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(jobName);
        if (!scheduler.checkExists(jobKey)) {
            throw new JobExecutionException("업데이트 실패: 등록되지 않은 Job -> " + jobName);
        }
    }

    // Job 등록
    public void createScheduledJob(String jobName) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(jobName);

        if (!scheduler.checkExists(jobKey)) {
            JobDetail jobDetail = JobBuilder.newJob(QuartzJobRunner.class)
                .withIdentity(jobKey)
                .usingJobData("jobName", jobName)
                .storeDurably()
                .build();

            scheduler.addJob(jobDetail, true);
            log.info("새로운 Job 등록: {}", jobName);
        }
    }
}
