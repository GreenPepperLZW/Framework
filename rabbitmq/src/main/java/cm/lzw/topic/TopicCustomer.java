package cm.lzw.topic;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @version : 1.0
 * @Author : lzw
 * @Date : 2021/12/19 17:46
 * @Description : 订阅模式，消费者，生产者位于：{@link test}
 */
@Component
public class TopicCustomer {

    @RabbitListener(bindings = {
            @QueueBinding(
                    // 不指定名称时，创建临时队列
                    value = @Queue,
                    // 声明绑定的交换机以及交换机类型
                    exchange = @Exchange(name = "topics",type = "topic"),
                    // 绑定的路由key,*：匹配一个词；#：匹配一个或多个词
                    key = {"user.save","user.*.test","user.#"}
            )
    })
    public void recevice(String message) {
        System.out.println("消费者1："+message);
    }

    @RabbitListener(bindings = {
            @QueueBinding(
                    // 不指定名称时，创建临时队列
                    value = @Queue,
                    // 声明绑定的交换机以及交换机类型
                    exchange = @Exchange(name = "topics",type = "topic"),
                    // 绑定的路由key,*：匹配一个词；#：匹配一个或多个词
                    key = {"order.#","product.#","user.*"}
            )
    })
    public void recevice2(String message) {
        System.out.println("消费者2："+message);
    }
}
