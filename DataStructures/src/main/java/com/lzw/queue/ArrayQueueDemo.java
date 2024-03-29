package com.lzw.queue;

import java.util.Scanner;

/**
 * 使用数组模拟队列
 * 当前demo问题：数组使用一次后无法再使用，不能复用
 * 改进：使用算法改进成一个环形队列
 *
 * @author : lzw
 * @date : 2022/7/19
 * @since : 1.0
 */
public class ArrayQueueDemo {
    public static void main(String[] args) {
        ArrayQueue arrayQueue = new ArrayQueue(3);
        char key = ' ';// 用户输入的数据
        Scanner scanner = new Scanner(System.in);
        boolean loop = true;
        while (loop) {
            System.out.println("s(show)：显示队列");
            System.out.println("e(exit)：退出程序");
            System.out.println("a(add)：添加上数据到队列");
            System.out.println("g(get)：从队列取出数据");
            System.out.println("h(head)：查看队列头数据");
            key = scanner.next().charAt(0);// 接收一个字符
            switch (key) {
                case 's':
                    arrayQueue.print();
                    break;
                case 'a':
                    System.out.println("请输入一个数值");
                    int value = scanner.nextInt();
                    arrayQueue.addQueue(value);
                    break;
                case 'g':
                    try {
                        int res = arrayQueue.getQueue();
                        System.out.printf("取出的数据是%d\n", res);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'h':
                    try {
                        int res = arrayQueue.headQueue();
                        System.out.printf("取出的数据是%d\n", res);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'e':
                    scanner.close();
                    loop = false;
                    break;
                default:
                    break;
            }
        }
    }
}

class ArrayQueue {

    // 队列最大值
    private int maxSize;
    // 队列尾，直接指向队列尾
    private int rear;
    // 队列头，指向队列头的上一个位置
    private int front;
    // 存放数据的数组，模拟队列
    private int[] arr;

    public ArrayQueue(int arrMaxSize) {
        this.maxSize = arrMaxSize;
        arr = new int[maxSize];
        rear = -1; // 指向队列尾，就是当前队列尾部数据的下标
        front = -1;// 指向队列头部，其实是指向队列头部数据的前一个位置，如果赋值为0，则代表指向头部的第一个数据，但初始化时数据都是空的
    }

    // 判断队列是否满
    public boolean isFull() {
        // 每加入一个数据，队列尾部则后移一位，在数组中，第一个数据的下标是0，所以是等于 maxSize - 1
        return rear == maxSize - 1;
    }

    // 判断队列是否为空
    public boolean isEntity() {
        return rear == front;
    }

    // 往队列里添加数据
    public void addQueue(int n) {
        // 判断队列是否已满
        if (isFull()) {
            System.out.println("队列已满");
            return;
        }
        // 队列尾部向后移一位，将数据添加到这个位置
        // ++rear是先将rear自增1后再使用，如果现在是第一次加数据，则最终的代码为 arr[0] = n
        // 一下这行代码可以写为 rear++;arr[rear] = n;
        arr[++rear] = n;
    }

    // 从队列取数据
    public int getQueue() {
        // 判断数据是否为空
        if (isEntity()) {
            throw new RuntimeException("队列为空，没有数据了");
        }
        // 取出数据,队列头向后移一位
        // 如果是第一次取出数据，队列头从 -1 变为 0，将下标为0的数据取出之后，第 0 个位置的数据变为空，所以此时front是指向队列头部数据的前一个位置，并不是直接指向头部数据
        // 那么下次要取出头数据是，其实是要加1的

        return arr[++front];
    }

    // 打印队列
    public void print() {
        if (isEntity()) {
            System.out.println("队列为空，没有数据");
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.printf("arr[%d] = %d\n", i, arr[i]);
        }
    }

    // 显示队列头数据，并不是取出数据
    public int headQueue() {
        if (isEntity()) {
            System.out.println("队列为空");
            throw new RuntimeException("队列为空");
        }
        return arr[front + 1];
    }
}
