package com.lzw.web.servlet;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;

import java.util.Arrays;

/**
 * 使用RegistrationBean方式注册servlet、Filter、Listener
 *
 * @author : lzw
 * @date : 2022/12/8
 * @since : 1.0
 */

@Configuration(proxyBeanMethods = true) // 保证依赖的组件是单实例的
public class MyRegistConfig {


    /**
     * 注册 /my,/my02路径不会被拦截器拦截，原因是 /my，/my02的匹配精度大于 “/”，dispatcherServlet的拦截路径就是 “/”
     * 具体配置方法：{@link DispatcherServletAutoConfiguration.DispatcherServletRegistrationConfiguration#dispatcherServletRegistration(DispatcherServlet, WebMvcProperties, ObjectProvider)}
     *
     * @return
     */
    @Bean
    public ServletRegistrationBean myServlet() {
        MyServlet myServlet = new MyServlet();
        return new ServletRegistrationBean(myServlet, "/my", "/my02");
    }

    @Bean
    public FilterRegistrationBean myFilter() {
        MyFilter myFilter = new MyFilter();

        // 拦截指定的Servlet
        // return new FilterRegistrationBean(myFilter,myServlet());

        // 拦截指定的路径
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(myFilter);
        filterRegistrationBean.setUrlPatterns(Arrays.asList("/my", "/css/*"));
        return filterRegistrationBean;
    }

    @Bean
    public ServletListenerRegistrationBean MyListener() {
        MyServletContextListener myServletContextListener = new MyServletContextListener();
        return new ServletListenerRegistrationBean(myServletContextListener);
    }
}
