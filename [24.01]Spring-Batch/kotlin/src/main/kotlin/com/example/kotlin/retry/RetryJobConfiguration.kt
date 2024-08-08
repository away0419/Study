package com.example.kotlin.retry

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
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.retry.policy.SimpleRetryPolicy
import org.springframework.transaction.PlatformTransactionManager
import java.util.*

@Configuration
class RetryJobConfiguration {
    private val log = LoggerFactory.getLogger(this.javaClass)!!


    @Bean
    fun retryItemReader(): ItemReader<String> = ListItemReader(listOf("one", "two", "three"))

    @Bean
    fun retryItemProcessor(): ItemProcessor<String, String> = ItemProcessor {
        log.info("retryProcessor")
        it.uppercase(Locale.getDefault())
    }

    @Bean
    fun retryItemWriter(): ItemWriter<String> = ItemWriter { items ->
        items.forEach {
            log.info(it)
            if (it.contains("O")) {
                log.info("retryping item writer:{}", it)
                throw IllegalArgumentException("에러발생")
            }
        }
    }

    @Bean
    fun retryStep(
        jobRepository: JobRepository,
        platformTransactionManager: PlatformTransactionManager
    ): Step {
        log.info(">>>> retryStep")
        return StepBuilder("retryStep", jobRepository)
            .chunk<String, String>(10, platformTransactionManager)
            .reader(retryItemReader())
            .processor(retryItemProcessor())
            .writer(retryItemWriter())
            .faultTolerant() // 내결함성 기능 활성화
            .retry(IllegalArgumentException::class.java) // retry 하려는 예외 타입 설정
            .retryLimit(2) // retry 제한 횟수 설정
            .retryPolicy(SimpleRetryPolicy()) // retry 어떤 조건과 기준으로 적용할 것인지 정책 설정. default: SimpleRetryPolicy
            .noRetry(NullPointerException::class.java) // retry 하지 않을 예외 타입 설정
            .noRollback(NullPointerException::class.java) // rollback 하지 않을 예외 타입 설정
            .build()
    }

    @Bean
    fun retryJob(jobRepository: JobRepository, retryStep: Step): Job {
        log.info(">>>> retryJob")
        return JobBuilder("retryJob", jobRepository)
            .start(retryStep)
            .build()
    }

}