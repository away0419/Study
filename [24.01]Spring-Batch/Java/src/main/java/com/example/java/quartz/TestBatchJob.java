package com.example.java.quartz;

import java.util.Arrays;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import lombok.extern.slf4j.Slf4j;

/**
 * 다른 개발자가 만든 Batch Job
 * QuartzJob 상속 받고 Batch 관련 설정만 한다면 Quartz 자동 등록 됨.
 * yaml 추가 하지 않아도 실행됨.
 */
@Slf4j
@Component
public class TestBatchJob extends QuartzJob<Integer,String>{

    public TestBatchJob(JobLauncher jobLauncher, JobRepository jobRepository,
        PlatformTransactionManager transactionManager) {
        super(jobLauncher, jobRepository, transactionManager);
    }

    // Spring Batch Job 생성
    @Override
    public Job job() {
        return new JobBuilder(getJobName(), super.getJobRepository())
            .start(step())
            .build();
    }

    // Spring Batch Step 생성
    // 필요시 여러개 생성 가능
    @Override
    public Step step() {
        log.info(">>>> TestBatchJob Step1");
        return new StepBuilder(getJobName() + "Step", super.getJobRepository())
            .<Integer, String>chunk(10, super.getTransactionManager())
            .reader(reader())
            .processor(processor())
            .writer(writer())
            .build();
    }

    // Trigger Interval 세팅
    @Override
    public String getTriggerInterval() {
        // 초 분 시 일 월 요일
        return "*/1 * * * * ?";
    }

    // 가져올 데이터
    @Override
    public ItemReader<Integer> reader() {
        return new ListItemReader<>(Arrays.asList(1,2,3));
    }

    // 데이터 가공
    @Override
    public ItemProcessor<Integer, String> processor() {
        return String::valueOf;
    }

    // 가공된 데이터 저장
    @Override
    public ItemWriter<String> writer() {
        return items -> items.forEach(log::info);
    }

    // Job Parameter 생성
    @Override
    public JobParameters createJobParameters() {
        return new JobParametersBuilder()
            .addLong("time", System.currentTimeMillis())
            .toJobParameters();
    }
}
