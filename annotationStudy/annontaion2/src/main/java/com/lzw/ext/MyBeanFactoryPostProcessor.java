package com.lzw.ext;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author : lzw
 * @date : 2022/10/13
 * @since : 1.0
 */
@Component
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {


    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("====MyBeanFactoryPostProcessor...postProcessBeanFactory====");
        System.out.println("被定义的bean的个数：" + beanFactory.getBeanDefinitionCount());
        String[] definitionNames = beanFactory.getBeanDefinitionNames();
        System.out.println("bean的名字：" + Arrays.asList(definitionNames));
    }

}
