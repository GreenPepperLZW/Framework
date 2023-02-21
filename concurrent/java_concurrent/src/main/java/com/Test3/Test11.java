package com.Test3;

import lombok.extern.slf4j.Slf4j;

/**
 * 线程八锁题目分析
 *
 * @author : lzw
 * @date : 2023/2/13
 * @since : 1.0
 */
@Slf4j
public class Test11 {
    public static void main(String[] args) {
        Number n1 = new Number();
        new Thread(() -> {
            log.debug("begin");
            n1.a();
        }, "t1").start();

        new Thread(() -> {
            log.debug("begin");
            n1.b();
        }, "t2").start();

        new Thread(() -> {
            log.debug("begin");
            n1.c();
        }, "t3").start();

    }
}

@Slf4j
class Number {
    public synchronized void a() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.debug("1");
    }

    public synchronized void b() {
        log.debug("2");
    }

    public void c() {
        log.debug("3");
    }
}
