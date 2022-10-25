package com.lzw.ext;

import com.lzw.bean.Blue;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.stereotype.Component;

/**
 * BeanDefinitionRegistryPostProcessor 是 BeanFactoryPostProcessor 接口的子接口
 *
 * @author : lzw
 * @date : 2022/10/13
 * @see org.springframework.beans.factory.config.BeanFactoryPostProcessor
 * @since : 1.0
 */
@Component
public class MyBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {


    // BeanDefinitionRegistry 是bean定义信息的保存中心
    // 之后BeanFactory就是按照BeanDefinitionRegistry保存的每一个bea定义的信息来创建实例的
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        System.out.println("postProcessBeanDefinitionRegistry...bean的数量：" + registry.getBeanDefinitionCount());
//        RootBeanDefinition definition = new RootBeanDefinition(Blue.class);
        AbstractBeanDefinition definition = BeanDefinitionBuilder.rootBeanDefinition(Blue.class).getBeanDefinition();
        registry.registerBeanDefinition("hello", definition);
    }

    // 该方法是继承父类BeanFactoryPostProcessor实现的方法
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("被定义的bean的个数：" + beanFactory.getBeanDefinitionCount());
    }
}
