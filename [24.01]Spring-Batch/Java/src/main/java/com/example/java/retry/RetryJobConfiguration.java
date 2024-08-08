package com.example.java.retry;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.skip.LimitCheckingItemSkipPolicy;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Arrays;

@Configuration
@Slf4j
public class RetryJobConfiguration {
    @Bean
    public ItemReader<String> retryItemReader() {
        return new ListItemReader<>(Arrays.asList("one", "two", "Three"));

    }

    @Bean
    public ItemProcessor<String, String> retryItemProcessor() {
        log.info("itemProcessor");

        return String::toUpperCase;
    }

    @Bean
    public ItemWriter<String> retryItemWriter() {
        return items -> {
            items.forEach(item -> {
                log.info("retryItemWriter: " + item);
                if (item.contains("O")) {
                    log.info("예외발생: " + item);
                    throw new IllegalArgumentException("에러발생"); // writer에서 발생했으므로 chunk 다시 실행. 로그 확인하면 이해하기 쉬움
                }
            });
        };
    }

    @Bean
    public Step retryChunkStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        log.info("retryChunk Step");
        return new StepBuilder("retryChunkStep", jobRepository)
                .<String, String>chunk(10, platformTransactionManager)
                .reader(retryItemReader())
                .processor(retryItemProcessor())
                .writer(retryItemWriter())
                .faultTolerant() // 내결함성 기능 활성화
                .retry(IllegalArgumentException.class) // retry 하려는 예외 타입 설정
                .retryLimit(2) // retry 제한 횟수 설정
                .retryPolicy(new SimpleRetryPolicy()) // retry 어떤 조건과 기준으로 적용할 것인지 정책 설정. default: SimpleRetryPolicy
                .noRetry(NullPointerException.class) // retry 하지 않을 예외 타입 설정
                .noRollback(NullPointerException.class) // rollback 하지 않을 예외 타입 설정
                .build();
    }

    @Bean
    public Job retryChunkJob(JobRepository jobRepository, Step retryChunkStep) {
        log.info(">>>> retryChunkJob");
        return new JobBuilder("retryChunkJob", jobRepository)
                .start(retryChunkStep)
                .build();
    }

}
