package com.Test3;

import lombok.extern.slf4j.Slf4j;

/**
 * 活锁
 * 活锁是出现在连个线程互相改变对方的结束条件，最后谁也无法改变
 *
 * @author : lzw
 * @date : 2023/3/7
 * @since : 1.0
 */
@Slf4j
public class TestLiveLock {

    static final Object lock = new Object();
    static volatile int count = 10;

    public static void main(String[] args) {
        new Thread(() -> {
            // 期望减到0退出循环
            while (count > 0) {
                try {
                    Thread.sleep(40);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                count--;
                log.debug("count:{}", count);
            }
        }, "t1").start();

        new Thread(() -> {
            // 期望超过到20退出
            while (count < 20) {
                try {
                    Thread.sleep(40);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                count++;
                log.debug("count:{}", count);
            }
        }, "t2").start();
    }
}
