package com.Test3;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;

/**
 * 由于继承导致线程不安全示例
 *
 * @author : lzw
 * @date : 2023/2/14
 * @since : 1.0
 */
public class Test12 {

    private static int loopNumber = 20000;

    private static int threadNumber = 2;

    public static void main(String[] args) {
        ThreadSafeSubClass threadSafe = new ThreadSafeSubClass();
        for (int i = 0; i < threadNumber; i++) {
            new Thread(() -> {
                threadSafe.method1(loopNumber);
            }, "threadName-" + (i + 1)).start();
        }
    }
}


@Slf4j
class ThreadSafe {
    public final void method1(int loopNumber) {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < loopNumber; i++) {
            log.debug("{}，id:{}", Thread.currentThread().getName(), Thread.currentThread().getId());
            method2(list);
            method3(list);
        }
    }

    public void method2(ArrayList<String> list) {
        list.add("1");
    }

    public void method3(ArrayList<String> list) {
        list.remove(0);
    }
}

@Slf4j
class ThreadSafeSubClass extends ThreadSafe {

    @Override
    public void method3(ArrayList<String> list) {

//        ArrayList<String> list = list2;

        // 这个线程中的变量有可能会被T1和T2线程同时访问到，从而引起线程安全问题
        new Thread(() -> {
            log.debug("method3:{},id:{}", Thread.currentThread().getName(), Thread.currentThread().getId());
            list.remove(0);
        }).start();


        // T1、T2线程同时调用method3方法时，相当于这种写法，一个方法里有两个Thread，同时在操作list，
        // 此时的list参数在method3里似于一个成员变量
        /*new Thread(() -> {
            log.debug("method3:{},id:{}", Thread.currentThread().getName(), Thread.currentThread().getId());
            list.remove(0);
        }).start();*/


    }
}
