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

> ## MultiStep

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

<br/>
<br/>

> ## Flow

<details>
  <summary>설정</summary>

- 추가 설정 없음.

  ```yaml
  spring:
    batch:
      job:
        enabled: true # default true. false 하면 모든 job 비활성화.
        name: flowJob # 해당 이름으로 된 job만 실행.
  ```

</details>


<details>
  <summary>flowJobConfiguration</summary>

- Java는 Flow 종료 메소드인 .end()가 따로 필요했으나, Kotlin은 Flow 종료 메소드 .end()를 따로 하지 않아도 됨.

  ```kotlin
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
  ```

</details>


<details>
  <summary>customFlowConfiguration</summary>

- 위에서 설명한 차이 말고는 Java와 동일.

  ```kotlin
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
  ```

</details>


<br/>
<br/>

> ## Scope 

<details>
  <summary>설정</summary>

- 파라미터 입력 설정.

![img.png](image/img.png)

</details>


<details>
  <summary>JobParameterConfiguration</summary>

- 기본적인 틀 수정.
- @Value를 이용한 파라미터 가져오기.

  ```kotlin
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
  ```

</details>

<br/>
<br/>

> ## Tasklet

<details>
  <summary>Lambda</summary>

- Java와 동일.

  ```kotlin
  package com.example.kotlin.tasklet
  
  import org.slf4j.LoggerFactory
  import org.springframework.batch.core.Job
  import org.springframework.batch.core.Step
  import org.springframework.batch.core.job.builder.JobBuilder
  import org.springframework.batch.core.repository.JobRepository
  import org.springframework.batch.core.step.builder.StepBuilder
  import org.springframework.batch.repeat.RepeatStatus
  import org.springframework.context.annotation.Bean
  import org.springframework.context.annotation.Configuration
  import org.springframework.transaction.PlatformTransactionManager
  
  @Configuration
  class TaskletJobConfig1 {
      private val log = LoggerFactory.getLogger(this.javaClass)!!
  
      @Bean
      fun taskletStep(
          jobRepository: JobRepository,
          platformTransactionManager: PlatformTransactionManager
      ): Step {
          log.info(">>>> taskletStep1")
          return StepBuilder("flowStep1", jobRepository).tasklet({ contribution, chunkContext ->
              for (i in 0..10) {
                  log.info("람다식 ${i}번째 비지니스 로직");
              }
              RepeatStatus.FINISHED
          }, platformTransactionManager).build()
      }
  
      @Bean
      fun taskletJob(jobRepository: JobRepository, taskletStep: Step): Job {
          log.info(">>>> taskletJob1")
          return JobBuilder("taskletJob1", jobRepository)
              .start(taskletStep)
              .build()
      }
  
  }
  ```

</details>

<details>
  <summary>MethodInvokingTaskletAdapter</summary>

- Java와 동일함.

  ```kotlin
  package com.example.kotlin.tasklet
  
  import org.slf4j.LoggerFactory
  
  class MethodInvokingTaskletAdapterService {
      private val log = LoggerFactory.getLogger(this.javaClass)!!
      fun businessLogic(){
          for (i in 1.. 10){
              log.info("MethodInvokingTaskletAdapterService : ${i}번째 비즈니스 로직")
          }
      }
  }
  ```

  ```kotlin
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
  ```

</details>

<details>
  <summary>CustomTasklet</summary>

- Tasklet을 커스텀 하여 사용하는 방법.
- Java와 동일함.

  ```kotlin
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
  ```

  ```kotlin
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
  ```

</details>

<br/>
<br/>

> ## Chunk

<details>
  <summary>ChunkConfiguration</summary>

- Java와 동일.

  ```kotlin
  package com.example.kotlin.chunk
  
  import org.slf4j.LoggerFactory
  import org.springframework.batch.core.Job
  import org.springframework.batch.core.Step
  import org.springframework.batch.core.job.builder.JobBuilder
  import org.springframework.batch.core.repository.JobRepository
  import org.springframework.batch.core.step.builder.StepBuilder
  import org.springframework.batch.item.ItemProcessor
  import org.springframework.batch.item.ItemReader
  import org.springframework.batch.item.ItemWriter
  import org.springframework.batch.item.support.ListItemReader
  import org.springframework.batch.repeat.RepeatStatus
  import org.springframework.context.annotation.Bean
  import org.springframework.context.annotation.Configuration
  import org.springframework.transaction.PlatformTransactionManager
  import java.util.*
  
  @Configuration
  class ChunkConfiguration {
      private val log = LoggerFactory.getLogger(this.javaClass)!!
  
  
      @Bean
      fun reader(): ItemReader<String> = ListItemReader(listOf("one", "two", "three"))
  
      @Bean
      fun processor(): ItemProcessor<String, String> = ItemProcessor { it.uppercase(Locale.getDefault()) }
  
      @Bean
      fun writer(): ItemWriter<String> = ItemWriter { items -> items.forEach { log.info(it) } }
  
      @Bean
      fun chunkStep(
          jobRepository: JobRepository,
          platformTransactionManager: PlatformTransactionManager
      ): Step {
          log.info(">>>> chunkStep")
          return StepBuilder("chunkStep", jobRepository)
              .chunk<String, String>(10, platformTransactionManager)
              .reader(reader())
              .processor(processor())
              .writer(writer())
              .build()
      }
  
      @Bean
      fun chunkJob(jobRepository: JobRepository, chunkStep: Step): Job {
          log.info(">>>> chunkJob")
          return JobBuilder("chunkJob", jobRepository)
              .start(chunkStep)
              .build()
      }
  }
  ```

