package com.lzw.test;

import com.lzw.bean.Car;
import com.lzw.config.MainConfigOfLifeCycle;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author : lzw
 * @date : 2022/5/11
 * @since : 1.0
 */
public class IOCTest_LifeCycle {

    @Test
    public void test01() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfLifeCycle.class);
        System.out.println("容器创建完成。。。。");
//        Car bean = applicationContext.getBean(Car.class);
        applicationContext.close();

    }
}
