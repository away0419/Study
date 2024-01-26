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

@Slf4j
@Configuration
public class MultiJobConfiguration {

    @Bean
    public Tasklet multiTaskLet(){
        return (contribution, chunkContext) -> {
            log.info(">>>> multiTaskLet");
            return RepeatStatus.FINISHED;
        };
    }

    @Bean
    public Step multiStep1(JobRepository jobRepository, Tasklet multiTaskLet, PlatformTransactionManager platformTransactionManager){
        log.info(">>>> multiStep1");
        return new StepBuilder("multiStep1", jobRepository).tasklet(multiTaskLet, platformTransactionManager).build();
    }

    @Bean
    public Step multiStep2(JobRepository jobRepository, Tasklet multiTaskLet, PlatformTransactionManager platformTransactionManager){
        log.info(">>>> multiStep2");
        return new StepBuilder("multiStep2", jobRepository).tasklet(multiTaskLet, platformTransactionManager).build();
    }

    @Bean
    public Step multiStep3(JobRepository jobRepository, Tasklet multiTaskLet, PlatformTransactionManager platformTransactionManager) {
        log.info(">>>> multiStep3");
        return new StepBuilder("multiStep3", jobRepository).tasklet(multiTaskLet, platformTransactionManager).build();
    }

    @Bean
    public Job multiJob(JobRepository jobRepository, Step multiStep1, Step multiStep2, Step multiStep3) {
        log.info(">>>> multiJob");
        return new JobBuilder("multiJob", jobRepository)
                .start(multiStep1)
                .next(multiStep2)
                .next(multiStep3)
                .build();
    }
}
