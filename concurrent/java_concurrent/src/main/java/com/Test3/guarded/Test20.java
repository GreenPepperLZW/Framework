package com.Test3.guarded;

import com.util.Downloader;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

/**
 * 设计模式-保护性暂停-实现
 * a线程去等待b线程的结果
 * <p>
 * 对比join()的好处
 * 1.变量不用设为全局变量
 * 2.b线程将结果设置完成以后可以继续做其他事情
 *
 * @author : lzw
 * @date : 2023/3/6
 * @since : 1.0
 */
@Slf4j
public class Test20 {
    // 线程1等待线程2的下载结果
    public static void main(String[] args) {
        GuardedObject guardedObject = new GuardedObject();
        new Thread(() -> {
            synchronized (guardedObject) {
                // 等待结果
                log.debug("等待结果");
                List<String> list = (List<String>) guardedObject.get(2000);
                log.debug("结果的大小:{}", list.size());
            }
        }, "t1").start();
        
        new Thread(() -> {
            log.debug("开始下载");
            try {
                // 等两秒，让get()方法内部超时
                Thread.sleep(2000);

                List<String> list = Downloader.download();
                // 赋值,将结果传给线程1
                guardedObject.complete(list);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "t2").start();
    }

}


class GuardedObject {

    // 结果
    private Object response;

    public Object get(long timeout) {
        synchronized (this) {
            // 记录开始时间
            long begin = System.currentTimeMillis();
            // 记录经历的时间
            long passedTime = 0;
            while (response == null) {
                // 这一轮经历的时间
                long waitTime = timeout - passedTime;
                // 经历的时间超过了最大等待时间，退出循环
                //if (passed >= timeout) {
                if (waitTime <= 0) {
                    break;
                }
                try {
                    // 避免虚假唤醒，等待时长为timeout - passed
                    // timeout为2秒,如果从15:00:00开始，在15:00:01时被虚假唤醒了，response的值还是空的,进入下一轮等待，
                    // 这个时候需要等待的时间则为timeout - passed，也就是1秒，因为前面已经等了1秒
                    this.wait(waitTime);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                // 计算已经等待的时间
                passedTime = System.currentTimeMillis() - begin;
            }
            return this.response;
        }
    }

    public void complete(Object response) {
        synchronized (this) {
            this.response = response;
            // 给结果赋值完成后唤醒等待的线程
            this.notifyAll();
        }
    }
}
