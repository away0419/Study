package com.example.java.itemReader.cursor;


import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class JpaPagingItemReaderJobConfiguration {
    private ItemWriter<Pay> jpaPagingItemWriter() {
        return list -> {
            for (Pay pay :
                    list) {
                log.info("Current Pay={}", pay);
            }
        };
    }

    @Bean
    public JpaPagingItemReader<Pay> jpaPagingItemReader(EntityManagerFactory entityManagerFactory) {
        return new JpaPagingItemReaderBuilder<Pay>()
                .name("jpaPagingItemReader")
                .pageSize(10)
                .queryString("SELECT p FROM Pay p")
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

    @Bean
    public Step jpaPagingItemReaderStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager, JpaPagingItemReader<Pay> jpaPagingItemReader) {
        log.info(">>>> jpaPagingItemReaderStep");
        return new StepBuilder("jpaPagingItemReaderStep", jobRepository)
                .<Pay, Pay>chunk(10, platformTransactionManager)
                .reader(jpaPagingItemReader)
//                .processor(processor())
                .writer(jpaPagingItemWriter())
                .build();
    }

    @Bean
    public Job jpaPagingItemReaderJob(JobRepository jobRepository, Step jpaPagingItemReaderStep) {
        log.info(">>>> jpaPagingItemReaderJob");
        return new JobBuilder("jpaPagingItemReaderJob", jobRepository)
                .start(jpaPagingItemReaderStep)
                .build();
    }
}
