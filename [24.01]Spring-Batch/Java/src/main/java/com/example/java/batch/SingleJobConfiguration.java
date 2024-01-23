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
@Configuration // Job @Configuration에 등록.
public class SingleJobConfiguration {
    @Bean
    public Tasklet singleTasklet() {
        return (contribution, chunkContext) -> {
            log.info(">>>> SingleTaskLet");
            return RepeatStatus.FINISHED;
        };
    }

    @Bean
    public Step singleStep(JobRepository jobRepository, Tasklet singleTasklet, PlatformTransactionManager platformTransactionManager) {
        log.info(">>>> SingleStep");
        return new StepBuilder("singleStep", jobRepository).tasklet(singleTasklet, platformTransactionManager).build();
    }

    @Bean
    public Job singleJob(JobRepository jobRepository, Step singleStep) {
        log.info(">>>> SingleJob");
        return new JobBuilder("singleJob", jobRepository)
                .start(singleStep)
                .build();
    }
}
