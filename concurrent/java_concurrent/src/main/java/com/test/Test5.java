package com.test;

/**
 * 测试线程优先级setPriority()方法与yield()礼让方法
 *
 * @author : lzw
 * @date : 2023/2/8
 * @since : 1.0
 */
public class Test5 {
    public static void main(String[] args) {
        Runnable task1 = () -> {
            int count = 0;
            // 死循环
            for (; ; ) {
                // 轮到task1执行时，将时间片礼让给task2,最终结果与导致两个线程中count的差距非常大
//                Thread.yield();
                System.out.println("--------1>" + count++);
            }
        };
        Runnable task2 = () -> {
            int count = 0;
            // 死循环
            for (; ; ) {
//                Thread.yield();
                System.out.println("        --------2>" + count++);
            }
        };
        Thread t1 = new Thread(task1, "t1");
        Thread t2 = new Thread(task2, "t2");
//        t1.setPriority(Thread.MIN_PRIORITY);
//        t2.setPriority(Thread.MAX_PRIORITY);
        t1.start();
        t2.start();


    }
}
