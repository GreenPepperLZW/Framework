package com.test;

import lombok.extern.slf4j.Slf4j;

/**
 * 使用多线程统筹烧开水泡茶的情景
 *
 * @author : lzw
 * @date : 2023/2/10
 * @since : 1.0
 */
@Slf4j
public class Test8 {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            log.debug("开始洗水壶...");
            try {
                Thread.sleep(1000);
                log.debug("开始烧开水....");
                Thread.sleep(5000);
                log.debug("烧开水完成");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            try {
                log.debug("洗茶壶");
                Thread.sleep(1000);
                log.debug("洗茶杯...");
                Thread.sleep(2000);
                log.debug("拿茶叶");
                Thread.sleep(1000);
                // 等待开水烧开
                t1.join();
                log.debug("开始泡茶....");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "t2");

        t1.start();
        t2.start();
    }
}
