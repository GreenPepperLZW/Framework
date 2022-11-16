package org.lzw.config;

import org.lzw.bean.Car;
import org.lzw.bean.Pet;
import org.lzw.bean.User;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * @author : lzw
 * @date : 2022/11/16
 * @since : 1.0
 * <p>
 * proxyBeanMethods,代理bean的方法，默认值为true
 * Full模式和Lite模式
 * full(proxyBeanMethods = true),获取的每一个bean都是相同的，每次获取前都会判断容器中是否存在，如果存在，则从容器中获取
 * lite(proxyBeanMethods = false)，每次获取的bean都是新创建的，不会去容器中判断是否存在，解决组件依赖问题
 * <p>
 * 如果知识单纯的添加组件，不会被人依赖，可以调为lite轻量模式，少了一步判断操作，启动会比较快
 */
// 导入配置文件
@ImportResource("classpath:spring.xml")
@Configuration(proxyBeanMethods = true)
// 1.开启配置导入功能，2.将Car类这个组件自动注册到容器中，可以不用写@Component注解了，用此方式，可以为第三方包中没有写@Component注解的类绑定值
@EnableConfigurationProperties(Car.class)
public class MyConfig {

    @Bean
    @ConditionalOnBean(name = "pet01")//条件装配
    public User user01() {
        return new User("小明", 11);
    }

    @Bean
    public Pet pet01() {
        return new Pet("狗");
    }

}
