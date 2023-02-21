package com.Test3;

import lombok.extern.slf4j.Slf4j;

/**
 * 变量在多线程共享下的最终值
 * 记录问题：下意识认为多核cpu下在测试代码中之启动两个线程应该不会发生上下文切换的问题，但是不要忘记了，此时系统中
 * 还运行这许多其他进程，不能保证8核cpu的电脑上只有八个线程在运行，这也是不可能的。
 *
 * @author : lzw
 * @date : 2023/2/10
 * @since : 1.0
 */
@Slf4j
public class Test9 {
    static int count = 0;

    public static void main(String[] args) throws InterruptedException {

        test1();

//        test2();

    }

    /**
     * 不加锁时数据不一致
     *
     * @throws InterruptedException
     */
    private static void test1() throws InterruptedException {
        log.debug("++++++++++开始执行");
        long start = System.currentTimeMillis();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                count++;
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                count--;
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();
        long end = System.currentTimeMillis();
        log.debug("++++++++++执行结束,耗时：{}", end - start);
        log.debug("-{}", count);
    }

    /**
     * 使用对象锁
     *
     * @throws InterruptedException
     */
    private static void test2() throws InterruptedException {
        log.debug("++++++++++开始执行");
        long start = System.currentTimeMillis();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                synchronized (Test9.class) {
                    count++;
                }
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                synchronized (Test9.class) {
                    count--;
                }
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();
        long end = System.currentTimeMillis();
        log.debug("++++++++++执行结束,耗时：{}", end - start);
        log.debug("-{}", count);
    }
}
