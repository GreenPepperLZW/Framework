package com.lzw.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author : lzw
 * @date : 2022/5/5
 * @since : 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Person {

    /**
     * # 这里@Value注解的作用相当与配置文件中 xml配置中value属性的作用
     * 使用@Value赋值的写法
     * 1.基本数值
     * 2.可以写SpEl,#{}
     * 3.可以写${}，取出配置文件(application.properties,application.yml)中的值（在运行环境变量里的值）
     */

    @Value("张三")
    private String name;

    @Value("#{20-2}")
    private int age;

    @Value("${person.nickName}")
    private String nickName;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
