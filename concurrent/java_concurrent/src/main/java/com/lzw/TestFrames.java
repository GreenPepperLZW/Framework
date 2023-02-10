package com.lzw;

/**
 * 栈帧先进后出，栈内存由栈帧组成，每一个方法的调用就是一个栈帧
 *
 * @author : lzw
 * @date : 2023/1/13
 * @since : 1.0
 */
public class TestFrames {
    public static void main(String[] args) {
        method1(10);
    }

    private static void method1(int x) {
        int y = x + 1;
        Object m = method2();
        System.out.println(m);
    }

    private static Object method2() {
        Object o = new Object();
        return o;
    }
}