</details>

<br/>
<br/>

> ## ItemReader

<details>
  <summary>Data 설정</summary>

- Java와 동일.

  ```kotlin
  package com.example.kotlin.itemReader.cursor
  
  import jakarta.persistence.Column
  import jakarta.persistence.Entity
  import jakarta.persistence.GeneratedValue
  import jakarta.persistence.GenerationType
  import jakarta.persistence.Id
  import jakarta.persistence.Table
  import java.time.LocalDateTime
  import java.time.format.DateTimeFormatter
  
  @Entity
  @Table(name = "PAY")
  class Pay(
      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      var id: Long? = null,
      var amount: Long? = null,
      var txName: String? = null,
      @Column(columnDefinition = "TIMESTAMP")
      var txDateTime: LocalDateTime? = null
  ) {
      companion object {
          private val FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
      }
  
      constructor(id: Long?, amount: Long?, txDateTime: LocalDateTime?) : this() {
          this.id = id
          this.amount = amount
          this.txDateTime = txDateTime
      }
  
      override fun toString(): String {
          return "Pay(id=$id, amount=$amount, txName=$txName, txDateTime=$txDateTime)"
      }
  
  }
  
  ```
  
  ```yaml
  spring:
    batch:
      job:
        enabled: true # default true. false 하면 모든 job 비활성화.
        name: jdbcCursorItemReaderJob # 해당 이름으로 된 job만 실행.
  
    datasource:
      url: jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
      driverClassName: org.h2.Driver
      username: sa
      password:
  
    jpa:
      database-platform: org.hibernate.dialect.H2Dialect
      hibernate:
        ddl-auto: update
      defer-datasource-initialization: true
  
    h2:
      console:
        enabled: true
        path: /h2-console
  
    sql:
      init:
        data-locations: classpath:test.sql
  ```

  ```sql
  insert into pay (amount, tx_name, tx_date_time) VALUES (1000, 'trade1', '2018-09-10 00:00:00');
  insert into pay (amount, tx_name, tx_date_time) VALUES (2000, 'trade2', '2018-09-10 00:00:00');
  insert into pay (amount, tx_name, tx_date_time) VALUES (3000, 'trade3', '2018-09-10 00:00:00');
  insert into pay (amount, tx_name, tx_date_time) VALUES (4000, 'trade4', '2018-09-10 00:00:00');
  ```

</details>


<details>
  <summary>JdbcCursorItemReaderJobConfiguration</summary>

- Java와 동일.

  ```kotlin
  package com.example.kotlin.itemReader.cursor
  
  import org.slf4j.LoggerFactory
  import org.springframework.batch.core.Job
  import org.springframework.batch.core.Step
  import org.springframework.batch.core.job.builder.JobBuilder
  import org.springframework.batch.core.repository.JobRepository
  import org.springframework.batch.core.step.builder.StepBuilder
  import org.springframework.batch.item.ItemWriter
  import org.springframework.batch.item.database.JdbcCursorItemReader
  import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder
  import org.springframework.context.annotation.Bean
  import org.springframework.context.annotation.Configuration
  import org.springframework.jdbc.core.BeanPropertyRowMapper
  import org.springframework.transaction.PlatformTransactionManager
  import javax.sql.DataSource
  
  @Configuration
  class JdbcCursorItemReaderJobConfiguration {
      private val log = LoggerFactory.getLogger(this.javaClass)!!
  
      @Bean
      fun jdbcCursorItemReader(dataSource: DataSource): JdbcCursorItemReader<Pay> = JdbcCursorItemReaderBuilder<Pay>()
          .fetchSize(10)
          .dataSource(dataSource)
          .rowMapper(BeanPropertyRowMapper(Pay::class.java))
          .sql("SELECT id, amount, tx_name, tx_date_time FROM pay")
          .name("jdbcCursorItemReader")
          .build()
  
      @Bean
      fun jdbcCursorItemWriter(): ItemWriter<Pay> =
          ItemWriter { items -> items.forEach { log.info("Current com.example.kotlin.itemReader.cursor.Pay={}", it.toString()) } }
  
      @Bean
      fun jdbcCursorItemReaderStep(
          jobRepository: JobRepository,
          platformTransactionManager: PlatformTransactionManager,
          jdbcCursorItemReader: JdbcCursorItemReader<Pay>
      ): Step {
          log.info(">>>> jdbcCursorItemReaderStep")
          return StepBuilder("jdbcCursorItemReaderStep", jobRepository)
              .chunk<Pay, Pay>(10, platformTransactionManager)
              .reader(jdbcCursorItemReader)
              .writer(jdbcCursorItemWriter())
              .build()
      }
  
      @Bean
      fun jdbcCursorItemReaderJob(jobRepository: JobRepository, jdbcCursorItemReaderStep: Step): Job {
          log.info(">>>> jdbcCursorItemReaderJob")
          return JobBuilder("jdbcCursorItemReaderJob", jobRepository)
              .start(jdbcCursorItemReaderStep)
              .build()
      }
  
  }
  ```

