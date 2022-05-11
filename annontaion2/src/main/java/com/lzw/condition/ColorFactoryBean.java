package com.lzw.condition;

import com.lzw.bean.Color;
import org.springframework.beans.factory.FactoryBean;

/**
 * 创建一个spring定义的FactoryBean
 * @author : lzw
 * @date : 2022/5/11
 * @since : 1.0
 */
public class ColorFactoryBean implements FactoryBean<Color> {

    // 返回一个color对象，这个对象会添加到容器中
    @Override
    public Color getObject() throws Exception {
        return new Color();
    }

    @Override
    public Class<?> getObjectType() {
        return Color.class;
    }

    // 控制单例
    // true: 单例
    // false：多例
    @Override
    public boolean isSingleton() {
        return false;
    }
}
