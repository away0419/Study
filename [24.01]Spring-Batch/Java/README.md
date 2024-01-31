> ## Version

- Spring Boot, Spring Batch 각각의 버전별 사용법이 다름. 현재 실습 버전은 다음과 같음.
  - Spring Boot : 3.2.2
  - Spring Batch : 5
  - JDK : 17
- 해당 실습 기준 인터넷에 보이는 Spring Batch 실습은 대게 Spring Boot 2, Spring Batch 4 버전임.
- Spring Batch 5의 경우 JDK 17 이상만 가능.


<br/>
<br/>

> ## Dependency

<details>
    <summary>Gradle</summary>

- Batch를 사용하기 위해선 메타 데이터를 저장할 DB가 필요함.
  - h2는 메타 데이터용 테이블을 자동으로 만들어주지만 다른 DB는 테이블을 만들어야함.
  - Spring Batch 라이브러리에 org.springframework.batch.core 폴더에 가보면 schema-mysql.sql 파일이 있음.
- lombok은 로그를 찍기 위해 추가함.

    ```gradle
    dependencies {
        implementation 'org.springframework.boot:spring-boot-starter-web'
        developmentOnly 'org.springframework.boot:spring-boot-devtools'
        testImplementation 'org.springframework.boot:spring-boot-starter-test'
    
        compileOnly 'org.projectlombok:lombok'
        annotationProcessor 'org.projectlombok:lombok'
        implementation 'com.h2database:h2'
        implementation 'org.springframework.boot:spring-boot-starter-batch'
        implementation 'org.springframework.boot:spring-boot-starter-quartz:2.7.5'
    }
    ```

</details>

<br/>
<br/>

> ## Single Step

<details>
    <summary>JavaApplication</summary>

- SpringBoot 3 이상은 @EnableAspectJAutoProxy 설정을 하면 오류가 발생함.

    ```java
    package com.example.java;
    
    import org.springframework.boot.SpringApplication;
    import org.springframework.boot.autoconfigure.SpringBootApplication;
    import org.springframework.context.annotation.EnableAspectJAutoProxy;
    
    @SpringBootApplication
    //@EnableAspectJAutoProxy // Batch 기능 활성화. Batch Config 클래스가 여러개 존재 한다면 Main에 적용하는 것이 좋음.
    public class JavaApplication {
    
        public static void main(String[] args) {
            SpringApplication.run(JavaApplication.class, args);
        }
    
    }
    ```

</details>

<details>
    <summary>SingleJobConfiguration</summary>

- 실행 후 로그를 확인해보면 Job이 언제 실행 되는지 알 수 있음.
- 먼저 Step이 등록되고, Step에서 JobRepository를 사용하므로 Job이 등록됨. 이후 Step에서 taskLet을 사용하므로 TaskLet이 등록됨.

    ```java
    package com.example.java.batch;
    
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.batch.core.Job;
    import org.springframework.batch.core.Step;
    import org.springframework.batch.core.job.builder.JobBuilder;
    import org.springframework.batch.core.repository.JobRepository;
    import org.springframework.batch.core.step.builder.StepBuilder;
    import org.springframework.batch.core.step.tasklet.Tasklet;
    import org.springframework.batch.repeat.RepeatStatus;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.transaction.PlatformTransactionManager;
    
    @Slf4j
    @Configuration // Job @Configuration에 등록.
    public class SingleJobConfiguration {
        @Bean
        public Tasklet singleTasklet() {
            return (contribution, chunkContext) -> {
                log.info(">>>> SingleTaskLet");
                return RepeatStatus.FINISHED;
            };
        }
    
        @Bean
        public Step singleStep(JobRepository jobRepository, Tasklet singleTasklet, PlatformTransactionManager platformTransactionManager) {
            log.info(">>>>>> singleStep");
            return new StepBuilder("singleStep", jobRepository).tasklet(singleTasklet, platformTransactionManager).build();
        }
    
        @Bean
        public Job singleJob(JobRepository jobRepository, Step singleStep) {
            log.info(">>>>>> singleJob");
            return new JobBuilder("singleJob", jobRepository)
                    .start(singleStep)
                    .build();
        }
    }
    ```

</details>

<br/>
<br/>


> ## Multi Step

<details>
  <summary>설정</summary>

- Spring Boot 3은 다중 작업을 지원하지 않음. 즉, Job이 여러 개일 경우 하나만 작업할 수 있음. 
- 설정 파일에서 어떤 작업을 실행할지 설정해야 함.

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

