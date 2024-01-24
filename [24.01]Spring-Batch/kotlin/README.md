> ## Version

- Spring Boot, Spring Batch 각각의 버전별 사용법이 다름. 현재 실습 버전은 다음과 같음.
    - Spring Boot : 3.2.2
    - Spring Batch : 5
    - JDK : 17
    - kotlin("jvm") : 1.9.22
- 해당 실습 기준 인터넷에 보이는 Spring Batch 실습은 대게 Spring Boot 2, Spring Batch 4 버전임.
- Spring Batch 5의 경우 JDK 17 이상만 가능.


<br/>
<br/>

> ## Dependency

<details>
    <summary>Gradle</summary>

- Java와 동일.

    ```kotlin
    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-batch")
        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        developmentOnly("org.springframework.boot:spring-boot-devtools")
        runtimeOnly("com.h2database:h2")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testImplementation("org.springframework.batch:spring-batch-test")
    }
    ```

</details>

<br/>
<br/>


> ## Single Step

<details>
    <summary>KotlinApplication</summary>

- Java와 동일.

</details>

<details>
    <summary>SingleJobConfiguration</summary>

- Java와 동일.

    ```kotlin
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
    ```


</details>

