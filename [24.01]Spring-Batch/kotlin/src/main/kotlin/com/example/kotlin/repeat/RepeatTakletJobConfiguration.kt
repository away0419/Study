package com.example.kotlin.repeat

import org.slf4j.LoggerFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.batch.repeat.policy.SimpleCompletionPolicy
import org.springframework.batch.repeat.support.RepeatTemplate
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

@Configuration
class RepeatTakletJobConfiguration {
    private val log = LoggerFactory.getLogger(this.javaClass)!!

    fun repeatBusinessTaskLet(): Tasklet {
        return Tasklet { contribution, chunkContext ->
            log.info(">>>> repeatBusinessTaskLet")
            RepeatStatus.CONTINUABLE
        };
    }

    @Bean
    fun repeatStep(
        jobRepository: JobRepository,
        singleTaskLet: Tasklet,
        platformTransactionManager: PlatformTransactionManager,
    ): Step {
        log.info(">>>> repeatStep")
        return StepBuilder("repeatStep", jobRepository)
            .tasklet({ contribution, chunkContext ->
                log.info(">>>> repeatStepTasklet")
                val repeatTemplate = RepeatTemplate()

                repeatTemplate.setCompletionPolicy(SimpleCompletionPolicy(3))
                repeatTemplate.iterate { repeatBusinessTaskLet().execute(contribution, chunkContext)!! }

                RepeatStatus.FINISHED
            }, platformTransactionManager).build()
    }

    @Bean
    fun repeatTaskletJob(
        jobRepository: JobRepository,
        repeatStep: Step,
        platformTransactionManager: PlatformTransactionManager
    ): Job {
        log.info(">>>> repeatTaskletJob")
        return JobBuilder("repeatTaskletJob", jobRepository)
            .start(repeatStep)
            .build()
    }

}