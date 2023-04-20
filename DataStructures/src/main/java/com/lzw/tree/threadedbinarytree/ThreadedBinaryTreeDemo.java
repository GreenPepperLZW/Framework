package com.lzw.tree.threadedbinarytree;

/**
 * 线索化二叉树
 * 由于前、中、后 三种次序遍历，左边的节点总是在右边节点的前面遍历，所有有空间指针的节点，他的左指针指向前驱节点，或者是左字树，右指针指向后继节点，或者是右子树；
 * 这里的前驱后继节点是根据一个顺序二叉树正常 前、中、后序遍历输出后的结果来看的，比如一个二叉树的结构如下：
 * 1
 * 3       6
 * 8  10  14
 * （1的左子节点是3，右子节点是6，3的左子节点是8，右子节点是10，6的左子节点是14，右子节点是null）
 * 按照中序输出的顺序为 8,3,10,1,14,6
 * 那么8的后继是3，前驱是null，10的前驱是3，后继是1
 *
 * <p>
 * 线索化二叉树就是去操作有空闲指针的节点，按照某种次序遍历的的关系来存放前驱和后继指针到这些空闲的指针中去
 * <p>
 * 线索化的过程就是在遍历中修改空指针的过程
 *
 * @author : lzw
 * @date : 2023/4/20
 * @since : 1.0
 */
public class ThreadedBinaryTreeDemo {
    public static void main(String[] args) {

        HeroNode root = new HeroNode(1, "tom");
        HeroNode node1 = new HeroNode(3, "jack");
        HeroNode node2 = new HeroNode(6, "smith");
        HeroNode node3 = new HeroNode(8, "mary");
        HeroNode node4 = new HeroNode(10, "king");
        HeroNode node5 = new HeroNode(14, "dim");

        //手动构建二叉树
        root.setLeft(node1);
        root.setRight(node2);

        node1.setLeft(node3);
        node1.setRight(node4);

        node2.setLeft(node5);

        BinaryTree tree = new BinaryTree();
        tree.setRoot(root);
        // 线索化
        tree.threadedNodes();

        // 测试 以10号节点测试,它的前驱节点应该是3号
        HeroNode left = node4.getLeft();
        HeroNode right = node4.getRight();
        System.out.println("10号节点的前驱节点：" + left);
        System.out.println("10号节点的后继节点：" + right);
    }
}


/**
 * 线索化二叉树
 */
class BinaryTree {
    private HeroNode root;

    // 为了实现线索化，需要创建一个指向当前节点的前驱节点一个指针
    // 在递归进行线索化时，pre总是保留当前节点的前一个节点
    private HeroNode pre = null;

    public void setRoot(HeroNode root) {
        this.root = root;
    }

    public void threadedNodes() {
        this.threadedNodes(root);
    }

    /**
     * 中序线索化
     *
     * @param node 需要线索化的节点
     */
    public void threadedNodes(HeroNode node) {
        // 如果这个节点的左子节点或者右子节点不为空，则会继续向下递归，如果为空了则开始设置的左右空闲指针的前驱后继指针
        if (node == null) {
            return;
        }
        // 按照中序线索化的逻辑处理节点
        // 1：先线索化左子树
        threadedNodes(node.getLeft());

        // 2.处理父节点，即线索化当前节点
        // 此时这个节点的左子节点为空,则他的前驱节点为pre
        if (node.getLeft() == null) {
            // 递归中第一次进入到这里的时候，这个节点是二叉树中最后一层最左边的一个节点，此时pre是空的
            node.setLeftType(1);
            node.setLeft(pre);
        }

        // 处理右继节点
        if (pre != null && pre.getRight() == null) {
            // 让前驱节点的右指针指向当前节点
            pre.setRight(node);
            pre.setRightType(1);
        }
        // pre是个全局变量，下一次递归进入时，pre中存储的对象其实就是当前节点的前驱节点
        pre = node;

        // 3.线索化右子树
        threadedNodes(node.getRight());

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

    /**
     * 前、中、后 三种次序遍历，左边的节点总是在右边节点的前面遍历，所有有空间指针的节点，他的左指针指向前驱节点，或者是左字树，右指针指向后继节点，或者是右子树
     */
    // 如果leftType == 0 表示指向的是左子树，如果是 1 则表示指向前驱节点
    private int leftType;
    // 如果rightType == 0 表示指向的是右子树，如果是 1 则表示指向后继节点
    private int rightType;

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

    public int getLeftType() {
        return leftType;
    }

    public void setLeftType(int leftType) {
        this.leftType = leftType;
    }

    public int getRightType() {
        return rightType;
    }

    public void setRightType(int rightType) {
        this.rightType = rightType;
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

