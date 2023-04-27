package com.lzw.huffmantree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 哈夫曼树创建
 *
 * @author : lzw
 * @date : 2023/4/27
 * @since : 1.0
 */
public class HuffmanTree {
    public static void main(String[] args) {
        int arr[] = {13, 7, 8, 3, 29, 6, 1};
        Node huffmanTree = createHuffmanTree(arr);
        huffmanTree.preOrder();
    }

    // 创建哈夫曼树
    public static Node createHuffmanTree(int[] arr) {
        List<Node> nodes = new ArrayList();
        for (int i = 0; i < arr.length; i++) {
            nodes.add(new Node(arr[i]));
        }


        //取出根节点权值最小的两个节点然后组成一个新的节点
        while (nodes.size() != 1) {
            // 从小到大排序
            Collections.sort(nodes);
            // nodes已经从小到大排序了，下标0、1就是最小的两个值
            Node left = nodes.get(0);
            Node right = nodes.get(1);
            Node rNode = new Node(left.value + right.value);
            rNode.left = left;
            rNode.right = right;
            nodes.remove(left);
            nodes.remove(right);
            nodes.add(rNode);
        }
        return nodes.get(0);
    }
}


/**
 * 节点类
 */
class Node implements Comparable<Node> {
    // 权值
    int value;
    // 指向左子节点
    Node left;
    // 指向右子节点
    Node right;

    public Node(int value) {
        this.value = value;
    }

    // 前序遍历，根，左，右
    public void preOrder() {
        if (this != null) {
            System.out.println(this);
            if (this.left != null) {
                this.left.preOrder();
            }
            if (this.right != null) {
                this.right.preOrder();
            }
        }
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                '}';
    }

    @Override
    public int compareTo(Node o) {
        // 从小到大排序
        return this.value - o.value;
    }
}
