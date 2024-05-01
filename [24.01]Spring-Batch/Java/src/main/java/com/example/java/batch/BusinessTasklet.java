package com.example.java.batch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

@Slf4j
public class BusinessTasklet implements Tasklet, StepExecutionListener {

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

        for(int i =0; i<10; i++){
            log.info(i+": 비즈니스 로직");
        }

        return RepeatStatus.FINISHED;

    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        log.info("Before Step");
        StepExecutionListener.super.beforeStep(stepExecution);
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        log.info("after Step");
        return StepExecutionListener.super.afterStep(stepExecution);
    }
}
