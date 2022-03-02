package lzw.exception;

import lzw.pojo.Customer;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : lzw
 * @date : 2022/3/1
 * @since : 1.0
 */
//@Configuration
public class RestartDemo {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    @Qualifier("restarReader")
    public RestarReader restarReader;

    @Autowired
    @Qualifier("restartWriter")
    public RestartWriter restartWriter;

    @Bean
    public Job restartJob() {
        return jobBuilderFactory.get("restartJob_2")
                .start(restartStep())
                .build();
    }

    @Bean
    public Step restartStep() {
        return stepBuilderFactory.get("restartStep")
                .<Customer,Customer>chunk(10)
                .reader(restarReader)
                .writer(restartWriter)
                .build();
    }


}
