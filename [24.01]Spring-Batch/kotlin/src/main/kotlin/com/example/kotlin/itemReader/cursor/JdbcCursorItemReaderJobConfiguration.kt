package com.example.kotlin.itemReader.cursor

import org.slf4j.LoggerFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.database.JdbcCursorItemReader
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.BeanPropertyRowMapper
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource

@Configuration
class JdbcCursorItemReaderJobConfiguration {
    private val log = LoggerFactory.getLogger(this.javaClass)!!

    @Bean
    fun jdbcCursorItemReader(dataSource: DataSource): JdbcCursorItemReader<Pay> = JdbcCursorItemReaderBuilder<Pay>()
        .fetchSize(10)
        .dataSource(dataSource)
        .rowMapper(BeanPropertyRowMapper(Pay::class.java))
        .sql("SELECT id, amount, tx_name, tx_date_time FROM pay")
        .name("jdbcCursorItemReader")
        .build()

    @Bean
    fun jdbcCursorItemWriter(): ItemWriter<Pay> =
        ItemWriter { items -> items.forEach { log.info("Current com.example.kotlin.itemReader.cursor.Pay={}", it.toString()) } }

    @Bean
    fun jdbcCursorItemReaderStep(
        jobRepository: JobRepository,
        platformTransactionManager: PlatformTransactionManager,
        jdbcCursorItemReader: JdbcCursorItemReader<Pay>
    ): Step {
        log.info(">>>> jdbcCursorItemReaderStep")
        return StepBuilder("jdbcCursorItemReaderStep", jobRepository)
            .chunk<Pay, Pay>(10, platformTransactionManager)
            .reader(jdbcCursorItemReader)
            .writer(jdbcCursorItemWriter())
            .build()
    }

    @Bean
    fun jdbcCursorItemReaderJob(jobRepository: JobRepository, jdbcCursorItemReaderStep: Step): Job {
        log.info(">>>> jdbcCursorItemReaderJob")
        return JobBuilder("jdbcCursorItemReaderJob", jobRepository)
            .start(jdbcCursorItemReaderStep)
            .build()
    }

}