</details>

<details>
  <summary>JpaCursorItemReaderJobConfiguration</summary>

- Java와 대부분 동일.
- Java와 달리 JPQL 사용해야 실행 됨.

  ```kotlin
  package com.example.kotlin.itemReader.cursor
  
  import jakarta.persistence.EntityManagerFactory
  import org.slf4j.LoggerFactory
  import org.springframework.batch.core.Job
  import org.springframework.batch.core.Step
  import org.springframework.batch.core.job.builder.JobBuilder
  import org.springframework.batch.core.repository.JobRepository
  import org.springframework.batch.core.step.builder.StepBuilder
  import org.springframework.batch.item.ItemWriter
  import org.springframework.batch.item.database.JpaCursorItemReader
  import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder
  import org.springframework.context.annotation.Bean
  import org.springframework.context.annotation.Configuration
  import org.springframework.transaction.PlatformTransactionManager
  
  @Configuration
  class JpaCursorItemReaderJobConfiguration {
      private val log = LoggerFactory.getLogger(this.javaClass)!!
  
      @Bean
      fun jpaCursorItemReader(entityManagerFactory: EntityManagerFactory): JpaCursorItemReader<Pay> =
          JpaCursorItemReaderBuilder<Pay>()
              .name("jpaCursorItemReader")
              .queryString("SELECT p FROM Pay p")
              .entityManagerFactory(entityManagerFactory)
              .build()
  
      @Bean
      fun jpaCursorItemWriter(): ItemWriter<Pay> =
          ItemWriter { items -> items.forEach { log.info("Current Pay={}", it.toString()) } }
  
      @Bean
      fun jpaCursorItemReaderStep(
          jobRepository: JobRepository,
          platformTransactionManager: PlatformTransactionManager,
          jpaCursorItemReader: JpaCursorItemReader<Pay>
      ): Step {
          log.info(">>>> jdbcCursorItemReaderStep")
          return StepBuilder("jdbcCursorItemReaderStep", jobRepository)
              .chunk<Pay, Pay>(10, platformTransactionManager)
              .reader(jpaCursorItemReader)
              .writer(jpaCursorItemWriter())
              .build()
      }
  
      @Bean
      fun jpaCursorItemReaderJob(jobRepository: JobRepository, jpaCursorItemReaderStep: Step): Job {
          log.info(">>>> jpaCursorItemReaderJob")
          return JobBuilder("jpaCursorItemReaderJob", jobRepository)
              .start(jpaCursorItemReaderStep)
              .build()
      }
  
  }
  ```
</details>

<details>
  <summary>JdbcPagingItemReaderConfiguration</summary>

