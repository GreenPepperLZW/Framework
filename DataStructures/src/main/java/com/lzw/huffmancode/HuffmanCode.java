package com.lzw.huffmancode;

import java.io.*;
import java.util.*;

/**
 * 哈夫曼编码实现，并进行数据压缩和解压
 *
 * @author : lzw
 * @date : 2023/4/27
 * @since : 1.0
 */
public class HuffmanCode {


    // 存放哈夫曼编码的集合
    static Map<Byte, String> huffmanCodes = new HashMap<>();
    // 拼接叶子节点的路径
    static StringBuilder stringBuilder = new StringBuilder();

    public static void main(String[] args) {
        String str = "i like like like java do you like a java bc";
//        String str = "i like java k";
        byte[] bytes = str.getBytes();
        //System.out.println("压缩前长度：" + bytes.length);

        /*List<Node> nodes = getNodes(bytes);
        // System.out.printf(nodes.toString());
        Node huffmanTree = createHuffmanTree(nodes);
        // 前序遍历
        preOrder(huffmanTree);

        // 生成赫夫曼编码表
        Map<Byte, String> huffmanCode = getCodes(huffmanTree);
        // 输出
        //System.out.println("生成的哈夫曼编码表：" + huffmanCode);

        zip(bytes, huffmanCode);*/
        // 将以上方法封装为这一个方法
        byte[] huffmanBytes = huffmanZip(bytes);
        System.out.println(Arrays.toString(huffmanBytes));

        byte[] bytes1 = deCode(huffmanCodes, huffmanBytes);
        System.out.println("解码后的" + new String(bytes1));

        // 测试压缩文件
        zipFile("D:\\temp\\what you name.jpg", "D:\\temp\\myName.zip");

        // 测试解压文件
        unZipFile("D:\\temp\\myName.zip", "D:\\temp\\myName.jpg");
    }

    /**
     * 将一个字符串构造成一个以其中每个单个字符和这个字符出现的次数组成的对象集合
     *
     * @param bytes 字符串的字节数组
     * @return List<Node> 对象集合
     */
    private static List<Node> getNodes(byte[] bytes) {
        List<Node> nodes = new ArrayList<>();
        //存储每个字节出现的次数
        Map<Byte, Integer> counts = new Hashtable<>();
        for (byte b : bytes) {
            // 统计一个字符出现的次数
            counts.merge(b, 1, Integer::sum);
            /*if (counts.get(b) != null) {
                counts.put(b, counts.get(b) + 1);
            } else {
                counts.put(b, 1);
            }*/
        }
        // 将各个节点组成一个集合
        for (Map.Entry<Byte, Integer> node : counts.entrySet()) {
            nodes.add(new Node(node.getKey(), node.getValue()));
        }
        return nodes;
    }

    // 创建哈夫曼树
    public static Node createHuffmanTree(List<Node> nodes) {
        while (nodes.size() > 1) {
            // 排序
            Collections.sort(nodes);
            Node left = nodes.get(0);
            Node right = nodes.get(1);
            Node node = new Node(null, left.weight + right.weight);
            node.left = left;
            node.right = right;
            nodes.remove(left);
            nodes.remove(right);
            nodes.add(node);
        }
        return nodes.get(0);
    }

    // 前序遍历
    public static void preOrder(Node root) {
        if (root != null) {
            root.preOrder();
        } else {
            System.out.println("空");
        }
    }

    // 重载
    public static Map<Byte, String> getCodes(Node node) {
        if (node == null) {
            return null;
        }
        getCodes(node.left, "0", stringBuilder);
        getCodes(node.right, "1", stringBuilder);
        return huffmanCodes;
    }

    /**
     * 生成赫夫曼编码
     * 将传入的node节点的所有叶子节点的哈夫曼编码得到，并分别存入到huffmanCodes集合中
     *
     * @param node          节点
     * @param code          路径，节点与节点之间的值，node到左子节点的路径为0，到右子节点的路径为1
     * @param stringBuilder 用于拼接路径
     */
    public static void getCodes(Node node, String code, StringBuilder stringBuilder) {
        StringBuilder stringBuilder2 = new StringBuilder(stringBuilder);
        stringBuilder2.append(code);
        if (node != null) {
            // 判断当前node是否是叶子节点
            if (node.data == null) {
                // 非叶子节点，需要向左右递归
                getCodes(node.left, "0", stringBuilder2);
                getCodes(node.right, "1", stringBuilder2);
            } else {
                // 叶子节点，此次递归结束，将路径加入到huffmanCodes集合中
                huffmanCodes.put(node.data, stringBuilder2.toString());
            }
        }
    }

