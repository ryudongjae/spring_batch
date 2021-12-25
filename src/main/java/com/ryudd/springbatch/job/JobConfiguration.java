package com.ryudd.springbatch.job;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class JobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job simpleJob(){
        return jobBuilderFactory.get("simpleJob")
                //simpleJob 이라는 이름으로 BatchJob을 생성한다.
                .start(simpleStep1(null))
                .next(simpleStep1(null))
                .build();
    }
    @Bean
    @JobScope
    public Step simpleStep1(@Value("#{jobParameters[requestDate]}") String requestDate) {
        return stepBuilderFactory.get("simpleStep1")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>> This is step1");
                    log.info(">>>> requestDate = {}",requestDate);
                    return RepeatStatus.FINISHED;
                }).build();
    }
    @Bean
    @JobScope
    public Step simpleStep2(@Value("#{jobParameters[requestDate]}") String requestDate) {
        return stepBuilderFactory.get("simpleStep1")
                .tasklet((contribution, chunkContext) -> {
                    //step안에서 수행될 기능들을 명시한다.
                    //Tasklet은 Step안에서 단일로 수행될 커스텀한 기능들을 선언할 때 사용한다.
                    log.info(">>>> This is step2");
                    log.info(">>>> requestDate = {}",requestDate);
                    return RepeatStatus.FINISHED;
                }).build();
    }

}
