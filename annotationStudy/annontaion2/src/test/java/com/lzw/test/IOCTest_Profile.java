package com.lzw.test;

import com.lzw.config.MainConfigOfProfile;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.sql.DataSource;

/**
 * 测试@Profile注解
 *
 * @author : lzw
 * @date : 2022/5/11
 * @since : 1.0
 */
public class IOCTest_Profile {

    /**
     * 切换环境的方法
     * 1.使用命令行动态参数，-Dspring.profiles.active=test
     * 2.使用代码的方式切换环境
     */

    @Test
    public void test01() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfProfile.class);
        System.out.println("容器创建完成。。。。");
        String[] beanNamesForType = applicationContext.getBeanNamesForType(DataSource.class);
        for (String s : beanNamesForType) {
            System.out.println(s);
        }

        applicationContext.close();
    }

    /**
     * 使用代码的方式切换环境
     */
    @Test
    public void test02() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        //1.创建一个applicationContext
        //2.设置需要激活的环境
        applicationContext.getEnvironment().setActiveProfiles("test", "dev");
        //3.注册主配置类
        applicationContext.register(MainConfigOfProfile.class);
        //4.启动刷新容器
        applicationContext.refresh();

        System.out.println("容器创建完成。。。。");
        String[] beanNamesForType = applicationContext.getBeanNamesForType(DataSource.class);
        for (String s : beanNamesForType) {
            System.out.println(s);
        }

        applicationContext.close();
    }
}
