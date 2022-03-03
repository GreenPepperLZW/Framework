package lzw.joblauncher;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 测试jobLauncher调度器的使用
 * @author : lzw
 * @date : 2022/3/2
 * @since : 1.0
 */
@Configuration
public class JobLauncherDemo {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job jobLauncherDemoJob() {
        return jobBuilderFactory.get("jobLauncherDemoJob")
                .start(jobLauncherDemoStep())
                .build();
    }

    @Bean
    public Step jobLauncherDemoStep() {
        return stepBuilderFactory.get("jobLauncherDemoStep")
                .tasklet(new Tasklet(){
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("批处理启动。。。");
                        return RepeatStatus.FINISHED;
                    }
                }).build();
    }
}
