package com.lzw;

import com.lzw.bean.Person;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author : lzw
 * @date : 2022/5/5
 * @since : 1.0
 */
public class MainTest {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
        Person persion = (Person)applicationContext.getBean("person");
        System.out.println(persion);


    }
}
