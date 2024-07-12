package com.example.java.repeat;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.batch.repeat.policy.SimpleCompletionPolicy;
import org.springframework.batch.repeat.support.RepeatTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
public class repeatJobConfiguration {
    public Tasklet repeatBusinessTasklet() {
        return (contribution, chunkContext) -> {
            log.info("businessTasklet 실행");

            return RepeatStatus.CONTINUABLE;
        };
    }

    @Bean
    public Tasklet repeatTasklet() {
        return (contribution, chunkContext) -> {
            log.info(">>>> repeatTasklet");
            RepeatTemplate repeatTemplate = new RepeatTemplate();
            repeatTemplate.setCompletionPolicy(new SimpleCompletionPolicy(3));
            repeatTemplate.iterate((repeatCallBack) -> repeatBusinessTasklet().execute(contribution, chunkContext));

            return RepeatStatus.FINISHED;
        };
    }

    @Bean
    public Step repeatStep(JobRepository jobRepository, Tasklet repeatTasklet, PlatformTransactionManager platformTransactionManager) {
        log.info(">>>> repeatStep");
        return new StepBuilder("repeatStep", jobRepository)
                .tasklet(repeatTasklet, platformTransactionManager)
                .build();
    }

    @Bean
    public Job repeatJob(JobRepository jobRepository, Step repeatStep) {
        log.info(">>>> repeatJob");
        return new JobBuilder("repeatJob", jobRepository)
                .start(repeatStep)
                .build();
    }
}
