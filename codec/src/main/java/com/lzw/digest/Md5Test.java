package com.lzw.digest;

import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * 摘要算法
 * 又叫Hash算法、散列函数、数字摘要、消息摘要。它是一种单向算法，既结果不可逆，用户可以通过hash算法对目标信息生成一段特定长度的唯一hash值，
 * 不同算法生成的长度不一样，但不能通过这个hash值重新获得目标信息。
 *
 * 常见算法实现有MD5、SHA、MAC、MD2,MD4,HAVAL
 *
 * @author : lzw
 * @date : 2021/12/25
 * @since : 1.0
 */
public class Md5Test {

    private static final String UTF8 = StandardCharsets.UTF_8.name();

    /**
     * 使用jdk类库实现md5加密
     */
    @Test
    public void jdkMd5Test() throws Exception{
        String str = "生如夏花";
        // 定义算法
        String algorithm = "MD5";
        // 获取原始内容的字节数组
        MessageDigest md = MessageDigest.getInstance(algorithm);


    }
}
