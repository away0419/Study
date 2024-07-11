package com.example.java.itemWriter;


import com.example.java.itemReader.cursor.Pay;
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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
public class JpaItemWriterJobConfiguration {

    @Bean
    public JpaItemWriter<Pay2> jpaItemWriter(EntityManagerFactory entityManagerFactory) {
        JpaItemWriter<Pay2> jpaItemWriter = new JpaItemWriter<>();
        jpaItemWriter.setEntityManagerFactory(entityManagerFactory);
        return jpaItemWriter;
    }

    @Bean
    public ItemProcessor<Pay, Pay2> jpaItemWriterProcessor() {
        return pay -> new Pay2(pay.getAmount(), pay.getTxName(), pay.getTxDateTime());
    }

    @Bean
    public JpaPagingItemReader<Pay> jpaItemWriterPagingItemReader(EntityManagerFactory entityManagerFactory) {
        return new JpaPagingItemReaderBuilder<Pay>()
                .name("jpaItemWriterPagingItemReader")
                .pageSize(10)
                .queryString("SELECT p FROM Pay p")
                .entityManagerFactory(entityManagerFactory)
                .build();
    }


    @Bean
    public Step jpaItemWriterStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager, JpaPagingItemReader<Pay> jpaItemWriterPagingItemReader, JpaItemWriter<Pay2> jpaItemWriter, ItemProcessor<Pay, Pay2> jpaItemWriterProcessor) {
        log.info(">>>> jpaItemWriterStep");
        return new StepBuilder("jpaItemWriterStep", jobRepository)
                .<Pay, Pay2>chunk(10, platformTransactionManager)
                .reader(jpaItemWriterPagingItemReader)
                .processor(jpaItemWriterProcessor)
                .writer(jpaItemWriter)
                .build();
    }

    @Bean
    public Job jpaItemWriterJob(JobRepository jobRepository, Step jpaItemWriterStep) {
        log.info(">>>> jpaItemWriterJob");
        return new JobBuilder("jpaItemWriterJob", jobRepository)
                .start(jpaItemWriterStep)
                .build();
    }

}
