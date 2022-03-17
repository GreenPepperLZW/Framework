package lzw.joblauncher;

import lzw.pojo.Customer;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.validation.BindException;

import javax.sql.DataSource;

/**
 * 测试jobLauncher调度器的使用
 * @author : lzw
 * @date : 2022/3/2
 * @since : 1.0
 */
@Configuration
public class JobLauncherDemo{

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private DataSource dataSource;

    private static final String msg = null;


    @Bean
    public Job jobLauncherDemoJob(Step jobLauncherDemoStep) {
        return jobBuilderFactory.get("jobLauncherDemoJob")
                .start(jobLauncherDemoStep)
                .build();
    }

    @Bean
    public Step jobLauncherDemoStep(@Qualifier("itemLauncherReader") FlatFileItemReader<Customer> reader,
                                    @Qualifier("itemLauncherWriterDb") JdbcBatchItemWriter<Customer> writer) {
        return stepBuilderFactory.get("jobLauncherDemoStep")
                .<Customer, Customer>chunk(5)
                .reader(reader)
                .writer(writer)
                .build();
    }

    @Bean(name = "itemLauncherWriterDb")
    @StepScope
    public JdbcBatchItemWriter<Customer> itemLauncherWriterDb() {
        JdbcBatchItemWriter writer = new JdbcBatchItemWriter<Customer>();
        writer.setDataSource(dataSource);
        writer.setSql("insert into customer(id,firstName,lastName,birthday) values" +
                "(:id,:firstName,:lastName,:birthday)");
        // 将Customer中对应属性的值与Sql语句中的四个值进行替换
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Customer>());
        return writer;
    }



    @Bean(name = "itemLauncherReader")
    @StepScope
    public FlatFileItemReader<Customer> itemLauncherReader(@Value("#{jobParameters[msg]}") String msg) {
        System.out.println("=========参数："+msg);
         return new FlatFileItemReaderBuilder<Customer>()
                .name("itemLauncherReader")
                 .resource(new ClassPathResource("customer.txt"))
                .delimited()
                .delimiter("|")
                .names(new String[]{"id", "fistName", "lastName", "birthday"})
                .fieldSetMapper(new CustFileSetMapper())
                .linesToSkip(1)
                .build();

    }

    public class CustFileSetMapper implements FieldSetMapper<Customer>{

        @Override
        public Customer mapFieldSet(FieldSet fieldSet) throws BindException {
            Customer customer = new Customer();
            customer.setId(fieldSet.readLong("id"));
            customer.setFirstName(fieldSet.readString("fistName"));
            customer.setLastName(fieldSet.readString("lastName"));
            customer.setBirthday(fieldSet.readString("birthday"));
            return customer;
        }
    }

}

