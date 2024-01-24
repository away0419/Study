package com.example.kotlin.Batch

import org.slf4j.LoggerFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

@Configuration
class SingleJobConfiguration {
    private val log = LoggerFactory.getLogger(this.javaClass)!!
    @Bean
    fun singleTaskLet(): Tasklet {
        return Tasklet { contribution: StepContribution?, chunkContext: ChunkContext? ->
            log.info(">>>> SingleTaskLet")
            RepeatStatus.FINISHED
        };
    }

    @Bean
    fun singleStep(
        jobRepository: JobRepository,
        platformTransactionManager: PlatformTransactionManager,
    ): Step {
        log.info(">>>> SingleStep")
        return StepBuilder("singleStep", jobRepository)
            .tasklet(singleTaskLet(), platformTransactionManager).build()
    }

    @Bean
    fun job(jobRepository: JobRepository, platformTransactionManager: PlatformTransactionManager): Job {
        log.info(">>>> SingleJob")
        return JobBuilder("singleJob", jobRepository)
            .start(singleStep(jobRepository,platformTransactionManager))
            .build()
    }

}