- java와 동일.

  ```kotlin
  package com.example.kotlin.itemReader.cursor
  
  import org.slf4j.LoggerFactory
  import org.springframework.batch.core.Job
  import org.springframework.batch.core.Step
  import org.springframework.batch.core.job.builder.JobBuilder
  import org.springframework.batch.core.repository.JobRepository
  import org.springframework.batch.core.step.builder.StepBuilder
  import org.springframework.batch.item.ItemWriter
  import org.springframework.batch.item.database.JdbcPagingItemReader
  import org.springframework.batch.item.database.Order
  import org.springframework.batch.item.database.PagingQueryProvider
  import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder
  import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean
  import org.springframework.context.annotation.Bean
  import org.springframework.context.annotation.Configuration
  import org.springframework.jdbc.core.BeanPropertyRowMapper
  import org.springframework.transaction.PlatformTransactionManager
  import javax.sql.DataSource
  
  @Configuration
  class JdbcPagingItemReaderConfiguration {
      private val log = LoggerFactory.getLogger(this.javaClass)!!
  
      private fun createQueryProvider(dataSource: DataSource): PagingQueryProvider {
          val queryProviderFactoryBean = SqlPagingQueryProviderFactoryBean()
          val map = HashMap<String, Order>()
          map["id"] = Order.ASCENDING
  
          queryProviderFactoryBean.setDataSource(dataSource)
          queryProviderFactoryBean.setSelectClause("id, amount, tx_name, tx_date_time")
          queryProviderFactoryBean.setFromClause("pay")
          queryProviderFactoryBean.setWhereClause("amount >= :amount")
          queryProviderFactoryBean.setSortKeys(map)
  
          return queryProviderFactoryBean.`object`
      }
  
      @Bean
      fun jdbcPagingItemReader(dataSource: DataSource): JdbcPagingItemReader<Pay> {
          val parameterValues = HashMap<String, Any>()
          parameterValues["amount"] = 2000
  
          return JdbcPagingItemReaderBuilder<Pay>()
              .name("jdbcPagingItemReader")
              .pageSize(10)
              .dataSource(dataSource)
              .rowMapper(BeanPropertyRowMapper(Pay::class.java))
              .parameterValues(parameterValues)
              .queryProvider(createQueryProvider(dataSource))
              .build()
      }
  
      @Bean
      fun jdbcPagingItemWriter(): ItemWriter<Pay> =
          ItemWriter { items -> items.forEach { log.info("Current Pay={}", it.toString()) } }
  
      @Bean
      fun jdbcPagingItemReaderStep(
          jobRepository: JobRepository,
          platformTransactionManager: PlatformTransactionManager,
          jdbcPagingItemReader: JdbcPagingItemReader<Pay>
      ): Step {
          log.info(">>>> jdbcPagingItemReaderStep")
          return StepBuilder("jdbcPagingItemReaderStep", jobRepository)
              .chunk<Pay, Pay>(10, platformTransactionManager)
              .reader(jdbcPagingItemReader)
              .writer(jdbcPagingItemWriter())
              .build()
      }
  
      @Bean
      fun jdbcPagingItemReaderJob(jobRepository: JobRepository, jdbcPagingItemReaderStep: Step): Job {
          log.info(">>>> jdbcPagingItemReaderJob")
          return JobBuilder("jdbcPagingItemReaderJob", jobRepository)
              .start(jdbcPagingItemReaderStep)
              .build()
      }
  }
  ```

</details>

<details>
  <summary>JpaPagingItemReaderJobConfiguration</summary>

- java 동일

  ```kotlin
  package com.example.kotlin.itemReader.cursor
  
  import jakarta.persistence.EntityManagerFactory
  import org.slf4j.LoggerFactory
  import org.springframework.batch.core.Job
  import org.springframework.batch.core.Step
  import org.springframework.batch.core.job.builder.JobBuilder
  import org.springframework.batch.core.repository.JobRepository
  import org.springframework.batch.core.step.builder.StepBuilder
  import org.springframework.batch.item.ItemWriter
  import org.springframework.batch.item.database.JpaPagingItemReader
  import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder
  import org.springframework.context.annotation.Bean
  import org.springframework.context.annotation.Configuration
  import org.springframework.transaction.PlatformTransactionManager
  
  @Configuration
  class JpaPagingItemReaderJobConfiguration {
      private val log = LoggerFactory.getLogger(this.javaClass)!!
  
      @Bean
      fun jpaPagingItemReader(entityManagerFactory: EntityManagerFactory): JpaPagingItemReader<Pay> =
          JpaPagingItemReaderBuilder<Pay>()
              .name("jpaPagingItemReader")
              .pageSize(10)
              .queryString("SELECT p FROM Pay p")
              .entityManagerFactory(entityManagerFactory)
              .build()
  
      @Bean
      fun jpaPagingItemWriter(): ItemWriter<Pay> =
          ItemWriter { items -> items.forEach { log.info("Current Pay={}", it.toString()) } }
  
      @Bean
      fun jpaPagingItemReaderStep(
          jobRepository: JobRepository,
          platformTransactionManager: PlatformTransactionManager,
          jpaPagingItemReader: JpaPagingItemReader<Pay>
      ): Step {
          log.info(">>>> jpaPagingItemReaderStep")
          return StepBuilder("jpaPagingItemReaderStep", jobRepository)
              .chunk<Pay, Pay>(10, platformTransactionManager)
              .reader(jpaPagingItemReader)
              .writer(jpaPagingItemWriter())
              .build()
      }
  
      @Bean
      fun jpaPagingItemReaderJob(jobRepository: JobRepository, jpaPagingItemReaderStep: Step): Job {
          log.info(">>>> jpaPagingItemReaderJob")
          return JobBuilder("jpaPagingItemReaderJob", jobRepository)
              .start(jpaPagingItemReaderStep)
              .build()
      }
  }
  ```

</details>


<br/>
<br/>

> ## ItemWriter

<details>
  <summary>JdbcBatchItemWriterJobConfiguration</summary>

