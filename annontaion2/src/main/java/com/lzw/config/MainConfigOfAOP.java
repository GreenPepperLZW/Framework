package com.lzw.config;

import com.lzw.aop.LogAspects;
import com.lzw.aop.MathCalculator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;


/*
 * AOP【动态代理】：
 *      指在程序运行期间动态的将某段代码切入到指定的位置进行运行的编程方式
 *  1.导入AOP模块，Spring AOP
 *  2.定义一个业务逻辑类（MathCalculator），在业务逻辑运行的时候将日志进行打印（方法之前、方法运行结束，方法出现异常等情况）
 *  3.定义一个日志切面类（LogAspects），切面类里面的方法需要动态感知MathCalculator.div运行到那一步
 *          通知方法：
 *              前置通知：(@Before)，在目标方法运行之前运行，即LogAspects中的logStart
 *              后置通知：(@After)，在目标方法运行结束之后运行，即LogAspects中的logEnd,无论方法时正常结束还是异常结束都会执行
 *              返回通知：(@AfterReturning),在目标方法正常返回以后运行，即LogAspects中的logReturn
 *              异常通知：(@AfterThrowing)，logException,在目标方法出现异常以后运行，即LogAspects中的logException
 *              环绕通知：(@Around)，动态代理，手动推进目标方法运行，(joinPoint.proceed())
 * 4.给切面类的目标方法标注何时何地运行（通知注解）
 * 5.将切面类和业务逻辑类（目标方法所在类）都加入到容器中
 * 6.必须告诉Spring哪个类时切面类(给切面类上加一个注解，@Aspect)
 * 7.给配置加入@EnableAspectJAutoProxy，相当于xml配置中的【<aop:aspectj-autoproxy></aop:aspectj-autoproxy>】，开启基于注解的aop模式
 *
 * 三步：
 *  1)、将业务逻辑组件和切面类都加入到容器中，告诉Spring哪个是切面类(@Aspect)
 *  2)、在切面类上的每一个通知方法上都标注注解，告诉Spring何时何地运行（切入点表达式）
 *  3)、开始基于注解的aop模式，@EnableAspectJAutoProxy
 *
 * AOP原理：【查看给容器中注册了什么组件，这个组件什么时候工作，这个组件工作时候的功能是什么】
 *  1.@EnableAspectJAutoProxy是什么？
 *      @EnableAspectJAutoProxy导入一个AspectJAutoProxyRegistrar.class组件，利用AspectJAutoProxyRegistrar向容器中注册组件
 *      在AspectJAutoProxyRegistrar中，当internalAutoProxyCreator这个bean不存在时,就注册一个叫AnnotationAwareAspectJAutoProxyCreator的bean(注解装配模式的AspectJ自动代理创建器)
 *
 * 2.AnnotationAwareAspectJAutoProxyCreator
 *  AnnotationAwareAspectJAutoProxyCreator
 *      ->AspectJAwareAdvisorAutoProxyCreator
 *          ->AbstractAdvisorAutoProxyCreator
 *              ->AbstractAutoProxyCreator implements SmartInstantiationAwareBeanPostProcessor, BeanFactoryAware
 *                  ->ProxyProcessorSupport->ProxyConfig
 *  主要关注后置处理器（在bean初始化完成前后做事情），自动装配BeanFactory
 *
 * 启动流程
 *      1.传入配置类(AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfAOP.class))，创建ioc容器
 *      2.注册配置类(register(annotatedClasses);)，调用refresh()方法
 *      3.registerBeanPostProcessors(beanFactory);注册bean的后置处理器来方便拦截bean的创建
 *          3.1先获取ioc容器中已经定义了的需要创建对象的所有BeanPostProcessor
 *          3.2给容器中加入其他的BeanPostProcessor
 *          3.3优先注册实现了PriorityOrdered接口的BeanPostProcessor
 *          3.4再容器中注册实现了Ordered接口的BeanPostProcessor
 *          3.5最后在注册没有实现优先级接口的BeanPostProcessors
 *          3.6注册BeanPostProcessors，实际上就是创建BeanPostProcessors对象，然后保存在容器中：
 *         	    以创建internalAutoProxyCreator的BeanPostProcessors【[org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator】来看创建过程
 *          	3.6.1、创建bean的实例【instanceWrapper = createBeanInstance(beanName, mbd, args);】
 *          	3.6.2、执行【populateBean(beanName, mbd, instanceWrapper);】方法，给bean的各种属性赋值
 *           	3.6.3、【initializeBean(beanName, exposedObject, mbd);】初始化bean
 *           	    初始化流程，主要有一下四个方法
 *                  1.invokeAwareMethods(beanName, bean);处理Aware接口的方法回调
                    2.applyBeanPostProcessorsBeforeInitialization(wrappedBean, beanName);，执行后置处理器的postProcessBeforeInitialization(result, beanName);方法
                    3.invokeInitMethods(beanName, wrappedBean, mbd);执行初始化方法，这里执行初始化方法可以自己定义，比如：@Bean(initMethod = "init", destroyMethod = "destroy")
                    4.applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);执行后置处理器的postProcessAfterInitialization(result, beanName);方法
            3.7.把BeanPostProcessor注册到BeanFactory中，beanFactory.addBeanPostProcessor(postProcessor);

 *  =================以上是创建AnnotationAwareAspectJAutoProxyCreator的过程
 *          3.6.4实例化所有剩余的(非lazy-init)单例
 *              执行方法：finishBeanFactoryInitialization(beanFactory);
 *              1)、遍历容器中所有的定义的bean，一次创建对象
 *              beanFactory.getBean(CONVERSION_SERVICE_BEAN_NAME, ConversionService.class));
 *                 ->doGetBean()-> Object sharedInstance = getSingleton(beanName)->createBean();
 *              2)、创建bean
 *                  1)、先从缓存中获取bean，如果能获取到，说明bean之前被创建过，直接使用，否则再去创建，只要创建好的bean，都会被缓存起来
 *                  2)、createBean(),创建bean的过程
 *                      1)、resolveBeforeInstantiation(beanName, mbdToUse);
 */
