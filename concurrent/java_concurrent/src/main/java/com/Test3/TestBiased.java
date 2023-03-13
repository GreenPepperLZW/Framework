package com.Test3;

import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

/**
 * 偏向锁测试
 * t1线程运行时dog对象头中mark word 后三位是101，第一个1指开启偏向锁，01指无所状态
 * 加锁后，mark work 后三位还是 101，前10位记录线程id
 * 解锁后，状态不变，前十位记录线程id，后三位为101
 * 唤醒t2
 * <p>
 * t2运行，加锁前，dog对象头数据此时状态和t1线程运行结束后一致，保持不变
 * 加锁后，产生竞争，偏向锁升级为偏向锁，mark word前十位不再记录线程id,二十保存栈帧中锁记录的地址，后三位变为000
 * 第一个1表示非偏向锁，00表示轻量锁
 * 解锁后，对象头和锁记录进行cas交换，将所记录中的hasCode换回到对象头中，后三位变为001，0表示非偏向锁，01表示无锁
 *
 * @author : lzw
 * @date : 2023/2/22
 * @since : 1.0
 */
@Slf4j
public class TestBiased {

    public static void main(String[] args) {
        Dog dog = new Dog();

        new Thread(() -> {
            log.debug("加锁前：{}", ClassLayout.parseInstance(dog).toPrintable());
            synchronized (dog) {
                log.debug("加锁后：{}", ClassLayout.parseInstance(dog).toPrintable());
            }
            log.debug("解锁后：{}", ClassLayout.parseInstance(dog).toPrintable());
            // 等待
            synchronized (TestBiased.class) {
                // 唤醒t2线程
                TestBiased.class.notify();
            }
        }, "t1").start();

        // t2线程
        new Thread(() -> {
            synchronized (TestBiased.class) {
                try {
                    // 防止t1和t2交错运行，让t2先进行等待
                    TestBiased.class.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            log.debug("加锁前：{}", ClassLayout.parseInstance(dog).toPrintable());
            synchronized (dog) {
                log.debug("加锁后：{}", ClassLayout.parseInstance(dog).toPrintable());
            }
            log.debug("解锁后：{}", ClassLayout.parseInstance(dog).toPrintable());
        }, "t2").start();

    }
}


class Dog {

}
