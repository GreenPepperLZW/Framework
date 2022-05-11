package com.lzw.bean;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author : lzw
 * @date : 2022/5/11
 * @since : 1.0
 */
@Component
public class Dog {

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



}
