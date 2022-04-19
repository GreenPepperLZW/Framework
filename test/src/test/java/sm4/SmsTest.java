package sm4;

import org.bouncycastle.crypto.engines.SM4Engine;
import org.junit.Test;

/**
 * 测试国密sm4对称加密
 * @author : lzw
 * @date : 2022/4/14
 * @since : 1.0
 */
public class SmsTest {


    @Test
    public void testCBC() {
        SM4Engine sm4Engine = new SM4Engine();
        //设置待加密的文本
        String plainText = "hello world";
        SM4Utils sm4 = new SM4Utils();
        //设置 密钥 16长度的字符
        sm4.setSecretKey("b7b3gSMFWd9a67i1");
        //设置 向量 16长度的字符
        sm4.setIv("LlFe66u15Md8Ycg1");
        //声明密钥和向量是否是32长度的十六进制的字符串，如果true则需要设置密钥向量都是十六进制的32长度字符串。Util.byteToHex("b7b3gSMFWd9a67i1".getBytes())
        sm4.setHexString(false);

        //进行加密
        String cipherText = sm4.encryptData_CBC(plainText);
        System.out.println("密文: " + cipherText);
        System.out.println("");

        //进行解密
        plainText = sm4.decryptData_CBC(cipherText);
        System.out.println("明文: " + plainText);
    }





}
