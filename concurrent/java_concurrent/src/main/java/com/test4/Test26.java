package com.test4;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

/**
 * 使用 park() unPark() 实现固定顺序输出
 *
 * @author : lzw
 * @date : 2023/3/10
 * @since : 1.0
 */
@Slf4j
public class Test26 {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            LockSupport.park();
            log.debug("---------->1");
        }, "t1");
        t1.start();

        new Thread(() -> {
            log.debug("---------->2");
            LockSupport.unpark(t1);
        }, "t1").start();
    }
}
