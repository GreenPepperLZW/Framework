package com.lzw.encoder;

import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * 为了更方便的在网络间传输数据，将数据转成base64
 * <p>
 * base64是一种编码算法，并不能说是一种加密算法
 *
 * @author : lzw
 * @date : 2021/12/25
 * @since : 1.0
 */
public class Base64Test {


    private static final String UTF8 = StandardCharsets.UTF_8.name();

    /**
     * 使用jdk原生类库实现base64编码算法
     * jdk1.8之后
     */
    @Test
    public void jdkBase64Test() throws Exception {
        String str = "生如夏花";
        // base64编码，55Sf5aaC5aSP6Iqx
        String encodeStr = Base64.getEncoder().encodeToString(str.getBytes(UTF8));
        System.out.println("base64编码后的结果：" + encodeStr);

        // 解码
        byte[] decode = Base64.getDecoder().decode(encodeStr.getBytes(UTF8));
        System.out.println("base64解码解码后的结果：" + new String(decode, UTF8));
    }

    /**
     * 使用apache项目下的commons-codec来实现base64编解码操作
     */
    @Test
    public void commonsCodecTest() throws Exception {
        String str = "生如夏花";
        // 编码，55Sf5aaC5aSP6Iqx
        String encodeStr = org.apache.commons.codec.binary.Base64.encodeBase64String(str.getBytes(UTF8));
        System.out.println("base64编码后的结果：" + encodeStr);

        // 解码
        byte[] decode = org.apache.commons.codec.binary.Base64.decodeBase64(encodeStr.getBytes(UTF8));
        System.out.println("base64解码解码后的结果：" + new String(decode, UTF8));
    }

    /**
     * 测试等号补充
     * <p>
     * base64以三个字节为一组，如果最后一组不足3个字节，则使用=号补充
     */
    @Test
    public void test() throws Exception {
        // String str = "哈";
        // UTF8编码中，中文占三个字节,一个汉字时不会有=号填充充的情况
        // GBK编码中，中文占两个字节, uf4=

        // GBK编码中，一个英文占一个字节,一个字母时会
        String str = "a";

        String encodeStr = org.apache.commons.codec.binary.Base64.encodeBase64String(str.getBytes("gbk"));
        System.out.println(encodeStr);
    }

}
