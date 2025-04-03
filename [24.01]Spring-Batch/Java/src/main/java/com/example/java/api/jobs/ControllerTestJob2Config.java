package com.example.java.api.jobs;

import java.util.Arrays;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ControllerTestJob2Config {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    private String getJobName() {
        return "ControllerTestJob2";
    }

    @Bean(name = "ControllerTestJob2")
    public Job job() {
        return new JobBuilder(getJobName(), jobRepository)
            .start(this.step())
            .build();
    }

    @Bean(name = "ControllerTestStep2")
    public Step step() {
        log.info(">>>> {} Step 시작", getJobName());
        return new StepBuilder(getJobName() + "Step", jobRepository)
            .<Integer, String>chunk(10, transactionManager)
            .reader(this.reader())
            .processor(processor())
            .writer(writer())
            .build();
    }

    /*
        해당 부분을 Bean 등록 하고 StepScope 를 넣는 이유는 다음과 같다.
        만약 하나라도 없다면 reader() 메소드를 사용하는 Step에서 한번만 실행하고 생성된 ItemReader를 계속 사용한다.
        따라서 한번 호출 이후에는 이미 Writer를 통해 데이터가 빠져나가 ItemReader 데이터가 없어지므로 정상 작동하지 않는다.
        왜 한번만 호출하냐 Job이 @Bean 등록 되어 있기때문으로 추측된다. Job이라는 객체를 한번만 만들어지기 때문에 연쇄적으로
        step(), reader(), writer() 등은 하나만 만들어 진다. 이후 해당 Job + Parameter로 새로운 인스턴스를 만들어도 결국
        Job은 동일한 하나의 객체를 공유하여 사용하기 때문에 원하는 결과가 나오지 않는 것이다.
        결국, Job을 새로 생성해서 jobLauncher.run(job, jobParameters); 해야 하는데 이럴 경우 Spring Batch의 기능을 활용하기 어려워진다.
        이를 해결하는 방법은 결국 reader()에 @StepScope 적용하여 job 실행될 때마다 새로운 ItemReader 만드는게 최선이다.
        따라서 Reader 에 @Bean, @StepScope 적용해야 한다.
        (Step, Job 은 Builder 시 매개변수로 던지는 이름이 Bean name이 되지만 reader() 는 그렇지 못하므로 명시해줘야하는 번거러움이 있다.)
     */
    @Bean(name ="ControllerTestReader2")
    @StepScope
    public ItemReader<Integer> reader() {
        return new ListItemReader<>(Arrays.asList(321321, 2, 3));
    }

    private ItemProcessor<Integer, String> processor() {
        return String::valueOf;
    }

    private ItemWriter<String> writer() {
        return items -> items.forEach(log::info);
    }

}
