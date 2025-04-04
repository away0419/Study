> ### 기본 설정
<details>
    <summary>DB 연결 제거 및 Job 설정</summary>

- DB 연결 제거.

```properties
spring.application.name=scheduler
spring.batch.job.enabled=true
spring.batch.jdbc.initialize-schema=never
spring.batch.job.repository-type=map
spring.datasource.url=none
```

</details>

<br/>
<br/>

> ### Spring Scheduler

<details>
    <summary>Spring Scheduler 활성화</summary>

- 라이브러리 설치 필요 없음.
- 현재 DB 사용 하지 않으므로 제외.
- @EnableScheduler 어노테이션 추가.

```java
package com.example.scheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableScheduling
public class SchedulerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchedulerApplication.class, args);
    }

}
```
</details>

<details>
    <summary>fixedDelay </summary>

- 메소드의 실행이 끝난 시간을 기준으로 설정된 시간마다 실행.
- 작업 수행시간을 포함하므로 호출이 Delay 될 수 있음.

    ```java
    @Scheduled(fixedDelay = 1000) // 1초마다 실행
        public void fixedDelay() throws InterruptedException {
            LocalDateTime now = LocalDateTime.now(); // 현재 시간 가져오기
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedNow = now.format(formatter); // 포맷팅
    
            System.out.println("fixedDelay Start: " + formattedNow);
            Thread.sleep(500); // 0.5초 지연
        }
    ```
</details>


<details>
    <summary>fixedRate</summary>

- 메소드의 실행이 실행하는 시간을 기준으로 설정된 시간 마다 실행.
- 이전 작업의 시작 지점부터 시간을 재므로 Delay 발생 x.

    ```java
    @Scheduled(fixedRate = 1000) // 1초마다 실행
    public void fixedRate() throws InterruptedException {
        LocalDateTime now = LocalDateTime.now(); // 현재 시간 가져오기
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        String formattedNow = now.format(formatter); // 포맷팅
    
        Thread.sleep(500); // 0.5초 지연
        System.out.println("fixedRate Start: " + formattedNow);
    }
    ```


</details>

<details>
    <summary>initialDelay</summary>

- 추가 옵션.
- 설정된 시간 후부터 스케줄러 실행.

    ```java
    @Scheduled(fixedRate = 5000, initialDelay = 3000) // 3초 후 5초마다 실행
    public void initialDelay() throws InterruptedException {
        LocalDateTime now = LocalDateTime.now(); // 현재 시간 가져오기
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        String formattedNow = now.format(formatter); // 포맷팅

        Thread.sleep(500); // 0.5초 지연
        System.out.println("initialDelay Start: " + formattedNow);
    }
    ```


</details>

<details>
    <summary>cron</summary>

- Corn 표현식 사용.

    ```java
    @Scheduled(cron = "*/10 * * * * *") // 10초마다 실행
    public void cron() throws InterruptedException {
        LocalDateTime now = LocalDateTime.now(); // 현재 시간 가져오기
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        String formattedNow = now.format(formatter); // 포맷팅
    
        Thread.sleep(500); // 0.5초 지연
        System.out.println("cron Start: " + formattedNow);
    }
    ```

</details>


<br/>
<br/>


> ### Quartz Scheduler

