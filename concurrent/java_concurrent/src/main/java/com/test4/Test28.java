package com.test4;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用ReentrantLock中的await,signal方法实现交替输出
 *
 * @author : lzw
 * @date : 2023/3/13
 * @since : 1.0
 */
public class Test28 {
    public static void main(String[] args) throws InterruptedException {
        AwaitSignal awaitSignal = new AwaitSignal(5);
        Condition a = awaitSignal.newCondition();
        Condition b = awaitSignal.newCondition();
        Condition c = awaitSignal.newCondition();

        new Thread(() -> {
            awaitSignal.print("a", a, b);
        }, "a").start();
        new Thread(() -> {
            awaitSignal.print("b", b, c);
        }, "b").start();
        new Thread(() -> {
            awaitSignal.print("c", c, a);
        }, "c").start();

        Thread.sleep(1000);

        // 上面三个线程启动时，都会进入到环境变量中等待，需要由线其他程唤醒其中过一个才能继续执行
        awaitSignal.lock();
        try {
            a.signal();
        } finally {
            awaitSignal.unlock();
        }

    }
}

@Slf4j
class AwaitSignal extends ReentrantLock {

    private int loopNumber;

    public AwaitSignal(int loopNumber) {
        this.loopNumber = loopNumber;
    }

    /**
     * @param str     打印的内容
     * @param current 正在执行的线程
     * @param next    下一个要唤醒的线程
     */
    public void print(String str, Condition current, Condition next) {
        for (int i = 0; i < loopNumber; i++) {
            lock();
            try {
                current.await();
                log.debug(str);
                next.signal();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                unlock();
            }
        }
    }
}

