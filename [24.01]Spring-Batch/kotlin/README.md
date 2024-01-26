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

<br/>
<br/>

> ## MultiJob

<details>
  <summary>설정</summary>

- Java와 동일.

  ```yaml
  spring:
    batch:
      job:
        enabled: true # default true. false 하면 모든 job 비활성화.
        name: multiJob # 해당 이름으로 된 job만 실행.
  ```


</details>

<details>
  <summary>MultiJobConfiguration</summary>

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
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

@Configuration
class MultiJobConfiguration {
    private val log = LoggerFactory.getLogger(this.javaClass)!!

    @Bean
    fun multiTasklet(): Tasklet {
        log.info(">>>> multiTaskLet")
        return Tasklet { contribution: StepContribution?, chunkContext: ChunkContext? ->
            log.info(">>>> multiTaskLet")
            RepeatStatus.FINISHED
        }
    }

    @Bean
    fun multiStep1(
        jobRepository: JobRepository,
        multiTasklet: Tasklet,
        platformTransactionManager: PlatformTransactionManager
    ): Step {
        log.info(">>>> multiStep1")
        return StepBuilder("multiStep1", jobRepository).tasklet(multiTasklet, platformTransactionManager).build()
    }

    @Bean
    fun multiStep2(
        jobRepository: JobRepository,
        multiTasklet: Tasklet,
        platformTransactionManager: PlatformTransactionManager
    ): Step {
        log.info(">>>> multiStep2")
        return StepBuilder("multiStep2", jobRepository).tasklet(multiTasklet, platformTransactionManager).build()
    }

    @Bean
    fun multiStep3(
        jobRepository: JobRepository,
        multiTasklet: Tasklet,
        platformTransactionManager: PlatformTransactionManager
    ): Step {
        log.info(">>>> multiStep3")
        return StepBuilder("multiStep3", jobRepository).tasklet(multiTasklet, platformTransactionManager).build()
    }

    @Bean
    fun multiJob(
        jobRepository: JobRepository,
        multiStep1: Step,
        multiStep2: Step,
        multiStep3: Step,
    ): Job {
        return JobBuilder("multiJob", jobRepository)
            .start(multiStep1)
            .next(multiStep2)
            .next(multiStep3)
            .build()
    }

}
```

</details>