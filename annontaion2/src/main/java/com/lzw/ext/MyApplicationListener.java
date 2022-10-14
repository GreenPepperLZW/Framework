package com.lzw.ext;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author : lzw
 * @date : 2022/10/13
 * @since : 1.0
 */
@Component
public class MyApplicationListener implements ApplicationListener<ApplicationEvent> {

    // 当容器中发布此事件后，方法触发
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        System.out.println("收到事件" + event);

    }
}
