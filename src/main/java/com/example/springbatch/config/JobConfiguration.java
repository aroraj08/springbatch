package com.example.springbatch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
//@EnableBatchProcessing
public class JobConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("Step 1 of Job");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("Step 2 of job");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step step3() {
        return stepBuilderFactory.get("step3")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("Step 3 of job");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Job helloWorldJob() {
        return jobBuilderFactory.get("job1")
                .start(step1())
                .next(step2())
                .next(step3())
                .build();
    }

    @Bean
    public Job anotherJob() {

        return jobBuilderFactory.get("job2")
                .start(step1())
                .on("COMPLETED").to(step2())
                .from(step2()).on("COMPLETED").stopAndRestart(step3()) // Job will stop here and
                // will start from step3 with manual intervention
                .from(step3()).end()
                .build();
    }

}
