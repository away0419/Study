package com.example.kotlin.itemProcessor

import com.example.kotlin.itemReader.cursor.Pay
import com.example.kotlin.itemWriter.Pay2
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
import org.springframework.batch.item.support.CompositeItemProcessor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.BeanPropertyRowMapper
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource

@Configuration
class CompositeItemProcessorJobConfiguration {
    private val log = LoggerFactory.getLogger(this.javaClass)!!

    @Bean
    fun compositeItemProcessorWriter(entityManagerFactory: EntityManagerFactory): JpaItemWriter<Pay2> {
        val jpaItemWriter = JpaItemWriter<Pay2>()
        jpaItemWriter.setEntityManagerFactory(entityManagerFactory)
        return jpaItemWriter
    }

    fun compositeItemProcessor1(): ItemProcessor<Pay, Pay2> {
        return ItemProcessor { pay ->
            log.info("processor1 실행")
            Pay2(pay.amount, pay.txName, pay.txDateTime)
        }
    }

    fun compositeItemProcessor2(): ItemProcessor<Pay2, Pay2> {
        return ItemProcessor { pay ->
            log.info("processor2 실행")
            pay
        }
    }

    @Bean
    fun compositeItemProcessor(): CompositeItemProcessor<Any, Any> {
        val processor = CompositeItemProcessor<Any, Any>()
        processor.setDelegates(listOf(compositeItemProcessor1(), compositeItemProcessor2()))
        return processor
    }

    @Bean
    fun compositeItemProcessorReader(dataSource: DataSource): JdbcCursorItemReader<Pay> =
        JdbcCursorItemReaderBuilder<Pay>()
            .fetchSize(10)
            .dataSource(dataSource)
            .rowMapper(BeanPropertyRowMapper(Pay::class.java))
            .sql("SELECT id, amount, tx_name, tx_date_time FROM pay")
            .name("compositeItemProcessorReader")
            .build()

    @Bean
    fun compositeItemProcessorStep(
        jobRepository: JobRepository,
        platformTransactionManager: PlatformTransactionManager,
        compositeItemProcessorReader: JdbcCursorItemReader<Pay>,
        compositeItemProcessor: CompositeItemProcessor<Any, Any>,
        compositeItemProcessorWriter: JpaItemWriter<Pay2>
    ): Step {
        log.info(">>>> compositeItemProcessorStep")
        return StepBuilder("compositeItemProcessorStep", jobRepository)
            .chunk<Any, Any>(10, platformTransactionManager)
            .reader(compositeItemProcessorReader)
            .processor(compositeItemProcessor)
            .writer(compositeItemProcessorWriter as JpaItemWriter<Any>)
            .build()
    }

    @Bean
    fun compositeItemProcessorJob(jobRepository: JobRepository, compositeItemProcessorStep: Step): Job {
        log.info(">>>> compositeItemProcessorJob")
        return JobBuilder("compositeItemProcessorJob", jobRepository)
            .start(compositeItemProcessorStep)
            .build()
    }
}