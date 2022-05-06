package com.lzw.config;

import com.lzw.bean.BookService;
import com.lzw.bean.Person;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

/**
 * 使用配置类替代配置文件
 * # @Configuration 注解告诉spring这是一个配置类
 *
 * @author : lzw
 * @date : 2022/5/5
 * @since : 1.0
 */
@Configuration
/*@ComponentScan(value = "com.lzw",excludeFilters = {
        // 将controller注解标注的类排除

         * type：排除规则按照：ANNOTATION、ASSIGNABLE_TYPE、ASPECTJ、REGEX、CUSTOM
         * ANNOTATION：按照注解来排除
         * ASSIGNABLE_TYPE： 按照给定的类型
         * ASPECTJ:ASPECTJ表达式
         * REGEX： 正则表达式
         * CUSTOM：自定义规则

        @ComponentScan.Filter(type = FilterType.ANNOTATION,classes = Controller.class),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,classes = BookService.class),
        @ComponentScan.Filter(type = FilterType.CUSTOM,classes = MyTypeFilter.class)
},

        // 扫描的时候只需要包含哪些组件，需要关闭默认的扫描规则，否则不生效
        includeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION,classes = Service.class)
},
        // 禁用默认的扫描规则
        useDefaultFilters = false
)*/

//  ComponentScans可以包含多条规则
@ComponentScans({
    @ComponentScan(value = "com.lzw",includeFilters = {
            @ComponentScan.Filter(type = FilterType.CUSTOM,classes = MyTypeFilter.class)
    },useDefaultFilters = false)
})

public class MainConfig {

    /**
     * # @Bean 注解替代<bean/> 标签
     * 类型为返回值类型
     * id默认是用方法名作为id
     *
     * @return
     */
    @Bean
    public Person person() {
        return new Person("李四", 20);
    }
}
