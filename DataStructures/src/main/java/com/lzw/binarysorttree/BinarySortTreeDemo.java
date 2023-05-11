package com.lzw.binarysorttree;

/**
 * 二叉排序树的性质：
 * 1.若它的左子树不空, 并且左子树上所有结点的值均小于它的根结点的值。
 * 2.若它的右子树不空, 并且右子树上所有结点的值均大于它的根结点的值。
 * 3.它的左、右子树也分另为二叉排序树。
 * <p>
 * 二叉排序树的创建和遍历
 *
 * @author : lzw
 * @date : 2023/5/8
 * @since : 1.0
 */
public class BinarySortTreeDemo {

    public static void main(String[] args) {
        int[] arr = {7, 3, 10, 12, 5, 1, 9, 6, 8, 11, 17, 4};
//        int[] arr = {7, 3, 10, 12, 5, 1, 9, 2};
        BinarySortTree binarySortTree = new BinarySortTree();

        // 添加节点到二叉排序树中
        for (int i = 0; i < arr.length; i++) {
            binarySortTree.add(new Node(arr[i]));
        }

        // 中序遍历
        binarySortTree.infixOrder();

        // 删除
        Node node = new Node(5);
//        binarySortTree.delete(node);
        binarySortTree.delete(node);
        System.out.println("删除后遍历");
        // 中序遍历
        binarySortTree.infixOrder();
    }
}


/**
 * 二叉排序树
 */
class BinarySortTree {
    // 根节点
    private Node root;

    /**
     * 添加节点
     *
     * @param node
     */
    public void add(Node node) {
        if (root == null) {
            root = node;
        } else {
            root.add(node);
        }
    }

    // 删除节点
    public void delete(Node node) {
        if (node != null) {
            if (root.value == node.value) {
                root = null;
            } else {
                root.delete(node);
            }
        }
    }

    // 查找要删除的节点
    public Node search(int value) {
        if (root == null) {
            return null;
        } else {
            return root.search(value);
        }
    }

    // 查找父节点
    public Node searchParent(int value) {
        if (root == null) {
            return null;
        } else {
            return root.searchParent(value);
        }
    }

    public void delete2(int value) {
        if (root == null) {
            return;
        } else {
            Node targetNode = search(value);
            if (targetNode == null) {
                // 说明没有要删除的节点
                return;
            }
            // 如果要删除的节点没有父节点，说明要删除的节点是根节点或者这个二叉排序树只有一个节点
            if (root.value == value) {
                root = null;
            }
            Node parentNode = searchParent(value);

            // 如果要删除的节点是叶子节点
            if (targetNode.left == null && targetNode.right == null) {
                // 判断 targetNode是父节点的左子节点还是右子节点
                if (parentNode.left != null && parentNode.left.value == value) {
                    parentNode.left = null;
                } else if (parentNode.right != null && parentNode.right.value == value) {
                    parentNode.right = null;
                }
            }// 如果要删除的节点有两个子节点
            else if (targetNode.left != null && targetNode.right != null) {
                int minValue = delRightTreeiMin(targetNode.right);
                // 不移动节点，将值替换即可
                targetNode.value = minValue;
            }// 如果要删除的节点有一个子节点
            else {
                // 如果target的左子节点不为空
                if (targetNode.left != null) {
                    if (parentNode != null) {
                        // 如果target是parent的左子节点
                        if (parentNode.left.value == value) {
                            parentNode.left = targetNode.left;
                        } else {
                            // target是parent的右子节点
                            parentNode.right = targetNode.left;
                        }
                    } else {
                        root = targetNode.left;
                    }

                } else {// target的右子节点不为空
                    if (parentNode != null) {
                        // 如果target是parent的左子节点
                        if (parentNode.left.value == value) {
                            parentNode.left = targetNode.right;
                        } else {
                            // target是parent的右子节点
                            parentNode.right = targetNode.right;
                        }
                    } else {
                        root = targetNode.right;
                    }

                }
            }
        }
    }

    /**
     * 该方法作用和 findSmailNode()方法是一样的，写法不一样
     *
     * @param node 需要移动的节点
     * @return 返回 node 下的最小节点
     */
    public int delRightTreeiMin(Node node) {
        Node tatget = node;
        while (tatget.left != null) {
            tatget = tatget.left;
        }
        // 删除这个最小节点
        delete2(tatget.value);
        // 返回最小值
        return tatget.value;
    }

    /**
     * 中序遍历
     */
    public void infixOrder() {
        if (root != null) {
            root.infixOrder();
            System.out.println();
        } else {
            System.out.println("二叉排序树为空，不能遍历");
        }
    }
}

/**
 * 节点
 */
class Node {

    // 值
    int value;

    // 左子节点
    Node left;

    // 右子节点
    Node right;

    public Node() {
    }

    public Node(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value + " ";
    }

