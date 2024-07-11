package com.example.java.itemProcessor;

import com.example.java.itemReader.cursor.Pay;
import com.example.java.itemWriter.Pay2;
import jakarta.persistence.EntityManagerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Configuration
public class compositeItemProcessorJobConfiguration {
    @Bean
    public JpaItemWriter<Pay2> compositeItemProcessorWriter(EntityManagerFactory entityManagerFactory) {
        JpaItemWriter<Pay2> jpaItemWriter = new JpaItemWriter<>();
        jpaItemWriter.setEntityManagerFactory(entityManagerFactory);
        return jpaItemWriter;
    }

    public ItemProcessor<Pay, Pay2> compositeItemProcessor1() {
        return pay -> {
            log.info("Processor1 실행");
            return new Pay2(pay.getAmount(), pay.getTxName(), pay.getTxDateTime());
        };
    }

    public ItemProcessor<Pay2, Pay2> compositeItemProcessor2() {
        return item -> {
            log.info("processor2 실행");
            return item;
        };
    }

    @Bean
    public CompositeItemProcessor compositeItemProcessor() {
        CompositeItemProcessor processor = new CompositeItemProcessor<>();
        List<ItemProcessor> delegates = new ArrayList<>();

        delegates.add(compositeItemProcessor1());
        delegates.add(compositeItemProcessor2());
        processor.setDelegates(delegates);

        return processor;
    }

    @Bean
    public JpaPagingItemReader<Pay> compositeItemProcessorReader(EntityManagerFactory entityManagerFactory) {
        return new JpaPagingItemReaderBuilder<Pay>()
                .name("compositeItemProcessorReader")
                .pageSize(10)
                .queryString("SELECT p FROM Pay p")
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

    @Bean
    public Step compositeItemProcessorStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager, JpaPagingItemReader<Pay> compositeItemProcessorReader, JpaItemWriter<Pay2> compositeItemProcessorWriter, CompositeItemProcessor compositeItemProcessor) {
        log.info(">>>> compositeItemProcessorStep");
        return new StepBuilder("compositeItemProcessorStep", jobRepository)
                .<Pay, Pay2>chunk(10, platformTransactionManager)
                .reader(compositeItemProcessorReader)
                .processor(compositeItemProcessor)
                .writer(compositeItemProcessorWriter)
                .build();
    }

    @Bean
    public Job compositeItemProcessorJob(JobRepository jobRepository, Step compositeItemProcessorStep) {
        log.info(">>>> compositeItemProcessorJob");
        return new JobBuilder("compositeItemProcessorJob", jobRepository)
                .start(compositeItemProcessorStep)
                .build();
    }
}
