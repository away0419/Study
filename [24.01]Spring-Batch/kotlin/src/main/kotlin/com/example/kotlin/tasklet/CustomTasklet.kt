package com.example.kotlin.tasklet

import org.slf4j.LoggerFactory
import org.springframework.batch.core.ExitStatus
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.StepExecution
import org.springframework.batch.core.StepExecutionListener
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus

class CustomTasklet (): Tasklet, StepExecutionListener{
    private val log = LoggerFactory.getLogger(this.javaClass)!!

    override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus? {
        for (i in 1.. 10){
            log.info("CustomTasklet ${i}번째 비즈니스 로직")
        }

        return RepeatStatus.FINISHED
    }

    override fun beforeStep(stepExecution: StepExecution) {
        log.info("Before Step")
        super.beforeStep(stepExecution)
    }

    override fun afterStep(stepExecution: StepExecution): ExitStatus? {
        log.info("After Step")
        return super.afterStep(stepExecution)
    }
}