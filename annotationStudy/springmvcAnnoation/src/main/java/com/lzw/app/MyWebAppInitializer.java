package com.lzw.app;

import com.lzw.config.AppConfig;
import com.lzw.config.RootConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * @author : lzw
 * @date : 2022/10/26
 * @since : 1.0
 */
public class MyWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    // 获取根容器的配置类，（相当于没有进行整合之前的spring的配置文件），创建父容器
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{RootConfig.class};
    }

    // 获取web容器的配置类（相当于springMvc配置文件），创建子容器
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{AppConfig.class};
    }

    //获取DispatchServlet的映射信息
    @Override
    protected String[] getServletMappings() {
        // /，拦截所有请求，但是不拦截*.jsp页面
        // .*，拦截所有请求，同时也会拦截jsp页面，jsp页面时tomcat的jsp引擎解析的
        return new String[]{"/"};
    }
}
