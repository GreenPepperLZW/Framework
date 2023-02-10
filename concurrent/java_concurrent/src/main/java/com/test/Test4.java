package com.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 调用interrupt()方法打断线程的休眠，唤醒该线程
 *
 * @author : lzw
 * @date : 2023/2/7
 * @since : 1.0
 */
@Slf4j
public class Test4 {
    public static void main(String[] args) {
        Thread t1 = new Thread() {
            @Override
            public void run() {
                log.debug("enter sleep");
                try {
                    Thread.sleep(2000);
                    // 使用TimeUnit工具类让其休眠1s
                    TimeUnit.SECONDS.sleep(1);
                    log.debug("go on...");
                } catch (InterruptedException e) {
                    log.debug("wake up...");
                    throw new RuntimeException(e);
                }
            }
        };
        t1.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // 主线程休眠1s后唤醒t1线程
        log.debug("interrupt...");
        t1.interrupt();
    }
}

