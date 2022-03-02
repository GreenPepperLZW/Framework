package lzw.itemwriter.todb;

import lzw.pojo.Customer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * 向数据库写
 *
 * @author : lzw
 * @date : 2022/3/2
 * @since : 1.0
 */
@Configuration
public class ItemWriterDbConfig {

    @Autowired
    private DataSource dataSource;

    @Bean
    public JdbcBatchItemWriter<Customer> itemWriterDb() {
        JdbcBatchItemWriter writer = new JdbcBatchItemWriter<Customer>();
        writer.setDataSource(dataSource);
        writer.setSql("insert into customer(id,firstName,lastName,birthday) values" +
                "(:id,:firstName,:lastName,:birthday)");
        // 将Customer中对应属性的值与Sql语句中的四个值进行替换
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Customer>());
        return writer;
    }
}
