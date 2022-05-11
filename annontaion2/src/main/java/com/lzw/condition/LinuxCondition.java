package com.lzw.condition;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * 是否给容器中注册组件的条件实现类
 *
 * 是否是linux系统
 * @author : lzw
 * @date : 2022/5/9
 * @since : 1.0
 */
public class LinuxCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        // congtext
        // 1.能获取到ios使用的beanFactory
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        // 2.获取类加载器
        ClassLoader classLoader = context.getClassLoader();
        // 3.获取当前环境信息
        String property = context.getEnvironment().getProperty("os.name");
        // 是否是window系统
        if (property.contains("Linux")) {
            return true;
        }
        // 4.获取到bean定义 的注册类
        BeanDefinitionRegistry registry = context.getRegistry();

        // 可以判断容器中的注册情况，也可以给容器中注册bean
        if (registry.containsBeanDefinition("person")) {
//            return true;
        }

        return false;
    }
}
