package com.lzw.jasypt;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;

/**
 * @author : lzw
 * @date : 2024/6/4
 * @since : 1.0
 */
public class Jasypt {
    public static void main(String[] args) {
        // 加密
        String encPwd1 = encyptPwd("mykey", "username");
        // 加密
        String encPwd2 = encyptPwd("mykey", "password");
        System.out.println(encPwd1);
        System.out.println(encPwd2);
    }

    /**
     * 加密方法
     *
     * @param password jasypt所需要的加密密码配置
     * @param value    需要加密的密码
     */
    public static String encyptPwd(String password, String value) {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(password);
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setStringOutputType("base64");
        encryptor.setConfig(config);
        String result = encryptor.encrypt(value);
        return result;
    }

    /**
     *数据库配置:
     *spring:
     *   datasource:
     *     driver-class-name: dm.jdbc.driver.DmDriver
     *     url: jdbc:dm://ip:port/xxxx?zeroDateTimeBehavior=convertToNull&useUnicode=true&characterEncoding=utf-8&clobAsString=true
     *     username: ENC(xU9yNLOa0MsHQ054swj93g==)
     *     password: ENC(qYRUoMjLCVFceU+Ybl05E9u37wddzkhb)
     *
     *配置文件:
     *jasypt:
     *   encryptor:
     *     password: mykey
     *     algorithm: PBEWithMD5AndDES
     *     iv-generator-classname: org.jasypt.iv.NoIvGenerator
     */
}
