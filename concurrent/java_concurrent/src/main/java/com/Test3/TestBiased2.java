package com.Test3;

import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

import java.util.ArrayList;

/**
 * 批量重偏向测试
 * <p>
 * 当对一个线程偏向撤销超过20次（默认次数）时，不会再做撤销偏向的操作，将会偏向另一个线程
 * <p>
 * 不是针对某个锁，而是针对线程，线程1获得了30个对象的锁，等线程1执行完并释放锁后执行线程2，
 * 线程2中获取这三十个锁，此时这三十个锁都偏向于线程1，线程2在获取这三十个锁时，前20个锁都会先去分别撤销对线程1的偏向，并升级为轻量级锁作用于线程2，解锁后，该锁的状态也不再是偏向锁
 * 当超过20次时，第20个锁作用于线程2的时候，不会再去做对线程1的偏向撤销,直接批量重偏向线程2，提高效率。
 *
 * @author : lzw
 * @date : 2023/2/23
 * @since : 1.0
 */
@Slf4j
public class TestBiased2 {

    public static void main(String[] args) {

        ArrayList<Cat> objList = new ArrayList<>();

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 30; i++) {
                Cat cat = new Cat();
                objList.add(cat);
                synchronized (cat) {
                    log.debug(i + "\t" + ClassLayout.parseInstance(cat).toPrintable());
                }
            }
            synchronized (objList) {
                objList.notify();
            }
        }, "t1");

        t1.start();

        Thread t2 = new Thread(() -> {
            synchronized (objList) {
                try {
                    objList.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                log.debug("==============");
                for (int i = 0; i < 30; i++) {
                    Cat cat = objList.get(i);
                    log.debug(i + "\t" + ClassLayout.parseInstance(cat).toPrintable());
                    synchronized (cat) {
                        log.debug(i + "\t" + ClassLayout.parseInstance(cat).toPrintable());
                    }
                    log.debug(i + "\t" + ClassLayout.parseInstance(cat).toPrintable());
                }
            }
        }, "t2");
        t2.start();

    }
}

class Cat {

}
