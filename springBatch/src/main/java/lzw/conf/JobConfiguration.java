package lzw.conf;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : lzw
 * @date : 2022/2/24
 * @since : 1.0
 */
//@Configuration
@EnableBatchProcessing
public class JobConfiguration {


    /**
     * 注入创建任务对象的工厂
     */
    @Autowired
    private JobBuilderFactory jobBuilderFactory;


    /**
     * 注入创建setp的工厂
     */
    @Autowired
    private StepBuilderFactory stepBuilderFactory;


    /**
     * 创建任务对象
     * @return
     */
    @Bean
    public Job helloJob() {
        return jobBuilderFactory.get("helloJob")
                // 指定任务执行的step
                .start(step1()).build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                // 具体要执行的功能
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("hello world");
                        // 返回执行后的状态，决定还要不要执行后面的step
                        return RepeatStatus.FINISHED;
                    }
                }).build();
    }

}