    /**
     * @param bytes        原始的字符串对应的byte数组
     * @param huffmanCodes 使用原始字符串生成的哈夫曼树继而生成的赫夫曼编码表
     * @return 返回赫夫曼编码处理后的byte数组
     */
    private static byte[] zip(byte[] bytes, Map<Byte, String> huffmanCodes) {

        // 1.利用赫夫曼编码表将原始byte转成对应的字符串，比如说字母 i 变为10，a变为010
        // 1000111101001111000011110100111100001111010011110000100101010011101011100011011011100111011110100111101001111000011010100101010011101
        StringBuilder strBuilder = new StringBuilder();
        for (byte b : bytes) {
            strBuilder.append(huffmanCodes.get(b));
        }
        // System.out.println("压缩后的赫夫曼二进制编码：" + strBuilder);
        // 2.将strBuilder转为byte数组，一个字节八位，首先确定这个数组的长度
        int len = (strBuilder.length() + 7) / 8;
        // 创建 存储压缩后的byte数组，这里长度+1是为了存最后一位数据的真实长度
        byte[] by = new byte[len + 1];
        int index = 0;
        byte endStrLength = 0;
        // 开始将数据放入压缩后的字节数组
        for (int i = 0; i < strBuilder.length(); i += 8) {
            String strByte;
            if (i + 8 > strBuilder.length()) {
                strByte = strBuilder.substring(i);
                // 记录最后一个数据的长度
                endStrLength = (byte) strByte.length();
            } else {
                strByte = strBuilder.substring(i, i + 8);
            }
            // 将strByte转成一个byte，放入到 by中，八位表示一个字节
            by[index] = (byte) Integer.parseInt(strByte, 2);
            index++;
        }
        // 存储最后一个字符的长度
        by[by.length - 1] = endStrLength;
        return by;
    }

    /**
     * 将以上方法进行封装
     *
     * @param bytes 原始的字符串对应的byte数组
     * @return 返回赫夫曼编码处理后(压缩后)的byte数组
     */
    private static byte[] huffmanZip(byte[] bytes) {
        List<Node> nodes = getNodes(bytes);
        // 创建赫夫曼树
        Node huffmanTree = createHuffmanTree(nodes);
        // 根据赫夫曼树生成赫夫曼编码表
        Map<Byte, String> huffmanCode = getCodes(huffmanTree);
        // 根据赫夫曼编码表对数据进行压缩
        return zip(bytes, huffmanCode);
    }

