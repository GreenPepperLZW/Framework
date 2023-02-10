package com.lzw;

import lombok.extern.slf4j.Slf4j;

/**
 * @author : lzw
 * @date : 2023/1/13
 * @since : 1.0
 */
@Slf4j(topic = "c.Test1")
public class Test1 {

    public static void main(String[] args) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                log.info("开始执行");
            }
        }, "t1");
        thread.run();
        log.info("man开始执行");
    }


}
