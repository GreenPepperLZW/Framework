package com.n1;

/**
 * 查看栈于栈帧的关系
 * 线程一启动，虚拟机就会为它分配一块栈内存，在这个线程里，每调用一次方法，就会创建一个栈帧，方法执行完成后才会释放栈帧
 * 出入栈顺序：后进先出
 *
 * @author : lzw
 * @date : 2023/2/7
 * @since : 1.0
 */
public class TestFrames {
    public static void main(String[] args) {

        Thread t1 = new Thread() {
            @Override
            public void run() {
                method1(20);
            }
        };
        t1.setName("t1");
        t1.start();
        method1(10);
    }

    public static void method1(int x) {
        int y = x + 1;
        Object m = method2();
        System.out.println(m);
    }

    public static Object method2() {
        Object n = new Object();
        return n;
    }
}
