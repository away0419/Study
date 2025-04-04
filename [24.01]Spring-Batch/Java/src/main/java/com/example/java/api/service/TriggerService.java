package com.example.java.api.service;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TriggerService {
    private final Scheduler scheduler;

    // 트리거 없으면 등록
    public void createTrigger(String jobName, String triggerName, String cronExpression) throws SchedulerException {
        TriggerKey triggerKey = buildTriggerKey(jobName, triggerName);

        if (!scheduler.checkExists(triggerKey)) {
            scheduler.scheduleJob(buildTrigger(jobName, triggerKey, cronExpression));
            log.info("새로운 트리거 등록: {} / {}", jobName, cronExpression);
        } else {
            log.info("이미 존재하는 트리거");
        }
    }

    // 트리거 있으면 업데이트
    public void updateTrigger(String jobName, String triggerName, String cronExpression) throws SchedulerException {
        TriggerKey triggerKey = buildTriggerKey(jobName, triggerName);

        if (scheduler.checkExists(triggerKey)) {
            scheduler.pauseTrigger(triggerKey);
            scheduler.rescheduleJob(triggerKey, buildTrigger(jobName, triggerKey, cronExpression));
            log.info("기존 트리거 갱신: {} / {}", jobName, cronExpression);
        } else {
            log.warn("등록 하려는 트리거가 존재하지 않음: {}", triggerKey);
        }
    }

    // 트리거(쿼츠 스케줄러) 정지
    public void pauseTrigger(String jobName, String triggerName) throws SchedulerException {
        TriggerKey triggerKey = buildTriggerKey(jobName, triggerName);
        if (scheduler.checkExists(triggerKey)) {
            scheduler.pauseTrigger(triggerKey);
            log.info("트리거 일시정지: {}", triggerKey);
        } else {
            log.warn("정지 하려는 트리거가 존재하지 않음: {}", triggerKey);
        }
    }

    // 트리거(쿼츠 스케줄러) 재시작
    public void resumeTrigger(String jobName, String triggerName) throws SchedulerException {
        TriggerKey triggerKey = buildTriggerKey(jobName, triggerName);
        if (scheduler.checkExists(triggerKey)) {
            scheduler.resumeTrigger(triggerKey);
            log.info("트리거 다시 활성화: {}", triggerKey);
        } else {
            log.warn("변경 하려는 트리거가 존재하지 않음: {}", triggerKey);
        }
    }

    // 트리거(쿼츠 스케줄러) 삭제
    public void deleteTrigger(String jobName, String triggerName) throws SchedulerException {
        TriggerKey triggerKey = buildTriggerKey(jobName, triggerName);
        if (scheduler.checkExists(triggerKey)) {
            scheduler.pauseTrigger(triggerKey);
            boolean result = scheduler.unscheduleJob(triggerKey);
            if (result) {
                log.info("트리거 삭제 완료: {}", triggerKey);
            } else {
                log.warn("트리거 삭제 실패: {}", triggerKey);
            }
        } else {
            log.warn("삭제 하려는 트리거가 존재하지 않음: {}", triggerKey);
        }
    }

    // ──────────────────────────────────────────────────────────────
    // 공통 유틸 메서드
    // ──────────────────────────────────────────────────────────────

    // triggerName 생성 로직을 별도의 메소드로 분리
    private TriggerKey buildTriggerKey(String jobName, String triggerName) {
        return TriggerKey.triggerKey(String.format("%s-%s-Trigger", jobName, triggerName));
    }

    // 트리거 객체 생성 공통 메서드
    private Trigger buildTrigger(String jobName, TriggerKey triggerKey, String cronExpression) {
        return TriggerBuilder.newTrigger()
            .withIdentity(triggerKey)
            .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
            .forJob(JobKey.jobKey(jobName))
            .build();
    }
}
