package com.lzw.bean;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * 实现ApplicationContextAware，ApplicationContextAwareProcessor是 {@link org.springframework.beans.factory.config.BeanPostProcessor} 众多实现者之一
 * 它可以将IOC容器注入到当前对象中
 *
 * @author : lzw
 * @date : 2022/5/11
 * @since : 1.0
 */
@Component
public class Dog implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    public Dog() {
        System.out.println("dog constructor......");
    }

    // 对象创建并赋值之后调用
    @PostConstruct
    public void init() {
        System.out.println("dog PostConstruct.......");
    }

    // 容器移除之前调用
    @PreDestroy
    public void destroy() {
        System.out.println("dog PreDestroy");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
