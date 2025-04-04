package com.example.java.api.config;

import javax.sql.DataSource;

import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
public class QuartzConfig {
    @Autowired
    private ApplicationContext applicationContext;

    // Spring의 JobFactory를 사용하여 Quartz가 Spring 빈을 사용할 수 있게 설정
    @Bean
    public JobFactory jobFactory() {
        return new SpringJobFactory(applicationContext);
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(@Qualifier("dataSource") DataSource mainDataSource, JobFactory jobFactory) {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setDataSource(mainDataSource);  // main 데이터 소스를 설정
        factory.setJobFactory(jobFactory);      // Quartz에서 Spring 빈을 사용할 수 있도록 설정
        return factory;
    }
}
