package com.Test3;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

/**
 * @author : lzw
 * @date : 2023/3/7
 * @since : 1.0
 */
@Slf4j
public class TestParkUnpark {

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            log.debug("start...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            // 进入等待
            LockSupport.park();
            log.debug("ressume...");
        }, "t1");

        t1.start();
        Thread.sleep(2000);
        // 恢复t1线程的运行
        log.debug("unpark...");
        LockSupport.unpark(t1);
    }
}
