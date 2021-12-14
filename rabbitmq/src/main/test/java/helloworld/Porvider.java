package helloworld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.junit.jupiter.api.Test;
import util.RabbitMqUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @version : 1.0
 * @Author : lzw
 * @Date : 2021/12/12 18:13
 * @Description : 直连模式，不通过交换机;消息提供者
 */
public class Porvider {

    /**
     * 生产消息
     */
    @Test
    public void testSendMessage() throws IOException, TimeoutException, InterruptedException {
        Connection connection = RabbitMqUtils.getConnection();
        // 获取连接通道
        Channel channel = connection.createChannel();
        /**
         * 声明通道绑定的对应的队列
         * 参数1：队列名称，如果不存在会自动创建
         * 参数2：队列是否要持久化到磁盘，true：持久化，false：不持久化
         * 参数3：是否独占队列，该队列只允许当前连接可用，其他连接不可用该队列，true
         * 参数4：队列使用完后是否自动删除
         * 参数5；其他参数
         */
        channel.queueDeclare("hello", true, false, false, null);
        /**
         * 发布消息
         * 参数1：交换机
         * 参数2：队列名称
         * 参数3：发布消息时的一些属性
         * 参数4：消息的具体内容
         */
        for (int i=1;i<5;i++){
            channel.basicPublish("","hello",null,"hello rabbitMq".getBytes());
            Thread.sleep(1000);
        }


        RabbitMqUtils.closeConnectionAndChannel(channel,connection);

    }
}
