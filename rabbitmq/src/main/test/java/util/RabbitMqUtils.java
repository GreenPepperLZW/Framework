package util;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @version : 1.0
 * @Author : lzw
 * @Date : 2021/12/14 22:07
 * @Description :
 */
public class RabbitMqUtils {

    // 在类加载时创建连接工厂
    private static ConnectionFactory connectionFactory;
    static {
        // 1.创建连接mq的连接工厂
        connectionFactory = new ConnectionFactory();
        // 2.设置连接rabbitmq的主机
        connectionFactory.setHost("39.104.201.23");
        // 3.设置端口号
        connectionFactory.setPort(5672);
        // 4.设置连接哪个虚拟主机
        connectionFactory.setVirtualHost("/ems");
        // 5.设置访问虚拟主机的用户名和密码
        connectionFactory.setUsername("ems");
        connectionFactory.setPassword("123");
    }

    /**
     * 提供连接对象
     * @return {@link Connection}
     */
    public static Connection getConnection() {
        try {
            // 获取连接对象
            return connectionFactory.newConnection();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 关闭通道和关闭连接方法
     * @param channel 通道
     * @param connection 连接
     */
    public static void closeConnectionAndChannel(Channel channel, Connection connection) {
        try {
            if (null != channel) {
                channel.close();
            }
            if (null != connection) {
                connection.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
