package lzw.reflection;

import java.lang.annotation.ElementType;

/**
 * 获取所有类型得class
 * @author : lzw
 * @date : 2022/4/13
 * @since : 1.0
 */
public class Test03 {

    public static void main(String[] args) {
        // 类
        Class c1 = Object.class;
        // 接口
        Class c2 = Comparable.class;
        // 一维数组
        Class c3 = String[].class;
        // 二维数组
        Class c4 = String[][].class;
        // 注解
        Class c5 = Override.class;
        // 枚举
        Class c6 = ElementType.class;
        // 基本数据类型
        Class c7 = Integer.class;
        // void
        Class c8 = void.class;
        // Class
        Class c9 = Class.class;

    }

}
