package com.example.kotlin.itemReader.cursor

import jakarta.persistence.EntityManagerFactory
import org.slf4j.LoggerFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.database.JpaCursorItemReader
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

@Configuration
class JpaCursorItemReaderJobConfiguration {
    private val log = LoggerFactory.getLogger(this.javaClass)!!

    @Bean
    fun jpaCursorItemReader(entityManagerFactory: EntityManagerFactory): JpaCursorItemReader<Pay> =
        JpaCursorItemReaderBuilder<Pay>()
            .name("jpaCursorItemReader")
            .queryString("SELECT p FROM Pay p")
            .entityManagerFactory(entityManagerFactory)
            .build()

    @Bean
    fun jpaCursorItemWriter(): ItemWriter<Pay> =
        ItemWriter { items -> items.forEach { log.info("Current Pay={}", it.toString()) } }

    @Bean
    fun jpaCursorItemReaderStep(
        jobRepository: JobRepository,
        platformTransactionManager: PlatformTransactionManager,
        jpaCursorItemReader: JpaCursorItemReader<Pay>
    ): Step {
        log.info(">>>> jdbcCursorItemReaderStep")
        return StepBuilder("jdbcCursorItemReaderStep", jobRepository)
            .chunk<Pay, Pay>(10, platformTransactionManager)
            .reader(jpaCursorItemReader)
            .writer(jpaCursorItemWriter())
            .build()
    }

    @Bean
    fun jpaCursorItemReaderJob(jobRepository: JobRepository, jpaCursorItemReaderStep: Step): Job {
        log.info(">>>> jpaCursorItemReaderJob")
        return JobBuilder("jpaCursorItemReaderJob", jobRepository)
            .start(jpaCursorItemReaderStep)
            .build()
    }

}