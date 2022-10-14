package com.lzw.test;

import com.lzw.ext.ExtConfig;
import org.junit.Test;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 测试扩展原理 {@link ExtConfig}
 *
 * @author : lzw
 * @date : 2022/10/13
 * @since : 1.0
 */
public class IOCTest_Ext {


    @Test
    public void test01() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ExtConfig.class);
        System.out.println("容器创建完成....");

        //发布事件
        applicationContext.publishEvent(new ApplicationEvent(new String("自定义发布的事件")) {
        });
        applicationContext.close();
    }
}
