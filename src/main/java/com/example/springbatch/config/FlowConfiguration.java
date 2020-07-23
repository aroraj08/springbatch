package com.example.springbatch.config;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class FlowConfiguration {

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Tasklet tasklet() {
        return new MyTasklet();
    }

    @Bean
    public Step step1Flow() {

        return stepBuilderFactory.get("step1Flow")
                .tasklet(tasklet()).build();
    }

    @Bean
    public Step step2Flow() {
        return stepBuilderFactory.get("step2Flow")
                .tasklet(tasklet()).build();
    }

    @Bean
    public Step step3Flow() {
        return stepBuilderFactory.get("step3Flow")
                .tasklet(tasklet()).build();
    }

    @Bean(name= "flow1")
    public Flow flow1() {

        FlowBuilder<Flow> flowBuilder = new FlowBuilder<Flow>("flow1");
        return flowBuilder.start(step1Flow())
                .next(step2Flow())
                .end();
    }

    @Bean(name = "flow2")
    public Flow flow2() {

        FlowBuilder<Flow> flowFlowBuilder = new FlowBuilder<>("flow2");
        return flowFlowBuilder.start(step3Flow())
                .end();
    }

    class MyTasklet implements Tasklet {

        @Override
        public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext)
                throws Exception {
            System.out.println(String.format("%s has been executed on thread %s" ,
                    chunkContext.getStepContext().getStepName(), Thread.currentThread().getName()));
            return RepeatStatus.FINISHED;
        }
    }
}


