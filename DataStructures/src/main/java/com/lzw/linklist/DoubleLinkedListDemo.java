package com.lzw.linklist;

/**
 * 双向链表
 *
 * @author : lzw
 * @date : 2023/3/30
 * @since : 1.0
 */
public class DoubleLinkedListDemo {

    public static void main(String[] args) {
        System.out.println("双向链表的测试==========");
        DoubleLinkedList linkedList = new DoubleLinkedList();
        Node node3 = new Node(3, "吴用", "智多星");
        Node node1 = new Node(1, "宋江", "及时雨");
        Node node2 = new Node(2, "卢俊义", "玉麒麟");
        Node node4 = new Node(4, "林冲", "豹子头");
        System.out.println("============添加============");
        // 添加
        linkedList.add(node1);
        linkedList.add(node2);
        linkedList.add(node3);
        linkedList.add(node4);
        // 遍历
        linkedList.list();
        System.out.println("============修改============");
        // 修改
        Node newNode2 = new Node(2, "小卢", "金麒麟");
        linkedList.update(newNode2);
        linkedList.list();
        System.out.println("============删除============");
        // 删除
        linkedList.delete(4);
        linkedList.list();
    }
}

class DoubleLinkedList {

    // 初始化头节点
    private final Node head = new Node(0, "", "");

    // 返回头节点
    public Node getHead() {
        return this.head;
    }

    // 遍历双向链表
    public void list() {
        // 判断链表是否为空
        if (head.next == null) {
            System.out.println("链表为空");
            return;
        }
        Node temp = head.next;
        while (true) {
            if (temp == null) {
                break;
            }
            // 输出
            System.out.println(temp);
            // temp后移
            temp = temp.next;
        }
    }

    /**
     * 在链表的末尾添加
     *
     * @param node
     */
    public void add(Node node) {
        // 找到最后的位置
        Node temp = head;
        while (true) {
            if (temp.next == null) {
                // 最后一个节点已经找到
                break;
            }
            temp = temp.next;
        }
        // 形成一个双向链表
        temp.next = node;
        node.pre = temp;

    }

    /**
     * 修改
     *
     * @param node
     */
    public void update(Node node) {

        Node temp = head;
        boolean flag = false;
        while (true) {
            if (temp == null) {
                // 已遍历完所有节点
                break;
            }
            // 找到要修改的节点
            if (temp.no == node.no) {
                flag = true;
                break;
            }
            temp = temp.next;
        }
        if (flag) {
            temp.name = node.name;
            temp.nickName = node.nickName;
        } else {
            System.out.printf("没有找到编号为 d% 的节点,无法修改 \n", node.no);
        }
    }

    /**
     * 删除
     *
     * @param no
     */
    public void delete(int no) {
        // 找到要删除的节点，不需要找到要删除节点的上一个节点
        // 直接从头节点的下一个节点开始找
        Node temp = head.next;
        if (temp == null) {
            System.out.println("链表为空，无法删除");
            return;
        }
        boolean flag = false;
        while (true) {
            if (temp == null) { // 已经找到最后一个节点的next了
                break;
            }
            if (temp.no == no) {
                flag = true;
                break;
            }
            temp = temp.next;
        }
        if (flag) {
            // 删除的本质就是将要删除的的节点从链表中断开
            temp.pre.next = temp.next;
            // 如果temp是最后一个节点，则它的next为空
            if (temp.next != null) {
                temp.next.pre = temp.pre;
            }
        } else {
            System.out.printf("没有找到编号为 d% 的节点,无法删除 \n", no);
        }
    }
}

class Node {

    // 编号
    public int no;
    public String name;
    public String nickName;
    // 下一个节点
    public Node next;
    // 上一个节点
    public Node pre;

    public Node(int no, String name, String nickName) {
        this.no = no;
        this.name = name;
        this.nickName = nickName;
    }

    @Override
    public String toString() {
        return "Node{" +
                "no=" + no +
                ", name='" + name + '\'' +
                ", nickName='" + nickName +
                '}';
    }
}
