package sm4;

import org.bouncycastle.crypto.engines.SM4Engine;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.util.encoders.Hex;

/**
 * @author : lzw
 * @date : 2022/4/14
 * @since : 1.0
 */
public class SmsTestECB {
    //加解密的字节快大小
    public static final int BLOCK_SIZE = 16;

    /**
     * SM4ECB加密算法
     * @param in            待加密内容
     * @param keyBytes      密钥
     * @return
     */
    public static byte[] encryptByEcb0(byte[] in, byte[] keyBytes) {
        SM4Engine sm4Engine = new SM4Engine();
        sm4Engine.init(true, new KeyParameter(keyBytes));
        int inLen = in.length;
        byte[] out = new byte[inLen];
        int times = inLen / BLOCK_SIZE;

        for (int i = 0; i < times; i++) {
            sm4Engine.processBlock(in, i * BLOCK_SIZE, out, i * BLOCK_SIZE);
        }

        return out;
    }

    /**
     * SM4ECB加密算法
     * @param in            待加密内容
     * @param keyBytes      密钥
     * @return
     */
    public static String encryptByEcb(byte[] in, byte[] keyBytes) {
        byte[] out = encryptByEcb0(in, keyBytes);
        String cipher = Hex.toHexString(out);
        return cipher;
    }

    /**
     * SM4的ECB加密算法
     * @param content   待加密内容
     * @param key       密钥
     * @return
     */
    public static String encryptByEcb(String content, String key) {
        byte[] keyBytes = Hex.decode(key);

        String cipher = encryptByEcb(content.getBytes(), keyBytes);
        return cipher;
    }

    /**
     * SM4的ECB解密算法
     * @param in        密文内容
     * @param keyBytes  密钥
     * @return
     */
    public static byte[] decryptByEcb0(byte[] in, byte[] keyBytes) {
        SM4Engine sm4Engine = new SM4Engine();
        sm4Engine.init(false, new KeyParameter(keyBytes));
        int inLen = in.length;
        byte[] out = new byte[inLen];

        int times = inLen / BLOCK_SIZE;

        for (int i = 0; i < times; i++) {
            sm4Engine.processBlock(in, i * BLOCK_SIZE, out, i * BLOCK_SIZE);
        }

        return out;
    }

    /**
     * SM4的ECB解密算法
     * @param in        密文内容
     * @param keyBytes  密钥
     * @return
     */
    public static String decryptByEcb(byte[] in, byte[] keyBytes) {
        byte[] out = decryptByEcb0(in, keyBytes);
        String plain = Hex.toHexString(out);
        return plain;
    }

    /**
     * SM4的ECB解密算法
     * @param cipher    密文内容
     * @param key       密钥
     * @return
     */
    public static String decryptByEcb(String cipher, String key) {
        byte[] in = Hex.decode(cipher);
        byte[] keyBytes = Hex.decode(key);

        String plain = decryptByEcb(in, keyBytes);
        return plain;
    }

    public static void main(String[] args) {
        //密钥
        String key = "1234567890123456";
        String s = Hex.toHexString(key.getBytes());
        //明文
        String text = "1231564646151511";
        System.out.println("明文" + text);
        String encryptData = encryptByEcb(text, s);
        System.out.println("密文" + encryptData);
        String t = decryptByEcb(encryptData, s);
        String s1 = new String(Hex.decode(t));
        System.out.println("解密后" + s1);
    }
}
