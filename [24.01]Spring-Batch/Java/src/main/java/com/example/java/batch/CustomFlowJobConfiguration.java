package com.example.java.batch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Random;

@Slf4j
@Configuration
public class CustomFlowJobConfiguration {
    @Bean
    public Tasklet customFlowTasklet(){
        return (contribution, chunkContext) -> {
            log.info(">>>> customFlowTaskLet");
            return RepeatStatus.FINISHED;
        };
    }

    @Bean
    public Step customFlowStep1(JobRepository jobRepository, Tasklet customFlowTasklet, PlatformTransactionManager platformTransactionManager){
        log.info(">>>> customFlowStep1");
        return new StepBuilder("customFlowStep1", jobRepository).tasklet(customFlowTasklet, platformTransactionManager).build();
    }

    @Bean
    public Step customFlowStep2(JobRepository jobRepository, Tasklet customFlowTasklet, PlatformTransactionManager platformTransactionManager){
        log.info(">>>> customFlowStep2");
        return new StepBuilder("customFlowStep2", jobRepository).tasklet(customFlowTasklet, platformTransactionManager).build();
    }

    @Bean
    public Step customFlowStep3(JobRepository jobRepository, Tasklet customFlowTasklet, PlatformTransactionManager platformTransactionManager) {
        log.info(">>>> customFlowStep3");
        return new StepBuilder("customFlowStep3", jobRepository).tasklet(customFlowTasklet, platformTransactionManager).build();
    }

    @Bean
    public JobExecutionDecider decider() {
        return new OddDecider();
    }

    public static class OddDecider implements JobExecutionDecider {
        @Override
        public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
            Random rand = new Random();

            int randomNumber = rand.nextInt(50) + 1;
            log.info("랜덤숫자: {}", randomNumber);

            if(randomNumber % 2 == 0) {
                return new FlowExecutionStatus("EVEN");
            } else {
                return new FlowExecutionStatus("ODD");
            }
        }
    }

    @Bean
    public Job customFlowJob(JobRepository jobRepository, Step customFlowStep1, Step customFlowStep2, Step customFlowStep3) {
        log.info(">>>> customFlowJob");
        return new JobBuilder("customFlowJob", jobRepository)
                .start(customFlowStep1)// step1 시작.
                .next(decider()) // step1 이후 decider 시작.
                .on("EVEN") // decider 결과가 EVEN 일 경우
                .to(customFlowStep2) // step2로 이동.
                .on("*") // step2의 (모든 결과)일 때
                .end() // customFlow 종료
                .from(decider()) // 만약 decider 결과가
                .on("ODD") // *(모든 결과) 일 경우
                .to(customFlowStep3) // step3으로 이동.
                .next(customFlowStep2) // step3 이후 step2로 이동.
                .on("*") // step2의 결과가 *(모든 결과) 일 경우
                .end() // customFlow 종료
                .end()// job 종료
                .build();
    }


}
