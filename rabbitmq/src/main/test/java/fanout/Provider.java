package fanout;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import util.RabbitMqUtils;

import java.io.IOException;

/**
 * @version : 1.0
 * @Author : lzw
 * @Date : 2021/12/15 22:36
 * @Description : 广播模式，生产者
 */
public class Provider {

    public static void main(String[] args) throws IOException {
        // 获取连接对象
        Connection connection = RabbitMqUtils.getConnection();
        // 创建通道
        Channel channel = connection.createChannel();
        /**
         * 通过通道声明指定交换机
         * 参数1：交换机名称
         * 参数2：交换及类型,如fanout：广播
         */
        channel.exchangeDeclare("logs","fanout");
        /**
         * 发送消息
         * 参数1：交换机名称
         * 参数2：路由key
         * 参数3：额外参数，如消息是否持久化
         * 参数4：具体消息内容
         */
        channel.basicPublish("logs","",null,"fanout type message".getBytes());
        // 关闭通道,释放资源
        RabbitMqUtils.closeConnectionAndChannel(channel,connection);
    }
}