- 단일 스텝과 동일한 구조.
- next() 함수를 통해 다음 Step을 설정.

  ```java
  package com.example.java.batch;
  
  import lombok.extern.slf4j.Slf4j;
  import org.springframework.batch.core.Job;
  import org.springframework.batch.core.Step;
  import org.springframework.batch.core.job.builder.JobBuilder;
  import org.springframework.batch.core.repository.JobRepository;
  import org.springframework.batch.core.step.builder.StepBuilder;
  import org.springframework.batch.core.step.tasklet.Tasklet;
  import org.springframework.batch.repeat.RepeatStatus;
  import org.springframework.context.annotation.Bean;
  import org.springframework.context.annotation.Configuration;
  import org.springframework.transaction.PlatformTransactionManager;
  
  @Slf4j
  @Configuration
  public class MultiJobConfiguration {
  
      @Bean
      public Tasklet multiTaskLet(){
          return (contribution, chunkContext) -> {
              log.info(">>>> multiTaskLet");
              return RepeatStatus.FINISHED;
          };
      }
  
      @Bean
      public Step multiStep1(JobRepository jobRepository, Tasklet multiTaskLet, PlatformTransactionManager platformTransactionManager){
          log.info(">>>> multiStep1");
          return new StepBuilder("multiStep1", jobRepository).tasklet(multiTaskLet, platformTransactionManager).build();
      }
  
      @Bean
      public Step multiStep2(JobRepository jobRepository, Tasklet multiTaskLet, PlatformTransactionManager platformTransactionManager){
          log.info(">>>> multiStep2");
          return new StepBuilder("multiStep2", jobRepository).tasklet(multiTaskLet, platformTransactionManager).build();
      }
  
      @Bean
      public Step multiStep3(JobRepository jobRepository, Tasklet multiTaskLet, PlatformTransactionManager platformTransactionManager) {
          log.info(">>>> multiStep3");
          return new StepBuilder("multiStep3", jobRepository).tasklet(multiTaskLet, platformTransactionManager).build();
      }
  
      @Bean
      public Job multiJob(JobRepository jobRepository, Step multiStep1, Step multiStep2, Step multiStep3) {
          log.info(">>>> multiJob");
          return new JobBuilder("multiJob", jobRepository)
                  .start(multiStep1)
                  .next(multiStep2)
                  .next(multiStep3)
                  .build();
      }
  }
  ```

</details>

<br/>
<br/>

> ## Flow

<details>
  <summary>설정</summary>

- 딱히 없음.

  ```yaml
  spring:
    batch:
      job:
        enabled: true
        name: flowJob
  ```

</details>

<details>
  <summary>FlowJobConfiguration</summary>

- flow 설정은 Job에서 할 수 있음.
- 현재 스텝의 결과 상태에 따라 분기를 나누어 설정하는 방식임. 
  - BatchStatus가 아닌 ExitStatus 결과에 따라 분기가 나뉘어 지는 것임.
  - ExitStatus 코드는 StepExecutionListenerSupport 상속 받아 커스텀이 가능함. (Spring Batch5는 StepExecutionListenerSupport를 더이상 사용 하지 않음. 다른 방법을 찾아 봐야할 듯.)

  ```java
  package com.example.java.batch;
  
  import lombok.extern.slf4j.Slf4j;
  import org.springframework.batch.core.ExitStatus;
  import org.springframework.batch.core.Job;
  import org.springframework.batch.core.Step;
  import org.springframework.batch.core.job.builder.JobBuilder;
  import org.springframework.batch.core.repository.JobRepository;
  import org.springframework.batch.core.step.builder.StepBuilder;
  import org.springframework.batch.core.step.tasklet.Tasklet;
  import org.springframework.batch.repeat.RepeatStatus;
  import org.springframework.context.annotation.Bean;
  import org.springframework.context.annotation.Configuration;
  import org.springframework.transaction.PlatformTransactionManager;
  
  @Slf4j
  @Configuration
  public class FlowJobConfiguration {
      @Bean
      public Tasklet flowTasklet(){
          return (contribution, chunkContext) -> {
              log.info(">>>> flowTaskLet");
              contribution.setExitStatus(ExitStatus.FAILED); // Step 실행 후 상태. 기본적으로 BatchStatus와 ExitStatus 값이 동일하도록 설정되어 있음.
              return RepeatStatus.FINISHED;
          };
      }
  
      @Bean
      public Step flowStep1(JobRepository jobRepository, Tasklet flowTasklet, PlatformTransactionManager platformTransactionManager){
          log.info(">>>> flowStep1");
          return new StepBuilder("flowStep1", jobRepository).tasklet(flowTasklet, platformTransactionManager).build();
      }
  
      @Bean
      public Step flowStep2(JobRepository jobRepository, Tasklet flowTasklet, PlatformTransactionManager platformTransactionManager){
          log.info(">>>> flowStep2");
          return new StepBuilder("flowStep2", jobRepository).tasklet(flowTasklet, platformTransactionManager).build();
      }
  
      @Bean
      public Step flowStep3(JobRepository jobRepository, Tasklet flowTasklet, PlatformTransactionManager platformTransactionManager) {
          log.info(">>>> flowStep3");
          return new StepBuilder("flowStep3", jobRepository).tasklet(flowTasklet, platformTransactionManager).build();
      }
  
      @Bean
      public Job flowJob(JobRepository jobRepository, Step flowStep1, Step flowStep2, Step flowStep3) {
          log.info(">>>> flowJob");
          return new JobBuilder("flowJob", jobRepository)
                  .start(flowStep1)
                      .on("FAILED") // FAILED 일 경우 -> 이때 FAILED BatchStatus 결과가 아니라 ExitStatus 결과 값임.
                      .to(flowStep2) // step2로 이동.
                      .on("*") // step2의 (모든 결과)일 때
                      .end() // flow 종료
                  .from(flowStep1) // 만약 step1의 결과가
                      .on("*") // *(모든 결과) 일 경우 -> 위에서 이미 FAILED Flow가 있어 걸러지므로 결국 FAILED 제외한 결과로 볼 수 있음.
                      .to(flowStep3) // step3으로 이동.
                      .next(flowStep2) // step3 이후 step2로 이동.
                      .on("*") // step2의 결과가 *(모든 결과) 일 경우
                      .end() // flow 종료
                  .end()// job 종료
                  .build();
      }
  }
  ```

