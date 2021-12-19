package cm.lzw.fanout;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @version : 1.0
 * @Author : lzw
 * @Date : 2021/12/19 17:11
 * @Description : 广播模式消费者，生产者位于：{@link test}
 */
@Component
public class FanoutCustomer {

    /**
     * 使用bindings，直接绑定队列与交换机，不用单独声明队列
     * @param message
     */
    @RabbitListener(bindings = {
            @QueueBinding(
                    // 不指定队列名称时代表生成一个临时队列
                    value = @Queue,
                    // 绑定的交换机以及交换机的类型
                    exchange = @Exchange(value = "logs",type = "fanout")
            )
    })
    public void receivel(String message) {
        System.out.println("fanout消费者1:"+message);
    }

    @RabbitListener(bindings = {
            @QueueBinding(
                    // 不指定队列名称时代表生成一个临时队列
                    value = @Queue,
                    // 绑定的交换机以及交换机的类型
                    exchange = @Exchange(value = "logs",type = "fanout")
            )
    })
    public void receivel2(String message) {
        System.out.println("fanout消费者2:"+message);
    }

}
