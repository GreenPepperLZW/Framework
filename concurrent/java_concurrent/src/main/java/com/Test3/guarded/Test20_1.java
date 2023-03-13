package com.Test3.guarded;

import lombok.extern.slf4j.Slf4j;

import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

/**
 * 设计模式-保护性暂停-实现
 * a线程去等待b线程的结果
 * <p>
 * 对比join()的好处
 * 1.变量不用设为全局变量
 * 2.b线程将结果设置完成以后可以继续做其他事情
 * ===============================================
 * 多任务版 GuardedObject2,场景如下：
 * 一层居民楼中有多个居民，每个居民有自己对应的邮箱，邮递员将邮件放入邮箱，居民从邮箱中获取邮件,一个邮递员对应一个居民
 *
 * @author : lzw
 * @date : 2023/3/6
 * @since : 1.0
 */
@Slf4j
public class Test20_1 {

    // 线程1等待线程2的下载结果
    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            new People().start();
        }
        Thread.sleep(1000); // 等待一秒，确保boxes数组已经有值
        for (Integer id : Mailboxes.getIds()) {
            new Postman(id, "内容" + id).start();
        }
    }

}

/**
 * 居民类
 */
@Slf4j
class People extends Thread {

    // 收信
    @Override
    public void run() {
        // 创建邮箱中的格子
        GuardedObject2 guardedObject = Mailboxes.createGuardedObject();
        log.debug("开始收信id：{}", guardedObject.getId());
        Object mail = guardedObject.get(5000);
        log.debug("收到信id：{},信件内容：{}", guardedObject.getId(), mail);
    }
}

/**
 * 邮递员列
 */
@Slf4j
class Postman extends Thread {

    // 信箱id,也就是邮箱中的每个小格子
    private int id;

    // 邮件内容
    private String mail;

    public Postman(int id, String mail) {
        this.id = id;
        this.mail = mail;
    }

    // 投信件
    @Override
    public void run() {
        GuardedObject2 guaredObject = Mailboxes.getGuaredObject(id);
        // 送信
        log.debug("送行id：{}，送信的内容：{}", id, mail);
        guaredObject.complete(mail);
    }
}

/**
 * 邮箱
 */
class Mailboxes {
    // 使用线程安全的集合
    private static Map<Integer, GuardedObject2> boxes = new Hashtable<>();

    private static int id = 1;

    // 产生唯一id
    public static synchronized int generateId() {
        return id++;
    }

    // 根据id获取信箱
    public static GuardedObject2 getGuaredObject(int id) {
        return boxes.remove(id);
    }

    public static synchronized GuardedObject2 createGuardedObject() {
        GuardedObject2 go = new GuardedObject2(generateId());
        boxes.put(go.getId(), go);
        return go;
    }

    // 返回所有id集合
    public static Set<Integer> getIds() {
        return boxes.keySet();
    }
}

/**
 * 邮箱中的小格子
 */
class GuardedObject2 {

    // 标识
    private int id;
    // 结果
    private Object response;

    public GuardedObject2(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

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
