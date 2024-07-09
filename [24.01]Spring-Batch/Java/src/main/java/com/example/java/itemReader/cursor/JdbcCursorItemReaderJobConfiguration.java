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
                .name("jdbcCursorItemReader") // Bean의 이름이 아니며 Spring Batch의 ExecutionContext에서 저장되어질 이름
                .fetchSize(10) // DB에서 한번에 가져올 데이터 양. PagingSize랑은 다름.
                .dataSource(dataSource) // DB 객체
                .rowMapper(new BeanPropertyRowMapper<>(Pay.class)) // 쿼리 결과를 Java 인스턴스로 매핑하기 위한 Mapper
                .sql("SELECT id, amount, tx_name, tx_date_time FROM pay")
//                .beanRowMapper(Class<T>) // 별도의 rowMapper 설정하지 않을 때, 클래스 타입을 설정하면 자동으로 객체와 매핑.
//                .queryArguments(Object... args) // 쿼리 파라미터 설정. sql에서 ? 있는 경우 해당 순서에 맞는 곳에 자동으로 들어감.
//                .maxItemCount(int count) // 조회 할 최대 Item 수
//                .currentItemCount(int conunt) // 조회 Item의 시작 지점
//                .maxRows(int maxRows) // ResultSet 오브젝트가 포함 할 수 있는 최대 행 수
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



