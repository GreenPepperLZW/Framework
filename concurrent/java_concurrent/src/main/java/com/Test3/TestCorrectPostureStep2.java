package com.Test3;

import lombok.extern.slf4j.Slf4j;

/**
 * 模拟多线程中，一个线程中条件不满足时，无法继续执行，必须等待条件满足才能执行。
 * 使用wait()方法等待
 * <p>
 * 使用notify()方法唤醒线程
 *
 * @author : lzw
 * @date : 2023/3/1
 * @since : 1.0
 */
@Slf4j
public class TestCorrectPostureStep2 {

    static final Object room = new Room();

    static boolean hasCigarette = false; //有没有烟
    static boolean hasTakeout = false;

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            synchronized (room) {
                log.debug("有烟没【{}】", hasCigarette);
                if (!hasCigarette) {
                    log.debug("没烟，先歇会儿...");
                    try {
//                        Thread.sleep(2000);
                        room.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                log.debug("有烟没？【{}】", hasCigarette);
                if (hasCigarette) {
                    log.debug("可以开始干活了");
                }
            }
        }, "小南").start();


        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                synchronized (room) {
                    log.debug("可以开始干活了....");
                }
            }, "其他人").start();
        }

        Thread.sleep(1000);

        new Thread(() -> {
            // 拿到锁，成为room的owner
            synchronized (room) {
                hasCigarette = true;
                // 使用notify()方法唤醒线程
                room.notify();
                log.debug("烟到了");
            }
        }, "送烟的").start();
    }
}
