package direct;

import com.rabbitmq.client.*;
import util.RabbitMqUtils;

import java.io.IOException;

/**
 * @version : 1.0
 * @Author : lzw
 * @Date : 2021/12/18 14:20
 * @Description : 订阅模式，消费者2
 */
public class Customer2 {
    public static void main(String[] args) throws IOException {
        Connection connection = RabbitMqUtils.getConnection();
        Channel channel = connection.createChannel();
        // 通过通道声明交换机已经交换及类型
        channel.exchangeDeclare("logs_direct", "direct");
        // 创建临时队列，用来接收路由过来的消息
        String queue = channel.queueDeclare().getQueue();
        // 交换机和队列的绑定
        channel.queueBind(queue, "logs_direct", "info");
        channel.queueBind(queue, "logs_direct", "error");
        channel.queueBind(queue, "logs_direct", "warning");

        // 消费消息
        channel.basicConsume(queue,true,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者2"+new String(body));
            }
        });


    }
}
