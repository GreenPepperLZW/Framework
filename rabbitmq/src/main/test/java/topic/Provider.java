package topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import util.RabbitMqUtils;

import java.io.IOException;

/**
 * @version : 1.0
 * @Author : lzw
 * @Date : 2021/12/19 13:53
 * @Description : 动态路由模式，生产者
 */
public class Provider {
    public static void main(String[] args) throws IOException {
        Connection connection = RabbitMqUtils.getConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare("topics", "topic");
        /**
         *  * (star) can substitute for exactly one word.    匹配不多不少恰好1个词
         *  # (hash) can substitute for zero or more words.  匹配一个或多个词
         */
        //发布消息
        String roteKey = "user.save";
        channel.basicPublish("topics",roteKey,null,("topic动态路由模型，routeKey:["+roteKey+"]").getBytes());
        // 释放资源
        RabbitMqUtils.closeConnectionAndChannel(channel,connection);
    }
}
