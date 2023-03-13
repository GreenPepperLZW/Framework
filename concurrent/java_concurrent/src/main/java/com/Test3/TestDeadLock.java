package com.Test3;

/**
 * @author : lzw
 * @date : 2023/3/7
 * @since : 1.0
 */
public class TestDeadLock {
    public static void main(String[] args) {
        Object alock = new Object();
        Object block = new Object();

        new Thread(() -> {
            synchronized (alock) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                synchronized (block) {

                }
            }
        }).start();

        new Thread(() -> {
            synchronized (block) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                synchronized (alock) {

                }
            }
        }).start();

    }
}
