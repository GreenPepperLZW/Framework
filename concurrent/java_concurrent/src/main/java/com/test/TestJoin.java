package com.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author : lzw
 * @date : 2023/2/8
 * @since : 1.0
 */
@Slf4j
public class TestJoin {
    static int r = 0;
    static int r1 = 0;
    static int r2 = 0;

    public static void main(String[] args) {
        try {
//            test01();
//            test02();
            test03();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void test01() throws InterruptedException {
        log.debug("开始");
        Thread t1 = new Thread() {
            @Override
            public void run() {
                log.debug("开始");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                log.debug("结束");
                r = 10;
                log.debug("赋值结束");
            }
        };

        t1.start();
        // 比t1线程多休眠1毫秒，等待t1线程赋值结束
//        Thread.sleep(1001);
        //调用join方法等待t1线程结束
        t1.join();
        log.debug("结果为：{}", r);
        log.debug("结束");
    }

    public static void test02() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            r1 = 10;
        });

        Thread t2 = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            r2 = 20;
        });

        t1.start();
        t2.start();
        long stat = System.currentTimeMillis();
        log.debug("join begin");
        t1.join();
        log.debug("join end");
        log.debug("join begin");
        t2.join();
        log.debug("join end");
        long end = System.currentTimeMillis();
        // 最终耗费时间为2秒左右，t1、t2同时启动，t1等待两秒的过程中，t2已经执行完成
        log.debug("r1:{},r2:{},cost:{}", r1, r2, end - stat);
    }

    /**
     * 测试有参数的join方法，有时间限制的等待
     */
    public static void test03() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            r1 = 10;
        });
        t1.start();
        // 超过等待时间则终止等待，继续往下运行
        t1.join(5);
        log.debug("r1:{}", r1);
    }
}
