package com.example.java.itemWriter;

import com.example.java.itemReader.cursor.Pay;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Slf4j
@Configuration
public class JdbcBatchItemWriterJobConfiguration {

    @Bean
    public JdbcBatchItemWriter<Pay> jdbcBatchItemWriter(DataSource dataSource){
        return new JdbcBatchItemWriterBuilder<Pay>()
                .dataSource(dataSource)
                .sql("insert into pay2(amount, tx_name, tx_date_time) values (:amount, :txName, :txDateTime)")
                .beanMapped() // Pojo 기반으로 SQL values 매핑. 사용시 @Bean 등록 필수
//                .columnMapped() // MAP<Key, Value> 기반으로 SQL values 매핑.
//                .assertUpdates(boolean) // 트랜잭션 이후 적어도 하나의 항목이 변하지 않을 경우 예외 발생여부를 설정함. 기본값은 true
                .build();
    }

    @Bean
    public JdbcCursorItemReader<Pay> jdbcBatchItemReader(DataSource dataSource) {
        return new JdbcCursorItemReaderBuilder<Pay>()
                .name("jdbcCursorItemReader") // Bean의 이름이 아니며 Spring Batch의 ExecutionContext에서 저장되어질 이름
                .fetchSize(10) // DB에서 한번에 가져올 데이터 양. PagingSize랑은 다름.
                .dataSource(dataSource) // DB 객체
                .rowMapper(new BeanPropertyRowMapper<>(Pay.class)) // 쿼리 결과를 Java 인스턴스로 매핑하기 위한 Mapper
                .sql("SELECT id, amount, tx_name, tx_date_time FROM pay")
                .build();
    }


    @Bean
    public Step jdbcBatchItemWriterStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager, JdbcCursorItemReader<Pay> jdbcBatchItemReader, JdbcBatchItemWriter<Pay> jdbcBatchItemWriter) {
        log.info(">>>> jdbcBatchItemWriterStep");
        return new StepBuilder("jdbcBatchItemWriterStep", jobRepository)
                .<Pay, Pay>chunk(10, platformTransactionManager)
                .reader(jdbcBatchItemReader)
//                .processor(processor())
                .writer(jdbcBatchItemWriter)
                .build();
    }

    @Bean
    public Job jdbcBatchItemWriterJob(JobRepository jobRepository, Step jdbcBatchItemWriterStep) {
        log.info(">>>> jdbcBatchItemWriterJob");
        return new JobBuilder("jdbcBatchItemWriterJob", jobRepository)
                .start(jdbcBatchItemWriterStep)
                .build();
    }

}
