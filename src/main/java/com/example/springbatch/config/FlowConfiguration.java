package com.example.springbatch.config;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlowConfiguration {

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Step step1Flow() {

        return stepBuilderFactory.get("step1Flow")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("inside step 1 of flow");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step step2Flow() {
        return stepBuilderFactory.get("step2Flow")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("inside step 2 of flow");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Flow flow() {

        FlowBuilder<Flow> flowBuilder = new FlowBuilder<Flow>("myFlow");
        return flowBuilder.start(step1Flow())
                .next(step2Flow())
                .end();
    }
}