- java와 동일.

  ```kotlin
  package com.example.kotlin.itemWriter
  
  import com.example.kotlin.itemReader.cursor.Pay
  import org.slf4j.LoggerFactory
  import org.springframework.batch.core.Job
  import org.springframework.batch.core.Step
  import org.springframework.batch.core.job.builder.JobBuilder
  import org.springframework.batch.core.repository.JobRepository
  import org.springframework.batch.core.step.builder.StepBuilder
  import org.springframework.batch.item.database.JdbcBatchItemWriter
  import org.springframework.batch.item.database.JdbcCursorItemReader
  import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder
  import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder
  import org.springframework.context.annotation.Bean
  import org.springframework.context.annotation.Configuration
  import org.springframework.jdbc.core.BeanPropertyRowMapper
  import org.springframework.transaction.PlatformTransactionManager
  import javax.sql.DataSource
  
  @Configuration
  class JdbcBatchItemWriterJobConfiguration {
      private val log = LoggerFactory.getLogger(this.javaClass)!!
  
      @Bean
      fun jdbcBatchItemWriterReader(dataSource: DataSource): JdbcCursorItemReader<Pay> =
          JdbcCursorItemReaderBuilder<Pay>()
              .fetchSize(10)
              .dataSource(dataSource)
              .rowMapper(BeanPropertyRowMapper(Pay::class.java))
              .sql("SELECT id, amount, tx_name, tx_date_time FROM pay")
              .name("jdbcBatchItemWriterReader")
              .build()
  
      @Bean
      fun jdbcBatchItemWriter(dataSource: DataSource): JdbcBatchItemWriter<Pay> {
          return JdbcBatchItemWriterBuilder<Pay>()
              .dataSource(dataSource)
              .sql("insert into pay2(amount, tx_name, tx_date_time) values (:amount, :txName, :txDateTime)")
              .beanMapped() // Pojo 기반으로 SQL values 매핑. 사용시 @Bean 등록 필수
  //                .columnMapped() // MAP<Key, Value> 기반으로 SQL values 매핑.
  //                .assertUpdates(boolean) // 트랜잭션 이후 적어도 하나의 항목이 변하지 않을 경우 예외 발생여부를 설정함. 기본값은 true
              .build();
      }
  
      @Bean
      fun jdbcBatchItemWriterStep(
          jobRepository: JobRepository,
          platformTransactionManager: PlatformTransactionManager,
          jdbcCursorItemReader: JdbcCursorItemReader<Pay>,
          jdbcBatchItemWriter: JdbcBatchItemWriter<Pay>
      ): Step {
          log.info(">>>> jdbcBatchItemWriterStep")
          return StepBuilder("jdbcBatchItemWriterStep", jobRepository)
              .chunk<Pay, Pay>(10, platformTransactionManager)
              .reader(jdbcCursorItemReader)
              .writer(jdbcBatchItemWriter)
              .build()
      }
  
      @Bean
      fun jdbcBatchItemWriterJob(jobRepository: JobRepository, jdbcBatchItemWriterStep: Step): Job {
          log.info(">>>> jdbcBatchItemWriterJob")
          return JobBuilder("jdbcBatchItemWriterJob", jobRepository)
              .start(jdbcBatchItemWriterStep)
              .build()
      }
  }
  ```

</details>

<details>
  <summary>JpaItemWriterJobConfiguration</summary>

- java와 동일.

  ```kotlin
  package com.example.kotlin.itemWriter
  
  import com.example.kotlin.itemReader.cursor.Pay
  import jakarta.persistence.EntityManagerFactory
  import org.slf4j.LoggerFactory
  import org.springframework.batch.core.Job
  import org.springframework.batch.core.Step
  import org.springframework.batch.core.job.builder.JobBuilder
  import org.springframework.batch.core.repository.JobRepository
  import org.springframework.batch.core.step.builder.StepBuilder
  import org.springframework.batch.item.ItemProcessor
  import org.springframework.batch.item.database.JdbcCursorItemReader
  import org.springframework.batch.item.database.JpaItemWriter
  import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder
  import org.springframework.context.annotation.Bean
  import org.springframework.context.annotation.Configuration
  import org.springframework.jdbc.core.BeanPropertyRowMapper
  import org.springframework.transaction.PlatformTransactionManager
  import javax.sql.DataSource
  
  @Configuration
  class JpaItemWriterJobConfiguration {
      private val log = LoggerFactory.getLogger(this.javaClass)!!
  
      @Bean
      fun jpaItemWriter(entityManagerFactory: EntityManagerFactory): JpaItemWriter<Pay2> {
          val jpaItemWriter = JpaItemWriter<Pay2>()
          jpaItemWriter.setEntityManagerFactory(entityManagerFactory)
          return jpaItemWriter
      }
  
      @Bean
      fun jpaItemWriterProcessor(): ItemProcessor<Pay, Pay2> {
          return ItemProcessor { pay ->
              Pay2(pay.amount, pay.txName, pay.txDateTime)
          }
      }
  
      @Bean
      fun jpaItemWriterReader(dataSource: DataSource): JdbcCursorItemReader<Pay> =
          JdbcCursorItemReaderBuilder<Pay>()
              .fetchSize(10)
              .dataSource(dataSource)
              .rowMapper(BeanPropertyRowMapper(Pay::class.java))
              .sql("SELECT id, amount, tx_name, tx_date_time FROM pay")
              .name("jpaItemWriterReader")
              .build()
  
      @Bean
      fun jpaItemWriterStep(
          jobRepository: JobRepository,
          platformTransactionManager: PlatformTransactionManager,
          jpaItemWriterReader: JdbcCursorItemReader<Pay>,
          jpaItemWriterProcessor: ItemProcessor<Pay, Pay2>,
          jpaItemWriter: JpaItemWriter<Pay2>
      ): Step {
          log.info(">>>> jpaItemWriterStep")
          return StepBuilder("jpaItemWriterStep", jobRepository)
              .chunk<Pay, Pay2>(10, platformTransactionManager)
              .reader(jpaItemWriterReader)
              .processor(jpaItemWriterProcessor)
              .writer(jpaItemWriter)
              .build()
      }
  
      @Bean
      fun jpaItemWriterJob(jobRepository: JobRepository, jpaItemWriterStep: Step): Job {
          log.info(">>>> jpaItemWriterJob")
          return JobBuilder("jpaItemWriterJob", jobRepository)
              .start(jpaItemWriterStep)
              .build()
      }
  
  }
  ```

