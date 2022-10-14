package com.lzw.ext;

import com.lzw.bean.Blue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


/* 为了排版使用普通注释
 * 扩展原理：
 * 1、 BeanPostProcessor:Bean的后置处理器，bean创建对象初始化前后进行拦截工作的（暂不清楚具体作用在哪）
 *  BeanFactoryPostprocessor，BeanFactory的后置处理器:
 *      在BeanFactory标准初始化之后调用，具体的执行时间是在所有bean定义已经保存加载了(definition)，但是bean的实例还未创建
 *
 * BeanFactoryPostprocessor基本原理：
 *  1)、ioc容器启动时的13个步骤中，会有一个步骤来执行它
 *  2)、invokeBeanFactoryPostProcessors(beanFactory);
 *      如何找到这些MyBeanFactoryPostProcessor的呢?
 *      1)、直接在BeanFactory中找到所有类型是MyBeanFactoryPostProcessor的组件，并执行他们的方法
 *      2)、在初始化创建其他组件（finishBeanFactoryInitialization(beanFactory);）前面执行
 *
 *  2、再来看他的一个子接口：interface BeanDefinitionRegistryPostProcessor extends BeanFactoryPostProcessor
 *      其中的postProcessBeanDefinitionRegistry()方法会优先于BeanFactoryPostProcessor执行，
 *      可以利用BeanDefinitionRegistryPostProcessor给容器中再额外添加一些组件，演示案例：com.lzw.ext.MyBeanDefinitionRegistryPostProcessor
 *
 *  3、ApplicationListener,监听容器中发布的事件，事件驱动模型开发；
 *      interface ApplicationListener<E extends ApplicationEvent> extends EventListener
 *      监听 ApplicationEvent 及其下面的子事件
 *
 *    基于事件开发步骤：
 *      1)、写一个监听器来监听某个事件（ApplicationEvent及其子类）
 *      2)、把监听器加入到容器
 *      3)、只要容器中有相关事件的发布，我们就能监听到这些事件，比如：
 *              org.springframework.context.event.ContextRefreshedEvent:容器刷新完成，Spring会发布这个事件
 *              org.springframework.context.event.ContextClosedEvent：容器关闭会发布事件
 *      4)、发布一个事件
 */

/**
 * //方便查看一些相关的类
 *
 * @see org.springframework.beans.factory.config.BeanFactoryPostProcessor
 * @see com.lzw.ext.MyBeanDefinitionRegistryPostProcessor
 * @see org.springframework.context.ApplicationListener
 */

/**
 * 扩展原理
 *
 * @author : lzw
 * @date : 2022/10/13
 * @since : 1.0
 */
@Configuration
@ComponentScan("com.lzw.ext")
public class ExtConfig {

    @Bean
    public Blue blue() {
        return new Blue();
    }
}
