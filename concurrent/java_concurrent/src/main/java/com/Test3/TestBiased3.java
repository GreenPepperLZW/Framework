package com.Test3;

import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

import java.util.Vector;
import java.util.concurrent.locks.LockSupport;

/**
 * 批量撤销
 *
 * @author : lzw
 * @date : 2023/2/28
 * @since : 1.0
 */
@Slf4j
public class TestBiased3 {

    static Thread t1, t2, t3;

    public static void main(String[] args) throws InterruptedException {
        Vector<Pig> list = new Vector<>();
        int loopNumber = 39;

        t1 = new Thread(() -> {
            for (int i = 0; i < loopNumber; i++) {
                Pig pig = new Pig();
                list.add(pig);
                synchronized (pig) {
                    log.debug("加锁前：{}", ClassLayout.parseInstance(pig).toPrintable());
                }
            }
            LockSupport.unpark(t2);
        }, "t1");
        t1.start();

        t2 = new Thread(() -> {
            LockSupport.park();
            log.debug("===================");
            for (int i = 0; i < loopNumber; i++) {
                Pig pig = list.get(i);
                list.add(pig);
                synchronized (pig) {
                    log.debug("加锁后：{}", ClassLayout.parseInstance(pig).toPrintable());
                }
            }
            LockSupport.unpark(t3);
        }, "t2");
        t2.start();

        t3 = new Thread(() -> {
            LockSupport.park();
            log.debug("===================");
            for (int i = 0; i < loopNumber; i++) {
                Pig pig = list.get(i);
                list.add(pig);
                log.debug("加锁前：{}", ClassLayout.parseInstance(pig).toPrintable());
                synchronized (pig) {
                    log.debug("加锁后：{}", ClassLayout.parseInstance(pig).toPrintable());
                }
                log.debug("解锁后：{}", ClassLayout.parseInstance(pig).toPrintable());
            }
        }, "t3");
        t3.start();

        t3.join();
        // 偏向锁撤销超过40次时，这个类下所创建的对象会默认为不可偏向
        log.debug(ClassLayout.parseInstance(new Pig()).toPrintable());
    }
}

class Pig {

}
