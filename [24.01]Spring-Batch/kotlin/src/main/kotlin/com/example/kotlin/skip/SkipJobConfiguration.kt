package com.example.kotlin.skip

import org.slf4j.LoggerFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.core.step.skip.LimitCheckingItemSkipPolicy
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.ItemReader
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.support.ListItemReader
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager
import java.util.*

@Configuration
class SkipJobConfiguration {

    private val log = LoggerFactory.getLogger(this.javaClass)!!


    @Bean
    fun skipItemReader(): ItemReader<String> = ListItemReader(listOf("one", "two", "three"))

    @Bean
    fun skipItemProcessor(): ItemProcessor<String, String> = ItemProcessor {
        log.info("skipProcessor")
        it.uppercase(Locale.getDefault())
    }

    @Bean
    fun skipItemWriter(): ItemWriter<String> = ItemWriter { items ->
        items.forEach {
            log.info(it)
            if (it.contains("O")) {
                log.info("Skipping item writer:{}", it)
                throw IllegalArgumentException("에러발생")
            }
        }
    }

    @Bean
    fun skipStep(
        jobRepository: JobRepository,
        platformTransactionManager: PlatformTransactionManager
    ): Step {
        log.info(">>>> skipStep")
        return StepBuilder("skipStep", jobRepository)
            .chunk<String, String>(10, platformTransactionManager)
            .reader(skipItemReader())
            .processor(skipItemProcessor())
            .writer(skipItemWriter())
            .faultTolerant() // 내결함성 기능 활성화
            .skip(IllegalArgumentException::class.java) // skip 하려는 예외 타입 설정
            .skipLimit(2) // skip 제한 횟수 설정
            .skipPolicy(LimitCheckingItemSkipPolicy()) // skip을 어떤 조건과 기준으로 적용할 것인지 정책 설정. default: LimitCheckingItemSkipPolicy
            .noSkip(NullPointerException::class.java) // skip 하지 않을 예외 타입 설정
            .build()
    }

    @Bean
    fun skipJob(jobRepository: JobRepository, skipStep: Step): Job {
        log.info(">>>> skipJob")
        return JobBuilder("skipJob", jobRepository)
            .start(skipStep)
            .build()
    }

}