</details>

<br/>
<br/>

> ## ItemProcessor

<details>
  <summary>CompositeItemProcessorJobConfiguration</summary>

- java와 대부분 동일.
- kotlin은 클래스 타입에 Any를 넣어야 하며 이럴 경우 as를 이용하여 타입 변환을 해주어야 함.

  ```kotlin
  package com.example.kotlin.itemProcessor
  
  import com.example.kotlin.itemReader.cursor.Pay
  import com.example.kotlin.itemWriter.Pay2
  import jakarta.persistence.EntityManagerFactory
  import org.slf4j.LoggerFactory
  import org.springframework.batch.core.Job
  import org.springframework.batch.core.Step
  import org.springframework.batch.core.job.builder.JobBuilder
  import org.springframework.batch.core.repository.JobRepository
  import org.springframework.batch.core.step.builder.StepBuilder
  import org.springframework.batch.item.ItemProcessor
  import org.springframework.batch.item.database.JdbcCursorItemReader
  import org.springframework.batch.item.database.JpaItemWriter
  import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder
  import org.springframework.batch.item.support.CompositeItemProcessor
  import org.springframework.context.annotation.Bean
  import org.springframework.context.annotation.Configuration
  import org.springframework.jdbc.core.BeanPropertyRowMapper
  import org.springframework.transaction.PlatformTransactionManager
  import javax.sql.DataSource
  
  @Configuration
  class CompositeItemProcessorJobConfiguration {
      private val log = LoggerFactory.getLogger(this.javaClass)!!
  
      @Bean
      fun compositeItemProcessorWriter(entityManagerFactory: EntityManagerFactory): JpaItemWriter<Pay2> {
          val jpaItemWriter = JpaItemWriter<Pay2>()
          jpaItemWriter.setEntityManagerFactory(entityManagerFactory)
          return jpaItemWriter
      }
  
      fun compositeItemProcessor1(): ItemProcessor<Pay, Pay2> {
          return ItemProcessor { pay ->
              log.info("processor1 실행")
              Pay2(pay.amount, pay.txName, pay.txDateTime)
          }
      }
  
      fun compositeItemProcessor2(): ItemProcessor<Pay2, Pay2> {
          return ItemProcessor { pay ->
              log.info("processor2 실행")
              pay
          }
      }
  
      @Bean
      fun compositeItemProcessor(): CompositeItemProcessor<Any, Any> {
          val processor = CompositeItemProcessor<Any, Any>()
          processor.setDelegates(listOf(compositeItemProcessor1(), compositeItemProcessor2()))
          return processor
      }
  
      @Bean
      fun compositeItemProcessorReader(dataSource: DataSource): JdbcCursorItemReader<Pay> =
          JdbcCursorItemReaderBuilder<Pay>()
              .fetchSize(10)
              .dataSource(dataSource)
              .rowMapper(BeanPropertyRowMapper(Pay::class.java))
              .sql("SELECT id, amount, tx_name, tx_date_time FROM pay")
              .name("compositeItemProcessorReader")
              .build()
  
      @Bean
      fun compositeItemProcessorStep(
          jobRepository: JobRepository,
          platformTransactionManager: PlatformTransactionManager,
          compositeItemProcessorReader: JdbcCursorItemReader<Pay>,
          compositeItemProcessor: CompositeItemProcessor<Any, Any>,
          compositeItemProcessorWriter: JpaItemWriter<Pay2>
      ): Step {
          log.info(">>>> compositeItemProcessorStep")
          return StepBuilder("compositeItemProcessorStep", jobRepository)
              .chunk<Any, Any>(10, platformTransactionManager)
              .reader(compositeItemProcessorReader)
              .processor(compositeItemProcessor)
              .writer(compositeItemProcessorWriter as JpaItemWriter<Any>)
              .build()
      }
  
      @Bean
      fun compositeItemProcessorJob(jobRepository: JobRepository, compositeItemProcessorStep: Step): Job {
          log.info(">>>> compositeItemProcessorJob")
          return JobBuilder("compositeItemProcessorJob", jobRepository)
              .start(compositeItemProcessorStep)
              .build()
      }
  }
  ```

