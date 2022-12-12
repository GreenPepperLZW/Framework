package com.lzw.web.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * @author : lzw
 * @date : 2022/12/12
 * @since : 1.0
 */
@Deprecated
// 使用druid-stater启动器后，这些配置不需要自己配置
//@Configuration
public class MyDataSourceConfig {

    @Bean
    @ConfigurationProperties("spring.datasource")
    public DataSource dataSource() throws SQLException {
        DruidDataSource druidDataSource = new DruidDataSource();
        // 加入监控功能,设置过滤器,stat是StatFilter的别名，这个别名映射配置信息保存在druid-xxx.jar!/META-INF/druid-filter.properties。
        druidDataSource.setFilters("stat");
        // 加入防火墙功能，预防sql注入
        druidDataSource.setFilters("wall");
        return druidDataSource;
    }


    /**
     * 配置监控页
     */
    @Bean
    public ServletRegistrationBean statViewSerlvet() {
        StatViewServlet statViewServlet = new StatViewServlet();
        ServletRegistrationBean<StatViewServlet> statViewServletServletRegistrationBean = new ServletRegistrationBean<>(statViewServlet, "/druid/*");
        // 配置登录页的登录用户名、密码
        statViewServletServletRegistrationBean.addInitParameter("loginUsername", "a");
        statViewServletServletRegistrationBean.addInitParameter("loginPassword", "a");
        return statViewServletServletRegistrationBean;
    }

    /**
     * WebStatFilter 用于采集web-jdbc关联监控的数据
     */
    @Bean
    public FilterRegistrationBean webStatFilter() {
        WebStatFilter webStatFilter = new WebStatFilter();
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(webStatFilter);
        // 过滤哪些路径
        filterRegistrationBean.setUrlPatterns(Arrays.asList("/*"));
        // 放行哪些路径
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }

}
