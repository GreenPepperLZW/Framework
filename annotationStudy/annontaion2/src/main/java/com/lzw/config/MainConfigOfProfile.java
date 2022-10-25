package com.lzw.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.StringValueResolver;

import javax.sql.DataSource;

/**
 * # @Profile:指定组件在哪个环境的情况下才能被注册到容器中
 * 1).加了环境表示的bean,只有这个环境被激活的时候才能注册到容器里面去，默认是default环境
 * 2).@Profile写在配置类上，只有是指定的环境的时候，整个配置类里面的所有配置才能开始生效
 * 3).没有标注环境标识的bean，在任何情况下都是加载的
 * @author : lzw
 * @date : 2022/10/9
 * @since : 1.0
 */
// 加载外部配置文件
@PropertySource("classpath:/dbconfig.properties")
@Configuration
public class MainConfigOfProfile implements EmbeddedValueResolverAware {

    // 复习一遍自动赋值,使用不同的方式获取变量

    @Value("db.user")
    private String userName;

    private String driverClass;

    private StringValueResolver valueResolver;

    /**
     * 测试数据源
     *
     * @return
     * @throws Exception
     */
    //@Profile("default")
    @Profile("test")
    @Bean("testDataSource")
    public DataSource dataSourceTest(@Value("db.password") String passwd) throws Exception {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser(userName);
        dataSource.setPassword(passwd);
        // 使用值解析器获取变量
        dataSource.setJdbcUrl(valueResolver.resolveStringValue("${db.jdbcUrl}"));
        dataSource.setDriverClass(driverClass);
        return dataSource;
    }

    /**
     * 开发数据源
     *
     * @return
     * @throws Exception
     */
    @Profile("dev")
    @Bean("devDataSource")
    public DataSource dataSourceDev() throws Exception {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser("root");
        dataSource.setPassword("sasa");
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/test");
        dataSource.setDriverClass("com.mysql.jdbc.Driver");
        return dataSource;
    }

    /**
     * 生产数据源
     *
     * @return
     * @throws Exception
     */
    @Profile("prod")
    @Bean("prodDataSource")
    public DataSource dataSourcePro() throws Exception {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser("root");
        dataSource.setPassword("sasa");
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/test");
        dataSource.setDriverClass("com.mysql.jdbc.Driver");
        return dataSource;
    }

    @Override
    public void setEmbeddedValueResolver(StringValueResolver resolver) {
        // 获取值解析器
        this.valueResolver = resolver;
        driverClass = valueResolver.resolveStringValue("${db.driverClass}");
    }
}
