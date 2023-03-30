package com.lzw.linklist;

import java.util.Stack;

/**
 * 单向链表实现
 * 实现了一下功能：
 * 1.获取到单向链表的有效个数
 * 2.将节点插入链表
 * 3.按照节点中的序号有序的插入到链表
 * 4.更新节点
 * 5.删除节点
 * 6.遍历节点
 * 7.找到倒数第k个节点
 * 7.单向链表反转
 *
 * @author : lzw
 * @date : 2023/3/27
 * @since : 1.0
 */
public class SingleLinkListDemo {
    public static void main(String[] args) {

        HeroNode node3 = new HeroNode(3, "吴用", "智多星");
        HeroNode node1 = new HeroNode(1, "宋江", "及时雨");
        HeroNode node2 = new HeroNode(2, "卢俊义", "玉麒麟");
        HeroNode node4 = new HeroNode(4, "林冲", "豹子头");
        SingleLingList singleLingList = new SingleLingList();

        /*singleLingList.add(node1);
        singleLingList.add(node2);
        singleLingList.add(node3);
        singleLingList.add(node4);*/

        // 按照编号的顺序加入
        singleLingList.addByOrder(node1);
        singleLingList.addByOrder(node2);
        singleLingList.addByOrder(node3);
        singleLingList.addByOrder(node4);

        // 修改节点
        HeroNode updateNode = new HeroNode(2, "玉麒麟", "卢俊义");
        singleLingList.update(updateNode);


        // 删除节点
        singleLingList.delete(3);
        singleLingList.list();

        // 统计有效节点个数
        int length = getLength(singleLingList.getHead());
        System.out.printf("效节点个数为：%d \n", length);

        // 获取倒数第k个数据
        int k = 2;
        HeroNode lastIndexNode = findLastIndexNode(singleLingList.getHead(), k);
        System.out.printf("倒数第" + k + " 个的节点是 " + lastIndexNode + "\n");

        System.out.println("==================链表反转=====================");
        // 链表反转
        reverseList(singleLingList.getHead());
        singleLingList.list();

        /*System.out.println("==================递归实现链表反转=====================");
        HeroNode node = recReverseList(singleLingList.getHead());
        System.out.println(node.toString());*/
        System.out.println("==================逆序打印=====================");
        reversePrint(singleLingList.getHead());

        System.out.println("==================合并两个链表，并有序=====================");
        merageList();
    }

    /**
     * 获取到单向链表的个数(如果是带头头节点的链表，需要不统计头节点)
     *
     * @param head 链表的头节点
     * @return 有效节点个数
     */
    public static int getLength(HeroNode head) {
        if (head.next == null) {
            return 0;
        }
        int length = 0;
        HeroNode cur = head.next; // 从头节点的下一个节点开始统计，没有统计头节点
        while (cur != null) {
            length++;
            cur = cur.next;
        }
        return length;
    }

    /**
     * 找到倒数第k个节点
     *
     * @param head
     * @param index
     * @return
     */
    public static HeroNode findLastIndexNode(HeroNode head, int index) {
        if (head.next == null) {
            return null;
        }
        // 得到链表的总长度
        int size = getLength(head);
        if (index <= 0 || index > size) {
            return null;
        }
        HeroNode cur = head.next; // 从头节点的下一个节点开始计算，相当于忽略了头节点的位置
        // 单向链表只能从头开始循环，遍历的次数为 size - index，就是倒数第k个节点
        // 假如长度为5，需要查倒数第五个，则条件为i<0,不会进入循环，刚好返回头节点的下一个节点，也就是倒是第五个
        // 假如长度为5，需要查倒数第三个，则条件为i<2,循环两次，第二个节点的下一个节点刚好为倒数第三个
        for (int i = 0; i < size - index; i++) {
            cur = cur.next;
        }
        return cur;
    }

