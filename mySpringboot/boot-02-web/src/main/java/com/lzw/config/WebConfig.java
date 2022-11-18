package com.lzw.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.HiddenHttpMethodFilter;

/**
 * @author : lzw
 * @date : 2022/11/18
 * @since : 1.0
 */
@Configuration(proxyBeanMethods = false)
public class WebConfig {

    private String methodParam = "_m";
    // 自定义表单提交时请求参数key
    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        HiddenHttpMethodFilter hiddenHttpMethodFilter = new HiddenHttpMethodFilter();
        hiddenHttpMethodFilter.setMethodParam(methodParam);
        return hiddenHttpMethodFilter;
    }
}
