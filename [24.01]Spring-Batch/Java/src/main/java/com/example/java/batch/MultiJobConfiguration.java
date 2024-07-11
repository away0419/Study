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
        return new StepBuilder("multiStep3", jobRepository).tasklet(multiTaskLet, platformTransactionManager)
//                .startLimit() // Step의 실행 횟수. 기본값 Integer.MAX_VALUE. 실행 횟수 초과해서 다시 실행하려하는 경우 예외 발생.
//                .allowStartIfComplete() // 재시작 가능한 Job에서 Step 이전 성공 여부와 상관없이 항상 실행하기 위한 설정. True여야 항상 실행. 실행마다 유효성 검증이 필요하거나 사전 작업이 필요한 Step일 경우 사용.
                .build();
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
