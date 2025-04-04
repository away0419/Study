package com.example.java.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.java.api.dto.ScheduleRequest;
import com.example.java.api.service.QuartzJobService;
import com.example.java.api.service.TriggerService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor
public class ScheduleController {
    private final QuartzJobService quartzJobService;
    private final TriggerService triggerService;

    // Job & Trigger 등록
    @PostMapping("/register")
    public ResponseEntity<String> registerScheduledJob(@RequestBody ScheduleRequest scheduleRequest) throws Exception {
        quartzJobService.createScheduledJob(scheduleRequest.getJobName()); // 등록 Job 없으면 새로 생성
        triggerService.createTrigger(scheduleRequest.getJobName(), scheduleRequest.getTriggerName(),
            scheduleRequest.getCronExpression()); // Trigger 등록
        return ResponseEntity.ok("Job & Trigger 등록");
    }

    // Job 정지
    @PostMapping("/job/pause")
    public ResponseEntity<String> pauseScheduledJob(@RequestBody ScheduleRequest scheduleRequest) throws Exception {
        quartzJobService.pauseScheduledJob(scheduleRequest);
        return ResponseEntity.ok("Job 정지 완료");
    }

    // Job 재시작
    @PostMapping("/job/resume")
    public ResponseEntity<String> resumeScheduledJob(@RequestBody ScheduleRequest scheduleRequest) throws Exception {
        quartzJobService.resumeScheduledJob(scheduleRequest);
        return ResponseEntity.ok("Job 재시작 완료");
    }

    // Job 삭제
    @DeleteMapping("/job/delete")
    public ResponseEntity<String> deleteScheduledJob(@RequestBody ScheduleRequest scheduleRequest) throws Exception {
        quartzJobService.deleteScheduledJob(scheduleRequest);
        return ResponseEntity.ok("Job 삭제 완료");
    }

    // Trigger 수정
    @PutMapping("/trigger/update")
    public ResponseEntity<String> updateScheduledTrigger(@RequestBody ScheduleRequest scheduleRequest) throws
        Exception {
        quartzJobService.validateJobExists(scheduleRequest.getJobName()); // 등록 Job 없으면 예외 발생
        triggerService.updateTrigger(scheduleRequest.getJobName(), scheduleRequest.getTriggerName(),
            scheduleRequest.getCronExpression()); // Trigger 수정
        return ResponseEntity.ok("Trigger 수정 완료");
    }

    // Trigger 정지
    @PostMapping("/trigger/pause")
    public ResponseEntity<String> pauseTrigger(@RequestBody ScheduleRequest scheduleRequest) throws Exception {
        quartzJobService.validateJobExists(scheduleRequest.getJobName()); // 등록 Job 없으면 예외 발생
        triggerService.pauseTrigger(scheduleRequest.getJobName(), scheduleRequest.getTriggerName()); // Trigger 정지
        return ResponseEntity.ok("Trigger 정지 완료");
    }

    // Trigger 재시작
    @PostMapping("/trigger/resume")
    public ResponseEntity<String> resumeTrigger(@RequestBody ScheduleRequest scheduleRequest) throws Exception {
        quartzJobService.validateJobExists(scheduleRequest.getJobName()); // 등록 Job 없으면 예외 발생
        triggerService.resumeTrigger(scheduleRequest.getJobName(), scheduleRequest.getTriggerName()); // Trigger 재시작
        return ResponseEntity.ok("Trigger 재시작 완료");
    }

    // Trigger 삭제
    @DeleteMapping("/trigger/delete")
    public ResponseEntity<String> deleteTrigger(@RequestBody ScheduleRequest scheduleRequest) throws Exception {
        quartzJobService.validateJobExists(scheduleRequest.getJobName()); // 등록 Job 없으면 예외 발생
        triggerService.deleteTrigger(scheduleRequest.getJobName(), scheduleRequest.getTriggerName()); // Trigger 삭제
        return ResponseEntity.ok("Trigger 삭제 완료");
    }

}
