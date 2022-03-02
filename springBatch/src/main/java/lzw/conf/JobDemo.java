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
 * 一个job包含多个step示例
 *
 * @author : lzw
 * @date : 2022/2/25
 * @since : 1.0
 */
//@Configuration
@EnableBatchProcessing
public class JobDemo {

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
     * @Description: 让step1, step2, step3依次执行
     */
    @Bean
    public Job jobDemoJob() {
        return jobBuilderFactory.get("jobDemoJob")
                .start(step1())
                // setp1 执行完状态为 COMPLETED 时再去执行 step1
                .on("COMPLETED").to(step1())
                // step2 满足COMPLETED再到达step3
                .from(step2()).on("COMPLETED").to(step3())
                .from(step3())
                .end()
                .build();

        // .from(step2()).on("COMPLETED").fail() 让step失败
        // .from(step2()).on("COMPLETED").stopAndRestart() 让step停止并重新启动

        // .start(step1())
        // .next(step2())
        // .next(step3())
        // .build();


    }

    @Bean
    public Step step3() {
        return stepBuilderFactory.get("step3")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("step3");
                        return RepeatStatus.FINISHED;
                    }
                }).build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("step2");
                        return RepeatStatus.FINISHED;
                    }
                }).build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("step1");
                        return RepeatStatus.FINISHED;
                    }
                }).build();
    }

}
