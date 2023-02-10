package com.lzw;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author : lzw
 * @date : 2023/1/13
 * @since : 1.0
 */
public class Test2 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<String> task = new FutureTask<>(() -> {
            System.out.println("callable执行");
            Thread.sleep(2000);
            return "aaa";

        });
        Thread thread = new Thread(task, "t1");
        thread.start();
        // 开始等待t1执行，t1执行完成后才能获取到值
        String s = task.get();
        System.out.println("t1返回的值：" + s);
        System.out.println("主线程执行");
    }
}
