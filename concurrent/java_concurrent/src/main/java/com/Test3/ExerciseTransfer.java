package com.Test3;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;

/**
 * 转账案例演示
 *
 * @author : lzw
 * @date : 2023/2/16
 * @since : 1.0
 */
@Slf4j
public class ExerciseTransfer {

    static Random random = new Random();

    public static int randomAmount() {
        return random.nextInt(99) + 1;
    }

    public static void main(String[] args) throws InterruptedException {
        Account a = new Account(1000);
        Account b = new Account(1000);
        // a账户向b账户赚钱
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                a.transfer(b, randomAmount());
            }
        });
        // b账户向a账户赚钱
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                b.transfer(a, randomAmount());
            }
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        log.debug("a账户余额：{}，b账户余额：{}", a.getMoney(), b.getMoney());
    }
}

// 账户
class Account {
    private int money;

    public Account(int money) {
        this.money = money;
    }

    public int getMoney() {
        return money;
    }

    public int setMoney(int money) {
        return this.money = money;
    }

    /**
     * @param target 收款账户
     * @param amount 转出金额
     */
    public void transfer(Account target, int amount) {
        /**
         *  a账户的余额是共享变量，b账户的余额也是共享变量，
         *  此时使用synchronized只能保护this的共享变量，不能保护其他实例在操纵时的线程安全问题
         *  两个实例在两个线程中互相调用各自的共享变量，使用这两个实例共享的对象或类来做锁，即可保证线程安全问题
         */
        synchronized (Account.class) {
            if (this.money >= amount) {
                this.setMoney(this.getMoney() - amount);
                target.setMoney(target.getMoney() + amount);
            }
        }
    }
}
