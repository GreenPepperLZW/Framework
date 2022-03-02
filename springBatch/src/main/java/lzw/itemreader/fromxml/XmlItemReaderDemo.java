package lzw.itemreader.fromxml;

import lzw.listener.MyJobListener;
import lzw.pojo.Customer;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.xstream.XStreamMarshaller;

import java.util.HashMap;
import java.util.Map;

/**
 * 从xml中读取数据
 *
 * @author : lzw
 * @date : 2022/3/1
 * @since : 1.0
 */
public class XmlItemReaderDemo {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    @Qualifier("xmlFileWriter")
    private ItemWriter<? super Customer> xmlFileWriter;

    @Bean
    public Job xmlItemReaderDemoJob() {
        return jobBuilderFactory.get("xmlItemReaderDemoJob")
                .start(xmlItemReaderDemoStep())
                .listener(new MyJobListener())
                .build();
    }

    @Bean
    public Step xmlItemReaderDemoStep() {

        return stepBuilderFactory.get("xmlItemReaderDemoStep")
                .<Customer,Customer>chunk(5)
                .reader(xmlFileReader())
                .writer(xmlFileWriter)
                .build();
    }

    @Bean
    @StepScope
    public StaxEventItemReader<Customer> xmlFileReader() {
        StaxEventItemReader<Customer> reader = new StaxEventItemReader<Customer>();
        // 指定文件位置
        reader.setResource(new ClassPathResource("customer.xml"));
        // 跳过第一行
        reader.setFragmentRootElementName("customer");
        // 把xml转成对象
        XStreamMarshaller unmarshaller = new XStreamMarshaller();
        // 告诉unmarshaller把xml转成什么类型
        Map<String,Class> map = new HashMap<>();
        map.put("customer",Customer.class);
        unmarshaller.setAliases(map);

        reader.setUnmarshaller(unmarshaller);
        return reader;

    }
}
