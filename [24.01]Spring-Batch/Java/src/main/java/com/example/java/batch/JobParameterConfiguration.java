package com.example.java.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class JobParameterConfiguration {

    private final StepScopeParameter stepScopeParameter;

    @Bean
    @StepScope
    public Tasklet parameterTasklet() {
        return (contribution, chunkContext) -> {
            log.info(">>>> parameterTaskLet");
            log.info(">>>> stepScopeParameter: {}", stepScopeParameter.getDate());
            return RepeatStatus.FINISHED;
        };
    }

    // Bean 등록 하여 해당 Parameter 사용하기.
//    @Bean
//    public String jobScopeParameter(){
//        return "jobScopeParameter";
//    }
//    @Bean
//    @JobScope
//    public Step parameterStep(String jobScopeParameter, JobRepository jobRepository, Tasklet parameterTasklet, PlatformTransactionManager platformTransactionManager) {
//        log.info(">>>> parameterStep");
//        log.info(">>>> jobScopeParameter: {}", jobScopeParameter);
//        return new StepBuilder("parameterStep", jobRepository).tasklet(parameterTasklet, platformTransactionManager).build();
//    }

//    두 번째 방법. 환경 변수에 등록하여 사용하기.
    @Bean
    @JobScope
    public Step parameterStep(@Value("#{jobParameters[jobScopeParameter]}")String jobScopeParameter, JobRepository jobRepository, Tasklet parameterTasklet, PlatformTransactionManager platformTransactionManager) {
        log.info(">>>> parameterStep");
        log.info(">>>> jobScopeParameter: {}", jobScopeParameter);
        return new StepBuilder("parameterStep", jobRepository).tasklet(parameterTasklet, platformTransactionManager).build();
    }


// 세 전째 방법은 ApplicationRunner를 상속 받은 클래스를 구현하여 직접 파라미터를 주입해 job을 실행 시키는 방법.
//    @Component
//    @RequiredArgsConstructor
//    public class JobRunner implements ApplicationRunner {
//
//        private final JobLauncher jobLauncher;
//        private final Job job;
//
//        @Override
//        public void run(ApplicationArguments args) throws Exception {
//
//            JobParameters jobParameters = new JobParametersBuilder()
//                    .addString("name", "user1")
//                    .addLong("seq", 2L)
//                    .addDate("date", new Date())
//                    .addDouble("age", 16.5)
//                    .toJobParameters();
//
//            jobLauncher.run(job, jobParameters);
//        }
//    }

    @Bean
    public Job parameterJob(JobRepository jobRepository, Step parameterStep) {
        log.info(">>>> parameterJob");
        return new JobBuilder("parameterJob", jobRepository)
                .start(parameterStep)
                .build();
    }

}
