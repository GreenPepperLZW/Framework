package com.lzw.tx;


import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

/*
 *
 * 环境搭建：
 *  1.导入相关依赖
 *      数据源、数据库驱动、Spring-jdbc模块
 *  2.配置数据源、JdbcTemplate（Spring提供的简化操作数据库操作的工具）操作数据库
 *  3.给方法加上@Transactional,表示当前方法是一个事务
 *  4.想让@Transactional生效需要开启事务管理功能：
 *  5.配置事务管理器来控制事务
 *
 * 基本原理：
 *  1)、@EnableTransactionManagement默认给容器导入了两个组件
 *      AutoProxyRegistrar
 *      ProxyTransactionManagementConfiguration
 *  2)、
 *    AutoProxyRegistrar：给容器注册了一个InfrastructureAdvisorAutoProxyCreator类型的组件
 *    该组件原理与aop中AnnotationAwareAspectJAutoProxyCreator组件一样，
 *    都是利用后置处理器机制在对象创建以后，包装对象，返回一个代理对象（增强器），代理对象执行方法利用连接器链进行处理
 *
 *  3)、ProxyTransactionManagementConfiguration做了什么？
 *      给容器中注册事务增强器
 *          1)、事务增强器要用到事务注解的信息，将会解析@Transactional中的各个参数：关键类：AnnotationTransactionAttributeSource
 *          2)、事务拦截器
 *              TransactionInterceptor
 *              1)、先获取事务相关的属性
 *              2)、再获取TransactionAttribute，如果方法上的事务注解@Transactional(transactionManager = "transactionManager2")中没有指定transactionManager
 *                  则最终会根据PlatformTransactionManager.class类型从FactoryBean中获取
 *              3)、执行目标方法
 *                  如果异常，获取到事务管理器，利用事务管理回滚操作
 *                  如果正常，利用事务管理器，提交事务，并且返回执行结果
 *
 */

/**
 * 声明式事务测试
 *
 * @author : lzw
 * @date : 2022/10/12
 * @since : 1.0
 */
@Configuration
@EnableTransactionManagement
@ComponentScan({"com.lzw.tx"})
public class TxConfig {

    // 数据源
    @Bean
    public DataSource dataSource() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser("root");
        dataSource.setPassword("sasa");
        dataSource.setDriverClass("com.mysql.jdbc.Driver");
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/test");
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() throws PropertyVetoException {
        // spring对@Configuration类会特殊处理，给容器中加组件的方法，多次调用都是是从容器中找组件而已
        // 所以这里再次调用一遍dataSource()方法，并不会再创建一个DataSource的bean
        return new JdbcTemplate(dataSource());
    }

    // 注册事务管理器在容器中
    // 否则报错：No qualifying bean of type 'org.springframework.transaction.PlatformTransactionManager' available
    @Bean
    public PlatformTransactionManager transactionManager() throws PropertyVetoException {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean(name = "transactionManager2")
    public PlatformTransactionManager transactionManager2() throws PropertyVetoException {
        return new DataSourceTransactionManager(dataSource());
    }
}