    /**
     * 链表反转
     * 非递归实现
     */
    public static void reverseList(HeroNode head) {
        if (head.next == null) {
            return;
        }

        // 创建一个辅助变量
        HeroNode reverseHead = new HeroNode(0, null, null);
        HeroNode temp = new HeroNode(0, null, null);
        HeroNode cur = head.next;
        // 遍历链表
        while (cur != null) {
            // 保存当前节点的下一个节点，因为当前节点之后会从链表中取出，让cur后移
            temp = cur.next;

            // 将当前节点的下一个节点移动到reverseHead节点的前面，即保证cur的下一个节点指向新链表的最前端
            // 或者说将最前端的所有节点依次都指向当前循环节点的后面
            cur.next = reverseHead.next;
            // 将cur节点连接到新的链表上
            reverseHead.next = cur;
            // 后移
            cur = temp;
        }
        head.next = reverseHead.next;
    }

    /**
     * 利用栈先进先出的特点实现逆转打印的效果
     *
     * @param head 头节点
     */
    public static void reversePrint(HeroNode head) {
        if (head.next == null) {
            return;
        }
        // 创建一个栈
        Stack<HeroNode> heroNodes = new Stack<>();
        HeroNode cur = head.next;
        while (cur != null) {
            // 入栈
            heroNodes.push(cur);
            // 后移
            cur = cur.next;
        }
        while (heroNodes.size() > 0) {
            // 先进先出
            System.out.println(heroNodes.pop());
        }
    }

    public static HeroNode recReverseList(HeroNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        HeroNode result = recReverseList(head.next);

        head.next.next = head;
        head.next = null;
        return result;
    }

    public static void merageList() {
        // 直接在方法内部定义两个链表，不在main方法中定义，不然main方法中太乱了
        HeroNode node1 = new HeroNode(1, "宋江", "及时雨");
        HeroNode node2 = new HeroNode(2, "卢俊义", "玉麒麟");
        HeroNode node3 = new HeroNode(3, "吴用", "智多星");
        HeroNode node4 = new HeroNode(4, "林冲", "豹子头");
        HeroNode node5 = new HeroNode(5, "武松", "行者");
        HeroNode node6 = new HeroNode(6, "秦明", "霹雳火");
        HeroNode node7 = new HeroNode(7, "李逵", "黑旋风");
        HeroNode node8 = new HeroNode(8, "孙二娘", "母夜叉");
        HeroNode node9 = new HeroNode(9, "孙二娘", "母夜叉");

        SingleLingList l1 = new SingleLingList();
        SingleLingList l2 = new SingleLingList();

        l1.add(node2);
        l1.add(node1);
        l1.add(node4);
        l1.add(node3);

        l2.add(node8);
        l2.add(node6);
        l2.add(node5);
        l2.add(node7);
        l2.add(node9);
        SingleLingList newList = new SingleLingList();

        // 合并 将l1拼接到l2后面或者将l2拼接到l1后面
        HeroNode temp = l1.getHead().next;
        // 找到l1的最后一个节点
        HeroNode endNode = new HeroNode(0, "", "");
        while (temp != null) {
            endNode = temp;
            temp = temp.next;
        }
        // 此时temp为l1的最后一个节点
        endNode.next = l2.getHead().next;
        // 输出一下此时的l1
        temp = l1.getHead().next;
        while (true) {
            if (temp == null) {
                break;
            }
            // 输出节点信息
            System.out.println(temp);
            // 将节点后移
            temp = temp.next;
        }

        // TODO 遍历l1，取出每个节点序号进行冒泡排序
        System.out.println("=========排序============");


    }
}

/**
 * 定义SingleLinkList 管理HeroNode
 */
class SingleLingList {
    // 初始化一个节点头，头节点不可随意修改
    private final HeroNode head = new HeroNode(0, "", "");

    public HeroNode getHead() {
        return head;
    }

    /**
     * 添加节点到单向链表（不考虑HeroNode中的编号属性）
     * 1.找到当前链表的最后节点
     * 2.将最后的这个节点的 next 指向新的节点
     *
     * @param heroNode 需要添加的节点
     */
    public void add(HeroNode heroNode) {
        HeroNode temp = head;
        while (true) {
            // 找到最后的节点
            if (temp.next == null) {
                // 已经找到
                break;
            } else {
                // 如果没有找到，将temp后移
                temp = temp.next;
            }
        }
        // 当退出while循环时，temp就指向了链表的最后
        temp.next = heroNode;
        // 缺点：没次添加新节点时，都需要从头到尾找到最后一个节点
    }

