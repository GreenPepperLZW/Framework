package com.reentranlock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 验证ReentrantLock可重入特性
 *
 * @author : lzw
 * @date : 2023/3/7
 * @since : 1.0
 */
@Slf4j
public class Test22 {

    private static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        lock.lock();
        try {
            log.debug("进入main方法");
            m1();
        } finally {
            lock.unlock();
        }
    }

    public static void m1() {
        lock.lock();
        try {
            log.debug("进入m1方法");
            m2();
        } finally {
            lock.unlock();
        }
    }

    public static void m2() {
        lock.lock();
        try {
            log.debug("进入m2方法");
            m3();
        } finally {
            lock.unlock();
        }
    }

    public static void m3() {
        lock.lock();
        try {
            log.debug("进入m3方法");
        } finally {
            lock.unlock();
        }
    }
}
