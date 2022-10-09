package com.lzw.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StringValueResolver;

/**
 * @author : lzw
 * @date : 2022/5/11
 * @since : 1.0
 */
@Component
public class Red implements ApplicationContextAware, BeanNameAware, EmbeddedValueResolverAware {
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("传入的io容器:" + applicationContext);
    }

    @Override
    public void setBeanName(String name) {
        System.out.println("传入的当前bean的名字：" + name);
    }

    @Override
    public void setEmbeddedValueResolver(StringValueResolver resolver) {
        // 解析器
        String s = resolver.resolveStringValue("你好 ${os.name} 我今年#{20-2}");
        System.out.println("解析的字符串：" + s);
    }
}
