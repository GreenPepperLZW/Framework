package com.lzw.tree;

/**
 * 二叉树实现
 *
 * @author : lzw
 * @date : 2023/4/18
 * @since : 1.0
 */
public class BinaryTreeDemo {
    public static void main(String[] args) {
        // 创建二叉树
        BinaryTree binaryTree = new BinaryTree();

        // 创建节点
        HeroNode root = new HeroNode(1, "宋江");
        HeroNode node2 = new HeroNode(2, "吴用");
        HeroNode node3 = new HeroNode(3, "卢俊义");
        HeroNode node4 = new HeroNode(4, "林冲");
        HeroNode node5 = new HeroNode(5, "关胜");

        // 手动创建该二叉树
        root.setLeft(node2);
        root.setRight(node3);
        node3.setRight(node4);
        node3.setLeft(node5);
        binaryTree.setRoot(root);
        
        System.out.println("前序遍历");
        // 前序遍历
        binaryTree.preOder();

        System.out.println("中序遍历");
        // 中序遍历
        binaryTree.infixOrder();

        System.out.println("后续遍历");
        // 后续遍历
        binaryTree.postOrder();
    }
}


/**
 * 二叉树
 */
class BinaryTree {
    private HeroNode root;

    public void setRoot(HeroNode root) {
        this.root = root;
    }

    // 前序遍历
    public void preOder() {
        if (this.root != null) {
            this.root.preOrder();
        } else {
            System.out.println("二叉树为空，无法遍历");
        }
    }

    public void infixOrder() {
        if (this.root != null) {
            this.root.infixOrder();
        } else {
            System.out.println("二叉树为空，无法遍历");
        }
    }

    public void postOrder() {
        if (this.root != null) {
            this.root.postOrder();
        } else {
            System.out.println("二叉树为空，无法遍历");
        }
    }
}

/**
 * 节点类
 */
class HeroNode {
    private int no;

    private String name;

    // 指向左节点，默认null
    private HeroNode left;

    // 指向右节点，默认null
    private HeroNode right;

    public HeroNode(int no, String name) {
        this.no = no;
        this.name = name;
    }

    /**
     * 前序遍历，先遍历父节点，再遍历左节点，再遍历右节点
     *
     * @return
     */
    public void preOrder() {
        System.out.println(this);
        // 向左遍历
        if (this.left != null) {
            this.left.preOrder();
        }
        // 向右边遍历
        if (this.right != null) {
            this.right.preOrder();
        }
    }

    /**
     * 中序遍历，先遍历左节点，再遍历父节点，再遍历右节点
     */
    public void infixOrder() {
        // 向左节点遍历
        if (this.left != null) {
            this.left.infixOrder();
        }
        // 父节点下左边的的叶子节点为空时，会走这一行代码，然后一直出栈，回到父节点调用的栈，最后把父节点输出
        // 输出父节点
        System.out.println(this);

        // 向右节点遍历
        if (this.right != null) {
            this.right.infixOrder();
        }
    }

    /**
     * 后序遍历，先遍历左节点，再遍历右节点，最后遍历父节点
     */
    public void postOrder() {
        if (this.left != null) {
            this.left.postOrder();
        }

        if (this.right != null) {
            this.right.postOrder();
        }
        System.out.println(this);
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HeroNode getLeft() {
        return left;
    }

    public void setLeft(HeroNode left) {
        this.left = left;
    }

    public HeroNode getRight() {
        return right;
    }

    public void setRight(HeroNode right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return "HeroNode{" +
                "no=" + no +
                ", name='" + name + '\'' +
                '}';
    }
}
