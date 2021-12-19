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
     * 工作模式生产者
     */
    @Test
    public void testWork() {
        for (int i = 0; i < 10; i++) {
            rabbitTemplate.convertAndSend("work","work 模型消息"+1);
        }
    }

    /**
     * 广播模型生产者
     */
    @Test
    public void testFanout() {
        // 如果该交换机没有绑定消费者，交换机不会被创建
        rabbitTemplate.convertAndSend("logs","","fanout模型消息");
    }

    /**
     * route 路由模式生产者
     */
    @Test
    public void testRoute() {
        // String routeKey = "info";
        String routeKey = "error";
        rabbitTemplate.convertAndSend("directs",routeKey,"route模型消息");
    }

    /**
     * topic 动态路由
     * 订阅模式
     */
    @Test
    public void testTopic() {
        // String route = "user.save";
        // String routeKey = "order.save";
        String routeKey = "product.save.add";
        rabbitTemplate.convertAndSend("topics",routeKey,"topic模型消息");
    }
}
