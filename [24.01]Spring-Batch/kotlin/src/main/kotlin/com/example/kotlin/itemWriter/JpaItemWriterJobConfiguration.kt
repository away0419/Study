package com.example.kotlin.itemWriter

import com.example.kotlin.itemReader.cursor.Pay
import jakarta.persistence.EntityManagerFactory
import org.slf4j.LoggerFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.database.JdbcCursorItemReader
import org.springframework.batch.item.database.JpaItemWriter
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.BeanPropertyRowMapper
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource

@Configuration
class JpaItemWriterJobConfiguration {
    private val log = LoggerFactory.getLogger(this.javaClass)!!

    @Bean
    fun jpaItemWriter(entityManagerFactory: EntityManagerFactory): JpaItemWriter<Pay2> {
        val jpaItemWriter = JpaItemWriter<Pay2>()
        jpaItemWriter.setEntityManagerFactory(entityManagerFactory)
        return jpaItemWriter
    }

    @Bean
    fun jpaItemWriterProcessor(): ItemProcessor<Pay, Pay2> {
        return ItemProcessor { pay ->
            Pay2(pay.amount, pay.txName, pay.txDateTime)
        }
    }

    @Bean
    fun jpaItemWriterReader(dataSource: DataSource): JdbcCursorItemReader<Pay> =
        JdbcCursorItemReaderBuilder<Pay>()
            .fetchSize(10)
            .dataSource(dataSource)
            .rowMapper(BeanPropertyRowMapper(Pay::class.java))
            .sql("SELECT id, amount, tx_name, tx_date_time FROM pay")
            .name("jpaItemWriterReader")
            .build()

    @Bean
    fun jpaItemWriterStep(
        jobRepository: JobRepository,
        platformTransactionManager: PlatformTransactionManager,
        jpaItemWriterReader: JdbcCursorItemReader<Pay>,
        jpaItemWriterProcessor: ItemProcessor<Pay, Pay2>,
        jpaItemWriter: JpaItemWriter<Pay2>
    ): Step {
        log.info(">>>> jpaItemWriterStep")
        return StepBuilder("jpaItemWriterStep", jobRepository)
            .chunk<Pay, Pay2>(10, platformTransactionManager)
            .reader(jpaItemWriterReader)
            .processor(jpaItemWriterProcessor)
            .writer(jpaItemWriter)
            .build()
    }

    @Bean
    fun jpaItemWriterJob(jobRepository: JobRepository, jpaItemWriterStep: Step): Job {
        log.info(">>>> jpaItemWriterJob")
        return JobBuilder("jpaItemWriterJob", jobRepository)
            .start(jpaItemWriterStep)
            .build()
    }

}