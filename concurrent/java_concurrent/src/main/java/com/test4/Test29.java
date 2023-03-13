package com.test4;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

/**
 * 使用LookSupport中的 park,unpark方法实现交替输出
 *
 * @author : lzw
 * @date : 2023/3/13
 * @since : 1.0
 */
public class Test29 {

    static Thread t1;
    static Thread t2;
    static Thread t3;

    public static void main(String[] args) {
        ParkUnpark parkUnpark = new ParkUnpark(5);

        t1 = new Thread(() -> {
            parkUnpark.print("a", t2);
        }, "t1");

        t2 = new Thread(() -> {
            parkUnpark.print("b", t3);
        }, "t2");

        t3 = new Thread(() -> {
            parkUnpark.print("c", t1);
        }, "t3");

        t1.start();
        t2.start();
        t3.start();

        LockSupport.unpark(t1);

    }
}

@Slf4j
class ParkUnpark {

    private int loopNumber;

    public ParkUnpark(int loopNumber) {
        this.loopNumber = loopNumber;
    }

    /**
     * @param str  打印的内容
     * @param next 下一个要唤醒的线程
     */
    public void print(String str, Thread next) {
        for (int i = 0; i < loopNumber; i++) {
            LockSupport.park();
            log.debug(str);
            LockSupport.unpark(next);
        }
    }
}
