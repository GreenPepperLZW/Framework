package workquene;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import util.RabbitMqUtils;

import java.io.IOException;

/**
 * @version : 1.0
 * @Author : lzw
 * @Date : 2021/12/14 22:59
 * @Description : 工作队列，生产者
 */
public class Provider {

    public static void main(String[] args) throws IOException {
        // 获取连接对象
        Connection connection = RabbitMqUtils.getConnection();
        // 获取通道
        Channel channel = connection.createChannel();
        //通过通道声明队列
        channel.queueDeclare("work", false, false, false, null);
        //生产消息
        for (int i = 0; i < 10; i++) {
            channel.basicPublish("","work",null,(i+"hello work qunen").getBytes());
        }
        // 关闭连接
        RabbitMqUtils.closeConnectionAndChannel(channel,connection);
    }
}
