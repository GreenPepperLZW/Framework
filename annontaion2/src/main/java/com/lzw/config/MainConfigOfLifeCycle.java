package com.lzw.config;

import com.lzw.bean.Car;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * 测试bean的生命周期
 * <p>
 * bean的生命周期：bean创建————初始化————销毁的过程
 * <p>
 * bean的生命周期由容器管理，也可以自定义初始化和销毁的方法
 * <p>
 *
 *  构造器（对象创建）
 *      单实例：在容器启动的时候创建对象
 *      多实例：在每次获取对象的时候创建
 *  初始化：
 *      对象创建完成，并赋值好，调用初始化方法
 *  销毁:
 *      单实例：容器关闭的时候销毁
 *      多实例：容器不会管理这个bean;容器不会调用销毁方法
 *
 * 1).指定初始化和销毁方法:
 *      通过@Bean注解指定init-method和destroy-method方法
 *
 * @author : lzw
 * @date : 2022/5/11
 * @since : 1.0
 */
@Configuration
public class MainConfigOfLifeCycle {

//    @Scope("prototype")
    @Bean(initMethod = "init",destroyMethod = "destroy")
    public Car car() {
        return new Car();
    }

}
