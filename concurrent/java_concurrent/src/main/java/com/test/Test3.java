package com.test;

import lombok.extern.slf4j.Slf4j;

/**
 * sleep()方法被调用后线程进入TIMED_WAITING状态
 *
 * @author : lzw
 * @date : 2023/2/7
 * @since : 1.0
 */
@Slf4j
public class Test3 {
    public static void main(String[] args) {
        Thread t1 = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        t1.start();
        log.debug("t1 state:{}", t1.getState());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.debug("t1 state:{}", t1.getState());
    }

}
