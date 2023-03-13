package com.reentranlock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 锁超时
 * <p>
 * tryLock()方法
 *
 * @author : lzw
 * @date : 2023/3/10
 * @since : 1.0
 */
@Slf4j
public class Test24 {

    static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            try {
                if (!lock.tryLock(2, TimeUnit.SECONDS)) {
                    log.debug("没有立刻获得锁");
                    return;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                log.debug("没有获取到锁");
                return;
            }
            try {
                log.debug("获得到锁");
            } finally {
                lock.unlock();
            }
        }, "t1");

        lock.lock();
        log.debug("获得倒锁");

        t1.start();

        Thread.sleep(1000);
        log.debug("释放了锁");
        lock.unlock();
    }
}
