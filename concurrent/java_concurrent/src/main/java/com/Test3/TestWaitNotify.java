package com.Test3;

import lombok.extern.slf4j.Slf4j;

/**
 * 测试wait()和notify()方法
 *
 * @author : lzw
 * @date : 2023/2/28
 * @since : 1.0
 */
@Slf4j
public class TestWaitNotify {

    final static Object obj = new Object();

    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(() -> {
            synchronized (obj) {
                try {
                    log.debug("开始执行...");
                    // 必须成为obj锁的owner之后才能调用wait()或notify()方法
                    obj.wait();// 让线程在obj上一直等下去
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                log.debug("其他代码...");
            }
        }, "t1");
        t1.start();

        Thread t2 = new Thread(() -> {
            synchronized (obj) {
                try {
                    log.debug("开始执行");
                    obj.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                log.debug("其他代码...");
            }
        }, "t2");
        t2.start();

        // 主线程两秒后执行
        Thread.sleep(2000);
        log.debug("唤醒 obj 上其他线程");
        // 首先让主线程成为这个锁的owner
        synchronized (obj) {
            // obj.notify();// 随机唤醒一个线程
            obj.notifyAll();// 唤醒所有线程
        }
    }
}
