package com.test;

import lombok.extern.slf4j.Slf4j;

/**
 * 测试run()和start()方法
 *
 * @author : lzw
 * @date : 2023/2/7
 * @since : 1.0
 */
@Slf4j
public class Test1 {

    public static void main(String[] args) {
        Thread thread = new Thread("t1") {
            @Override
            public void run() {
                log.info("running");
            }
        };
        thread.run();// 主线程调用，没有开启一个新的线程
        thread.start();
        log.info("do other things");
    }
}
