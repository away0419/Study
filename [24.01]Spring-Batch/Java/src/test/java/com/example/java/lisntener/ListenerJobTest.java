package com.example.java.lisntener;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.java.JavaApplication;

@SpringBatchTest
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {JavaApplication.class, ListenerJobConfiguration.class})
public class ListenerJobTest {
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private Job listenerJob;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        // JobLauncherTestUtils에 Job 설정
        jobLauncherTestUtils.setJob(listenerJob);
    }

    @Test
    void testListenerJob() throws Exception {
        // given
        JobParameters jobParameters = jobLauncherTestUtils.getUniqueJobParameters();

        // when
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

        // then
        assertThat(jobExecution.getStatus()).isEqualTo(org.springframework.batch.core.BatchStatus.COMPLETED);

        // StepExecution 검증
        StepExecution stepExecution = jobExecution.getStepExecutions().iterator().next();
        assertThat(stepExecution.getStepName()).isEqualTo("listenerStep");
        assertThat(stepExecution.getCommitCount()).isEqualTo(1); // Tasklet은 보통 한 번만 커밋
        assertThat(stepExecution.getReadCount()).isEqualTo(0);  // Tasklet에서는 기본적으로 읽기가 없음
        assertThat(stepExecution.getWriteCount()).isEqualTo(0); // Tasklet에서는 기본적으로 쓰기가 없음
    }

    /**
     * BatchTestConfig: JobLauncherTestUtils 빈을 등록하기 위한 설정
     */
    @TestConfiguration
    static class BatchTestConfig {
        @Bean
        public JobLauncherTestUtils jobLauncherTestUtils() {
            return new JobLauncherTestUtils();
        }
    }

}
