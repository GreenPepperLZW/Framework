package fanout;

import com.rabbitmq.client.*;
import util.RabbitMqUtils;

import java.io.IOException;

/**
 * @version : 1.0
 * @Author : lzw
 * @Date : 2021/12/18 13:01
 * @Description : 广播模式，消费者1
 */
public class Customer1 {
    public static void main(String[] args) throws IOException {
        Connection connection = RabbitMqUtils.getConnection();
        Channel channel = connection.createChannel();

        // 通道绑定交换机
        channel.exchangeDeclare("logs", "fanout");
        // 获取一个临时队列，消息消费完后摧毁
        String queueName = channel.queueDeclare().getQueue();
        /**
         *  绑定交换机和队列
         *  参数1：队列名称
         *  参数2：交换机名称
         *  参数3：路由key,路由key在广播模式中没有实际意义
         */
        channel.queueBind(queueName, "logs", "");
        //消费消息
        channel.basicConsume(queueName,true,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者1："+new String(body));
            }
        });
    }
}
