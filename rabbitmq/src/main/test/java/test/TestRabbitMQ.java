package test;

import cm.lzw.RabbitMqApplicaion;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @version : 1.0
 * @Author : lzw
 * @Date : 2021/12/19 14:45
 * @Description : 使用springboot提供的模板进行测试
 */
@SpringBootTest(classes = RabbitMqApplicaion.class)
@RunWith(SpringRunner.class)
public class TestRabbitMQ {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * hello world
     * 直连模式生产消息
     */
    @Test
    public void testHello() {
        /**
         * 建消息体自动转换为为byte发送到队列
         * 如果没有该队列的消费者，队列不会被创建
         */
        rabbitTemplate.convertAndSend("hello","hello world");
    }

    /**
     * 工作模式
     */
    @Test
    public void testWork() {
        for (int i = 0; i < 10; i++) {
            rabbitTemplate.convertAndSend("work","work 模型消息"+1);
        }
    }
}
