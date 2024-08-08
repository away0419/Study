package com.example.java.skip;


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
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Arrays;

@Slf4j
@Configuration
public class skipJobConfiguration {
    @Bean
    public ItemReader<String> skipItemReader() {
        return new ListItemReader<>(Arrays.asList("one", "two", "Three"));

    }

    @Bean
    public ItemProcessor<String, String> skipItemProcessor() {
        log.info("itemProcessor");

        return String::toUpperCase;
    }

    @Bean
    public ItemWriter<String> skipItemWriter() {
        return items -> {
            items.forEach(item -> {
                log.info("skipItemWriter: " + item);
                if (item.contains("O")) {
                    log.info("예외발생: " + item);
                    throw new IllegalArgumentException("에러발생"); // writer에서 발생했으므로 chunk 다시 실행. 로그 확인하면 이해하기 쉬움
                }
            });
        };
    }

    @Bean
    public Step skipChunkStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        log.info("chunk Step");
        return new StepBuilder("chunkStep", jobRepository)
                .<String, String>chunk(10, platformTransactionManager)
                .reader(skipItemReader())
                .processor(skipItemProcessor())
                .writer(skipItemWriter())
                .faultTolerant() // 내결함성 기능 활성화
                .skip(IllegalArgumentException.class) // skip 하려는 예외 타입 설정
                .skipLimit(2) // skip 제한 횟수 설정
                .skipPolicy(new LimitCheckingItemSkipPolicy()) // skip을 어떤 조건과 기준으로 적용할 것인지 정책 설정. default: LimitCheckingItemSkipPolicy
                .noSkip(NullPointerException.class) // skip 하지 않을 예외 타입 설정
                .build();
    }

    @Bean
    public Job skipChunkJob(JobRepository jobRepository, Step skipChunkStep) {
        log.info(">>>> skipChunkJob");
        return new JobBuilder("skipChunkJob", jobRepository)
                .start(skipChunkStep)
                .build();
    }

}
