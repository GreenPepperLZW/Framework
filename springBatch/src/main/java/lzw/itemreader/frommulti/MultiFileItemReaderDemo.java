package lzw.itemreader.frommulti;

import lzw.listener.MyJobListener;
import lzw.pojo.Customer;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.validation.BindException;

/**
 * 从多个文件中读取信息
 *
 * @author : lzw
 * @date : 2022/3/1
 * @since : 1.0
 */
public class MultiFileItemReaderDemo {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    @Qualifier("multiFileWriter")
    private ItemWriter<? super Customer> multiFileWriter;

    /**
     * 读取多个文件所在的路径
     * 在类路径下所有一file开头的txt文件
     */
    @Value("classpath:/file*.txt")
    private Resource[] fileResources;

    @Bean
    public Job multiFileItemReaderDemoJob() {
        return jobBuilderFactory.get("multiFileItemReaderDemoJob")
                .start(multiFileItemReaderDemoStep())
                .listener(new MyJobListener())
                .build();
    }

    @Bean
    public Step multiFileItemReaderDemoStep() {

        return stepBuilderFactory.get("multiFileItemReaderDemoStep")
                .<Customer,Customer>chunk(5)
                .reader(multiFileReader())
                .writer(multiFileWriter)
                .build();
    }

    @Bean
    @StepScope
    public MultiResourceItemReader<Customer> multiFileReader() {
        MultiResourceItemReader <Customer> reader = new MultiResourceItemReader<>();
        // 虽说是多文件读取，但其实是逐个读取单个文件,所以要借助FlatFileItemReader来进行单个文件的读取
        reader.setDelegate(fileItemReaderDemoReader());
        reader.setResources(fileResources);
        return reader;
    }

    @Bean
    public FlatFileItemReader<Customer> fileItemReaderDemoReader() {
        FlatFileItemReader<Customer> reader = new FlatFileItemReader<Customer>();
        // 还需要这个路径吗？
        reader.setResource(new ClassPathResource("customer.txt"));
        //跳过第一行
        //reader.setLinesToSkip(1);
        //数据解析
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames(new String[]{"id","fistName","lastName","birthday"});
        //把解析出的一个数据映射为Customer对象
        DefaultLineMapper<Customer> mapper = new DefaultLineMapper<>();
        mapper.setLineTokenizer(tokenizer);
        mapper.setFieldSetMapper(new FieldSetMapper<Customer>() {

            /**
             * @Description: mapFieldSet 映射
             * @Param: [fieldSet]
             * @Return: com.example.springbatchdemo.pojo.Customer
             */
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
        mapper.afterPropertiesSet();
        reader.setLineMapper(mapper);
        return reader;
    }
}
