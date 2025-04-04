package com.example.java.api.config;

import org.quartz.Job;
import org.quartz.spi.JobFactory;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.context.ApplicationContext;

import com.example.java.quartz.QuartzJob;

public class SpringJobFactory implements JobFactory {

    private final ApplicationContext applicationContext;

    public SpringJobFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Job newJob(TriggerFiredBundle bundle, org.quartz.Scheduler scheduler) {
        return applicationContext.getBean(QuartzJob.class);
    }
}

