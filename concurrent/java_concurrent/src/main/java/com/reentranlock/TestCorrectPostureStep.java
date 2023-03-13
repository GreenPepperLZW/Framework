package com.reentranlock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 模拟多线程中，一个线程中条件不满足时，无法继续执行，必须等待条件满足才能执行。
 * 使用 ReentrantLock中的 signal()方法唤醒线程
 *
 * @author : lzw
 * @date : 2023/3/10
 * @since : 1.0
 */
@Slf4j
public class TestCorrectPostureStep {

    static final ReentrantLock room = new ReentrantLock();

    // 等烟的修饰室
    static Condition waitCigaretteSet = room.newCondition();
    // 等外卖的休息室
    static Condition waitTakeoutSet = room.newCondition();

    static boolean hasCigarette = false; //有没有烟
    static boolean hasTakeout = false; //外卖是否送到

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            room.lock();
            try {
                log.debug("有烟没【{}】 ", hasCigarette);
                while (!hasCigarette) {
                    log.debug("没烟，先歇会儿...");
                    try {
                        // 进入条件变量（进入休息室）
                        waitCigaretteSet.await();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                log.debug("可以开始干活了");
            } finally {
                room.unlock();
            }
        }, "小南").start();

        new Thread(() -> {
            room.lock();
            try {
                log.debug("外卖送到了没？【{}】", hasTakeout);
                if (!hasTakeout) {
                    log.debug("没外卖，先歇会儿！");
                    try {
                        waitTakeoutSet.await();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                log.debug("外卖送到了没？【{}】", hasTakeout);
                if (hasTakeout) {
                    log.debug("可以开始干活了");
                } else {
                    log.debug("没干成活");
                }
            } finally {
                room.unlock();
            }
        }, "小女").start();

        Thread.sleep(1000);

        new Thread(() -> {
            room.lock();
            try {
                hasTakeout = true;
                // 唤醒
                waitTakeoutSet.signal();
            } finally {
                room.unlock();
            }
        }, "送外卖的").start();

        Thread.sleep(2000);
        new Thread(() -> {
            room.lock();
            try {
                hasCigarette = true;
                // 唤醒
                waitCigaretteSet.signal();
            } finally {
                room.unlock();
            }
        }, "送烟的").start();
    }
}
