package com.lzw.bean;

/**
 * @author : lzw
 * @date : 2022/5/11
 * @since : 1.0
 */
public class Blue {
    private String name;

    private int age;

    public Blue() {
        System.out.println("Blue...constructor");
    }

    public Blue(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
