package com.example.java.chunk;

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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Arrays;

@Slf4j
@Configuration
public class ChunkConfiguration {

    @Bean
    public ItemReader<String> reader() {
        return new ListItemReader<>(Arrays.asList("one", "two", "Three"));

    }

    @Bean
    public ItemProcessor<String, String> processor() {
        return String::toUpperCase;
    }

    @Bean
    public ItemWriter<String> writer() {
        return items -> items.forEach(log::info);
    }

    @Bean
    public Step chunkStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        log.info("chunk Step");
        return new StepBuilder("chunkStep", jobRepository)
                .<String, String>chunk(10, platformTransactionManager)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    public Job chunkJob(JobRepository jobRepository, Step chunkStep) {
        log.info(">>>> chunkJob");
        return new JobBuilder("chunkJob", jobRepository)
                .start(chunkStep)
                .build();
    }
}
