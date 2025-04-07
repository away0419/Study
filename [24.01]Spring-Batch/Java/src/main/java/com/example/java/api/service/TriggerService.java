package com.example.java.api.service;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.stereotype.Service;

import com.example.java.api.code.OmErrorMessage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TriggerService {
    private final Scheduler scheduler;

    // 트리거 등록
    public void createTrigger(String jobName, String triggerName, String cronExpression) {
        TriggerKey triggerKey = buildTriggerKey(jobName, triggerName);

        try {
            if (scheduler.checkExists(triggerKey)) {
                throw new RuntimeException(OmErrorMessage.TRIGGER_ALREADY_EXISTS.getDesc());
            }

            scheduler.scheduleJob(buildTrigger(jobName, triggerKey, cronExpression));
            log.info("트리거 등록 완료: {} / {}", triggerKey.getName(), cronExpression);
        } catch (SchedulerException e) {
            throw new RuntimeException(OmErrorMessage.TRIGGER_CREATION_FAILED.getDesc(), e);
        }
    }

    // 트리거 갱신
    public void updateTrigger(String jobName, String triggerName, String cronExpression) {
        TriggerKey triggerKey = buildTriggerKey(jobName, triggerName);

        try {
            if (!scheduler.checkExists(triggerKey)) {
                throw new RuntimeException(OmErrorMessage.TRIGGER_NOT_FOUND.getDesc());
            }

            scheduler.rescheduleJob(triggerKey, buildTrigger(jobName, triggerKey, cronExpression));
            log.info("트리거 갱신 완료: {} / {}", triggerKey.getName(), cronExpression);
        } catch (SchedulerException e) {
            throw new RuntimeException(OmErrorMessage.TRIGGER_UPDATE_FAILED.getDesc(), e);
        }
    }

    // 트리거 일시정지
    public void pauseTrigger(String jobName, String triggerName) {
        TriggerKey triggerKey = buildTriggerKey(jobName, triggerName);

        try {
            if (!scheduler.checkExists(triggerKey)) {
                throw new RuntimeException(OmErrorMessage.TRIGGER_NOT_FOUND.getDesc());
            }

            scheduler.pauseTrigger(triggerKey);
            log.info("트리거 정지 완료: {}", triggerKey.getName());
        } catch (SchedulerException e) {
            throw new RuntimeException(OmErrorMessage.TRIGGER_PAUSE_FAILED.getDesc(), e);
        }
    }

    // 트리거 재시작
    public void resumeTrigger(String jobName, String triggerName) {
        TriggerKey triggerKey = buildTriggerKey(jobName, triggerName);

        try {
            if (!scheduler.checkExists(triggerKey)) {
                throw new RuntimeException(OmErrorMessage.TRIGGER_NOT_FOUND.getDesc());
            }

            scheduler.resumeTrigger(triggerKey);
            log.info("트리거 재시작 완료: {}", triggerKey.getName());
        } catch (SchedulerException e) {
            throw new RuntimeException(OmErrorMessage.TRIGGER_RESUME_FAILED.getDesc(), e);
        }
    }

    // 트리거 삭제
    public void deleteTrigger(String jobName, String triggerName) {
        TriggerKey triggerKey = buildTriggerKey(jobName, triggerName);

        try {
            if (!scheduler.checkExists(triggerKey)) {
                throw new RuntimeException(OmErrorMessage.TRIGGER_NOT_FOUND.getDesc());
            }

            scheduler.unscheduleJob(triggerKey);
            log.info("트리거 삭제 완료: {}", triggerKey.getName());
        } catch (SchedulerException e) {
            throw new RuntimeException(OmErrorMessage.TRIGGER_DELETE_FAILED.getDesc(), e);
        }
    }

    // ──────────────────────────────────────────────────────────────
    // 공통 유틸 메서드
    // ──────────────────────────────────────────────────────────────

    private TriggerKey buildTriggerKey(String jobName, String triggerName) {
        return TriggerKey.triggerKey(String.format("%s-%s-Trigger", jobName, triggerName));
    }

    private Trigger buildTrigger(String jobName, TriggerKey triggerKey, String cronExpression) {
        return TriggerBuilder.newTrigger()
            .withIdentity(triggerKey)
            .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
            .forJob(JobKey.jobKey(jobName))
            .build();
    }
}
