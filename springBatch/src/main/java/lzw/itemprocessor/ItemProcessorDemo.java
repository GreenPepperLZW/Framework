package lzw.itemprocessor;


import lzw.listener.MyJobListener;
import lzw.pojo.Customer;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;


/**
 * processor使用
 * ItemProcessor介于读取和输出之间
 * ItemProcessor<I,O>用于处理业务逻辑，验证，过滤等功能
 *
 * @author : lzw
 * @date : 2022/3/2
 * @since : 1.0
 */
@Configuration
public class ItemProcessorDemo {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    @Qualifier("dbJdbcReader")
    private ItemReader<? extends Customer> dbJdbcReader;

    @Autowired
    @Qualifier("fileItemWriter")
    private ItemWriter<? super Customer> fileItemWriter;

    @Autowired
    private ItemProcessor<Customer, Customer> firstNameUpperProcessor;

    @Autowired
    private ItemProcessor<Customer, Customer> idFilterProcessor;


    @Bean
    public Job itemProcessorDemoJob() {
        return jobBuilderFactory.get("itemProcessorDemoJob")
                .start(itemProcessorDemoStep())
                .listener(new MyJobListener())
                .build();
    }


    @Bean
    public Step itemProcessorDemoStep() {
        return stepBuilderFactory.get("itemProcessorDemoStep")
                .<Customer, Customer>chunk(5)
                .reader(dbJdbcReader)
                //.processor(firstNameUpperProcessor)  只一种处理方式
                .processor(process())
                .writer(fileItemWriter)
                .build();
    }

    /**
     * 同时用多种方式处理方式
     * @return
     */
    @Bean
    public CompositeItemProcessor<Customer, Customer> process() {
        CompositeItemProcessor<Customer, Customer> processor = new CompositeItemProcessor<Customer, Customer>();
        List<ItemProcessor<Customer, Customer>> delegates = new ArrayList<>();
        delegates.add(firstNameUpperProcessor);
        delegates.add(idFilterProcessor);
        processor.setDelegates(delegates);
        return processor;
    }


}
