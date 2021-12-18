package direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import util.RabbitMqUtils;

import java.io.IOException;

/**
 * @version : 1.0
 * @Author : lzw
 * @Date : 2021/12/18 13:50
 * @Description : 订阅模式，生产者
 */
public class Provider {
    public static void main(String[] args) throws IOException {
        Connection connection = RabbitMqUtils.getConnection();
        Channel channel = connection.createChannel();
        /**
         * 通过通道声明交换机
         * 参数1：交换机名称
         * 参数2：路由模式，订阅模式
         */
        channel.exchangeDeclare("logs_direct", "direct");
        // 发布消息
        // String routeingKy = "info";
        String routeingKy = "error";
        channel.basicPublish("logs_direct",routeingKy,null,("这是direct模型发布的基于route key:["+routeingKy+"]发送的消息").getBytes());
        // 关闭资源
        RabbitMqUtils.closeConnectionAndChannel(channel,connection);
    }
}
