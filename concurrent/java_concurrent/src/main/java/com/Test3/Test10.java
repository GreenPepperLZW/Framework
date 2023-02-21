package com.Test3;

import lombok.extern.slf4j.Slf4j;

/**
 * 使用面向对象的思想对Test9进行改造
 *
 * @author : lzw
 * @date : 2023/2/13
 * @since : 1.0
 */
@Slf4j
public class Test10 {
    public static void main(String[] args) throws InterruptedException {
        Room room = new Room();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                room.increment();
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                room.decrement();
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        log.debug("counter的值：{}", room.getCounter());

    }
}

class Room {
    private int counter = 0;

    public void increment() {
        synchronized (this) {
            counter++;
        }
    }

    /**
     * 把synchronized放在方法等价于上面的写法
     */
    public synchronized void decrement() {
        counter--;
    }

    public int getCounter() {
        /*synchronized (this) {
            return counter;
        }*/
        return counter;
    }
}
