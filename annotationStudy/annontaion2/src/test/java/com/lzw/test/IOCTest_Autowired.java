package com.lzw.test;

import com.lzw.bean.BookService;
import com.lzw.bean.Boss;
import com.lzw.config.MainConfigOfAutowired;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 测试属性自动注入
 *
 * @author : lzw
 * @date : 2022/10/09
 * @since : 1.0
 */
public class IOCTest_Autowired {

    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfAutowired.class);

    @Test
    public void test01() {
        System.out.println("容器创建完成....");
        BookService bean = applicationContext.getBean(BookService.class);
        bean.print();
        System.out.println(bean.toString());

        /*BookDao bookDao = applicationContext.getBean(BookDao.class);
        System.out.println(bookDao);
        */
        Boss bean1 = applicationContext.getBean(Boss.class);
        System.out.println(bean1);


        applicationContext.close();

    }

    public void printBeans(ApplicationContext applicationContext) {
        String[] definitionNames = applicationContext.getBeanDefinitionNames();
        for (String definitionName : definitionNames) {
            System.out.println(definitionName);
        }
    }
}
