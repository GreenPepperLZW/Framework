package lzw.conf;

import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * 在Job运行时可以以key=value形式传递参数
 *
 * @author : lzw
 * @date : 2022/2/25
 * @since : 1.0
 */
//@Configuration
public class ParametersDemo implements StepExecutionListener {

    /**
     * @Description: 注入创建任务对象的对象
     */
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    /**
     * @Description: 任务的执行由Step决定,注入创建Step对象的对象
     */
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    private Map<String, JobParameter> parameters;

    @Bean
    public Job paramterJob() {
        return jobBuilderFactory.get("parameterJob")
                .start(parameterStep())
                .build();
    }

    /**
     * Job执行的是step，Job使用的数据肯定是在step中使用
     * 那我们只需要给step传递数据即可，那如何给step传递参数
     *
     * 1.使用step级别的监听来传递数据
     * @return
     */
    @Bean
    public Step parameterStep() {
        return stepBuilderFactory.get("parameterStep")
                .listener(this)
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        // 输出接收到的参数值
                        System.out.println(parameters.get("info"));
                        return RepeatStatus.FINISHED;
                    }
                }).build();
    }


    @Override
    public void beforeStep(StepExecution stepExecution) {
        // 在执行之前为 parameters 赋值
        parameters = stepExecution.getJobParameters().getParameters();
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return null;
    }
}
