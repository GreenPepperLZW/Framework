package com.lzw.ext;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * 测试@EventListener注解在任意组件方法中进行事件监听
 *
 * @author : lzw
 * @date : 2022/10/14
 * @since : 1.0
 */

@Service
public class UserService {

    @EventListener(classes = ApplicationEvent.class)
    public void listener(ApplicationEvent event) {
        System.out.println("UserService");
    }
}
