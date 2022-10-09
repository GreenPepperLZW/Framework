package com.lzw.config;

import com.lzw.bean.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 测试给属性赋值,具体写法查看Person类 {@link com.lzw.bean.Person}
 *
 * @author : lzw
 * @date : 2022/10/8
 * @since : 1.0
 */
@Configuration
// 使用@PropertySource读取外部配置文件中的k/v保存到运行的环境变量中，person类就可以直接取到外部配置文件中的值
@PropertySource(value = {"classpath:/person.properties"})
public class MainConfigOfPropertyValues {

    @Bean
    public Person person() {
        return new Person();
    }
}
