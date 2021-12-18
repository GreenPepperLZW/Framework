package direct;

import com.rabbitmq.client.*;
import util.RabbitMqUtils;

import java.io.IOException;

/**
 * @version : 1.0
 * @Author : lzw
 * @Date : 2021/12/18 14:04
 * @Description : direct订阅模式，消费者1
 */
public class Custemer1 {
    public static void main(String[] args) throws IOException {
        Connection connection = RabbitMqUtils.getConnection();
        Channel channel = connection.createChannel();

        // 使用通道声明交换机以及交换及的类型
        channel.exchangeDeclare("logs_direct", "direct");
        // 获取临时队列
        String queueName = channel.queueDeclare().getQueue();
        // 基于路由key绑定交换机和队列
        channel.queueBind(queueName,"logs_direct","error");
        // 消费消息
        channel.basicConsume(queueName,true,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者1："+new String(body));
            }
        });
    }
}
