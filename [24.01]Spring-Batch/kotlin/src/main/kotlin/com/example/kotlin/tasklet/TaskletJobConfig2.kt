package com.example.kotlin.tasklet

import org.slf4j.LoggerFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.core.step.tasklet.MethodInvokingTaskletAdapter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

@Configuration
class TaskletJobConfig2 {
    private val log = LoggerFactory.getLogger(this.javaClass)!!

    @Bean
    fun methodInvokingTaskletAdapterService(): MethodInvokingTaskletAdapterService{
        return MethodInvokingTaskletAdapterService()
    }

    @Bean
    fun methodInvokingTaskletAdapter() : MethodInvokingTaskletAdapter{
        val methodInvokingTaskletAdapter = MethodInvokingTaskletAdapter()

        methodInvokingTaskletAdapter.setTargetObject(methodInvokingTaskletAdapterService())
        methodInvokingTaskletAdapter.setTargetMethod("businessLogic")

        return methodInvokingTaskletAdapter
    }

    @Bean
    fun taskletStep2(
        jobRepository: JobRepository,
        platformTransactionManager: PlatformTransactionManager
    ): Step {
        log.info(">>>> taskletStep2")
        return StepBuilder("flowStep1", jobRepository).tasklet(methodInvokingTaskletAdapter(), platformTransactionManager).build()
    }

    @Bean
    fun taskletJob2(jobRepository: JobRepository, taskletStep2: Step): Job {
        log.info(">>>> taskletJob2")
        return JobBuilder("taskletJob2", jobRepository)
            .start(taskletStep2)
            .build()
    }

}