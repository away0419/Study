package com.example.kotlin.chunk

import org.slf4j.LoggerFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.ItemReader
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.support.ListItemReader
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager
import java.util.*

@Configuration
class ChunkConfiguration {
    private val log = LoggerFactory.getLogger(this.javaClass)!!


    @Bean
    fun reader(): ItemReader<String> = ListItemReader(listOf("one", "two", "three"))

    @Bean
    fun processor(): ItemProcessor<String, String> = ItemProcessor { it.uppercase(Locale.getDefault()) }

    @Bean
    fun writer(): ItemWriter<String> = ItemWriter { items -> items.forEach { log.info(it) } }

    @Bean
    fun chunkStep(
        jobRepository: JobRepository,
        platformTransactionManager: PlatformTransactionManager
    ): Step {
        log.info(">>>> chunkStep")
        return StepBuilder("chunkStep", jobRepository)
            .chunk<String, String>(10, platformTransactionManager)
            .reader(reader())
            .processor(processor())
            .writer(writer())
            .build()
    }

    @Bean
    fun chunkJob(jobRepository: JobRepository, chunkStep: Step): Job {
        log.info(">>>> chunkJob")
        return JobBuilder("chunkJob", jobRepository)
            .start(chunkStep)
            .build()
    }
}