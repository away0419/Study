package com.example.kotlin.Batch

import org.slf4j.LoggerFactory
import org.springframework.batch.core.ExitStatus
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

@Configuration
class FlowJobConfiguration {
    private val log = LoggerFactory.getLogger(this.javaClass)!!

    @Bean
    fun flowTasklet(): Tasklet {
        return Tasklet { contribution, chunkContext ->
            log.info(">>>>> flowTasklet")
//            contribution.exitStatus = ExitStatus.FAILED
            RepeatStatus.FINISHED
        }
    }

    @Bean
    fun flowStep1(
        jobRepository: JobRepository,
        flowTasklet: Tasklet,
        platformTransactionManager: PlatformTransactionManager
    ): Step {
        log.info(">>>> flowStep1")
        return StepBuilder("flowStep1", jobRepository).tasklet(flowTasklet, platformTransactionManager).build()
    }

    @Bean
    fun flowStep2(
        jobRepository: JobRepository,
        flowTasklet: Tasklet,
        platformTransactionManager: PlatformTransactionManager
    ): Step {
        log.info(">>>> flowStep2")
        return StepBuilder("flowStep2", jobRepository).tasklet(flowTasklet, platformTransactionManager).build()
    }

    @Bean
    fun flowStep3(
        jobRepository: JobRepository,
        flowTasklet: Tasklet,
        platformTransactionManager: PlatformTransactionManager
    ): Step {
        log.info(">>>> flowStep3")
        return StepBuilder("flowStep3", jobRepository).tasklet(flowTasklet, platformTransactionManager).build()
    }

    @Bean
    fun flowJob(
        jobRepository: JobRepository,
        flowStep1: Step,
        flowStep2: Step,
        flowStep3: Step,
    ): Job {
        return JobBuilder("flowJob", jobRepository)
            .start(flowStep1) // step1 시작.
            .on("FAILED") // step1 결과 값 FAILED 일 경우.
            .to(flowStep2) // flow2 실행.
            .from(flowStep1) // 만약 step1의 결과 값이
            .on("*") // 모든 결과 값일 경우
            .to(flowStep3) // step3 실행.
            .end() // job 종료
            .build()
    }

}