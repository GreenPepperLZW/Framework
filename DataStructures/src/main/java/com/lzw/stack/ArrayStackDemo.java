package com.lzw.stack;

import java.util.Scanner;

/**
 * 使用数组模拟栈
 *
 * @author : lzw
 * @date : 2023/4/4
 * @since : 1.0
 */
public class ArrayStackDemo {

    public static void main(String[] args) {
        ArrayStack2 stack = new ArrayStack2(4);
        String key = "";
        boolean loop = true;

        Scanner scanner = new Scanner(System.in);

        while (loop) {
            System.out.println("show:显示数据");
            System.out.println("exit:退出程序");
            System.out.println("push:添加数据");
            System.out.println("pop:取出数据");
            System.out.println("请输入选择");
            key = scanner.next();
            switch (key) {
                case "show":
                    try {
                        stack.list();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "exit":
                    loop = false;
                    scanner.close();
                    break;
                case "push":
                    System.out.println("请输入值：");
                    int value = scanner.nextInt();
                    stack.push(value);
                    break;
                case "pop":
                    try {
                        int pop = stack.pop();
                        System.out.printf("出栈的数据是：%d \n", pop);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                default:
                    break;
            }
        }
        System.out.println("程序退出");
    }
}

/**
 * 使用数组模拟栈
 */
class ArrayStack2 {
    // 栈的大小
    private int maxSize;

    // 数组，用于存储数据
    private int[] stack;

    // top表示栈顶，初始化为-1
    private int top = -1;

    // 构造器
    public ArrayStack2(int maxSize) {
        this.maxSize = maxSize;
        stack = new int[this.maxSize];
    }

    // 栈满
    public boolean isFull() {
        return top == maxSize - 1;
    }

    // 栈空
    public boolean isEmpty() {
        return top == -1;
    }

    // 入栈
    public void push(int value) {
        if (isFull()) {
            System.out.println("已满");
            return;
        }
        stack[++top] = value;
    }

    // 出栈
    public int pop() {
        if (isEmpty()) {
            throw new RuntimeException("栈空,没有数据");
        }
        int value = stack[top];
        top--;
        return value;
    }

    // 遍历栈，从栈顶开始显示数据
    public void list() {
        if (isEmpty()) {
            throw new RuntimeException("栈空,没有数据");
        }
        for (int i = top; i >= 0; i--) {
            System.out.printf("stack[%d]=%d \n", i, stack[i]);
        }

    }

}
