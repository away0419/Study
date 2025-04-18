package com.example.java.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.java.api.code.OmMessage;
import com.example.java.api.dto.JobRequest;
import com.example.java.api.service.JobService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/job")
@RequiredArgsConstructor
public class JobController {
    private final JobService jobService;

    @PostMapping("/run")
    public ResponseEntity<String> runJob(@RequestBody JobRequest jobRequest) {
        jobService.runJob(jobRequest.getJobName());
        return ResponseEntity.ok(OmMessage.SPRING_BATCH_JOB_SUCCESS.getDesc());
    }
}
