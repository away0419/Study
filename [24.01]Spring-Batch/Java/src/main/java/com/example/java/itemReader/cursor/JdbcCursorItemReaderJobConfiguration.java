package com.example.java.itemReader.cursor;


import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Slf4j
@Configuration
public class JdbcCursorItemReaderJobConfiguration {

    private ItemWriter<Pay> jdbcCursorItemWriter() {
        return list -> {
            for (Pay pay :
                    list) {
                log.info("Current Pay={}", pay);
            }
        };
    }

    @Bean
    public JdbcCursorItemReader<Pay> jdbcCursorItemReader(DataSource dataSource) {
        return new JdbcCursorItemReaderBuilder<Pay>()
                .fetchSize(10) // DB에서 한번에 사져올 데이터 양. PagingSize랑은 다름.
                .dataSource(dataSource) // DB 객체
                .rowMapper(new BeanPropertyRowMapper<>(Pay.class)) // 쿼리 결과를 Java 인스턴스로 매핑하기 위한 Mapper
                .sql("SELECT id, amount, tx_name, tx_date_time FROM pay")
                .name("jdbcCursorItemReader") // Bean의 이름이 아니며 Spring Batch의 ExecutionContext에서 저장되어질 이름
                .build();
    }


    @Bean
    public Step jdbcCursorItemReaderStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager, JdbcCursorItemReader<Pay> jdbcCursorItemReader) {
        log.info(">>>> jdbcCursorItemReaderStep");
        return new StepBuilder("jdbcCursorItemReaderStep", jobRepository)
                .<Pay, Pay>chunk(10, platformTransactionManager)
                .reader(jdbcCursorItemReader)
//                .processor(processor())
                .writer(jdbcCursorItemWriter())
                .build();
    }

    @Bean
    public Job jdbcCursorItemReaderJob(JobRepository jobRepository, Step jdbcCursorItemReaderStep) {
        log.info(">>>> jdbcCursorItemReaderJob");
        return new JobBuilder("jdbcCursorItemReaderJob", jobRepository)
                .start(jdbcCursorItemReaderStep)
                .build();
    }
}



