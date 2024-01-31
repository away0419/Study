package com.example.java.batch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
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
public class FlowJobConfiguration {
    @Bean
    public Tasklet flowTasklet(){
        return (contribution, chunkContext) -> {
            log.info(">>>> flowTaskLet");
            contribution.setExitStatus(ExitStatus.FAILED); // Step 실행 후 상태. 기본적으로 BatchStatus와 ExitStatus 값이 동일하도록 설정되어 있음.
            return RepeatStatus.FINISHED;
        };
    }

    @Bean
    public Step flowStep1(JobRepository jobRepository, Tasklet flowTasklet, PlatformTransactionManager platformTransactionManager){
        log.info(">>>> flowStep1");
        return new StepBuilder("flowStep1", jobRepository).tasklet(flowTasklet, platformTransactionManager).build();
    }

    @Bean
    public Step flowStep2(JobRepository jobRepository, Tasklet flowTasklet, PlatformTransactionManager platformTransactionManager){
        log.info(">>>> flowStep2");
        return new StepBuilder("flowStep2", jobRepository).tasklet(flowTasklet, platformTransactionManager).build();
    }

    @Bean
    public Step flowStep3(JobRepository jobRepository, Tasklet flowTasklet, PlatformTransactionManager platformTransactionManager) {
        log.info(">>>> flowStep3");
        return new StepBuilder("flowStep3", jobRepository).tasklet(flowTasklet, platformTransactionManager).build();
    }

    @Bean
    public Job flowJob(JobRepository jobRepository, Step flowStep1, Step flowStep2, Step flowStep3) {
        log.info(">>>> flowJob");
        return new JobBuilder("flowJob", jobRepository)
                .start(flowStep1)
                    .on("FAILED") // FAILED 일 경우 -> 이때 FAILED BatchStatus 결과가 아니라 ExitStatus 결과 값임.
                    .to(flowStep2) // step2로 이동.
                    .on("*") // step2의 (모든 결과)일 때
                    .end() // flow 종료
                .from(flowStep1) // 만약 step1의 결과가
                    .on("*") // *(모든 결과) 일 경우 -> 위에서 이미 FAILED Flow가 있어 걸러지므로 결국 FAILED 제외한 결과로 볼 수 있음.
                    .to(flowStep3) // step3으로 이동.
                    .next(flowStep2) // step3 이후 step2로 이동.
                    .on("*") // step2의 결과가 *(모든 결과) 일 경우
                    .end() // flow 종료
                .end()// job 종료
                .build();
    }


}