    /**
     * 递归添加节点
     *
     * @param node 需要添加的节点
     */
    public void add(Node node) {
        if (node == null) {
            return;
        }
        // 判断要添加的值是否小于当前节点
        if (node.value <= this.value) {
            // 判断当前节点的左子节点是否为空
            if (this.left != null) {
                // 递归向左子节点添加
                this.left.add(node);
            } else {
                this.left = node;
            }
        } else {// 要添加的值大于当前节点
            if (this.right != null) {
                this.right.add(node);
            } else {
                this.right = node;
            }
        }
    }

    // 删除节点
    public void delete2() {
        if (this == null) {

        }
    }

    //*******************删除节点****************************

    /**
     * @param value 希望删除的节点
     * @return 如果找到则返回该节点，否则返回null
     */
    public Node search(int value) {
        if (value == this.value) {
            return this;
        } else if (value < this.value) {
            // 如果左子节点为空
            if (this.left == null) {
                return null;
            }
            return this.left.search(value);
        } else {
            // 要查找的值大于当前节点，向右子树递归查询
            if (this.right == null) {
                return null;
            }
            return this.right.search(value);
        }
    }

    /**
     * 查找要删除节点的父节点
     *
     * @param value 要删除的节点
     * @return 返回要删除节点的父节点
     */
    public Node searchParent(int value) {
        if ((this.right != null && this.right.value == value) || (this.left != null && this.left.value == value)) {
            return this;
        } else {
            // 如果查找的值小于当前节点的值，并且当前节点的左子节点不为空
            if (this.value > value && this.left != null) {
                return this.left.searchParent(value);
            } else if (this.value <= value && this.right != null) { // <= 防止有相同的值
                return this.right.searchParent(value);
            } else {
                // 上面两种条件都不满足，没有找到父节点,返回NUll
                return null;
            }
        }
    }

    //*******************删除节点****************************


    /**
     * 问题：该方法无法直接删除根节点
     *
     * @param target 要删除的节点
     */
    public void delete(Node target) {
        // 首先找到这个要删除的节点在哪个位置,向左右进行递归找到删除目标
        if (this.right != null && target.value >= this.value) {
            this.right.delete(target);
        } else if (this.left != null && target.value <= this.value) {
            this.left.delete(target);
        }

        boolean left = false;
        boolean right = false;
        // target是右子节点
        if (this.right != null && this.right.value == target.value) {
            // target节点下面是否还有节点
            // target的右子节点不为空
            if (this.right.right != null) {
                right = true;
            }
            // target的左子节点不为空
            if (this.right.left != null) {
                left = true;
            }
            // 开始判断四种情况
            if (!right && !left) {
                // target下没有节点，直接将这个节点置为空即可
                this.right = null;
            } else if (left && right) {
                // target的左右节点都不为空
                // 找到右子节点下最小的值，然后替换target的值，这个值同样会大于target左子节点下所有的值，满足二叉排序树
                Node smailNode = findSmailNode(this.right);
                // 将target的左子节点移动到smailNode的左边
//                smailNode.left = this.right.left;
                // 将target的右子节点移动到smailNode的右边
//                smailNode.right = this.right.right;
                // 将值替换掉即可，不需要移动节点
                this.right.value = smailNode.value;
                left = false;
                right = false;
            } else if (left) {
                // target的左子节点不为空
                this.right = this.right.left;
            } else if (right) {
                // target的右子节点不为空
                this.right = this.right.right;
            }
        }
        // target是右子节点
        if (this.left != null && this.left.value == target.value) {
            // target节点下面是否还有节点
            // target的右字节点不为空
            if (this.left.right != null) {
                right = true;
            }
            // target的左子节点不为空
            if (this.left.left != null) {
                left = true;
            }

            if (!right && !left) {
                // target下没有节点
                this.left = null;
            } else if (left && right) {
                // target的左右节点都不为空
                // 找到右子节点下最小的值，然后替换target的值
                Node smailNode = findSmailNode(this.right);
                // 将target的左子节点移动到smailNode的左边
//                smailNode.left = this.left.left;
                // 将target的右子节点移动到smailNode的右边
//                smailNode.right = this.left.right;
                this.left.value = smailNode.value;
                left = false;
                right = false;
                // 如果有两个一样的节点，需要继续向下走,这里不能return
            } else if (left) {
                // target的左子节点不为空
                this.left = this.left.left;
            } else if (right) {
                this.left = this.left.right;
            }
        }

    }

    /**
     * 找到最小节点，删除然后返回
     *
     * @param node 需要移动的节点
     * @return 返回 node下的最小节点
     */
    public Node findSmailNode(Node node) {
        if (node.left.left == null) {
            Node left = node.left;
            // 删除最小节点，这里不能简单的直接node.left = null，因为node.right可能不为空
            delete(node.left);
            //node.left = null;
            return left;
        }
        return findSmailNode(node.left);
    }

    /**
     * 中序遍历
     */
    public void infixOrder() {
        if (this.left != null) {
            this.left.infixOrder();
        }
        System.out.print(this);
        if (this.right != null) {
            this.right.infixOrder();
        }
    }
}
