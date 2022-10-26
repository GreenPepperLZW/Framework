package com.lzw.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;

/**
 * @author : lzw
 * @date : 2022/10/26
 * @since : 1.0
 */
// spring的容器不扫描controller，交给servlet容器去扫描
@ComponentScan(value = "com.lzw", excludeFilters = {
        @Filter(type = FilterType.ANNOTATION, classes = {Controller.class})
})
public class RootConfig {
}
