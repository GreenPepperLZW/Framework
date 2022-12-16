package com.lzw.web.acutuator.endpoint;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;

/**
 * 自定义监控端点，和info,health,metrics同一级别的组件
 *
 * @author : lzw
 * @date : 2022/12/16
 * @since : 1.0
 */
@Component
@Endpoint(id = "myservice")
public class MyServiceEndPoint {

    // 读方法
    @ReadOperation
    public Map getDockerInfo() {
        return Collections.singletonMap("dockerInfo", "docker started...");
    }

    // 写方法
    @WriteOperation
    public void stopDocker() {
        System.out.println("docker stopped");
    }
}
