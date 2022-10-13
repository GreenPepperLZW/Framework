package com.lzw.test;

import com.lzw.tx.TxConfig;
import com.lzw.tx.UserService;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 测试声明式事务 {@link com.lzw.tx.TxConfig}
 *
 * @author : lzw
 * @date : 2022/10/10
 * @since : 1.0
 */
public class IOCTest_Tx {


    @Test
    public void test01() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(TxConfig.class);
        System.out.println("容器创建完成....");

        UserService bean = applicationContext.getBean(UserService.class);
        bean.insertUser();
    }

}
