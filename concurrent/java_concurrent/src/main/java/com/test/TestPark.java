package com.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * 测试LockSupport类中的park()方法
 *
 * @author : lzw
 * @date : 2023/2/9
 * @since : 1.0
 */
@Slf4j
public class TestPark {
    public static void main(String[] args) throws InterruptedException {
        test01();
    }

    public static void test01() throws InterruptedException {
        Thread thread = new Thread(() -> {
            log.debug("park...");
            LockSupport.park();
            log.debug("unpark...");
            log.debug("打断状态：{}", Thread.currentThread().isInterrupted());
            // 此时再调用park()方法，无法生效，除非将打断标志清除，可以调用 Thread.interrupted() 方法
            boolean interrupted = Thread.interrupted();
            log.debug("unpark...");
        }, "t1");
        thread.start();

        TimeUnit.SECONDS.sleep(1);

        thread.interrupt();
    }
}
