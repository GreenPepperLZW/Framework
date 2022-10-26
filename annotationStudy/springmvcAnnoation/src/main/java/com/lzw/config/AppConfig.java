package com.lzw.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;

/**
 * @author : lzw
 * @date : 2022/10/26
 * @since : 1.0
 */
// servlet容器只扫描controller
// useDefaultFilters = false，禁用默认规则，否则不生效
@ComponentScan(value = "com.lzw", includeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Controller.class})
}, useDefaultFilters = false)
public class AppConfig {
}
