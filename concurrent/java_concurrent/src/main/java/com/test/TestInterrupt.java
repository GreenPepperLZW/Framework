package com.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 使用interrupt()方法打断正在sleep、wait、join的线程
 *
 * @author : lzw
 * @date : 2023/2/8
 * @since : 1.0
 */
@Slf4j
public class TestInterrupt {

    public static void main(String[] args) throws InterruptedException {
//        test1();
        test2();
    }

    /**
     * 使用interrupt()方法打断正在sleep、wait、join的线程
     *
     * @throws InterruptedException
     */
    private static void test1() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            log.debug("sleep...");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                // 抛出InterruptedException异常后，会清除打断标志，此时调用isInterrupted()方法会返回false
                throw new RuntimeException(e);
            }
        });
        t1.start();
        // 等t1线程进入睡眠后再去打断它
        Thread.sleep(1000);
        log.debug("interrupt...");
        // 在主线程中将t1线程的休眠状态打断，log.debug("sleep..."); 这行代码会执行成功，因为这时t1线程还没有进入休眠状态
        // 如果在t1线程还没进入睡眠前就打断，则打断的是一个正常运行的线程，那么打断标记就是true，如果打断的是一个睡眠的线程，则打断标记为false
        // interrupt()方法是给线程设置一个中断标志位
        t1.interrupt();
        log.debug("打断标记：{}", t1.isInterrupted());
    }

    /**
     * 打断正常运行的线程
     */
    public static void test2() {
        AtomicInteger count = new AtomicInteger();
        Thread t1 = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                log.debug("----->:{}", count.getAndIncrement());
            }
            log.debug("被打断了");
        });

        t1.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.debug("interrupt...");
        t1.interrupt();
    }
}
