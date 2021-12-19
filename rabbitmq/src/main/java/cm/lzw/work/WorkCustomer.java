package cm.lzw.work;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @version : 1.0
 * @Author : lzw
 * @Date : 2021/12/19 17:00
 * @Description : 工作模式，消费者，生产者位于：{@link test}
 */
@Component
public class WorkCustomer {


    /**
     * @RabbitListener 注解可以作用与方法上，在一个类中可以声明多个消费者
     * @param message
     */
    @RabbitListener(queuesToDeclare = @Queue("work"))
    public void receivel(String message) {
        System.out.println("消费者1："+message);
    }

    @RabbitListener(queuesToDeclare = @Queue("work"))
    public void receivel2(String message) {
        System.out.println("消费者2："+message);
    }


}
