package com.lzw.linklist;

import java.util.Stack;

/**
 * @author : lzw
 * @date : 2023/3/29
 * @since : 1.0
 */
public class TestStack {
    public static void main(String[] args) {
        Stack<String> stack = new Stack<>();
        // 入栈
        stack.add("jack");
        stack.add("tom");
        stack.add("smith");
        // 出栈

        while (stack.size() > 0) {
            // 将栈顶的数据取出
            System.out.println(stack.pop());
        }
    }
}
