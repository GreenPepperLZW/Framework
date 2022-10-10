package com.lzw.test;

import com.lzw.aop.MathCalculator;
import com.lzw.config.MainConfigOfAOP;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 测试AOP的几个注解 {@link com.lzw.config.MainConfigOfAOP}
 *
 * @author : lzw
 * @date : 2022/10/10
 * @since : 1.0
 */
public class IOCTest_AOP {


    @Test
    public void test01() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfAOP.class);
        System.out.println("容器创建完成....");
        MathCalculator bean = applicationContext.getBean(MathCalculator.class);
        bean.div(1, 1);
        System.out.println("==============");
        bean.div(1, 0);
        applicationContext.close();

    }

    public void printBeans(ApplicationContext applicationContext) {
        String[] definitionNames = applicationContext.getBeanDefinitionNames();
        for (String definitionName : definitionNames) {
            System.out.println(definitionName);
        }
    }
}
