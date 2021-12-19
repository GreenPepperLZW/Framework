package topic;

import com.rabbitmq.client.*;
import util.RabbitMqUtils;

import java.io.IOException;

/**
 * @version : 1.0
 * @Author : lzw
 * @Date : 2021/12/19 14:11
 * @Description : 动态路由，消费者1
 */
public class Costumer1 {
    public static void main(String[] args) throws IOException {
        //获取连接
        Connection connection = RabbitMqUtils.getConnection();
        // 创建通道
        Channel channel = connection.createChannel();
        // 通过通道声明交换及以及交换及类型
        channel.exchangeDeclare("topics", "topic");
        // 获取临时队列
        String queue = channel.queueDeclare().getQueue();
        // 绑定交换机和队列
        channel.queueBind(queue, "topics", "user.*");
        // 消费消息
        channel.basicConsume(queue, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者1："+new String(body));
            }
        });
    }
}
