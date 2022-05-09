package com.lzw.test;

import com.lzw.config.MainConfig;
import com.lzw.config.MainConfig2;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author : lzw
 * @date : 2022/5/9
 * @since : 1.0
 */
public class IOCTest {

    @Test
    public void test01() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig2.class);
        String[] definitionNames = applicationContext.getBeanDefinitionNames();
        for (String definitionName : definitionNames) {
            System.out.println(definitionName);
        }
        System.out.println("ioc容器创建完成....");
        Object persion = applicationContext.getBean("person");
        Object persion2 = applicationContext.getBean("person");
        System.out.println(persion == persion2);
    }


}
