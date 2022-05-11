package com.lzw.test;

import com.lzw.bean.Person;
import com.lzw.config.MainConfig2;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.Environment;

import java.util.Map;

/**
 * @author : lzw
 * @date : 2022/5/9
 * @since : 1.0
 */
public class IOCTest {

    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig2.class);


    public void printBeans(ApplicationContext applicationContext) {
        String[] definitionNames = applicationContext.getBeanDefinitionNames();
        for (String definitionName : definitionNames) {
            System.out.println(definitionName);
        }
    }

    /**
     * 测试@Scope和@Lazy 注解
     */
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

    /**
     * 测试@Conditional注解
     */
    @Test
    public void test02() {

        // 获取操作系统的名字
        Environment environment = applicationContext.getEnvironment();
        String property = environment.getProperty("os.name");
        System.out.println(property);

        String[] definitionNames = applicationContext.getBeanNamesForType(Person.class);
        for (String definitionName : definitionNames) {
            System.out.println(definitionName);
        }

        Map<String, Person> persons = applicationContext.getBeansOfType(Person.class);
        System.out.println(persons);
    }


    /**
     * 测试@Import组件
     */
    @Test
    public void test03() {
        printBeans(applicationContext);
    }


}
