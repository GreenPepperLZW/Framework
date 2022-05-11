package com.lzw.config;

import com.lzw.bean.Car;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
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
 * 2).通过bean实现InitializingBean（定义初始化逻辑），DisposableBean（定义销毁逻辑）
 * 3).使用JSR250规范：
 *      # @PostConstruct：在bean创建完成并且属性赋值完成，来执行初始化方法
 *      # @PreDestroy：在容器销毁之前通知我们进行清理工作
 * 4).BeanPostProcessor【interface】：bean的后置处理器
 *      在bean初始化前后进行一些初始化工作：
 *      postProcessBeforeInitialization：
 * @author : lzw
 * @date : 2022/5/11
 * @since : 1.0
 */
@ComponentScan(value = "com.lzw.bean")
@Configuration
public class MainConfigOfLifeCycle {

//    @Scope("prototype")
    @Bean(initMethod = "init",destroyMethod = "destroy")
    public Car car() {
        return new Car();
    }

}
