package com.example.java.batch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.MethodInvokingTaskletAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

// 외부 클래스에 Tasklet 구현
@Slf4j
@Configuration
public class TaskLetJobConfig3 {

    @Bean
    public Step task3Step(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        log.info(">>>> task3Step");
        return new StepBuilder("task3Step", jobRepository).tasklet(new BusinessTasklet(), platformTransactionManager).build();
    }

    @Bean
    public Job task3Job(JobRepository jobRepository, Step task3Step) {
        log.info(">>>> task3Job");
        return new JobBuilder("task3Job", jobRepository)
                .start(task3Step)
                .build();
    }
}
