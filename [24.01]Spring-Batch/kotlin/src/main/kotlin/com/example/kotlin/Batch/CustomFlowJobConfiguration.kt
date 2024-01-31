package com.example.kotlin.Batch

import org.slf4j.LoggerFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.Step
import org.springframework.batch.core.StepExecution
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.job.flow.FlowExecutionStatus
import org.springframework.batch.core.job.flow.JobExecutionDecider
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager
import java.util.*

@Configuration
class CustomFlowJobConfiguration {
    private val log = LoggerFactory.getLogger(this.javaClass)!!

    @Bean
    fun customFlowTasklet(): Tasklet {
        return Tasklet { contribution, chunkContext ->
            log.info(">>>>> customFlowTasklet")
            RepeatStatus.FINISHED
        }
    }

    @Bean
    fun customFlowStep1(
        jobRepository: JobRepository,
        customFlowTasklet: Tasklet,
        platformTransactionManager: PlatformTransactionManager
    ): Step {
        log.info(">>>> customFlowStep1")
        return StepBuilder("customFlowStep1", jobRepository).tasklet(customFlowTasklet, platformTransactionManager).build()
    }

    @Bean
    fun customFlowStep2(
        jobRepository: JobRepository,
        customFlowTasklet: Tasklet,
        platformTransactionManager: PlatformTransactionManager
    ): Step {
        log.info(">>>> customFlowStep2")
        return StepBuilder("customFlowStep2", jobRepository).tasklet(customFlowTasklet, platformTransactionManager).build()
    }

    @Bean
    fun customFlowStep3(
        jobRepository: JobRepository,
        customFlowTasklet: Tasklet,
        platformTransactionManager: PlatformTransactionManager
    ): Step {
        log.info(">>>> customFlowStep3")
        return StepBuilder("customFlowStep3", jobRepository).tasklet(customFlowTasklet, platformTransactionManager).build()
    }

    class OddDecider(): JobExecutionDecider {
        override fun decide(jobExecution: JobExecution, stepExecution: StepExecution?): FlowExecutionStatus {
            val rand = Random();
            val log = LoggerFactory.getLogger(this.javaClass)!!

            val randomNumber = rand.nextInt(50) + 1;
            log.info("랜덤숫자: {}", randomNumber);

            return if(randomNumber % 2 == 0) {
                FlowExecutionStatus("EVEN");
            } else {
                FlowExecutionStatus("ODD");
            }
        }
    }

    @Bean
    fun decider(): JobExecutionDecider{
        return OddDecider()
    }

    @Bean
    fun customFlowJob(
        jobRepository: JobRepository,
        customFlowStep1: Step,
        customFlowStep2: Step,
        customFlowStep3: Step,
    ): Job {
        return JobBuilder("customFlowJob", jobRepository)
            .start(customFlowStep1) // step1 시작.
            .next(decider()) // decider() 시작.
            .on("EVEN") // decider 결과 값 EVEN 일 경우.
            .to(customFlowStep2) // customFlow2 실행.
            .from(decider()) // 만약 decider() 결과 값이
            .on("ODD") // ODD 일 경우
            .to(customFlowStep3) // step3 실행.
            .end() // job 종료
            .build()
    }
    
}