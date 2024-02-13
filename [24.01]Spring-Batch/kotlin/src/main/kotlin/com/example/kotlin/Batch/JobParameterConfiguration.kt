package com.example.kotlin.Batch

import org.slf4j.LoggerFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

@Configuration
class JobParameterConfiguration {
    private val log = LoggerFactory.getLogger(this.javaClass)!!

    @Bean
    @StepScope
    fun jobParameterTaskLet(
        @Value("#{jobParameters[stepScopeParameter]}") stepScopeParameter: String,
    ): Tasklet {
        return Tasklet { contribution: StepContribution?, chunkContext: ChunkContext? ->
            log.info(">>>> jobParameterTaskLet")
            log.info(">>>> stepScopeParameter: {}", stepScopeParameter);
            RepeatStatus.FINISHED
        };
    }

    @Bean
    @JobScope
    fun jobParameterStep(
        @Value("#{jobParameters[jobScopeParameter]}") jobScopeParameter: String,
        jobRepository: JobRepository,
        jobParameterTaskLet: Tasklet,
        platformTransactionManager: PlatformTransactionManager,
    ): Step {
        log.info(">>>> jobParameterStep")
            log.info(">>>> jobScopeParameter: {}", jobScopeParameter)
        return StepBuilder("jobParameterStep", jobRepository)
            .tasklet(jobParameterTaskLet, platformTransactionManager).build()
    }

//    @Bean
//    fun job(jobRepository: JobRepository, jobParameterTaskLet: Tasklet, platformTransactionManager: PlatformTransactionManager): Job {
//        log.info(">>>> SingleJob")
//        return JobBuilder("jobParameterJob", jobRepository)
//            .start(jobParameterStep(jobRepository, jobParameterTaskLet, platformTransactionManager))
//            .build()
//    }

    @Bean
    fun jobParameterJob(jobRepository: JobRepository, jobParameterStep: Step): Job {
        log.info(">>>> jobParameterJob")
        return JobBuilder("jobParameterJob", jobRepository)
            .start(jobParameterStep)
            .build()
    }

}