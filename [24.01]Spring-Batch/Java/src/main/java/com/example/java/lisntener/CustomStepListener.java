package com.example.java.lisntener;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomStepListener {
    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
      log.info("@Before step: " + stepExecution.getStepName());
    }

    @AfterStep
    public void afterStep(StepExecution stepExecution) {
        log.info("@After step: " + stepExecution.getStepName());
    }
}
