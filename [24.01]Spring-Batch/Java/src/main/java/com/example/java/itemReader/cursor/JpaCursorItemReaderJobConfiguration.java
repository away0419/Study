package com.example.java.itemReader.cursor;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Slf4j
@Configuration
public class JpaCursorItemReaderJobConfiguration {

    private ItemWriter<Pay> jpaCursorItemWriter() {
        return list -> {
            for (Pay pay :
                    list) {
                log.info("Current Pay={}", pay);
            }
        };
    }

    @Bean
    public JpaCursorItemReader<Pay> jpaCursorItemReader(EntityManagerFactory entityManagerFactory) {
        return new JpaCursorItemReaderBuilder<Pay>()
                .name("jpaCursorItemReader") // Bean의 이름이 아니며 Spring Batch의 ExecutionContext에서 저장되어질 이름
                .queryString("SELECT id, amount, tx_name, tx_date_time FROM pay") // 조회 시 사용할 JPQL 문장 설정.
                .entityManagerFactory(entityManagerFactory) // JPQL 실행을 위한 EntityManager 생성 팩토리.
//                .parameterValues(Map<String,Object> parameters) // 쿼리 파라미터 설정
//                .maxItemCount(int count) // 조회 할 최대 Item 수
//                .currentItemCount(int count) // 조회 Item 시작 지점.
                .build();
    }


    @Bean
    public Step jpaCursorItemReaderStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager, JpaCursorItemReader<Pay> jpaCursorItemReader) {
        log.info(">>>> jpaCursorItemReaderStep");
        return new StepBuilder("jpaCursorItemReaderStep", jobRepository)
                .<Pay, Pay>chunk(10, platformTransactionManager)
                .reader(jpaCursorItemReader)
//                .processor(processor())
                .writer(jpaCursorItemWriter())
                .build();
    }

    @Bean
    public Job jpaCursorItemReaderJob(JobRepository jobRepository, Step jdbcCursorItemReaderStep) {
        log.info(">>>> jpaCursorItemReaderJob");
        return new JobBuilder("jpaCursorItemReaderJob", jobRepository)
                .start(jdbcCursorItemReaderStep)
                .build();
    }

}
