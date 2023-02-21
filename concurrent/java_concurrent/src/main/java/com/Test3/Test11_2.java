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
public class Test11_2 {
    public static void main(String[] args) {
        Number_2 n1 = new Number_2();
        Number_2 n2 = new Number_2();
        new Thread(() -> {
            log.debug("begin");
            n1.a();
        }, "t1").start();

        new Thread(() -> {
            log.debug("begin");
            n2.b();
        }, "t2").start();

    }
}

@Slf4j
class Number_2 {
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

}
