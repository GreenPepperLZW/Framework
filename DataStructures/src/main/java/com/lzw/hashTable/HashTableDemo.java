package com.lzw.hashTable;

import java.util.Hashtable;
import java.util.Scanner;

/**
 * @author : lzw
 * @date : 2023/4/18
 * @since : 1.0
 */
public class HashTableDemo {
    public static void main(String[] args) {
        // 默认大小为11,负载因子为 0.75
        Hashtable<String, String> hashtable = new Hashtable<>();
        hashtable.put("a", "a");

        HashTab hashTab = new HashTab(7);

        String key = "";
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("add： 添加员工");
            System.out.println("list： 显示雇员");
            System.out.println("exit：退出系统");
            System.out.println("find：查找员工");

            key = scanner.next();
            switch (key) {
                case "add":
                    System.out.println("输入id：");
                    int id = scanner.nextInt();
                    System.out.println("输入名字");
                    String name = scanner.next();
                    Emp emp = new Emp(id, name);
                    hashTab.add(emp);
                    break;
                case "list":
                    hashTab.list();
                    break;
                case "find":
                    System.out.println("请输入要查找的id：");
                    id = scanner.nextInt();
                    hashTab.findEmpById(id);
                    break;
                case "exit":
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    break;
            }
        }
    }
}


/**
 * 员工
 */
class Emp {

    public int id;
    public String name;
    public Emp next;

    public Emp(int id, String name) {
        this.id = id;
        this.name = name;
    }
}

/**
 * 哈希表，管理多条链表
 */
class HashTab {
    private EmpLinkList[] empLinkListsArray;
    private int size; // 有多少条链表


    public HashTab(int size) {
        this.size = size;
        empLinkListsArray = new EmpLinkList[size];
        // 初始化数组中的每一个链表
        for (int i = 0; i < size; i++) {
            empLinkListsArray[i] = new EmpLinkList();
        }
    }


    public void add(Emp emp) {
        // 根据员工的id，得到该员工应当添加到哪条链表中
        int empLinkedListNo = hashFun(emp.id);
        // 将emp加入到对应的链表中
        empLinkListsArray[empLinkedListNo].add(emp);
    }

    // 遍历
    public void list() {
        for (int i = 0; i < size; i++) {
            empLinkListsArray[i].list(i);
        }
    }

    public void findEmpById(int id) {
        int empLinkedListNo = hashFun(id);
        Emp emp = empLinkListsArray[empLinkedListNo].findEmpById(id);
        if (emp != null) {
            System.out.printf("在第 %d 条链表中找到员工 id=%d,name=%s\n", empLinkedListNo, emp.id, emp.name);
        } else {
            System.out.println("在哈希表中没有找到该员工");
        }
    }

    /**
     * 编写散列函数，使用取模法，判断这个员工应该放在哪条链表中
     *
     * @param id
     * @return
     */
    public int hashFun(int id) {
        return id % size;
    }

}


/**
 * 链表
 */
class EmpLinkList {
    // 头指针，指向第一个Emp，因此我们这个链表的head是有效的
    private Emp head;

    /**
     * 添加员工
     * 前提：员工编号是从小到大进行添加的
     *
     * @param emp 员工
     */
    public void add(Emp emp) {
        // 如果是添加第一个员工
        if (head == null) {
            head = emp;
            return;
        }
        // 如果head不为空
        Emp curEmp = head;
        while (true) {
            if (curEmp.next == null) {
                break;
            } else {
                curEmp = curEmp.next;
            }
        }
        curEmp.next = emp;
    }

    /**
     * 遍历链表
     */
    public void list(int index) {
        if (head == null) {
            System.out.println("第" + index + "条链表为空");
            return;
        }
        System.out.print("第" + index + "条链表的信息为：");
        Emp curEmp = head;
        while (true) {
            System.out.printf("=> id=%d nam=%s\t", curEmp.id, curEmp.name);
            if (curEmp.next == null) {
                break;
            } else {
                curEmp = curEmp.next;
            }
        }
        System.out.println();
    }

    public Emp findEmpById(int id) {
        if (head == null) {
            return null;
        }
        Emp curEmp = head;
        while (true) {
            if (curEmp.id == id) {
                break;
            }
            if (curEmp.next == null) {
                curEmp = null;
                break;
            }
            curEmp = curEmp.next;
        }
        return curEmp;
    }
}


