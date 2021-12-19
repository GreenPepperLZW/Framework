package cm.lzw.hello;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @version : 1.0
 * @Author : lzw
 * @Date : 2021/12/19 16:26
 * @Description : 直连模式，消费者，生产者位于：{@link test}
 */
@Component
/**
 * RabbitListener 监听队列
 * queuesToDeclare 没有队列时创建队列
 * @Queue 注解中的参数
 *  参数1：队列名称
 *  参数2：队列是否持久化
 *  参数3：是否自动删除
 *  参数4：是否独占
 *  默认参数为：持久化，非独占，不是自动删除队列
 */
@RabbitListener(queuesToDeclare = @Queue(value = "hello",durable = "true",autoDelete = "ture"))
public class HelloCustomer {

    /**
     * @RabbitHandler 注解的作用：声明从队列中取出消息是使用哪个方法进行处理
     * @param message
     */
    @RabbitHandler
    public void receivel(String message) {
        System.out.println("message:"+message);
    }

}
