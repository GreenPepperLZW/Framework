package helloworld;

import com.rabbitmq.client.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @version : 1.0
 * @Author : lzw
 * @Date : 2021/12/12 22:38
 * @Description : 直连模式,消费者
 */
public class Customer {

    public static void main(String[] args) throws IOException, TimeoutException {
        // 创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        // 绑定的主机
        connectionFactory.setHost("39.104.201.23");
        // 绑定的端口
        connectionFactory.setPort(5672);
        // 绑定的虚拟主机
        connectionFactory.setVirtualHost("/ems");
        // 用户名与密码
        connectionFactory.setUsername("ems");
        connectionFactory.setPassword("123");

        // 创建连接对象
        Connection connection = connectionFactory.newConnection();
        // 创建通道
        Channel channel = connection.createChannel();
        // 声明通道绑定的队列,消费者和生产者在声明通道绑定的队列时各参数必须一样
        channel.queueDeclare("hello", true, false, false, null);

        /**
         * 消费消息
         * 参数1：消费哪个队列的消息
         * 参数2：是否开启消息的自动确认机制
         * 参数3：消费消息时的回调接口
         */
        channel.basicConsume("hello",true,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                // 最后一个参数为队列中的消息
                System.out.println("消息："+new String(body));
            }
        });

        // 关闭通道
        channel.close();
        // 关闭连接
        connection.close();
    }
}
