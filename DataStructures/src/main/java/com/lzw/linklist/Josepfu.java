package com.lzw.linklist;

/**
 * 使用单向环形链表解决约瑟夫环问题
 *
 * @author : lzw
 * @date : 2023/3/30
 * @since : 1.0
 */
public class Josepfu {
    public static void main(String[] args) {
        CircleSingleLinkedList circleSingleLinkedList = new CircleSingleLinkedList();
        // 加入5个节点
        circleSingleLinkedList.addBoy(5);
        circleSingleLinkedList.list();

        // 出圈
        circleSingleLinkedList.countBoy(1, 2, 5);

    }
}

// 创建环形单向链表
class CircleSingleLinkedList {

    private Boy first = new Boy(-1);

    /**
     * 添加小孩节点，构建环形链表
     *
     * @param nums 要创建的环形链表有几个节点
     */
    public void addBoy(int nums) {
        // 至少创建1个节点
        if (nums < 1) {
            return;
        }
        Boy curBoy = null;
        for (int i = 1; i <= nums; i++) {
            // 创建新节点
            Boy boy = new Boy(i);

            if (i == 1) { // 如果是第一个节点
                // 将创建的节点指向first
                first = boy;
                // 形成环状
                first.setNext(first);
                // first 节点不能动，使用辅助变量，此时 curBoy就是first
                curBoy = first;
            } else {
                // 头节点的下一个节点指向新建的节点
                curBoy.setNext(boy);
                // 新创建的节点的下一个节点指向头节点，形成环状
                boy.setNext(first);
                // 辅助指针后移
                curBoy = boy;
            }
        }
    }

    /**
     * 几个小孩围在一起坐成一个圈，从指定的位置开始数数，比如数三下，数到三的小孩出圈，然后下一个小孩又开始数数，直到所有小孩都出圈
     * 计算出 出圈顺序
     *
     * @param startNo     从第几个小孩开始数
     * @param countNumber 数几下
     * @param nums        总共有多少小孩
     */
    public void countBoy(int startNo, int countNumber, int nums) {
        if (first == null || startNo < 1 || startNo > nums) {
            System.out.println("参数输入有误");
            return;
        }
        // 创建辅助指针，这个指针在first前面一位，也就是这个环形的最后一位
        Boy helper = first;
        while (true) {
            if (helper.getNext() == first) {
                break;
            }
            helper = helper.getNext();
        }
        // 让first和helper移动startNo - 1次，目的是为了让firs移动到startNo的位置上
        for (int j = 0; j < startNo - 1; j++) {
            helper = helper.getNext();
            first = first.getNext();
        }
        // 小孩报数
        while (true) {
            if (first == helper) {
                // 此时圈中只有一个人
                System.out.printf("最后出圈的节点 %d", first.getNo());
                break;
            }
            // 让first和helper指针同时移动 countNumber - 1次
            for (int j = 0; j < countNumber - 1; j++) {
                first = first.getNext();
                helper = helper.getNext();
            }
            // 上面这个循环完成后，first就是指向即将要出圈的小孩上
            System.out.printf("小孩 %d 出圈\n", first.getNo());


            // 以下两行代码，让要出圈的这个节点从环形中断开
            first = first.getNext(); // 让first的下一个节点指向first，此时first和helper中间隔了一个节点，这个节点就是要出圈的节点
            helper.setNext(first); // 让helper的下一个节点指向变更后的first，要出圈的节点从环形中断开

        }


    }

    /**
     * 遍历环形链表
     */
    public void list() {
        // 遍历环形链表
        if (first == null) {
            System.out.println("环形链表为空");
            return;
        }
        Boy temp = first;
        while (true) {
            System.out.printf("小孩的编号 %d \n", temp.getNo());
            if (first == temp.getNext()) {
                // 已经到最后一个节点
                break;
            }
            temp = temp.getNext(); // temp后移
        }
    }
}

/**
 * 节点类
 */
class Boy {
    // 编号
    public int no;
    // 下一个节点
    private Boy next;

    public Boy(int no) {
        this.no = no;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public Boy getNext() {
        return next;
    }

    public void setNext(Boy next) {
        this.next = next;
    }
}