</details>


<br/>
<br/>

> ## Repeat

<details>
  <summary>RepeatTakletJobConfiguration</summary>

- java와 동일.

  ```kotlin
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
  ```
</details>

<details>
  <summary>RepeatChunkJobConfiguration</summary>

- java 와 동일.

  ```kotlin
  package com.example.kotlin.repeat
  
  import org.slf4j.LoggerFactory
  import org.springframework.batch.core.Job
  import org.springframework.batch.core.Step
  import org.springframework.batch.core.job.builder.JobBuilder
  import org.springframework.batch.core.repository.JobRepository
  import org.springframework.batch.core.step.builder.StepBuilder
  import org.springframework.batch.item.ItemProcessor
  import org.springframework.batch.item.ItemReader
  import org.springframework.batch.item.ItemWriter
  import org.springframework.batch.item.support.ListItemReader
  import org.springframework.batch.repeat.RepeatStatus
  import org.springframework.batch.repeat.policy.SimpleCompletionPolicy
  import org.springframework.batch.repeat.support.RepeatTemplate
  import org.springframework.context.annotation.Bean
  import org.springframework.context.annotation.Configuration
  import org.springframework.transaction.PlatformTransactionManager
  import java.util.*
  
  @Configuration
  class RepeatChunkJobConfiguration {
      private val log = LoggerFactory.getLogger(this.javaClass)!!
  
  
      @Bean
      fun repeatChunkItemReader(): ItemReader<String> = ListItemReader(listOf("one", "two", "three"))
  
      @Bean
      fun repeatChunkItemProcessor(): ItemProcessor<String, String> = ItemProcessor {
          val repeatTemplate = RepeatTemplate()
          repeatTemplate.setCompletionPolicy(SimpleCompletionPolicy(3))
          repeatTemplate.iterate{
              log.info("repeatChunkProcessor")
              RepeatStatus.CONTINUABLE
          }
  
          it.uppercase(Locale.getDefault()) }
  
      @Bean
      fun repeatChunkItemWriter(): ItemWriter<String> = ItemWriter { items -> items.forEach { log.info(it) } }
  
      @Bean
      fun repeatChunkStep(
          jobRepository: JobRepository,
          platformTransactionManager: PlatformTransactionManager
      ): Step {
          log.info(">>>> repeatChunkStep")
          return StepBuilder("repeatChunkStep", jobRepository)
              .chunk<String, String>(10, platformTransactionManager)
              .reader(repeatChunkItemReader())
              .processor(repeatChunkItemProcessor())
              .writer(repeatChunkItemWriter())
              .build()
      }
  
      @Bean
      fun repeatChunkJob(jobRepository: JobRepository, repeatChunkStep: Step): Job {
          log.info(">>>> repeatChunkJob")
          return JobBuilder("repeatChunkJob", jobRepository)
              .start(repeatChunkStep)
              .build()
      }
  
  }
  ```


</details>

<br/>
<br/>

> ## Skip

<details>
  <summary>SkipJobConfiguration</summary>

