package com.lzw;

import com.lzw.bean.Person;
import com.lzw.config.MainConfig;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 使用配置文件方式和配置类方式给容器中注册组件
 * @author : lzw
 * @date : 2022/5/5
 * @since : 1.0
 */
public class MainTest {

    public static void main(String[] args) {

    }

    /**
     * 加载配置文件获取bean
     */
    @Test
    public void test() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
        Person persion = (Person)applicationContext.getBean("person");
        System.out.println(persion);
    }

    /**
     * 加载配置类获取bean
     */
    @Test
    public void teset() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig.class);
        Person bean = applicationContext.getBean(Person.class);
        System.out.println(bean);
        System.out.println("=================");
        // 根据名字获取bean的名称,获取容器中所有的bean
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println("beanDefinitionName："+beanDefinitionName);
        }
        System.out.println("=================");
        // 根据类型获取bean的名称
        String[] beanNamesForType = applicationContext.getBeanNamesForType(Person.class);
        for (String s : beanNamesForType) {
            System.out.println("beanNamesForType："+s);
        }

        System.out.println("=================");
    }

    @Test
    public void test03() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig.class);
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println("#"+beanDefinitionName);
        }
    }

}



















