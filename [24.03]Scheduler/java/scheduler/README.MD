> ### 기본 설정
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