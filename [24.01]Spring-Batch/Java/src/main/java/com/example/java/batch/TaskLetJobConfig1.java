package com.example.java.batch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

// 람다 형식
@Slf4j
@Configuration
public class TaskLetJobConfig1 {

    @Bean
    public Step taskStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        log.info(">>>> SingleStep");
        return new StepBuilder("taskStep", jobRepository).tasklet(((contribution, chunkContext) -> {
            for(int i =0; i<10; i++){
                log.info(i+": 비즈니스 로직");
            }
            return RepeatStatus.FINISHED;
        }), platformTransactionManager).build();
    }

    @Bean
    public Job taskJob(JobRepository jobRepository, Step taskStep) {
        log.info(">>>> SingleJob");
        return new JobBuilder("taskJob", jobRepository)
                .start(taskStep)
                .build();
    }
}