    /**
     * 完成数据的解压
     * 1.得到压缩后的字节数组数据
     * 2.得到原始数据生成的赫夫曼编码表
     * 3.将压缩后的字节数据转为二进制字符串
     * 4.根据赫夫曼编码将二进制字符串转为原始字符串
     *
     * @param huffmanCodes 哈夫曼编码表
     * @param huffmanBytes 经过哈夫曼编码后的原始数据的字节数组
     * @return 原始数据对应的字节数组
     */
    private static byte[] deCode(Map<Byte, String> huffmanCodes, byte[] huffmanBytes) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < huffmanBytes.length - 1; i++) {
            byte b = huffmanBytes[i];
            boolean flag = (i == huffmanBytes.length - 2); // 倒数第二位时已经是最后一个字符，最后一位存储的是倒数第二位的真实长度
            stringBuilder.append(byteToBitString(!flag, b, huffmanBytes[i + 1]));

        }
        // System.out.println("解压压缩后的赫夫曼二进制编码：" + stringBuilder);

        // 解码，将字符串按照指定的赫夫曼编码表进行解码
        // 首先将赫夫曼编码表反转
        Map<String, Byte> map = new HashMap<>();
        for (Map.Entry<Byte, String> entry : huffmanCodes.entrySet()) {
            map.put(entry.getValue(), entry.getKey());
        }
        List<Byte> list = new ArrayList<>();
        for (int i = 0; i < stringBuilder.length(); i++) {
            int count = 1;
            Byte b = null;
            boolean flag = true;
            // 从i的位置开始往后不断尝试去获取是否能取出字符，若能取出，则将i的位置直接移动到count的位置，然后循环取下一个字符
            while (flag) {
                String key = stringBuilder.substring(i, i + count);
                b = map.get(key);
                if (b == null) {
                    count++;
                } else {
                    flag = false;
                }
            }
            list.add(b);
            i += count - 1;
        }
        // System.out.println(list);
        byte[] b = new byte[list.size()];
        for (int i = 0; i < b.length; i++) {
            b[i] = list.get(i);
        }

        return b;
    }

    /**
     * 字节转成二进制编码
     *
     * @param b    字节
     * @param flag 标志是否需要补高位，如果为真表示需要补高位,如果是最后一个字节
     * @return 返回 b 对应的二进制的字符串（补码）
     */
    private static String byteToBitString(boolean flag, byte b, byte length) {
        int temp = b;// 将字节转为 int
        // 如果是正数，需要补高位，因为正数的原码就是补码，比如十进制的2对应的二进制是0000 0000 0000 0010，对应的补码也是0000 0000 0000 0010
        // 前面的0在计算时都是无效的，最终看到的其实是 10,不足八位，所以需要补高位
        temp |= 256;// 256对应的二进制是 1 0000 0000，通过按位或计算，相当于是往高位补0，并且得出来的结果必定是9位，通过截取后八位的操作之后，不会影响最终的值
        /*if (flag) {
            temp |= 256;
        }*/
        // 将int转为二进制，相当于是把字节转二进制
        String str = Integer.toBinaryString(temp);// 返回的是temp的补码
        if (flag) {
            // int类型占4个字节，也就是32位，但只要取后八位就可以了，因为 b 实际上就是一个字节，前面24位都是自动补的0
            return str.substring(str.length() - 8);
        } else {
            // 解决最后一个数据以0作为开头导致数据丢失的情况
            return str.substring(str.length() - (int) length);
        }
    }

    /**
     * 将文件进行压缩
     *
     * @param srcFile 需要压缩的文件全路径
     * @param dstFile 压缩后的存放路径
     */
    public static void zipFile(String srcFile, String dstFile) {
        FileInputStream inputStream = null;
        OutputStream outputStream = null;
        ObjectOutputStream oos = null;
        try {
            inputStream = new FileInputStream(srcFile);
            // 创建一个和文件一样大小的字节数组
            byte[] bytes = new byte[inputStream.available()];
            // 读取文件
            inputStream.read(bytes);
            // 对文件进行压缩
            byte[] huffmanBytes = huffmanZip(bytes);
            // 创建输出流，存放压缩文件
            outputStream = new FileOutputStream(dstFile);
//            outputStream.write(huffmanBytes);
            oos = new ObjectOutputStream(outputStream);

            // 把赫夫曼编码后的字节数组写入压缩文件
            oos.writeObject(huffmanBytes);
            // 将赫夫曼编码表以对象流的方式写入，之后恢复文件使用
            oos.writeObject(huffmanCodes);


        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            try {
                inputStream.close();
                outputStream.close();
                oos.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * @param zipFile 准备解压的文件
     * @param dstFile 解压到哪个路径
     */
    public static void unZipFile(String zipFile, String dstFile) {
        // 文件输入流
        InputStream is = null;
        // 文件输出流
        OutputStream os = null;
        // 对象输入流
        ObjectInputStream ois = null;
        try {
            is = new FileInputStream(zipFile);

            // 创建一个对象输入流
            ois = new ObjectInputStream(is);
            // 分别读取哈夫曼字节数组和哈夫曼编码表
            byte[] huffmanBytes = (byte[]) ois.readObject();
            // 读取哈夫曼编码表
            Map<Byte, String> codes = (Map<Byte, String>) ois.readObject();
            // 解码
            byte[] bytes = deCode(codes, huffmanBytes);

            // 将解码后的数组写入到 输出流
            os = new FileOutputStream(dstFile);
            // 写输出到文件夹
            os.write(bytes);

        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            try {
                os.close();
                ois.close();
                is.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

}

/**
 * 节点
 */
class Node implements Comparable<Node> {
    // 存放数据本身，比如 'a' => 97(97为 'a' 对应的ASCII编码)
    Byte data;

    // 权，表示字符出现的次数
    int weight;

    Node left;

    Node right;

    public Node(Byte data, int weight) {
        this.data = data;
        this.weight = weight;
    }

    // 前序遍历
    public void preOrder() {
        System.out.println(this);
        if (this.left != null) {
            this.left.preOrder();
        }
        if (this.right != null) {
            this.right.preOrder();
        }
    }

    @Override
    public int compareTo(Node o) {
        // 从小到大排序
        return this.weight - o.weight;
    }

    @Override
    public String toString() {
        return "Node{" +
                "data=" + data +
                ", weight=" + weight +
                '}';
    }
}
