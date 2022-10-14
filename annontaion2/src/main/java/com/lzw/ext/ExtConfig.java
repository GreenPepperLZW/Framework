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
 *      5)、原理
 *          debug调试执行过程，从ioc容器启动开始
 *          refresh()->finishRefresh();->publishEvent(new ContextRefreshedEvent(this))->publishEvent(event, null);->getApplicationEventMulticaster().multicastEvent(applicationEvent, eventType);
 *              ->invokeListener(listener, event);->doInvokeListener(listener, event);->listener.onApplicationEvent(event);
 *          1)、获取到事件多播器：getApplicationEventMulticaster()
 *          2)、multicastEvent(applicationEvent, eventType)，派发事件
 *              获取到所有的ApplicationListener，for循环进行操作，如果有Executor，则异步执行，否则同步执行
 *              拿到listener回调onApplicationEvent方法
 *      【事件多播器创建过程】
 *          ioc容器启动调用refresh()方法时会执行initApplicationEventMulticaster()方法进行创建
 *      【容器中有哪些监听器】
 *          ioc容器启动调用refresh()方法时会执行registerListeners()方法，从容器中获取所有的监听器，把他们注册到ApplicationEventMulticaster（派发器）中
 *
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
