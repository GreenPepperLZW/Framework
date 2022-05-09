package com.lzw.condition;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * 是否给容器中注册组件的条件实现类
 *
 * 是否是windows系统
 * @author : lzw
 * @date : 2022/5/9
 * @since : 1.0
 */
public class WindowsCondition implements Condition {

    /**
     *
     * @param context the condition context：判断条件能使用的上下文环境
     * @param metadata metadata of the {@link org.springframework.core.type.AnnotationMetadata class}
     * or {@link org.springframework.core.type.MethodMetadata method} being checked.
     *                 当前标注了Condition注解的元注解信息
     */
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
        if (property.contains("Windows")) {
            return true;
        }
        // 4.获取到bean定义 的注册类
        BeanDefinitionRegistry registry = context.getRegistry();

        return false;
    }
}
