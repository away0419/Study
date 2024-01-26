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