- java와 동일.

  ```kotlin
  package com.example.kotlin.skip
  
  import org.slf4j.LoggerFactory
  import org.springframework.batch.core.Job
  import org.springframework.batch.core.Step
  import org.springframework.batch.core.job.builder.JobBuilder
  import org.springframework.batch.core.repository.JobRepository
  import org.springframework.batch.core.step.builder.StepBuilder
  import org.springframework.batch.core.step.skip.LimitCheckingItemSkipPolicy
  import org.springframework.batch.item.ItemProcessor
  import org.springframework.batch.item.ItemReader
  import org.springframework.batch.item.ItemWriter
  import org.springframework.batch.item.support.ListItemReader
  import org.springframework.batch.repeat.RepeatStatus
  import org.springframework.batch.repeat.policy.SimpleCompletionPolicy
  import org.springframework.batch.repeat.support.RepeatTemplate
  import org.springframework.context.annotation.Bean
  import org.springframework.context.annotation.Configuration
  import org.springframework.transaction.PlatformTransactionManager
  import java.util.*
  
  @Configuration
  class SkipJobConfiguration {
  
      private val log = LoggerFactory.getLogger(this.javaClass)!!
  
  
      @Bean
      fun skipItemReader(): ItemReader<String> = ListItemReader(listOf("one", "two", "three"))
  
      @Bean
      fun skipItemProcessor(): ItemProcessor<String, String> = ItemProcessor {
          log.info("skipProcessor")
          it.uppercase(Locale.getDefault()) }
  
      @Bean
      fun skipItemWriter(): ItemWriter<String> = ItemWriter { items -> items.forEach { log.info(it)
          if(it.contains("O")){
              log.info("Skipping item writer:{}", it)
              throw IllegalArgumentException("에러발생")
          }
      } }
  
      @Bean
      fun skipStep(
          jobRepository: JobRepository,
          platformTransactionManager: PlatformTransactionManager
      ): Step {
          log.info(">>>> skipStep")
          return StepBuilder("skipStep", jobRepository)
              .chunk<String, String>(10, platformTransactionManager)
              .reader(skipItemReader())
              .processor(skipItemProcessor())
              .writer(skipItemWriter())
              .faultTolerant() // 내결함성 기능 활성화
              .skip(IllegalArgumentException::class.java) // skip 하려는 예외 타입 설정
              .skipLimit(2) // skip 제한 횟수 설정
              .skipPolicy(LimitCheckingItemSkipPolicy()) // skip을 어떤 조건과 기준으로 적용할 것인지 정책 설정. default: LimitCheckingItemSkipPolicy
              .noSkip(NullPointerException::class.java) // skip 하지 않을 예외 타입 설정
              .build()
      }
  
      @Bean
      fun skipJob(jobRepository: JobRepository, skipStep: Step): Job {
          log.info(">>>> skipJob")
          return JobBuilder("skipJob", jobRepository)
              .start(skipStep)
              .build()
      }
  
  }
  ```
</details>

<br/>
<br/>

> ## Retry

<details>
  <summary>RetryJobConfiguration</summary>

- java와 동일.

  ```kotlin
  package com.example.kotlin.retry
  
  import org.slf4j.LoggerFactory
  import org.springframework.batch.core.Job
  import org.springframework.batch.core.Step
  import org.springframework.batch.core.job.builder.JobBuilder
  import org.springframework.batch.core.repository.JobRepository
  import org.springframework.batch.core.step.builder.StepBuilder
  import org.springframework.batch.item.ItemProcessor
  import org.springframework.batch.item.ItemReader
  import org.springframework.batch.item.ItemWriter
  import org.springframework.batch.item.support.ListItemReader
  import org.springframework.context.annotation.Bean
  import org.springframework.context.annotation.Configuration
  import org.springframework.retry.policy.SimpleRetryPolicy
  import org.springframework.transaction.PlatformTransactionManager
  import java.util.*
  
  @Configuration
  class RetryJobConfiguration {
      private val log = LoggerFactory.getLogger(this.javaClass)!!
  
  
      @Bean
      fun retryItemReader(): ItemReader<String> = ListItemReader(listOf("one", "two", "three"))
  
      @Bean
      fun retryItemProcessor(): ItemProcessor<String, String> = ItemProcessor {
          log.info("retryProcessor")
          it.uppercase(Locale.getDefault())
      }
  
      @Bean
      fun retryItemWriter(): ItemWriter<String> = ItemWriter { items ->
          items.forEach {
              log.info(it)
              if (it.contains("O")) {
                  log.info("retryping item writer:{}", it)
                  throw IllegalArgumentException("에러발생")
              }
          }
      }
  
      @Bean
      fun retryStep(
          jobRepository: JobRepository,
          platformTransactionManager: PlatformTransactionManager
      ): Step {
          log.info(">>>> retryStep")
          return StepBuilder("retryStep", jobRepository)
              .chunk<String, String>(10, platformTransactionManager)
              .reader(retryItemReader())
              .processor(retryItemProcessor())
              .writer(retryItemWriter())
              .faultTolerant() // 내결함성 기능 활성화
              .retry(IllegalArgumentException::class.java) // retry 하려는 예외 타입 설정
              .retryLimit(2) // retry 제한 횟수 설정
              .retryPolicy(SimpleRetryPolicy()) // retry 어떤 조건과 기준으로 적용할 것인지 정책 설정. default: SimpleRetryPolicy
              .noRetry(NullPointerException::class.java) // retry 하지 않을 예외 타입 설정
              .noRollback(NullPointerException::class.java) // rollback 하지 않을 예외 타입 설정
              .build()
      }
  
      @Bean
      fun retryJob(jobRepository: JobRepository, retryStep: Step): Job {
          log.info(">>>> retryJob")
          return JobBuilder("retryJob", jobRepository)
              .start(retryStep)
              .build()
      }
  
  }
  ```
</details>