/**
 * 以下注释主要是方便查看各个类的具体内容和位置
 * 1.
 * @see org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator
 * 重写了父类AbstractAdvisorAutoProxyCreator中的initBeanFactory方法
 * {@link org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator#initBeanFactory(org.springframework.beans.factory.config.ConfigurableListableBeanFactory)}
 * <p>
 * 2.
 * @see org.springframework.aop.aspectj.autoproxy.AspectJAwareAdvisorAutoProxyCreator
 * <p>
 * 3.
 * // 重写了父类AbstractAutoProxyCreator中的setBeanFactory方法
 * {@link org.springframework.aop.framework.autoproxy.AbstractAdvisorAutoProxyCreator#setBeanFactory(org.springframework.beans.factory.BeanFactory)}
 * 在setBeanFactory中调用了initBeanFactory方法
 * {@link org.springframework.aop.framework.autoproxy.AbstractAdvisorAutoProxyCreator#initBeanFactory(org.springframework.beans.factory.config.ConfigurableListableBeanFactory)}
 * <p>
 * 4.
 * // 实现BeanFactoryAware重写了setBeanFactory方法
 * {@link org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator#setBeanFactory(org.springframework.beans.factory.BeanFactory)}
 */

/**
 * @author : lzw
 * @date : 2022/10/9
 * @since : 1.0
 */
@Configuration
@EnableAspectJAutoProxy
public class MainConfigOfAOP {

    // 将业务逻辑类加入到容器中
    @Bean
    public MathCalculator mathCalculator() {
        return new MathCalculator();
    }

    // 将切面类加入到容器中
    @Bean
    public LogAspects logAspects() {
        return new LogAspects();
    }

}
