package lzw.itemwriter.todb;

import lzw.listener.MyJobListener;
import lzw.pojo.Customer;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 从文件中读取数据向数据库中写入
 *
 * @author : lzw
 * @date : 2022/3/2
 * @since : 1.0
 */
@Configuration
public class ItemWriterDbDemo {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private ItemWriter<? super Customer> itemWriterDb;

    @Autowired
    private ItemReader<? extends Customer> itemReaderDbDemoReader;

    @Bean
    public Job ItemWriterDemoJob() {
        return jobBuilderFactory.get("itemWriterDemoJob_3")
                .start(itemWriterDbDemoStep())
                .listener(new MyJobListener())
                .build();
    }

    @Bean
    public Step itemWriterDbDemoStep() {

        return stepBuilderFactory.get("itemWriterDbDemoStep")
                .<Customer, Customer>chunk(5)
                .reader(itemReaderDbDemoReader)
                .writer(itemWriterDb)
                .build();
    }


}
