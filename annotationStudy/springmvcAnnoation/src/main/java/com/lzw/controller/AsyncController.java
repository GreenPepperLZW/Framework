package com.lzw.controller;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.concurrent.Callable;

/**
 * 异步处理
 *
 * @author : lzw
 * @date : 2022/11/4
 * @since : 1.0
 */
public class AsyncController {


    /**
     * springMvc处理异步请求的第一种方式，返回值定为 Callable<String> 即可
     *
     * @return
     */
    @RequestMapping("/async01")
    public Callable<String> async01() {
        System.out.println("主线程开始..." + Thread.currentThread() + "===>" + System.currentTimeMillis());
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("子线程开始..." + Thread.currentThread() + "===>" + System.currentTimeMillis());
                Thread.sleep(2000);
                System.out.println("子线程结束..." + Thread.currentThread() + "===>" + System.currentTimeMillis());
                return "Callable<String> async01()";
            }
        };
        System.out.println("主线程结束..." + Thread.currentThread() + "===>" + System.currentTimeMillis());
        return callable;
    }


}
