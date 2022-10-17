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
 *      在AspectJAutoProxyRegistrar中，创建一个名字为internalAutoProxyCreator,类型为AnnotationAwareAspectJAutoProxyCreator的bean(注解装配模式的AspectJ自动代理创建器)
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
 *                  首先搞清楚两个后置处理的执行时间
 *              【BeanPostProcessor是在Bean对象创建完成初始化前后调用】
 *              【InstantiationAwareBeanPostProcessor是在创建Bean实例之前先尝试用后置处理器返回对象的】
 *                  1)、先从缓存中获取bean，如果能获取到，说明bean之前被创建过，直接使用，否则再去创建，只要创建好的bean，都会被缓存起来
 *                  2)、createBean(),创建bean的过程
 *                      1)、resolveBeforeInstantiation(beanName, mbdToUse);让BeanPostProcessors有机会返回一个代理，而不是目标bean实例。
 *                          希望后置处理器在此能返回一个代理对象，如果能返回代理对象就使用，如果不能就继续往下走。
 *                          主要执行过程
 *                          bean = applyBeanPostProcessorsBeforeInstantiation(targetType, beanName);
 *                          ----->
         *                      if (bp instanceof InstantiationAwareBeanPostProcessor) {
                                    InstantiationAwareBeanPostProcessor ibp = (InstantiationAwareBeanPostProcessor) bp;
                                    Object result = ibp.postProcessBeforeInstantiation(beanClass, beanName);
                                    if (result != null) {
                                        return result;
                                    }
                                }
                             所以
                            【AnnotationAwareAspectJAutoProxyCreator在所有bean创建之前都会有一个拦截，应为它的父类实现了InstantiationAwareBeanPostProcessor
                            ，会调用AbstractAutoProxyCreator父类中的postProcessBeforeInstantiation方法】
                         2)、【AnnotationAwareAspectJAutoProxyCreator组件的作用，它是InstantiationAwareBeanPostProcessor类型的后置处理器】
                         作用：在每一个bean创建之前，调用postProcessBeforeInstantiation方法，
                         debug调试案例中MathCalculator和LogAspects这两个类在postProcessBeforeInstantiation方法中的创建过程。
                            1)、
                                if (beanName == null || !this.targetSourcedBeans.contains(beanName)) {
                                    if (this.advisedBeans.containsKey(cacheKey)) {
                                        return null;
                                    }
                                    if (isInfrastructureClass(beanClass) || shouldSkip(beanClass, beanName)) {
                                        this.advisedBeans.put(cacheKey, Boolean.FALSE);
                                        return null;
                                    }
                                }
                                对于以上两个判断的解读
                                1.判断当前bean是否在advisedBeans中（保存了所有需要增强的bean）
                                2.判断当前bean是否是基础类型的Advice、Pointcut、Advisor、AopInfrastructureBean，即当前bean是否实现了这些类
                                或者是否加了@Aspect注解，即判断当前类是不是切面，源码中的判断逻辑如下代码所示
                                【
                                    private boolean hasAspectAnnotation(Class<?> clazz) {
                                        return (AnnotationUtils.findAnnotation(clazz, Aspect.class) != null);
                                    }
                                】
                                3.是否需要跳过，shouldSkip()
                                    shouldSkip()方法里的逻辑：
                                    获取候选的增强器（即切面里的通知方法），如果增强器的类型是AspectJPointcutAdvisor，则返回true，debug调试中看到LogAspects类中定义的增强器的类型都为InstantiationModelAwarePointcutAdvisor，调用父类方法返回false
                            2)、debug调试案例中MathCalculator和LogAspects这两个类在【AbstractAutoProxyCreator类中】postProcessAfterInitialization方法中的执行过程。
                                return wrapIfNecessary(bean, beanName, cacheKey);//如果需要的话将该类进行包装
                                1)、
                                    //获取当前类的所有增强器（定义的aop通知方法）
                                    bject[] specificInterceptors = getAdvicesAndAdvisorsForBean(bean.getClass(), beanName, null);
                                    1.找到候选的所有增强器（找到哪些通知方法是需要切入到当前bean方法的）
                                    2.获取到能在当前bean中使用的增强器
                                    3.给增强器排序
                                2)、保存当前bean在advisedBeans全局集合中(需要进行增强的bean集合)
                                3)、如果当前bean需要增强，创建bean的代理对象；
                                    1.获取所有增强器（通知方法）
                                    2.保存到proxyFactory中
                                    3.创建代理对象，jdK代理h
                                        JdkDynamicAopProxy
                                        ObjenesisCglibAopProxy
                                关键代码如下
                                【
                                public AopProxy createAopProxy(AdvisedSupport config) throws AopConfigException {
                                    if (config.isOptimize() || config.isProxyTargetClass() || hasNoUserSuppliedProxyInterfaces(config)) {
                                        Class<?> targetClass = config.getTargetClass();
                                        if (targetClass == null) {
                                            throw new AopConfigException("TargetSource cannot determine target class: " +
                                                    "Either an interface or a target is required for proxy creation.");
                                        }
                                        if (targetClass.isInterface() || Proxy.isProxyClass(targetClass)) {
                                            return new JdkDynamicAopProxy(config);
                                        }
                                        return new ObjenesisCglibAopProxy(config);
                                    }
                                    else {
                                        return new JdkDynamicAopProxy(config);
                                    }
                                }
                                】

                                4)、所以返回到容器中的bean是已经增强过了的代理对象
                                5)、以后容器中获取到的就是这个组件的代理对象，执行目标方法的时候，代理对象就会执行通知方法的流程

                            3)、目标方法执行：
                                容器中保存了组件的代理对象(增强后的对象)，这个对象里保存了详细信息（比如增强器，目标对象）
                                1)、执行目标方法之前会执行CglibAopProxy.intercept();cglib拦截目标方法的执行
                                2)、主要逻辑
                                    根据ProxyFactory对象获取将要执行的目标方法的拦截器（将advice转为MethodInterceptor）
                                    List<Object> chain = this.advised.getInterceptorsAndDynamicInterceptionAdvice(method, targetClass);

                                3)、如果拦截器链为空，则直接执行目标方法
                                4)、如果有拦截器链，把需要执行的目标对象、目标方法、拦截器链的信息传入创建一个叫CglibMethodInvocation的对象
                                并调用proceed();方法
                                5)、拦截器链的触发过程
                                    1)、如果没有拦截器执行目标方法，或者执行到了最后一个拦截器则去执行目标方法
                                    2)、链式获取每一个拦截器，拦截器执行invoke方法，每一个拦截器等待下一个拦截器执行完成返回以后再来执行
                                        拦截器的机制，保证通知方法与目标方法的执行顺序

 *                      2)、doCreateBean(beanName, mbdToUse, args);真正的创建bean实例和3.6流程一样
 *
 * 总结：
 *      1)、@EnableAspectJAutoProxy 开启AOP功能
 *      2)、@EnableAspectJAutoProxy 会给容器注册一个AnnotationAwareAspectJAutoProxyCreator
 *      3)、AnnotationAwareAspectJAutoProxyCreator是一个bean在初始化之前进行拦截
 *      4)、容器的创建流程
 *          1)、registerBeanPostProcessors()方法注册后置处理器AnnotationAwareAspectJAutoProxyCreator
 *          2)、finishBeanFactoryInitialization(beanFactory);，初始化剩下的单实例bean
 *              创建业务逻辑组件和切面组件
 *          3)、AnnotationAwareAspectJAutoProxyCreator会拦截创建组件的创建过程
 *          4)、组件创建完后，判断组件是否需要增强
                如果需要：将切面的通知方法，包装成增强器（Advisor）；给业务逻辑组件创建一个代理对象（默认cglib代理）
        5)、执行目标方法
            1)、代理对象执行目标方法
            2)、CglibAopProxy.intercept()
                1)、得到目标方法的拦截器链（增强器包装成连接器）
                2)、利用拦截器的链式机制，依次进入每一个拦截器执行方法
                3)、效果
                    正常执行：前置通知->目标方法->后置通知->返回通知
                    异常执行：前置通知->目标方法->后置通知->异常通知
 *
 */
/**
 * ===============================================================
 * 以下注释主要是方便查看笔记AOP原理第二步中各个类的具体内容和位置
 * 1.
 *
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
