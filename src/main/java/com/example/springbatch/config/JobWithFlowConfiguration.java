package com.example.springbatch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JobWithFlowConfiguration {

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Bean
    public Step step1FlowJob() {
        return stepBuilderFactory.get("step1FlowJob")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("inside step 1 of Job with flow configuration");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Job job(Flow flow) {

        return jobBuilderFactory.get("jobWithFlow")
                .start(flow)
                .next(step1FlowJob())
                .end()
                .build();
    }

}
