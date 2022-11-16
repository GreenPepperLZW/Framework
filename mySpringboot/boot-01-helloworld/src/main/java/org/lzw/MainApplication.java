package org.lzw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author : lzw
 * @date : 2022/11/16
 * @since : 1.0
 */
@SpringBootApplication
public class MainApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(MainApplication.class, args);

        // 所有bean的名称
        String[] definitionNames = applicationContext.getBeanDefinitionNames();


        Object haha = applicationContext.getBean("haha");
        System.out.println("====" + haha);

        Object person = applicationContext.getBean("person");
        System.out.println("====" + person);
    }
}
