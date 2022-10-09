package com.lzw.test;

import com.lzw.bean.Person;
import com.lzw.config.MainConfigOfPropertyValues;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @author : lzw
 * @date : 2022/10/09
 * @since : 1.0
 */
public class IOCTest_PropertyOfValue {

    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfPropertyValues.class);

    @Test
    public void test01() {
        System.out.println("容器创建完成。。。。");
        printBeans(applicationContext);

        Person person = (Person) applicationContext.getBean("person");
        // 获取环境环境变量中的值
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        String property = environment.getProperty("person.nickName");
        System.out.println(property);


        System.out.println(person);
        applicationContext.close();

    }

    public void printBeans(ApplicationContext applicationContext) {
        String[] definitionNames = applicationContext.getBeanDefinitionNames();
        for (String definitionName : definitionNames) {
            System.out.println(definitionName);
        }
    }
}
