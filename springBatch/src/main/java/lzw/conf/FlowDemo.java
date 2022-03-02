package lzw.conf;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 1.Flow是多个Step的集合
 * 2.可以被多个Job复用
 * 3.使用FlowBuilder来创建
 *
 * @author : lzw
 * @date : 2022/2/25
 * @since : 1.0
 */
//@Configuration
@EnableBatchProcessing
public class FlowDemo {

    /**
     * @Description: 注入创建任务对象的对象
     */
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    /**
     * @Description: //任务的执行由Step决定,注入创建Step对象的对象
     */
    @Autowired
    private StepBuilderFactory stepBuilderFactory;


    /**
     * @Description: 创建Flow对象,指明F1ow对象包含哪些step
     */
    @Bean
    public Flow flowDemoFlow(){
        return new FlowBuilder<Flow>("flowDemoFlow")
                .start(flowDemoStep1())
                .next(flowDemoStep2())
                .build();
    }


    /**
     * 创建job对象
     */
    @Bean
    public Job fowDemoJob(){
        return jobBuilderFactory.get("fowDemoJob0")
                .start(flowDemoFlow())
                .next(flowDemoStep3())
                .end()
                .build();
    }

    @Bean
    public Step flowDemoStep1() {
        return stepBuilderFactory.get("flowDemoStep0")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("flowDemoStep0!");
                        return RepeatStatus.FINISHED;
                    }
                }).build();
    }

    @Bean
    public Step flowDemoStep2() {
        return stepBuilderFactory.get("flowDemoStep2")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("flowDemoStep2!");
                        return RepeatStatus.FINISHED;
                    }
                }).build();
    }

    @Bean
    public Step flowDemoStep3() {
        return stepBuilderFactory.get("flowDemoStep3")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("flowDemoStep3!");
                        return RepeatStatus.FINISHED;
                    }
                }).build();
    }


}
