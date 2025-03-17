package com.example.java.quartz;

import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.transaction.PlatformTransactionManager;

import lombok.Getter;
import lombok.Setter;

/**
 * 커스텀한 Spring Batch + Quartz Scheduler 추상 클래스
 * 다른 개발자는 Batch Job 설정만 하면 자동으로 Quartz 관련 설정이 되도록 만들고자 만듬.
 * @param <T> 입력 클래스
 * @param <V> 출력 클래스
 */

@Getter
@Setter
public abstract class QuartzJob<T,V> implements Job, BatchJob<T,V> {
    private final JobLauncher jobLauncher;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private org.springframework.batch.core.Job job;

    // QuartzJob 생성자 주입
    protected QuartzJob(JobLauncher jobLauncher, JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        this.jobLauncher = jobLauncher;
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
    }

    // Quartz JobDetail 생성.
    public JobDetail jobDetailBuilder() {
        return JobBuilder.newJob(this.getClass())
            .withIdentity(this.getJobName())
            .storeDurably()
            .build();
    }

    // Quartz Scheduler Trigger 설정. 다른 개발자가 Batch Job Class 만들 때 getTriggerInterval() 정의해야함.
    public Trigger trigger(JobDetail jobDetail) {
        return TriggerBuilder.newTrigger()
            .forJob(jobDetail)
            .withIdentity(this.getJobName() + "Trigger")
            .withSchedule(CronScheduleBuilder.cronSchedule(this.getTriggerInterval()))
            .build();
    }

    // Quartz Job 실행. 다른 개발자가 Batch Job Class 만들 때 job() 정의해야함.
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            if (job == null) {
                job = this.job();
            }
            jobLauncher.run(job, this.createJobParameters()); // 다른 개발자가 만든 Batch Job 실행
        } catch (Exception e) {
            throw new JobExecutionException(e);
        }
    }

    // 다른 개발자가 만든 Spring Batch Job Class Name
    @Override
    public String getJobName() {
        return this.getClass().getName();
    }
}
