package com.test.两阶段终止;

import lombok.extern.slf4j.Slf4j;

/**
 * @author : lzw
 * @date : 2023/2/9
 * @since : 1.0
 */
@Slf4j(topic = "c.Test6")
public class Test6 {
    public static void main(String[] args) throws InterruptedException {
        TwoPhaseTermination tpt = new TwoPhaseTermination();
        tpt.start();
        Thread.sleep(5000);
        tpt.stop();
    }
}

/**
 * 启动一个监控线程，每隔两秒监控一下cpu使用情况，并能够被别的线程停止和启动
 */
@Slf4j(topic = "c.TwoPhaseTermination")
class TwoPhaseTermination {

    private Thread monitor;

    public void start() {
        monitor = new Thread(() -> {
            while (true) {
                Thread current = Thread.currentThread();
                boolean interrupted = current.isInterrupted();
                if (interrupted) {
                    log.debug("被打断，开始料理后事...");
                    break;
                }
                try {
                    Thread.sleep(1000);
                    log.debug("执行监控记录");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    // 在睡眠时被打断，打断标记会被清空，这里再次将打断标记置为真
                    current.interrupt();
                }
            }
        });
        monitor.start();
    }

    public void stop() {
        monitor.interrupt();
    }
}
