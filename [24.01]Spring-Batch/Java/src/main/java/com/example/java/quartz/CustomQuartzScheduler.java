package com.example.java.quartz;

import java.util.Map;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CustomQuartzScheduler {
    private final Scheduler scheduler;
    private final ApplicationContext applicationContext;

    // QuartzScheduler 생성자 주입
    public CustomQuartzScheduler(Scheduler scheduler, ApplicationContext applicationContext) {
        this.scheduler = scheduler;
        this.applicationContext = applicationContext;
    }

    // QuartzJob 타입의 모든 빈 가져오기
    public Map<String, QuartzJob> getAllQuartzJobs() {
        // QuartzJob 타입으로 등록된 모든 빈을 검색
        return applicationContext.getBeansOfType(QuartzJob.class);
    }

    @PostConstruct
    public void scheduleJobs() throws SchedulerException {

        Map<String, QuartzJob> quartzJobs = this.getAllQuartzJobs();
        // quartzJobs.forEach((key, value) -> {
        //     try {
        //         // 1. 스케줄러 인스턴스 생성
        //         JobDetail jobDetail = value.jobDetailBuilder();
        //         // 2. 스케줄러에 JobDetail과 Trigger 등록
        //         scheduler.scheduleJob(jobDetail, value.trigger(jobDetail));
        //     } catch (Exception e) {
        //         log.error(e.getMessage(), e);
        //     }
        // });
        //
        // // 3. 스케줄러 시작
        // scheduler.start();
    }
}
