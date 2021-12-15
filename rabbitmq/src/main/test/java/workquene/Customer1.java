package workquene;

import com.rabbitmq.client.*;
import util.RabbitMqUtils;

import java.io.IOException;

/**
 * @version : 1.0
 * @Author : lzw
 * @Date : 2021/12/14 23:06
 * @Description : 工作队列，消费者1
 */
public class Customer1 {

    public static void main(String[] args) throws IOException {
        // 获取连接
        Connection connection = RabbitMqUtils.getConnection();
        // 创建通道
        Channel channel = connection.createChannel();
        // 每次可以接收到几条消息
        channel.basicQos(1);
        // 声明通道绑定的队列
        channel.queueDeclare("work",false,false,false,null);
        /**
         * 消费消息
         * 参数2为消息是否自动确认，true代表自动确认，告诉mq服务我已收到消息，此时mq服务将发送下一条消息到Customer1
         * 但是此时第一条消息正在处理中，第二条消息还未开始处理。由于开启了自动确认机制，会源源不断的接收消息。
         * 加入此时接收了五条消息，但在处理第二条消息时，Customer1挂调了，后面三条消息就丢失了
         */
        channel.basicConsume("work",false,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者-1"+new String(body));
                // 手动确认消息已消费, 参数1：手动确认标识，参数2：每次只确认一个
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        });
    }
}
