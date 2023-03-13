package com.reentranlock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用ReentrantLock解决哲学家就餐问题
 *
 * @author : lzw
 * @date : 2023/3/7
 * @since : 1.0
 */
public class TestDeadLock {
    public static void main(String[] args) {
        Chopstick c1 = new Chopstick("1");
        Chopstick c2 = new Chopstick("2");
        Chopstick c3 = new Chopstick("3");
        Chopstick c4 = new Chopstick("4");
        Chopstick c5 = new Chopstick("5");
        new Philosopher("苏格拉底", c1, c2).start();
        new Philosopher("柏拉图", c2, c3).start();
        new Philosopher("亚里士多德", c3, c4).start();
        new Philosopher("赫拉克利特", c4, c5).start();
        new Philosopher("阿基米德", c5, c1).start();
    }
}

// 筷子类
class Chopstick extends ReentrantLock {
    String name;

    public Chopstick(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "筷子{" + name + '}';
    }
}

@Slf4j
class Philosopher extends Thread {
    // 持有两个资源
    Chopstick left;
    Chopstick right;

    public Philosopher(String name, Chopstick left, Chopstick right) {
        super(name);
        this.left = left;
        this.right = right;
    }

    private void eat() {
        log.debug("eating...");
        try {
            Thread.sleep(1000); // sleep时会释放掉当前持有的锁
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        while (true) {
            // 获得左手筷子
            if (left.tryLock()) {
                try {
                    // 获得右手筷子
                    if (right.tryLock()) {
                        try {
                            // 吃饭
                            eat();
                        } finally {
                            // 放下右手筷子
                            right.unlock();
                        }
                    }
                } finally {
                    // 放下左手筷子
                    left.unlock();
                }
            }
        }
    }
}


