package com.example.kotlin.tasklet

import org.slf4j.LoggerFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

@Configuration
class TaskletJobConfig3 {
    private val log = LoggerFactory.getLogger(this.javaClass)!!

    @Bean
    fun customTasklet(): CustomTasklet{
        return CustomTasklet()
    }

    @Bean
    fun taskletStep3(
        jobRepository: JobRepository,
        platformTransactionManager: PlatformTransactionManager,
        customTasklet: CustomTasklet
    ): Step {
        log.info(">>>> taskletStep3")
        return StepBuilder("flowStep1", jobRepository).tasklet(customTasklet, platformTransactionManager).build()
    }

    @Bean
    fun taskletJob3(jobRepository: JobRepository, taskletStep3: Step): Job {
        log.info(">>>> taskletJob3")
        return JobBuilder("taskletJob3", jobRepository)
            .start(taskletStep3)
            .build()
    }

}