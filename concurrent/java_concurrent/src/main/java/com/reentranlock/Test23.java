package com.reentranlock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 验证ReentrantLock可打断
 * 如果当前线程迟迟无法获取锁，那么当前线程的等待状态是可以被其他线程用interrupt()方法打断的
 *
 * @author : lzw
 * @date : 2023/3/10
 * @since : 1.0
 */
@Slf4j
public class Test23 {

    static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            try {
                // 如果没有竞争那么此方法就会获取lock对象
                // 如果有竞争那么此方法进程阻塞队列，可以被其他方法用 interrupt 方法打断阻塞
                log.debug("尝试获取锁");
                lock.lockInterruptibly();
                log.debug("获得锁");
            } catch (InterruptedException e) {
                e.printStackTrace();
                log.debug("没有获得锁");
                return;
            } finally {
                log.debug("释放锁");
                lock.unlock();
            }
        }, "t1");

        lock.lock();


        t1.start();
        Thread.sleep(1000);
        // 在主线程里打断t1线程的阻塞
        t1.interrupt();

    }
}
