package com.example.springbatch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

@Configuration
public class SplitConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    @Qualifier("flow1")
    private Flow flow1;

    @Autowired
    @Qualifier("flow2")
    private Flow flow2;

    @Bean
    public Job splitJob() {

        return jobBuilderFactory.get("splitJob")
                .start(flow1)
                .split(new SimpleAsyncTaskExecutor()).add(flow2)
                .end()
                .build();
    }
}
