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
