package com.example.java.quartz;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;

/**
 * Batch 기본 정보
 * @param <T> 입력 클래스
 * @param <V> 출력 클래스
 */
public interface BatchJob<T, V> {

    Job job(); // Spring Batch JobBuilder

    Step step(); // Spring Batch StepBuilder

    ItemReader<T> reader(); // Spring Batch ItemReader

    ItemWriter<V> writer(); // Spring Batch ItemWriter

    ItemProcessor<T, V> processor(); // Spring Batch ItemProcessor

    JobParameters createJobParameters(); // Spring Batch Job Parameters

    String getJobName(); // Job ClassName

    String getTriggerInterval(); // Quartz Scheduler Trigger Interval
}
