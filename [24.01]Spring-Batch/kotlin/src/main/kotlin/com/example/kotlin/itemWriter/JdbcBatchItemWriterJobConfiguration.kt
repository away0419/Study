package com.example.kotlin.itemWriter

import com.example.kotlin.itemReader.cursor.Pay
import org.slf4j.LoggerFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.database.JdbcBatchItemWriter
import org.springframework.batch.item.database.JdbcCursorItemReader
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.BeanPropertyRowMapper
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource

@Configuration
class JdbcBatchItemWriterJobConfiguration {
    private val log = LoggerFactory.getLogger(this.javaClass)!!

    @Bean
    fun jdbcBatchItemWriterReader(dataSource: DataSource): JdbcCursorItemReader<Pay> =
        JdbcCursorItemReaderBuilder<Pay>()
            .fetchSize(10)
            .dataSource(dataSource)
            .rowMapper(BeanPropertyRowMapper(Pay::class.java))
            .sql("SELECT id, amount, tx_name, tx_date_time FROM pay")
            .name("jdbcBatchItemWriterReader")
            .build()

    @Bean
    fun jdbcBatchItemWriter(dataSource: DataSource): JdbcBatchItemWriter<Pay> {
        return JdbcBatchItemWriterBuilder<Pay>()
            .dataSource(dataSource)
            .sql("insert into pay2(amount, tx_name, tx_date_time) values (:amount, :txName, :txDateTime)")
            .beanMapped() // Pojo 기반으로 SQL values 매핑. 사용시 @Bean 등록 필수
//                .columnMapped() // MAP<Key, Value> 기반으로 SQL values 매핑.
//                .assertUpdates(boolean) // 트랜잭션 이후 적어도 하나의 항목이 변하지 않을 경우 예외 발생여부를 설정함. 기본값은 true
            .build();
    }

    @Bean
    fun jdbcBatchItemWriterStep(
        jobRepository: JobRepository,
        platformTransactionManager: PlatformTransactionManager,
        jdbcCursorItemReader: JdbcCursorItemReader<Pay>,
        jdbcBatchItemWriter: JdbcBatchItemWriter<Pay>
    ): Step {
        log.info(">>>> jdbcBatchItemWriterStep")
        return StepBuilder("jdbcBatchItemWriterStep", jobRepository)
            .chunk<Pay, Pay>(10, platformTransactionManager)
            .reader(jdbcCursorItemReader)
            .writer(jdbcBatchItemWriter)
            .build()
    }

    @Bean
    fun jdbcBatchItemWriterJob(jobRepository: JobRepository, jdbcBatchItemWriterStep: Step): Job {
        log.info(">>>> jdbcBatchItemWriterJob")
        return JobBuilder("jdbcBatchItemWriterJob", jobRepository)
            .start(jdbcBatchItemWriterStep)
            .build()
    }
}