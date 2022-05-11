package com.lzw.condition;

import com.lzw.bean.RainBow;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author : lzw
 * @date : 2022/5/11
 * @since : 1.0
 */
public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    /**
     * @param importingClassMetadata 当前类{@link com.lzw.config.MainConfig2}的所有注解信息
     * @param registry               current bean definition registry BeanDefinition注册类
     *                               把所有需要添加到容器中的bean,调用 BeanDefinitionRegistry.registerBeanDefinition 方法手动注册
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        boolean red = registry.containsBeanDefinition("com.lzw.bean.Red");
        boolean blue = registry.containsBeanDefinition("com.lzw.bean.Blue");
        if (red && blue) {
            // 手动注册一个bean
            // 指定bena名，bean的定义信息
            RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(RainBow.class);
            registry.registerBeanDefinition("rainBow",rootBeanDefinition);
        }
    }
}