    /**
     * 添加节点到单向链表（考虑HeroNode中的编号属性），根据排名将英雄插入到指定位置，如果有这个排名，则添加失败，并给出提示
     * 1.找到 要添加位置的前一个节点，比如要加节点的编号是2，则需要找到编号为1所在的节点
     * 2.假如这个节点找到了，设为temp，现在需要将要天添加的节点放在temp的后面，temp原先的next放在要添加的这个节点的next属性里
     *
     * @param heroNode 需要添加的节点
     */
    public void addByOrder(HeroNode heroNode) {
        HeroNode temp = head;
        boolean flag = false;
        while (true) {
            if (temp.next == null) {
                break; // temp已经在链表的最后，此时链表中还没有任何数据
            }
            if (temp.next.no > heroNode.no) { // 每次插入一个数据，这个条件第一次满足时，说明位置找到，就在temp的后面插入
                break;
            } else if (temp.next.no == heroNode.no) {
                flag = true;
                break;
            }
            temp = temp.next;// 后移，遍历当前链表
        }
        // 判断flag的值
        if (flag) {
            System.out.printf("准备插入的英雄编号：%d 已经存在，不能加入\n", heroNode.no);
        } else {
            // 插入到链表中
            // 先让temp的next指向要插入的节点next，如果直接将heroNode插入到temp的next中，则temp原先的temp会丢失，链表直接断开了
            heroNode.next = temp.next;
            // 将要插入的节点指向temp的next
            temp.next = heroNode;
        }
    }

    // 根据no编号修改节点的信息
    public void update(HeroNode heroNode) {
        if (head.next == null) {
            System.out.printf("链表为空...");
            return;
        }
        HeroNode temp = head.next;
        boolean flag = false;
        while (true) {
            if (temp.next == null) {
                // 已经到链表的最后
                break;
            }
            if (temp.no == heroNode.no) { // 找到要修改的节点了
                flag = true;
                break;
            }
            temp = temp.next;
        }
        if (flag) {
            // 进行修改
            temp.name = heroNode.name;
            temp.nickName = heroNode.nickName;
        } else {
            System.out.printf("没有找到编号为：%d的节点\n", heroNode.no);
        }

    }

    // 删除
    public void delete(int no) {
        if (head.next == null) {
            System.out.printf("链表为空...");
            return;
        }
        HeroNode temp = head;
        boolean flag = false;
        while (true) {
            if (temp.next == null) {
                // 已经到链表的最后
                break;
            }
            if (temp.next.no == no) { // 找到了要删除的节点的上一个节点
                flag = true;
                break;
            }
            temp = temp.next;
        }
        if (flag) {
            // 这里被删除的这个节点由于不会被其他类引用，一段时间之后会被GC
            temp.next = temp.next.next;
        } else {
            System.out.printf("编号为：%d的节点不存在\n", no);
        }
    }

    // 遍历
    public void list() {
        // 判断链表是否为空
        if (head.next == null) {
            System.out.println("链表为空");
            return;
        }

        HeroNode temp = head.next;
        while (true) {
            if (temp == null) {
                break;
            }
            // 输出节点信息
            System.out.println(temp);
            // 将节点后移
            temp = temp.next;
        }
    }

}

/**
 * 节点
 */
class HeroNode {

    // 编号
    public int no;
    public String name;
    public String nickName;
    // 下一个节点
    public HeroNode next;

    public HeroNode(int no, String name, String nickName) {
        this.no = no;
        this.name = name;
        this.nickName = nickName;
    }

    @Override
    public String toString() {
        return "HeroNode{" +
                "no=" + no +
                ", name='" + name + '\'' +
                ", nickName='" + nickName +
                '}';
    }
}
