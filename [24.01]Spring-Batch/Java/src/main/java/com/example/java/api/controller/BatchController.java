package com.example.java.api.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.java.api.service.BatchService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/batch")
@RequiredArgsConstructor
public class BatchController {
    private final BatchService batchService;

    @PostMapping("/{jobName}")
    public void batch(@PathVariable String jobName) throws Exception {
        batchService.runJob(jobName);
    }

    @PostMapping("/schedule")
    public ResponseEntity<String> scheduleJob(
        @RequestBody Map<String, String> jobData) throws Exception {
        try {
            batchService.scheduleJob(jobData.get("jobName"), jobData.get("cronExpression"));
            return ResponseEntity.ok("Quartz 스케줄 등록 완료");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Quartz 스케줄 등록 실패: " + e.getMessage());
        }
    }
}
