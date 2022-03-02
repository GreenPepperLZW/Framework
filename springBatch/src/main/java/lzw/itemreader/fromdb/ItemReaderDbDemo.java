package lzw.itemreader.fromdb;

import lzw.listener.MyJobListener;
import lzw.pojo.User;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * 从数据库中读取数据
 * <p>
 * 使用JdbcPagingItemReader对象从数据库中读取数据
 *
 * @author : lzw
 * @date : 2022/2/28
 * @since : 1.0
 */
//@Configuration
public class ItemReaderDbDemo {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public DataSource dataSource;

    @Autowired
    @Qualifier("dbJdbcWriter")
    private ItemWriter<? super User> dbJdbcWriter;

    @Bean
    public Job itemReaderDbDemoJob() {
        return jobBuilderFactory.get("itemReaderDbDemoJob")
                .start(itemReaderDbDemoStep())
                .listener(new MyJobListener())
                .build();
    }

    @Bean
    public Step itemReaderDbDemoStep() {
        return stepBuilderFactory.get("itemReaderDbDemoStep")
                // 一次读取两行数据
                .<User, User>chunk(2)
                .reader(dbJdbcReader())
                .writer(dbJdbcWriter)
                .build();
    }



    /**
     * StepScope注解的作用：和step同属于一个生命周期，只在job启动时才进行初始化。
     *
     * @return
     */
    @Bean
    @StepScope
    public JdbcPagingItemReader<User> dbJdbcReader() {
        JdbcPagingItemReader<User> reader = new JdbcPagingItemReader<>();

        // 指定数据源
        reader.setDataSource(dataSource);
        // 一次读取几条数据
        reader.setFetchSize(2);
        // 把读取到的记录转换成user对象
        reader.setRowMapper(new RowMapper<User>() {
            /**
             *
             * @param rs 结果集
             * @param rowNum 行数
             * @return
             * @throws SQLException
             */
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User user = new User();
                user.setId(rs.getInt(1));
                user.setUsername(rs.getString(2));
                user.setPassword(rs.getString(3));
                user.setAge(rs.getInt(4));
                return user;
            }
        });
        // 执行sql语句
        MySqlPagingQueryProvider provider = new MySqlPagingQueryProvider();
        // 指定查询哪些字段
        provider.setSelectClause("id,username,password,age");
        // 指定从哪张表中查询
        provider.setFromClause("from user");
        // 指定根据哪个字段进行排序
        Map<String, Order> sort = new HashMap<>(1);
        sort.put("id", Order.ASCENDING);
        provider.setSortKeys(sort);

        reader.setQueryProvider(provider);
        return reader;
    }


}
