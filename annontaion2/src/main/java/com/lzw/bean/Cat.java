package com.lzw.bean;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * 实现接口的方式来控制bean的初始化和销毁
 *
 * @author : lzw
 * @date : 2022/5/11
 * @since : 1.0
 */
@Component
public class Cat implements InitializingBean, DisposableBean {

    public Cat() {
        System.out.println("cat constructor...");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("cat destroy....");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("cat...afterPropertiesSet....");

    }
}
