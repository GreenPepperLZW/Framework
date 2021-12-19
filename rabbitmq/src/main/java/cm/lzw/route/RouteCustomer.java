package cm.lzw.route;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @version : 1.0
 * @Author : lzw
 * @Date : 2021/12/19 17:33
 * @Description :路由模式，消费者，生产者位于：{@link test}
 */
@Component
public class RouteCustomer {

    @RabbitListener(bindings = {
            @QueueBinding(
                    // 创建临时队列
                    value = @Queue,
                    // 绑定交换机以及交换机的类型,默认的交换机类型就为direct，可以省略
                    exchange = @Exchange(value = "directs",type = "direct"),
                    // 路由key
                    key = {"info","error","debug"}
            )
    })
    public void receivel(String message) {
        System.out.println("route消费者1路由key为info、error、debug消费的消息："+message);
    }

    @RabbitListener(bindings = {
            @QueueBinding(
                    // 创建临时队列
                    value = @Queue,
                    // 绑定交换机以及交换机的类型,默认的交换机类型就为direct，可以省略
                    exchange = @Exchange(value = "directs",type = "direct"),
                    // 路由key
                    key = {"error"}
            )
    })
    public void receive2(String message) {
        System.out.println("route消费者2路由key为error消费的消息："+message);
    }
}
