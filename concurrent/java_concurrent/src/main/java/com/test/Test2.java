package com.test;

import lombok.extern.slf4j.Slf4j;

/**
 * 获取线程状态信息
 *
 * @author : lzw
 * @date : 2023/2/7
 * @since : 1.0
 */
@Slf4j(topic = "c.Test2")
public class Test2 {
    public static void main(String[] args) {
        Thread t1 = new Thread() {
            @Override
            public void run() {
                log.info("Running");
            }
        };
        System.out.println("线程启动前：" + t1.getState());
        t1.start();
        System.out.println("线程启动后：" + t1.getState());

    }
}