- [API 구현](https://github.com/away0419/Study/tree/main/%5B24.01%5DSpring-Batch/Java)

<details>
  <summary>라이브러리 설치</summary>

```gradlew
implementation 'org.springframework.boot:spring-boot-starter-quartz'
```

</details>

<details>
  <summary>Job</summary>

- 배치 Job과 다름.
- 실제 수행하고자 하는 비즈니스 로직을 정의하기 위한 인터페이스.

  ```java
  package com.example.scheduler.quartz;
  
  import org.quartz.Job;
  import org.quartz.JobExecutionContext;
  import org.quartz.JobExecutionException;
  
  public class MyJob implements Job {
      @Override
      public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
          System.out.println("MyJob executed");
      }
  }
  
  ```

</details>

<details>
  <summary>JobListener</summary>

- Quartz Job 실행 이벤트 감지용 인터페이스.

  ```java
  package com.example.scheduler.quartz;
  
  import org.quartz.JobExecutionContext;
  import org.quartz.JobExecutionException;
  import org.quartz.JobListener;
  
  public class MyJobListener implements JobListener {
      @Override
      public String getName() {
          return "MyJobListener";
      }
  
      @Override
      public void jobToBeExecuted(JobExecutionContext jobExecutionContext) {
          System.out.println("[JobListener] Job 실행 전");
      }
  
      @Override
      public void jobExecutionVetoed(JobExecutionContext jobExecutionContext) {
          System.out.println("[JobListener] Job 실행 취소된 후");
  
      }
  
      @Override
      public void jobWasExecuted(JobExecutionContext jobExecutionContext, JobExecutionException e) {
          System.out.println("[JobListener] Job 실행 완료된 후");
  
      }
  }
  
  ```

</details>

<details>
  <summary>Scheduler</summary>

- 앞서 만든 Job, JobListener 등록을 위한 클래스.
- JobDetail
  - 앞서 만든 Job에 추가적인 정보를 붙혀 만든 객체.
  - Quartz 스케줄러에 의해 실행.
- JobBuilder
  - Quartz Scheduler에서 사용하는 유틸리티 클래스.
  - JobDetails 객체 생성하는데 사용됨.
- JobDataMap
  - Job의 상태 데이터를 관리하는데 사용되는 클래스.
  - Map 인터페이스를 확장한 것.
- Trigger
  - Scheduler 작업 실행 시간을 결정.
  - SimpleTrigger: 특정 시간에 시작하여 주기적으로 작업 실행.
  - CronTrigger: cron 표현식을 이용한 작업 실행.
- TriggerBuilder
  - 트리거 생성하는데 사용되는 클래스.
  - 트리거의 다양한 속성 설정 가능하며 최종적으로 트리거 인스턴스 생성.
  - JobDataMap을 사용하여 트리거 실행될 때 필요한 추가 데이터 제공 가능.

  ```java
  package com.example.scheduler.quartz;
  
  import org.quartz.CronScheduleBuilder;
  import org.quartz.CronTrigger;
  import org.quartz.JobBuilder;
  import org.quartz.JobDataMap;
  import org.quartz.JobDetail;
  import org.quartz.SchedulerException;
  import org.quartz.SimpleScheduleBuilder;
  import org.quartz.Trigger;
  import org.quartz.TriggerBuilder;
  import org.quartz.impl.StdSchedulerFactory;
  import org.springframework.context.annotation.Configuration;
  import org.springframework.stereotype.Component;
  
  import jakarta.annotation.PostConstruct;
  
  @Component
  public class Scheduler {
      private org.quartz.Scheduler scheduler;
  
      public Scheduler(org.quartz.Scheduler scheduler) {
          this.scheduler = scheduler;
      }
  
      /**
       * 스케줄러의 실제 처리 과정을 담당
       * @throws SchedulerException
       */
      @PostConstruct
      private void jobProgress() throws SchedulerException {
          cronScheduler();
      }
  
      private void simpleScheduler() throws SchedulerException {
          JobDetail job = JobBuilder
              .newJob(MyJob.class) // 해당 job 클래스를 이용하여 jobBuilder 인스턴스 생성
              //.ofType(Class jobClazz) Trigger가 이 JobDetail과 연결되어 발화될 때 인스턴스화 되고 실행되는 클래스를 설정함.
              //.requestRecovery(null or Boolean) 복구 또는 대체 상황이 발생했을 때 Job이 다시 실행되어야 하는지 여부 설정
              //.setJobData(JobDataMap) JobDataMap으로 JobDetail의 JobDataMap 설정
              //.storeDurably(null or Boolean) // Job이 영구적으로 저장되어야 하는지 여부 설정
              //.usingJobData() 주어진 키-캆 쌍을 JobDetail의 JobDataMap에 추가
              .withIdentity("myJob", "myGroup") // 이름과 그룹 설정. jobKey를 사용하여 JobDetail 식별 가능하게 해줌.
              .withDescription("FCM 처리를 위한 조회 Job") // 설명 추가
              .build(); // JobDetails 객체 생성
  
          //job
              // .getKey()    job의 Key 반환
              // .getDescription()    job 설명 반환
              // .getJobClass() job 구현 클래스 반환
              // .getJobDataMap() job의 data map 반환
              // .isDurable() job이 지속적으로 저장되는지 여부를 반환
              // .isPersistJobDataAfterExecution() job 실행 후 data map을 지속적으로 저장하는지 여부 반환
              // .isConcurrentExectionDisallowed() 동시에 여러 job 실행되는 것을 허용하는지 여부 반환
              //.requestsRecovery() job이 실패한 경우 다시 실행하도록 설정되어 있는지 여부 반환
  
          Trigger trigger = TriggerBuilder
              .newTrigger() //TriggerBuilder 생성
              .withIdentity("myTrigger", "myGroup")         // Trigger 이름, 그룹 지정
              .withDescription("FCM 처리를 위한 조회 Trigger")     // Trigger 설명
              //.forJob Trigger 실행할 작업을 설정
              //.startAt() 트리거가 실행되는 시간을 설정
              //.endAt(Date) 트기거가 종료되는 시간을 설정
              //.startAt() 트리거 시작 시간 설정
              .startNow() //트리거 즉시 실행
              .withSchedule( // 트리거 스케줄 설정
                  SimpleScheduleBuilder
                      .simpleSchedule()
                      .withIntervalInSeconds(5)
                      .repeatForever())
              //.modifiedByCalendar() 트리거가 수정되는 달력의 이름을 설정
              //.withPriority() 트리거 수선순위 설정
              //.usingJobData() 트리거의 jobDataMap에 해당 키-값 추가
              .build();
  
          scheduler = new StdSchedulerFactory().getScheduler();
          MyJobListener myJobListener = new MyJobListener();
          scheduler.getListenerManager().addJobListener(myJobListener);
          scheduler.start();
          scheduler.scheduleJob(job, trigger);
  
      }
  
      private void cronScheduler() throws SchedulerException {
  
          JobDetail job = JobBuilder
              .newJob(MyJob.class)
              .withIdentity("myJob", "myGroup")
              .withDescription("FCM 처리를 위한 조회 Job")
              .build();
  
          CronTrigger cronTrigger = TriggerBuilder
              .newTrigger()
              .withIdentity("fcmSendTrigger", "fcmGroup")
              .withDescription("FCM 처리를 위한 Trigger")
              .startNow()
              .withSchedule(CronScheduleBuilder.cronSchedule("0/10 * * * * ?"))
              .build();
  
          scheduler = new StdSchedulerFactory().getScheduler();
          MyJobListener myJobListener = new MyJobListener();
          scheduler.getListenerManager().addJobListener(myJobListener);
          scheduler.start();
          scheduler.scheduleJob(job, cronTrigger);
  
      }
  
  }
  
  ```

</details>
