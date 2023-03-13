package com.test4;

import lombok.extern.slf4j.Slf4j;

/**
 * 固定运行顺序
 * 控制程序一定先打印2
 *
 * @author : lzw
 * @date : 2023/3/10
 * @since : 1.0
 */
@Slf4j
public class Test25 {

    static final Object obj = new Object();
    // t2线程是否运行过
    static boolean t2Runned = false;

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            while (!t2Runned) {
                synchronized (obj) {
                    try {
                        // t2还没运行，等待
                        obj.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            log.debug("-------->1");
        }, "t1");

        Thread t2 = new Thread(() -> {
            synchronized (obj) {
                log.debug("-------->2");
                t2Runned = true;
                obj.notify();
            }
        }, "t2");

        t1.start();
        t2.start();
    }
}
