package com.Test3;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

/**
 * 卖票案例演示
 *
 * @author : lzw
 * @date : 2023/2/16
 * @since : 1.0
 */
@Slf4j
public class ExerciseSell {

    // Random为线程安全
    static Random random = new Random();

    public static void main(String[] args) throws InterruptedException {
        // 模拟多人买票
        TicketWindow ticketWindow = new TicketWindow(10000);
        // 统计卖出的票数
        List<Integer> amountList = new Vector<>();
        // 所有线程的集合
        List<Thread> threadList = new ArrayList<>();

        for (int i = 0; i < 2000; i++) {
            Thread thread = new Thread(() -> {
                // 买票
                int amount = ticketWindow.sell(randomAmount());
                amountList.add(amount);
            });
            threadList.add(thread);
            thread.start();
        }

        // 等待所有买票的线程结束
        for (Thread thread : threadList) {
            thread.join();
        }

        // 统计卖出的票数和剩余票数
        log.debug("余票数：{}", ticketWindow.getCount());
        log.debug("卖出的票数：{}", amountList.stream().mapToInt(i -> i).sum());

    }

    // 随机1-5
    public static int randomAmount() {
        return random.nextInt(5) + 1;
    }

}

// 卖票窗口类
class TicketWindow {
    private int count;

    public TicketWindow(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    // 加锁保护临界区代码
    public synchronized int sell(int amount) {
        if (this.count >= amount) {
            this.count -= amount;
            return amount;
        } else {
            return 0;
        }
    }
}
