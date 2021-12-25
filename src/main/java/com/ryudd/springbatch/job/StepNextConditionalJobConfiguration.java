package com.ryudd.springbatch.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class StepNextConditionalJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job stepNextConditionalJob(){
        return jobBuilderFactory.get("stepNextConditionalJob")
                .start(conditionalStep1())
                .on("FAILED")//FAILED 일 경우
                .to(conditionalStep3())//Step 3으로 이동
                .on("*")//Step3의 결과와 관계없이
                .end()//Step3으로 이동하면 Flow 종료
                .from(conditionalStep1()) //Step1로부터
                .on("*")//FAILED 외에 모든 경우
                .to(conditionalStep2())//Step 2로 이동한다.
                .next(conditionalStep3())//Step 2가 정상 종료되면  step3으로 이동한다.
                .on("*")//step 3 결과와 관계없이
                .end()//step 3으로 이동하면 flow 종료
                .end()//job 종료
                .build();
    }

    /**
     * step 1 failed =  step1 -> step3
     * step 1 success = step1 -> step2 -> step3
     * @return
     */
    @Bean
    public Step conditionalStep1() {
        return stepBuilderFactory.get("step1")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>>This is stepNextConditionalJob Step1");
                    /**
                     *EXitStatus 를 Failed로 지정한다.
                     * 해당 status를 보고 flow가 진행된다.
                     */
                    //contribution.setExitStatus(ExitStatus.FAILED);
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step conditionalStep2() {
        return stepBuilderFactory.get("conditionalJobStep2")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>> This is stepNextConditionalJob Step2");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step conditionalStep3() {
        return stepBuilderFactory.get("conditionalJobStep3")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>> This is stepNextConditionalJob Step3");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
