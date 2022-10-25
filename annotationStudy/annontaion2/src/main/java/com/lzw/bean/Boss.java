package com.lzw.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author : lzw
 * @date : 2022/10/9
 * @since : 1.0
 */
// 默认加在ioc容器中的组件，容器启动会调用无参构造器，再进行初始化赋值等操作
@Component
public class Boss {

    private Car car;

    // @Autowired标注在构造器位置，构造器使用的参数都是从IOC容器中获取
    // 如果组件只有一个有参构造器，那么这个有参构造器的@Autowired可以省略，参数位置的组件还是可以自动从容器中取
    //@Autowired
    public Boss(@Autowired Car car) {
        this.car = car;
    }

    public Car getCar() {
        return car;
    }

    /**
     * # @Autowired 注解标注在方法上，Spring容器创建当前对象时就会调用方法，完成赋值
     * 方法使用的参数，自定义类型的值从ioc容器中获取
     *
     * @param car
     */
    //@Autowired
    public void setCar(Car car) {
        this.car = car;
    }

    @Override
    public String toString() {
        return "Boss{" +
                "car=" + car +
                '}';
    }
}
