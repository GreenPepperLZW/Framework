package com.reentranlock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * await() 方法，进入等待状态（进入休息室）
 * signal() 方法，随机唤醒休息室中的一个线程
 *
 * @author : lzw
 * @date : 2023/3/10
 * @since : 1.0
 */
@Slf4j
public class Test25 {

    static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {

        // 创建条件变量，功能：当线程获得锁之后，但不满足执行条件后就会进入到其中（理解为休息室）
        Condition condition1 = lock.newCondition();
        Condition condition2 = lock.newCondition();

        lock.lock();
        try {
            // 进入休息室
            condition1.await();
            // 从休息室中随机唤醒一个线程
            condition1.signal();
            // 唤醒休息室中所有的线程
            // condition1.signalAll();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }

    }
}
