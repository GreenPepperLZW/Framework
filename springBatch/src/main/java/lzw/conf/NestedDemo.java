package lzw.conf;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.JobStepBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * 嵌套job使用，在job中执行job
 *
 * @author : lzw
 * @date : 2022/2/25
 * @since : 1.0
 */
//@Configuration
public class NestedDemo {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private JobLauncher launcher;

    /**
     * {@link ChildJob1}
     */
    @Autowired
    private Job childJobOne;

    /**
     * {@link ChildJob2}
     */
    @Autowired
    private Job childJobTwo;

    /**
     * 此时有三个job
     * 启动时应该运行哪一个呢，这个需要在配置文件中指定
     * @param jobRepository
     * @param transactionManager
     * @return
     */
    @Bean
    public Job parentJob(JobRepository jobRepository, PlatformTransactionManager transactionManager){
        return jobBuilderFactory.get("parentJobs")
                .start(childJob1(jobRepository,transactionManager))
                .next(childJob2(jobRepository,transactionManager))
                .build();
    }

    /**
     * @Description: childJob2返回Job类型的Step，特殊Step
     */
    private Step childJob2(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        // 将job类型转为step类型
        return new JobStepBuilder(new StepBuilder("childJob2"))
                .job(childJobTwo)
                // 使用启动父job的启动器来启动子job
                .launcher(launcher)
                // 指定序列化存储对象
                .repository(jobRepository)
                // 事务管理器
                .transactionManager(transactionManager)
                .build();

    }

    private Step childJob1(JobRepository jobRepository,PlatformTransactionManager transactionManager) {
        return new JobStepBuilder(new StepBuilder("childJob1"))
                .job(childJobOne)
                .launcher(launcher)
                .repository(jobRepository)
                .transactionManager(transactionManager)
                .build();
    }
}
