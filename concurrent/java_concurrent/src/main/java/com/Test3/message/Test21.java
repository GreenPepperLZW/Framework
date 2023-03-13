package com.Test3.message;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;

/**
 * 设计模式-生产者消费者-实现
 *
 * @author : lzw
 * @date : 2023/3/7
 * @since : 1.0
 */
public class Test21 {

    public static void main(String[] args) {

        MessageQuene quene = new MessageQuene(2);

        for (int i = 0; i < 3; i++) {
            int id = 1;
            new Thread(() -> {
                quene.put(new Message(id, "值" + id));
            }, "生产者" + i).start();
        }

        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                    quene.take();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "消费者").start();
    }

}

//消息队列
@Slf4j
class MessageQuene {

    // 队列容量
    private int capacity;

    // 消息容器,使用双向队列
    private LinkedList<Message> list = new LinkedList<>();

    public MessageQuene(int capacity) {
        this.capacity = capacity;
    }

    // 获取消息
    public Message take() {
        // 检查队列是否为空
        synchronized (list) {
            while (list.isEmpty()) {
                try {
                    log.debug("获取消息队列为空，开始等待");
                    list.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            // 从队列头部获取消息
            Message message = list.removeFirst();
            log.debug("已消费消息：{}", message.getValue());
            // 唤醒生产者，可以往队列里放消息了
            list.notifyAll();
            return message;
        }
    }

    // 存入消息
    public void put(Message message) {
        synchronized (list) {
            // 检查队列是否已满
            while (list.size() == capacity) {
                try {
                    log.debug("队列已满开始等待");
                    list.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            // 将消息加入队列尾部
            list.addLast(message);
            log.debug("已生产消息：{}", message.getValue());
            // 唤醒消费者
            list.notifyAll();
        }
    }
}

// 消息
final class Message {
    private int id;
    private Object value;

    public Message(int id, Object value) {
        this.id = id;
        this.value = value;
    }


    public int getId() {
        return id;
    }

    public Object getValue() {
        return value;
    }

}
