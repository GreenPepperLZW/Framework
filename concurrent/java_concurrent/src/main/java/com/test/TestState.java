package com.test;

import lombok.extern.slf4j.Slf4j;

/**
 * 测试Java中线程六种状态的装换
 *
 * @author : lzw
 * @date : 2023/2/9
 * @since : 1.0
 */
@Slf4j
public class TestState {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            log.debug("running....");
        }, "t1");

        Thread t2 = new Thread(() -> {
            while (true) {

            }
        }, "t2");
        t2.start();

        Thread t3 = new Thread(() -> {
            log.debug("running...");
        }, "t3");
        t3.start();

        Thread t4 = new Thread(() -> {
            synchronized (TestState.class) {
                try {
                    Thread.sleep(1000000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "t4");
        t4.start();

        Thread t5 = new Thread(() -> {
            try {
                // 等待t2线程执行完
                t2.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "t5");
        t5.start();

        Thread t6 = new Thread(() -> {
            // 此时对象锁被t4占用
            synchronized (TestState.class) {
                try {
                    Thread.sleep(1000000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "t6");
        t6.start();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        log.debug("t1 state：{}", t1.getState());
        log.debug("t2 state：{}", t2.getState());
        log.debug("t3 state：{}", t3.getState());
        log.debug("t4 state：{}", t4.getState());
        log.debug("t5 state：{}", t5.getState());
        log.debug("t6 state：{}", t6.getState());

    }
}
