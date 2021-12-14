package workquene;

import com.rabbitmq.client.*;
import util.RabbitMqUtils;

import java.io.IOException;

/**
 * @version : 1.0
 * @Author : lzw
 * @Date : 2021/12/14 23:06
 * @Description : 工作队列，消费者2
 */
public class Customer2 {
    public static void main(String[] args) throws IOException {
        // 获取连接
        Connection connection = RabbitMqUtils.getConnection();
        // 创建通道
        Channel channel = connection.createChannel();
        // 声明通道绑定的队列
        channel.queueDeclare("work",false,false,false,null);

        //消费消息
        channel.basicConsume("work",true,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者-2"+new String(body));
            }
        });
    }
}
