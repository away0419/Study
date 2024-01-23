> ## Spring Batch란

- 일련의 작업을 정해진 로직으로 수행하는 것. (일괄처리)
- 대용량 레코드 처리에 필수적인 기능을 제공함.
  - ex) 로깅/추적, 트랜잭션 관리, 작업 처리 통계, 작업 재시작, 건너뛰기, 리소스 관리
- 최적화 및 파티셔닝 기술을 통해 대용량 및 고성능 배치작업을 가능하게 하는 고급 기술 서비스 및 기능 제공.
- 배치가 실패하여 작업 재시작할 경우 실패한 지점부터 실행함.
- 중복 실행을 막기 위해 성공 이력이 있는 배치는 동일한 파라미터로 실행 시 예외 발생함.
- Scheduler & Quartz -> Batch Job 실행 시키기 위한 용도.
- Spring Batch는 Scheduler가 아님. Batch Job 관리 용도.
  - 배치는 대량의 데이터를 일괄적으로 처리하는 것.
  - 스케줄링은 특정 주기마다 자동으로 돌아가게 해주는 것.

<br/>
<br/>

> ## Spring Batch 특징

- 대용량 데이터 처리.
- 트랜잭션 관리.
- 재시도 기능.

<br/>
<br/>

> ## Spring Batch 구성

![Alt text](image/image.png)

<details>
    <summary>Job</summary>

- 배치 처리 과정을 하나의 단위로 만들어 놓은 객체.
- 배치 처리 과정에 있어 전체 계층 최상단에 위치.

</details>

<details>
    <summary>JobInstance</summary>

- Job의 실행 단위.
- Job 실행 시 하나의 JobInstance 발생.

</details>

<details>
    <summary>JobParameters</summary>

- JobInstance 구별 역할 및 JobInstance에 보내는 매개변수 역할.
- String, Double, Long, Date 4가지 형식만 지원함.

</details>

<details>
    <summary>JobExecution</summary>

- JobInstance 실행 시 생기는 객체.
  - 실패한 Job을 재실행할 경우 새로운 객체가 생성됨.
- JobInstance 상태, 시작시간, 종료시간, 생성시간 등의 정보를 담고 있음.

</details>

<details>
    <summary>Step</summary>

- Job의 배치처리를 정의하고 순차적인 단계를 캡슐화 한 것.
- 최소 1개의상의 step을 가져야하며 job의 실제 일괄 처리 제어하는 정보가 들어있음.

</details>

<details>
    <summary>StepExecution</summary>

- Step 실행 시 새로운 StepExecution 객체 생성됨.
  - Job 하나에 여러 Step이 있을 때, 실패한 Step 이후의 Step은 실행 되지 않으므로 실패 이후의 Step은 StepExecution 생성 안됨.
- JobExecution에 저장되는 정보 외에 Read Count, Write Count, Commit Count, Skip Count 등의 정보를 담고 있음.

</details>

<details>
    <summary>ExecutionContext</summary>

- 공유 데이터 저장소.
- JobExecutionContext, StepExecutionContext 2가지가 있음.
  - JobExecutionContext: Commit 시점에 데이터 저장.
  - StepExecutionContext: 실행 사이에 데이터 저장.

</details>

<details>
    <summary>JobRepository</summary>

- 위에서 설명한 모든 용어의 처리 정보를 담고 있는 매커니즘.
- Job 실행 시 JobRepository에 JobExecution, StepExecution 생성하고 ExecutionContext 정보들을 저장, 조회, 사용할 수 있는 공간이 되어줌.

</details>

<details>
    <summary>JobLauncher</summary>

- Job과 JobParameter 사용하여 Job 실행하는 객체.

</details>

<details>
    <summary>ItemReader</summary>

- Step에서 Item 읽어오는 인터페이스.
- 다양한 인터페이스가 존재하며 다양한 방법으로 Item 읽어올 수 있음.

</details>

<details>
    <summary>ItemWriter</summary>

- 처리 된 Data Write 할 때 사용함.
- 처기 결과에 따라 Insert, Update, Send가 될 수 있음.
- Read와 동일하게 다양한 인터페이스가 존재함.
- 기본적으로 Item을 Chunk로 묶어서 처리함.

</details>

<details>
    <summary>ItemProcessor</summary>

- Reader에서 읽어온 Item 처리하는 역할.
- 배치를 처리하는데 필수 요소는 아님.

</details>

<br/>
<br/>

> ## Spring Batch Step 동작 방식

<details>
    <summary>Tasklet</summary>

- Step 단계에서 '단일 레코드', '파일' 등 하나의 작업만 처리하는 방식.
- 각각의 처리를 하나의 트랜잭션에서 처리함.
- 파일을 읽고 처리한 다음 결과를 데이터베이스에 쓰는 등의 작업을 수행함.
- 단일 작업으로 작업이 끝날 때까지 대기 해야함.
- 대용량 데이터 처리에 적합하지 않음.

</details>

<details>
    <summary>Chunk</summary>

- Chunk: 데이터를 일정한 크기로 나눈 데이터 셋.
  - Chunk 단위로 나누면 전체 데이터를 한 번에 처리하지 않아도 되어 메모리 부하를 줄이고 성능을 향상시킬 수 있음.
- Step 단계에서 '단일 레코드를 묶어서' 여러 작업을 처리하는 방식.
- 묶인 레코드를 하나의 트랜잭션으로 처리하며 실패 시 롤백.
- 병렬 처리를 위해 Chunk 사용하되, 순차적으로 처리하는 방식임.
- 대용량 데이터를 처리할 때 사용하며, 중복 처리나 실패한 레코드 처리 등 예외 상황에 대한 대처가 용이함.

</details>

<details>
    <summary>Parallel Chunk</summary>

![Alt text](image/image-1.png)

- Chunk 방식의 처리에서 더욱 빠른 처리 속도를 위해 Chunk를 독립적으로 처리하여 여러 개의 Chunk를 병렬로 처리 하는 방식.
- 여러 대의 서버에서 동시에 작업을 처리할 때 사용할 수 있음.

</details>

<details>
    <summary>Remote Chunking</summary>

![Alt text](image/image-2.png)

- 여러 대의 서버에서 대용량 데이터 처리를 수행할 때 사용함.
- 서버 간에 데이터를 공유하고 각 서버에서 병렬로 처리함.

</details>

<br/>
<br/>

> ## Scheduler

- Batch Job을 실행 시키기 위한 방법.
- Quartz, Scheduler, Jenkins 등이 있음.
- Spring에서 Quartz Scheduler 지원함.
