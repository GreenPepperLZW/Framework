package com.lzw.config;

import com.lzw.bean.BookDao;
import com.lzw.bean.Car;
import com.lzw.bean.Color;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/*
 * 自动装配：
 * Spring利用依赖注入（DI），完成对IOC容器中各个组件的依赖关系赋值
 * <p>
 * 1)、@Autowired:自动注入
 *      1.1)、默认优先按照类型去容器中找对应的组件：applicationContext.getBean(BookService.class)
 *      1.2)、如果找到多个相同类型的组件，再将属性的名称作为组件的id去容器中查找 applicationContext.getBean("bookDao")
 *      1.3)、使用@Qualifier("bookDao2")指定需要装配的组件id，而不是使用属性名
 *      1.4)、自动装配默认一定要将属性赋值好，依赖的bean如果不存在就会报错
 *      1.5)、@Autowired(required = false),使用required = false来调整是否必须装配
 *      1.6)、@Primary：让Spring进行自动装配时，默认使用首选的bean,容器中存在两个bean,bookDao,bookDao2,如果再bookDao2上加上注解@Primary，
 *          则在自动装配时，没有明确指定的情况下，会首选装配bookDao2
 * <p>
 * 2)、Spring还支持使用@Resource(JSR250规范)和@Inject(JSR330规范)[java规范的注解]
 *      @Resource:
 *          可以和@Autowired一样实现自动装配的功能，默认是按照组件名进行装配的
 *          不支持@Primary功能和@Autowired(required = false)的功能
 *      @Inject
 *          需要导入javax.inject的包，和@Autowired功能一样，没有required = false的属性
 * <p>

 * ***************************
 * 加入以上注解之所以可以完成自动装配的原理在于 AutowiredAnnotationBeanPostProcessor 处理器，
 *
 * 3)、@Autowired还可用于构造器、参数、方法、属性上,具体演示查看 {@link com.lzw.bean.Boss} 类
 *      1)、【标注在方法位置上】@Bean+方法参数，参数从容器中获取，@Bean标注的方法参数上，@Autowired可以省略不写
 *      2)、【放在构造器位置上】
 *      3)、【放在参数位置】
 *
 * 4)、自定义组件想要使用spring容器底层的一些组件（ApplicationContext，BeanFactory，xxx）,自定义组件需要实现xxxAware,
 *      在创建对象的时候，会调用接口规定的方法注入到相关组件,具体演示查看 {@link com.lzw.bean.Red}
 *      xxxAware:具体功能实现是使用 xxxProcessor
 *          如：ApplicationContextAware====》ApplicationContextAwareProcessor
 */

/**
 * @author : lzw
 * @date : 2022/10/9
 * @see com.lzw.bean.Boss
 * @see com.lzw.bean.Red
 * @since : 1.0
 */
@Configuration
@ComponentScan({"com.lzw.bean"})
public class MainConfigOfAutowired {

    // 扫描进一个BookDao的bean，再手动注册一个BookDao的bean，看在BookService中会自动注入哪一个
    @Bean("bookDao2")
    // Spring自动装配时，首选bookDao2
    @Primary
    public BookDao bookDao() {
        BookDao bookDao = new BookDao();
        bookDao.setLabel(2);
        return bookDao;
    }

    /**
     * # @Bean标注的方法创建对象的时候，方法参数的值从容器中获取
     *
     * @param car
     * @return
     */
    @Bean
    public Color color(Car car) {
        Color color = new Color();
        color.setCar(car);
        return color;
    }
}
