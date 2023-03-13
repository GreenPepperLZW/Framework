package com.test4;

import lombok.extern.slf4j.Slf4j;

/**
 * 交替输出,循环5次
 * <p>
 * 线程1输出a
 * 线程2输出b
 * 线程2输出c
 *
 * @author : lzw
 * @date : 2023/3/10
 * @since : 1.0
 */
@Slf4j
public class Test27 {

    public static void main(String[] args) {
        WaitNotify wn = new WaitNotify(1, 5);
        new Thread(() -> {
            wn.print("a", 1, 2);
        }, "a").start();

        new Thread(() -> {
            wn.print("b", 2, 3);
        }, "a").start();

        new Thread(() -> {
            wn.print("c", 3, 1);
        }, "a").start();
    }
}


/**
 * 等待标记
 */
@Slf4j
class WaitNotify {

    // 等待标记
    private int flag;

    // 循环次数
    private int loopNumber;

    public WaitNotify(int flag, int loopNumber) {
        this.flag = flag;
        this.loopNumber = loopNumber;
    }

    public void print(String str, int waitflag, int nextFlag) {
        for (int i = 0; i < loopNumber; i++) {
            synchronized (this) {
                while (flag != waitflag) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                log.debug(str);
                flag = nextFlag;
                this.notifyAll();
            }
        }
    }
}
