package com.lzw.config;

import com.lzw.bean.Color;
import com.lzw.bean.Person;
import com.lzw.bean.Red;
import com.lzw.condition.*;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.*;

/**
 * 测试Scope注解
 *
 * @author : lzw
 * @date : 2022/5/6
 * @since : 1.0
 */
// Conditional作用在类上，满足条件，这个类中配置的所有bean注册都生效
@Conditional({WindowsCondition.class})
@Configuration
@Import({Color.class, Red.class, MyImportSelector.class, MyImportBeanDefinitionRegistrar.class})
public class MainConfig2 {

    // 默认注册的组件都是单实例的

    /**
     * @Scope 调整作用域
     * @see ConfigurableBeanFactory#SCOPE_PROTOTYPE  prototype
     * @see ConfigurableBeanFactory#SCOPE_SINGLETON  singleton
     * web环境下
     * @see org.springframework.web.context.WebApplicationContext#SCOPE_REQUEST 同一次请求创建一个实例
     * @see org.springframework.web.context.WebApplicationContext#SCOPE_SESSION 同一个session创建一个实例
     * <p>
     * prototype：多实例，ioc容器启动并不会调用方法创建对象放在容器中，每次获取的时候才会调用方法创建对象
     * singleton：单实例，默认值，ioc容器启动会调用方法创建对象放到ioc容器中，以后每次获取就是直接从容器中拿
     * <p>
     * 懒加载：
     * 针对单实例bean：默认在容器启动的时候创建对象
     * 懒加载：容器不创建对象，第一次使用的时候（获取）bean的时候创建对象，并进行一些初始化
     */
//    @Scope()
    @Bean
    @Lazy
    public Person person() {
        // 测试容什么时候调用方法
        System.out.println("给容器添加Person");
        return new Person("张三", 25);
    }

    /**
     * 测试@Conditional注解
     * 按照一定条件进行判断，满足条件给容器中注册bean
     * <p>
     * 如果是windows,给容器中注册@Bean("bill")
     * 如果是linux，容器中注册@Bean("linus")
     */
    @Bean("bill")
    @Conditional({WindowsCondition.class})
    public Person person01() {
        return new Person("Bill Gates", 63);
    }

    @Bean("linus")
    @Conditional({LinuxCondition.class})
    public Person person02() {
        return new Person("linus", 50);
    }

    /**
     * 给容器中注册组件的方式：
     * 1.包扫描+组件标注注解(@Controller/@Service/@Repository/@Component)
     * 2.@Bean【导入第三方包里面的组件】
     * 3.@Import【快速给容器中导入一个组件，springBoot自动配置中大量使用该注解】
     * 1).@Import(要导入到容器中的组件)：容器中就会自动注册这个组件，id默认是全类名
     * 2).@ImportSelector：返回需要导入的组件的全类名数组，批量导入
     * 3).@ImportBeanDefinitionRegistrar
     * 4.使用spring提供的FactoryBean(工厂bean)
     *      1).默认获取到的是工厂bean调用getObject创建的对象，而不是 FactoryBean
     *      2).要获取工厂bean本身，我们需要给id前面加一个 & 符号（实际代码调用：Object colorFactoryBean2 = applicationContext.getBean("&colorFactoryBean");）
     */

    /**
     * 测试FactoryBean
     *
     * @return
     */
    @Bean
    public ColorFactoryBean colorFactoryBean() {
        return new ColorFactoryBean();
    }

}
