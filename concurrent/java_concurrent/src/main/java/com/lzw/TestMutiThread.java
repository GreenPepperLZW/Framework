package com.lzw;

import lombok.extern.slf4j.Slf4j;

/**
 * @author : lzw
 * @date : 2023/1/13
 * @since : 1.0
 */
@Slf4j
public class TestMutiThread {

    public static void main(String[] args) {
        new Thread(() -> {
            log.info("t1");

        }, "t1").start();

        new Thread(() -> {
            log.info("t2");
        }, "t2").start();

    }


}