</details>

<details>
  <summary>customFlowConfiguration</summary>

- 직접 ExitStatus 만들어 flow 처리하는 방법임.
- Step에서 ExitStatus 설정을 하지 않고 분기 처리 로직을 따로 만드는 방법.

  ```java
  package com.example.java.batch;
  
  import lombok.extern.slf4j.Slf4j;
  import org.springframework.batch.core.*;
  import org.springframework.batch.core.job.builder.JobBuilder;
  import org.springframework.batch.core.job.flow.FlowExecutionStatus;
  import org.springframework.batch.core.job.flow.JobExecutionDecider;
  import org.springframework.batch.core.repository.JobRepository;
  import org.springframework.batch.core.step.builder.StepBuilder;
  import org.springframework.batch.core.step.tasklet.Tasklet;
  import org.springframework.batch.repeat.RepeatStatus;
  import org.springframework.context.annotation.Bean;
  import org.springframework.context.annotation.Configuration;
  import org.springframework.transaction.PlatformTransactionManager;
  
  import java.util.Random;
  
  @Slf4j
  @Configuration
  public class CustomFlowJobConfiguration {
      @Bean
      public Tasklet customFlowTasklet(){
          return (contribution, chunkContext) -> {
              log.info(">>>> customFlowTaskLet");
              return RepeatStatus.FINISHED;
          };
      }
  
      @Bean
      public Step customFlowStep1(JobRepository jobRepository, Tasklet customFlowTasklet, PlatformTransactionManager platformTransactionManager){
          log.info(">>>> customFlowStep1");
          return new StepBuilder("customFlowStep1", jobRepository).tasklet(customFlowTasklet, platformTransactionManager).build();
      }
  
      @Bean
      public Step customFlowStep2(JobRepository jobRepository, Tasklet customFlowTasklet, PlatformTransactionManager platformTransactionManager){
          log.info(">>>> customFlowStep2");
          return new StepBuilder("customFlowStep2", jobRepository).tasklet(customFlowTasklet, platformTransactionManager).build();
      }
  
      @Bean
      public Step customFlowStep3(JobRepository jobRepository, Tasklet customFlowTasklet, PlatformTransactionManager platformTransactionManager) {
          log.info(">>>> customFlowStep3");
          return new StepBuilder("customFlowStep3", jobRepository).tasklet(customFlowTasklet, platformTransactionManager).build();
      }
  
      @Bean
      public JobExecutionDecider decider() {
          return new OddDecider();
      }
  
      public static class OddDecider implements JobExecutionDecider {
          @Override
          public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
              Random rand = new Random();
  
              int randomNumber = rand.nextInt(50) + 1;
              log.info("랜덤숫자: {}", randomNumber);
  
              if(randomNumber % 2 == 0) {
                  return new FlowExecutionStatus("EVEN");
              } else {
                  return new FlowExecutionStatus("ODD");
              }
          }
      }
  
      @Bean
      public Job customFlowJob(JobRepository jobRepository, Step customFlowStep1, Step customFlowStep2, Step customFlowStep3) {
          log.info(">>>> customFlowJob");
          return new JobBuilder("customFlowJob", jobRepository)
                  .start(customFlowStep1)// step1 시작.
                  .next(decider()) // step1 이후 decider 시작.
                  .on("EVEN") // decider 결과가 EVEN 일 경우
                  .to(customFlowStep2) // step2로 이동.
                  .on("*") // step2의 (모든 결과)일 때
                  .end() // customFlow 종료
                  .from(decider()) // 만약 decider 결과가
                  .on("*") // *(모든 결과) 일 경우
                  .to(customFlowStep3) // step3으로 이동.
                  .next(customFlowStep2) // step3 이후 step2로 이동.
                  .on("*") // step2의 결과가 *(모든 결과) 일 경우
                  .end() // customFlow 종료
                  .end()// job 종료
                  .build();
      }
  
  
  }
  ```

</details>