package com.test;

import lombok.extern.slf4j.Slf4j;

/**
 * 主线程与守护进程
 * 默认情况下，Java进程需要等待所有线程都运行结束，才会结束。有一种特殊的的线程叫做守护线程，
 * 只要其他非守护线程运行结束了，即使守护线程的代码没有执行完，也会强制结束。
 * <p>
 * ===============
 * 注意：
 * 垃圾回收器线程就是一种守护线程
 * Tomcat中的Acceptor和Poller线程都是守护线程，所以Tomcat接收到shutdown命令后，不会等待他们处理完当前请求
 *
 * @author : lzw
 * @date : 2023/2/9
 * @since : 1.0
 */
@Slf4j
public class Test7 {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    break;
                }
            }
            log.debug("结束");
        }, "t1");
        t1.start();
        // 设为守护线程，主线程结束后，t1线程会被强制停止，因为此时已经没有其他存活的线程
        t1.setDaemon(true);
        Thread.sleep(1000);
        log.debug("结束");
    }
}
