package com.lzw.bean;

import org.springframework.stereotype.Component;

/**
 * @author : lzw
 * @date : 2022/5/11
 * @since : 1.0
 */
@Component
public class Car {

    public Car() {
        System.out.println("Car constructor.....");
    }

    /**
     * 自定义初始化方法
     */
    public void init() {
        System.out.println("Car... init....");
    }

    /**
     * 自定义销毁方法
     */
    public void destroy() {
        System.out.println("Car... destroy....");
    }


}
