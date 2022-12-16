package com.lzw.web.acutuator.info;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * 配置项目信息，和在application.yml中 info 的作用一样
 *
 * @author : lzw
 * @date : 2022/12/16
 * @since : 1.0
 */
@Component
public class ApplicaionInfoContributor implements InfoContributor {

    @Override
    public void contribute(Info.Builder builder) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("key", "value");
        builder.withDetail("code", "代码方式配置info");
        builder.withDetails(map);
    }
}
