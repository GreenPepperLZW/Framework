package com.lzw.tree;

/**
 * 以数组的方式存储二叉树，并实现二叉树的前序、中序、后序输出
 * 在数组中，父节点和左子节点之间相差的距离是是: 2 * i + 1，i为父节点的下标，2 * i + 1为子节点的下标；
 * 父节点和右子节点的关系是: 2 * i + 2，i为父节点的下标，2 * i + 1为子节点的下标；
 * 子节点和父节点的关系是(i-1)/2，i是子节点的下标，(i-1)/2是父节点的下标。
 *
 * @author : lzw
 * @date : 2023/4/19
 * @since : 1.0
 */
public class ArrayBinaryTreeDemo {
    public static void main(String[] args) {
        int array[] = {1, 2, 3, 4, 5, 6, 7};
        ArrayBinaryTree binaryTree = new ArrayBinaryTree(array);
        System.out.println("===前序遍历===");
        binaryTree.preOrder();
        System.out.println("\n===中序遍历===");
        binaryTree.infixOrder(0);
        System.out.println("\n===后序遍历===");
        binaryTree.postOrder(0);

    }
}

class ArrayBinaryTree {
    private int[] arr;

    public ArrayBinaryTree(int[] arr) {
        this.arr = arr;
    }

    //重载preOrder，在调用时就不用传参了
    public void preOrder() {
        this.preOrder(0);
    }

    /**
     * 前序遍历，根节点，左子节点，右子节点
     *
     * @param index 数组的下标
     */
    private void preOrder(int index) {
        if (arr == null || arr.length == 0) {
            System.out.println("数组为空，不能按照二叉树的前序遍历");
            return;
        }
        // 父节点
        System.out.print(arr[index] + " ");

        // 左节点，向左递归
        if ((index * 2) + 1 < arr.length) {
            preOrder((index * 2) + 1);
        }
        // 右节点，向右递归
        if ((index * 2) + 2 < arr.length) {
            preOrder((index * 2) + 2);
        }
    }

    /**
     * 中序遍历 左 父 右
     *
     * @param index 根节点
     */
    public void infixOrder(int index) {
        // 左节点，向左递归
        if ((index * 2) + 1 < arr.length) {
            infixOrder((index * 2) + 1);
        }

        System.out.print(arr[index] + " ");

        // 右节点，向右递归
        if ((index * 2) + 2 < arr.length) {
            infixOrder((index * 2) + 2);
        }
    }

    /**
     * 后序遍历，左，右，父
     *
     * @param index
     */
    public void postOrder(int index) {
        // 递归左子节点
        if (index * 2 + 1 < arr.length) {
            postOrder(index * 2 + 1);
        }
        // 递归右子节点
        if (index * 2 + 2 < arr.length) {
            postOrder(index * 2 + 2);
        }
        System.out.print(arr[index] + " ");
    }
}


