package com.example.java.batch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersValidator;
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
//                .incrementer(JobParametersIncrementer) // JobParameter 값을 자동으로 증가해 주는 설정
//                .preventRestart(true) // Job 재시작 가능 여부 설정. 기본은 true. false 설정 시, Job 실패해도 재시작 되지 않으며 재시작하려 하면 예외 발생.
//                .validator(JobParametersValidator) // JobParameter 검증 설정. 여기서 Exception 발생 시 Job 실행 안되므로 JobInstance, JobExecution 등이 생성되지 않음.
//                .listener(JobExecutionLister) // Job 라이프 사이클의 특정 시점에 콜백 제공받도록 하는 설정. JobParameter 변경없이 Job을 여러번 실행하고자 하거나, 파라미터 중 필요한 값을 증가시켜야할 때 사용.
                .build();
    }
}
