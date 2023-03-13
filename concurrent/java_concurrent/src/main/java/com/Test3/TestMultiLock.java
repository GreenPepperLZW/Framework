package com.Test3;

import lombok.extern.slf4j.Slf4j;

/**
 * 多把锁测试
 *
 * @author : lzw
 * @date : 2023/3/7
 * @since : 1.0
 */
public class TestMultiLock {

    public static void main(String[] args) {
        BigRoom bigRoom = new BigRoom();

        new Thread(() -> {
            bigRoom.sleep();
        }, "小南").start();

        new Thread(() -> {
            bigRoom.study();
        }, "小女").start();
    }
}

@Slf4j
class BigRoom {

    // 睡觉和学习互不打扰，准备两把锁
    private final Object studyRoom = new Object();
    private final Object bedRoom = new Object();

    public void sleep() {
        synchronized (bedRoom) {
            log.debug("sleep 2 小时");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void study() {
        synchronized (studyRoom) {
            log.debug("学习 20 分钟");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
