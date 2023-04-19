package com.lzw.tree;

/**
 * 二叉树实现
 * <p>
 * 无序二叉树
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

        // 前序遍历
        /*System.out.println("前序遍历");
        binaryTree.preOder();*/

        // 中序遍历
        /*System.out.println("中序遍历");
        binaryTree.infixOrder();*/

        // 后续遍历
        /*System.out.println("后续遍历");
        binaryTree.postOrder();*/

        // 前序查找 需要遍历四次
        /*System.out.println("前序查找");
        HeroNode res1 = binaryTree.preOrderSearch(5);
        System.out.println(res1);

        // 需要遍历三次
        System.out.println("中序查找");
        HeroNode res2 = binaryTree.infixOrderSearch(5);
        System.out.println(res2);

        // 需要遍历两次
        System.out.println("后序查找");
        HeroNode res3 = binaryTree.postOrderSearch(5);
        System.out.println(res3);*/

        // 删除测试
        System.out.println("删除前数据：");
        binaryTree.preOder();
        binaryTree.delete(5);
        System.out.println("删除后数据");
        binaryTree.preOder();
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

    // 删除节点
    public void delete(int no) {
        // 判断节点是否为空
        if (this == null) {
            System.out.printf("空树，不能进行删除操作");
            return;
        }
        if (this.root.getNo() == no) {
            // 将根节点置空
            this.root = null;
        }
        // 开始递归删除
        this.root.delete(no);
    }

    // 前序遍历
    public void preOder() {
        if (this.root != null) {
            this.root.preOrder();
        } else {
            System.out.println("二叉树为空，无法遍历");
        }
    }

    // 前序查找
    public HeroNode preOrderSearch(int no) {
        if (this.root != null) {
            return this.root.preOrderSearch(no);
        } else {
            return null;
        }
    }

    // 中序遍历
    public void infixOrder() {
        if (this.root != null) {
            this.root.infixOrder();
        } else {
            System.out.println("二叉树为空，无法遍历");
        }
    }

    // 中序查找
    public HeroNode infixOrderSearch(int no) {
        if (this.root != null) {
            return this.root.infixOrderSearch(no);
        } else {
            return null;
        }
    }

    // 后序遍历
    public void postOrder() {
        if (this.root != null) {
            this.root.postOrder();
        } else {
            System.out.println("二叉树为空，无法遍历");
        }
    }

    // 后序查找
    public HeroNode postOrderSearch(int no) {
        if (this.root != null) {
            return this.root.postOrderSearch(no);
        } else {
            return null;
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
     * 删除节点
     */
    public void delete(int no) {

        // 向左删除
        if (this.left != null && this.left.no == no) {
            this.left = null;
            return;
        }

        // 向右删除
        if (this.right != null && this.right.no == no) {
            this.right = null;
            return;
        }

        // 向左递归删除
        if (this.left != null) {
            this.left.delete(no);
        }
        // 向右递归删除
        if (this.right != null) {
            this.right.delete(no);
        }
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
     * 前序查找，先遍历父节点，再遍历左节点，再遍历右节点
     *
     * @param no 要查找的id
     * @return HeroNode
     */
    public HeroNode preOrderSearch(int no) {
        if (this.no == no) {
            return this;
        }
        HeroNode heroNode = null;
        // 如果找不到，向左递归查找
        if (this.left != null) {
            // 如果找到了就直接返回了，没必要再去向右边递归
            heroNode = this.left.preOrderSearch(no);
        }
        // 如果左递归没有找到，方法执行完毕，出栈，没有返回，heroNode会为空，为空了还需要向右递归查找
        if (heroNode != null) {
            return heroNode;
        }
        // 向右递归
        if (this.right != null) {
            heroNode = this.right.preOrderSearch(no);
        }
        // 此时即使heroNode为空也要进行返回了，因为下面已经没有代码执行了
        return heroNode;
    }

    /**
     * 中序查找
     */
    public HeroNode infixOrderSearch(int no) {

        HeroNode heroNode = null;
        // 向左递归查找
        if (this.left != null) {
            heroNode = this.left.infixOrderSearch(no);
        }
        if (heroNode != null) {
            return heroNode;
        }
        // 判断当前节点是否符合条件
        if (this.no == no) {
            return this;
        }

        // 向右边递归查找
        if (this.right != null) {
            heroNode = this.right.infixOrderSearch(no);
        }
        return heroNode;
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
     * 后续遍历查找，先遍历左节点，再遍历右节点，最后遍历父节点
     *
     * @param no 要查找的id
     */
    public HeroNode postOrderSearch(int no) {
        System.out.println("进入后续遍历~~");
        HeroNode heroNode = null;
        // 向左遍历
        if (this.left != null) {
            heroNode = this.left.postOrderSearch(no);
        }
        if (heroNode != null) {
            return heroNode;
        }
        // 向右遍历
        if (this.right != null) {
            heroNode = this.right.postOrderSearch(no);
        }
        if (heroNode != null) {
            return heroNode;
        }
        System.out.println("执行后续遍历~");
        // 如果左右子树都没有找到，比比较当前节点是不是要找的节点
        if (this.no == no) {
            return this;
        }
        return heroNode;
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
