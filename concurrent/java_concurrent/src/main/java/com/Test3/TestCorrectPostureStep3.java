package com.Test3;

import lombok.extern.slf4j.Slf4j;

/**
 * 模拟多线程中，一个线程中条件不满足时，无法继续执行，必须等待条件满足才能执行。
 * 使用notifyAll()唤醒线程
 *
 * @author : lzw
 * @date : 2023/3/1
 * @since : 1.0
 */
@Slf4j
public class TestCorrectPostureStep3 {

    static final Object room = new Room();

    static boolean hasCigarette = false; //有没有烟
    static boolean hasTakeout = false; //外卖是否送到

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            synchronized (room) {
                log.debug("有烟没【{}】 ", hasCigarette);
                while (!hasCigarette) {
                    log.debug("没烟，先歇会儿...");
                    try {
                        room.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                log.debug("有烟没？【{}】", hasCigarette);
                if (hasCigarette) {
                    log.debug("可以开始干活了");
                } else {
                    log.debug("没干成或");
                }
            }
        }, "小南").start();

        new Thread(() -> {
            synchronized (room) {
                log.debug("外卖送到了没？【{}】", hasTakeout);
                if (!hasTakeout) {
                    log.debug("没外卖，先歇会儿！");
                    try {
                        room.wait();
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
            }
        }, "小女").start();

        Thread.sleep(1000);

        new Thread(() -> {
            synchronized (room) {
                hasTakeout = true;
//                hasCigarette = true;
                // notify()只能随机唤醒一个线程，为防止错误唤醒小南线程，使用notifyAll()唤醒所有线程，这样就必然可以唤醒小女线程
                // 如果此时唤醒了小南线程，可以使用while循环让小南进入到下一轮等待
                room.notifyAll();
                log.debug("外卖到了！");
            }
        }, "送外卖的").start();
    }
}
