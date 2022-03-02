package lzw.itemreader.fromfile;

import lzw.listener.MyJobListener;
import lzw.pojo.Customer;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.validation.BindException;

import java.util.Set;

/**
 * 从普通文件中读取数据
 *
 * @author : lzw
 * @date : 2022/2/28
 * @since : 1.0
 */
//@Configuration
public class FileItemReaderDemo {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;


    @Autowired
    @Qualifier("flatFileItemWriter")
    private ItemWriter<? super Customer> flatFileItemWriter;

    @Bean
    public Job fileItemReaderDemoJob() {
        return jobBuilderFactory.get("fileItemReaderDemoJob")
                .start(fileItemReaderDemoStep())
                .listener(new MyJobListener())
                .build();
    }


    @Bean
    public Step fileItemReaderDemoStep() {

        return stepBuilderFactory.get("fileItemReaderDemoStep")
                .<Customer, Customer>chunk(5)
                .reader(fileItemReaderDemoReader())
                .writer(flatFileItemWriter)
                .build();
    }

    @Bean
    @StepScope
    public FlatFileItemReader<Customer> fileItemReaderDemoReader() {
        FlatFileItemReader<Customer> reader = new FlatFileItemReader<>();
        // 指定文件路径
        reader.setResource(new ClassPathResource("customer.txt"));
        // 跳过第1行，不进行读取
        reader.setLinesToSkip(1);
        // 解析数据
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        // 解析的文件中对应的字段d
        tokenizer.setNames(new String[]{"id", "fistName", "lastName", "birthday"});
        // 把解析出来的一行数据映射为Customer对象
        DefaultLineMapper<Customer> mapper = new DefaultLineMapper<>();

        mapper.setLineTokenizer(tokenizer);

        // 将解析出来的数据映射到customer当中的属性
        mapper.setFieldSetMapper(new FieldSetMapper<Customer>() {
            @Override
            public Customer mapFieldSet(FieldSet fieldSet) throws BindException {
                Customer customer = new Customer();
                customer.setId(fieldSet.readLong("id"));
                customer.setFirstName(fieldSet.readString("fistName"));
                customer.setLastName(fieldSet.readString("lastName"));
                customer.setBirthday(fieldSet.readString("birthday"));
                return customer;
            }
        });
        // 检查tokenizer和fieldSetMapper是否为空
        mapper.afterPropertiesSet();
        reader.setLineMapper(mapper);
        return reader;
    }
}
