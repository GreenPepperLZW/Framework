package com.lzw.encoder;

import org.junit.Test;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * 对请求地址url进行编码
 *
 * @author : lzw
 * @date : 2021/12/25
 * @since : 1.0
 */
public class UrlTest {

    private static final String UTF8 = StandardCharsets.UTF_8.name();

    @Test
    public void test1() throws Exception {
        String str = "https://www.bilibili.com/?param1=aa&param2=bb";
        // 编码,https%3A%2F%2Fwww.bilibili.com%2F%3Fparam1%3Daa%26param2%3Dbb
        String encodeStr = URLEncoder.encode(str, UTF8);
        System.out.println("编码后的请求路径:" + encodeStr);

        // 解码
        String decodeUrl = URLDecoder.decode(encodeStr, UTF8);
        System.out.println("解码后的地址" + decodeUrl);

    }
}
