package com.example.kotlin.itemReader.cursor

import jakarta.persistence.EntityManagerFactory
import org.slf4j.LoggerFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.database.JpaPagingItemReader
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

@Configuration
class JpaPagingItemReaderJobConfiguration {
    private val log = LoggerFactory.getLogger(this.javaClass)!!

    @Bean
    fun jpaPagingItemReader(entityManagerFactory: EntityManagerFactory): JpaPagingItemReader<Pay> =
        JpaPagingItemReaderBuilder<Pay>()
            .name("jpaPagingItemReader")
            .pageSize(10)
            .queryString("SELECT p FROM Pay p")
            .entityManagerFactory(entityManagerFactory)
            .build()

    @Bean
    fun jpaPagingItemWriter(): ItemWriter<Pay> =
        ItemWriter { items -> items.forEach { log.info("Current Pay={}", it.toString()) } }

    @Bean
    fun jpaPagingItemReaderStep(
        jobRepository: JobRepository,
        platformTransactionManager: PlatformTransactionManager,
        jpaPagingItemReader: JpaPagingItemReader<Pay>
    ): Step {
        log.info(">>>> jpaPagingItemReaderStep")
        return StepBuilder("jpaPagingItemReaderStep", jobRepository)
            .chunk<Pay, Pay>(10, platformTransactionManager)
            .reader(jpaPagingItemReader)
            .writer(jpaPagingItemWriter())
            .build()
    }

    @Bean
    fun jpaPagingItemReaderJob(jobRepository: JobRepository, jpaPagingItemReaderStep: Step): Job {
        log.info(">>>> jpaPagingItemReaderJob")
        return JobBuilder("jpaPagingItemReaderJob", jobRepository)
            .start(jpaPagingItemReaderStep)
            .build()
    }
}