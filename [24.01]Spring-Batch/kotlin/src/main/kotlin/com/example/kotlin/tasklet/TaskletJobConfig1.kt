package com.example.kotlin.tasklet

import org.slf4j.LoggerFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

@Configuration
class TaskletJobConfig1 {
    private val log = LoggerFactory.getLogger(this.javaClass)!!

    @Bean
    fun taskletStep(
        jobRepository: JobRepository,
        platformTransactionManager: PlatformTransactionManager
    ): Step {
        log.info(">>>> taskletStep1")
        return StepBuilder("flowStep1", jobRepository).tasklet({ contribution, chunkContext ->
            for (i in 0..10) {
                log.info("람다식 ${i}번째 비지니스 로직");
            }
            RepeatStatus.FINISHED
        }, platformTransactionManager).build()
    }

    @Bean
    fun taskletJob(jobRepository: JobRepository, taskletStep: Step): Job {
        log.info(">>>> taskletJob1")
        return JobBuilder("taskletJob1", jobRepository)
            .start(taskletStep)
            .build()
    }

}