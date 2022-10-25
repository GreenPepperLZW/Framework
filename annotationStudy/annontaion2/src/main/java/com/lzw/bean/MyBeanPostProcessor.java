package com.lzw.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * 将后置处理器加入到容器进行工作，将会对当前容器中扫描到的所有的bean都进行处理
 * 执行时间：对象创建完成，属性赋值和初始化之前执行
 * @author : lzw
 * @date : 2022/5/11
 * @since : 1.0
 */
@Component
public class MyBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println(beanName + "=>" + "postProcessBeforeInitialization" + "=>" + bean);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println(beanName + "=>" + "postProcessAfterInitialization" + "=>" + bean);
        return bean;
    }
}
