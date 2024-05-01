package com.example.java.batch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.MethodInvokingTaskletAdapter;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

// MethodInvokingTaskletAdapter 형식
@Slf4j
@Configuration
public class TaskLetJobConfig2 {

    @Bean
    public CustomService businessLogic() {
        return new CustomService();
    }

    @Bean
    public MethodInvokingTaskletAdapter myTasklet() {
        MethodInvokingTaskletAdapter adapter = new MethodInvokingTaskletAdapter();

        adapter.setTargetObject(businessLogic());
        adapter.setTargetMethod("businessLogic");

        return adapter;
    }

    @Bean
    public Step task2Step(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        log.info(">>>> task2Step");
        return new StepBuilder("task2Step", jobRepository).tasklet(myTasklet(), platformTransactionManager).build();
    }

    @Bean
    public Job task2Job(JobRepository jobRepository, Step task2Step) {
        log.info(">>>> task2Job");
        return new JobBuilder("task2Job", jobRepository)
                .start(task2Step)
                .build();
    }
}
