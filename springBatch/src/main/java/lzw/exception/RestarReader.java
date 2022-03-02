package lzw.exception;

import lzw.pojo.Customer;
import org.springframework.batch.item.*;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;

/**
 * 测试读取出错后进行重试读取
 *
 * @author : lzw
 * @date : 2022/3/1
 * @since : 1.0
 */
@Component("restarReader")
public class RestarReader implements ItemStreamReader<Customer> {

    private FlatFileItemReader<Customer> customerFlatFileItemReader = new FlatFileItemReader<>();
    /**
     * 数据库batch_step_execution_context表中将记录下curLine字段最后的值，如果读取过程中出现了错误，下次
     * 重试时将会从curLine记录的值的位置开始读取，已经成功读取过的数据不会再重复读取
     */
    private Long curLine = 0L;

    private boolean restart = false;

    /**
     * 向数据库中持久化信息
     */
    private ExecutionContext executionContext;

    public RestarReader() {
        customerFlatFileItemReader.setResource(new ClassPathResource("restart.txt"));
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        // 制定4个表头字段
        tokenizer.setNames(new String[]{"id", "firstName", "lastName", "birthday"});
        // 把一行映射为Customer对象
        DefaultLineMapper<Customer> mapper = new DefaultLineMapper<>();
        mapper.setLineTokenizer(tokenizer);
        // 字段映射
        mapper.setFieldSetMapper(new FieldSetMapper<Customer>() {
            @Override
            public Customer mapFieldSet(FieldSet fieldSet) throws BindException {
                Customer customer = new Customer();
                customer.setId(fieldSet.readLong("id"));
                customer.setFirstName(fieldSet.readString("firstName"));
                customer.setLastName(fieldSet.readString("lastName"));
                customer.setBirthday(fieldSet.readString("birthday"));
                return customer;
            }
        });
        mapper.afterPropertiesSet();
        customerFlatFileItemReader.setLineMapper(mapper);
    }

    /**
     * 一次读取一批数据，一批的数量在 chunk()方法中定义
     * read方法一次读取一个数据
     *
     * @return
     * @throws Exception
     * @throws UnexpectedInputException
     * @throws ParseException
     * @throws NonTransientResourceException
     */
    @Override
    public Customer read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        Customer customer = null;
        this.curLine++;

        if (restart) {
            // 需要重试时，从上一行报错的位置开始读取，跳过已经正确读取的行数
            customerFlatFileItemReader.setLinesToSkip(this.curLine.intValue() - 1);
            restart = false;
            System.out.println("Start reading from line" + this.curLine);
        }

        customerFlatFileItemReader.open(this.executionContext);
        // 重试
        customer = customerFlatFileItemReader.read();

        // 手动制造异常
        if (customer != null && customer.getFirstName().equals("WrongName")) {
            throw new RuntimeException("Something wrong.Customer id:" + customer.getId());
        }

        return customer;
    }

    /**
     * step执行之前触发
     *
     * @param executionContext
     * @throws ItemStreamException
     */
    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        this.executionContext = executionContext;
        if (executionContext.containsKey("curLine")) {
            this.curLine = executionContext.getLong("curLine");
            this.restart = true;
        } else {
            this.curLine = 0L;
            executionContext.put("curLine", this.curLine);
            System.out.println("Start reading from line:" + this.curLine + 1);
        }
    }

    /**
     * step中每执行成功一批触发一次
     * 发生异常后不会执行update方法
     *
     * @param executionContext
     * @throws ItemStreamException
     */
    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        executionContext.put("curLine", this.curLine);
        System.out.println("update方法：" + this.curLine);
    }

    /**
     * step执行完后触发
     *
     * @throws ItemStreamException
     */
    @Override
    public void close() throws ItemStreamException {

    }
}
