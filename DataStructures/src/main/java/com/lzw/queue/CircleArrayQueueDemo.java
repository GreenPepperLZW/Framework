package com.lzw.queue;

import java.util.Scanner;

/**
 * 环形队列，解决数组无法复用问题
 *
 * @author : lzw
 * @date : 2023/3/27
 * @since : 1.0
 */
public class CircleArrayQueueDemo {
    public static void main(String[] args) {
        CircleAarray arrayQueue = new CircleAarray(4);// 长度设置为4，其队列的有效数据最大是3
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
                    arrayQueue.showQueue();
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

class CircleAarray {
    // 队列最大值
    private int maxSize;
    // 队列尾，指向当前队列尾的后一个位置，也就是 当前队列尾部下标+1 = rear
    // 初始值：0
    private int rear;
    // 队列头，指向当前队列头数据的下标
    // 初始值：0
    private int front;
    // 存放数据的数组，模拟队列
    private int[] arr;

    public CircleAarray(int maxSize) {
        this.maxSize = maxSize;
        arr = new int[this.maxSize];
    }


    // 判断队列是否满
    public boolean isFull() {
        return (rear + 1) % maxSize == front;
    }

    // 判断队列是否为空
    public boolean isEntity() {
        return rear == front;
    }


    // 添加数据到队列
    public void addQueue(int n) {
        if (isFull()) {
            System.out.printf("队列已满，不能加入数据");
            return;
        }
        // 直接将数据加入
        arr[rear] = n;
        // rear往后移动一个位置，这里需要考虑是否已经到环形的最后一个位置，如果此时rear已经在maxSize-1的位置，则需要再次循环一次
        // 所以先往后移一个位置，再考虑循环问题，代码如下
        rear = (rear + 1) % maxSize; // 注意：这里会出现 队列尾小于队列头的情况
    }

    // 从队列取出数据
    public int getQueue() {
        // 判断队列是否为空
        if (isEntity()) {
            throw new RuntimeException("队列空，不能取数据");
        }
        // 取出数据，再将队列头后移一位，队列头后移同样需要考虑到循环的问题，同样使用取余解决（取余动作其实就是把一圈减掉，防止数组越界）
        int value = arr[front];
        front = (front + 1) % maxSize;

        return value;
    }

    // 显示队列中所有数据
    public void showQueue() {
        if (isEntity()) {
            System.out.println("队列为空，没有数据。。。");
            return;
        }
        //从front开始遍历，遍历多少个元素（需要遍历front到rear之间的元素）
        for (int i = front; i < front + size(); i++) {
            // 这里的下标取模同样是因为循环
            System.out.printf("arr[%d]=%d \n", i % maxSize, arr[i % maxSize]);
        }

    }

    // 求出当前队列有效数据的个数
    public int size() {
        return (rear - front + maxSize) % maxSize;
    }

    // 显示队列头元素
    public int headQueue() {
        if (isEntity()) {
            throw new RuntimeException("队列为空，没有数据。。。");
        }
        return arr[front];
    }

}
