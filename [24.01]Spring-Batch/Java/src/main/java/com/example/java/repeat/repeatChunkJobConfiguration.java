package com.example.java.repeat;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.batch.repeat.RepeatCallback;
import org.springframework.batch.repeat.RepeatContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.batch.repeat.policy.SimpleCompletionPolicy;
import org.springframework.batch.repeat.support.RepeatTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Arrays;

@Slf4j
@Configuration
public class repeatChunkJobConfiguration {

    @Bean
    public ItemReader<String> repeatItemReader() {
        return new ListItemReader<>(Arrays.asList("one", "two", "Three"));

    }

    @Bean
    public ItemProcessor<String, String> repeatItemProcessor() {
        RepeatTemplate repeatTemplate = new RepeatTemplate();
        repeatTemplate.setCompletionPolicy(new SimpleCompletionPolicy(3));
        repeatTemplate.iterate(repeatCallBack -> {
            log.info("repeatChunk Test");
            return RepeatStatus.CONTINUABLE;
        });
        log.info("itemProcessor");

        return String::toUpperCase;
    }

    @Bean
    public ItemWriter<String> repeatItemWriter() {
        return items -> items.forEach(log::info);
    }

    @Bean
    public Step repeatChunkStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        log.info("chunk Step");
        return new StepBuilder("chunkStep", jobRepository)
                .<String, String>chunk(10, platformTransactionManager)
                .reader(repeatItemReader())
                .processor(repeatItemProcessor())
                .writer(repeatItemWriter())
                .build();
    }

    @Bean
    public Job repeatChunkJob(JobRepository jobRepository, Step repeatChunkStep) {
        log.info(">>>> repeatChunkJob");
        return new JobBuilder("repeatChunkJob", jobRepository)
                .start(repeatChunkStep)
                .build();
    }

}
