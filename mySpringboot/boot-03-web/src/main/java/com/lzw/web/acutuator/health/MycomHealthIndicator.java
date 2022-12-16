package com.lzw.web.acutuator.health;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * 自定义监控检查，类名结尾必须为Indicator
 * <p>
 * 健康监控检查是将应用内所有组件的健康状态汇总后得到的信息
 * 如磁盘空间检查，ping是否可以通，数据库是否连接正常，
 * 如果应用内接入了其他第三发组件，可以通过继承 AbstractHealthIndicator 类来定制化健康检查
 *
 * @author : lzw
 * @date : 2022/12/16
 * @since : 1.0
 */
@Component
public class MycomHealthIndicator extends AbstractHealthIndicator {

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        // 存放业务信息
        HashMap<String, Object> map = new HashMap<>();
        if (1 == 1) {
            builder.status(Status.UP);
            map.put("count", 1);
            map.put("ms", 100);
        } else {
            builder.status(Status.OUT_OF_SERVICE);
            map.put("error", "连接超时");
        }

        builder.withDetail("code", 200)
                .withDetails(map);

    }
}
