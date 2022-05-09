package com.lzw.config;

import com.lzw.bean.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

/**
 * 测试Scope注解
 * @author : lzw
 * @date : 2022/5/6
 * @since : 1.0
 */
@Configuration
public class MainConfig2 {

    // 默认注册的组件都是单实例的

    /**
     * @Scope 调整作用域
     * @see ConfigurableBeanFactory#SCOPE_PROTOTYPE  prototype
     * @see ConfigurableBeanFactory#SCOPE_SINGLETON  singleton
     * web环境下
     * @see org.springframework.web.context.WebApplicationContext#SCOPE_REQUEST 同一次请求创建一个实例
     * @see org.springframework.web.context.WebApplicationContext#SCOPE_SESSION 同一个session创建一个实例
     *
     * prototype：多实例，ioc容器启动并不会调用方法创建对象放在容器中，每次获取的时候才会调用方法创建对象
     * singleton：单实例，默认值，ioc容器启动会调用方法创建对象放到ioc容器中，以后每次获取就是直接从容器中拿
     *
     * 懒加载：
     *  针对单实例bean：默认在容器启动的时候创建对象
     *  懒加载：容器不创建对象，第一次使用的时候（获取）bean的时候创建对象，并进行一些初始化
     */
//    @Scope()
    @Bean
    @Lazy
    public Person person() {
        // 测试容什么时候调用方法
        System.out.println("给容器添加Person");
        return new Person("张三",25);
    }


}
