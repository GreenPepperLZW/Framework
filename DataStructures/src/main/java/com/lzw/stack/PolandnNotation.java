package com.lzw.stack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * 逆波兰表达式计算
 *
 * @author : lzw
 * @date : 2023/4/7
 * @since : 1.0
 */
public class PolandnNotation {
    public static void main(String[] args) {

        // 中缀表达式转后缀表达式
        String expression = "1+((2+3)*4)-5";
        List<String> list = toInfixExpression(expression);
        System.out.println("中缀表达式：" + list);
        List<String> rpnList = parseSuffixExpression(list);
        System.out.println("后缀表达式：" + rpnList);

        // 将中缀表达式list转为后缀表达式list


        // 原中缀表达式：(3+4)*5-6
        // 转为后缀表达式：3 4 + 5 * 6 -，为了方便处理，字符之间使用空格隔开
        // 4 * 5 - 8 + 60 + 8 / 2 =》 4 5 * 8 - 60 + 8 2 / +
        /*String suffixExpression = "4 5 * 8 - 60 + 8 2 / +";
        List<String> rpnList = getListString(suffixExpression);*/
        int res = cal(rpnList);
        System.out.println("结果：" + res);

    }

    /**
     * 将中缀表达式转为对应的list
     * <p>
     * char类型 使用int接收，则显示十进制的编码，若进行比较运算，也是使用编码进行运算的；使用String接收，则显示字符
     *
     * @param expression
     * @return
     */
    public static List<String> toInfixExpression(String expression) {
        ArrayList<String> list = new ArrayList<>();
        // 用于遍历中缀表达式的指针
        int i = 0;
        // 对多位数的拼接
        String str;
        // 每遍历到一个字符就放入到 c
        char c;
        do {
            //如果是一个非数字（即符号），就加入到集合中
            if ((c = expression.charAt(i)) < 48 || (c = expression.charAt(i)) > 57) {
                list.add(c + "");
                i++; // 后移
            } else {
                str = ""; // 先直控
                // 如果是一个数字，需要考虑多位数的情况
                while (i < expression.length() && (c = expression.charAt(i)) >= 48 && (c = expression.charAt(i)) <= 57) {
                    str += c;
                    i++;
                }
                list.add(str);
            }
        } while (i < expression.length());
        return list;
    }

    /**
     * 将中缀表达式list转为后缀表达式list
     *
     * @param list
     * @return
     */
    public static List<String> parseSuffixExpression(List<String> list) {
        // 用来存放输出的后缀表达式
        ArrayList<String> rpnList = new ArrayList<>();
        // 用来存符号
        Stack<String> s1 = new Stack<>();
        for (String item : list) {
            // 如果是数字
            if (item.matches("\\d+")) {
                rpnList.add(item);
            } else if (item.equals("(")) {
                // 如果是左括号,也直接入栈
                s1.push(item);
            } else if (item.equals(")")) {
                // 如果是右括号，将栈中的数据都输出，直到遇见 “(” 左括号
                while (!s1.peek().equals("(")) {
                    rpnList.add(s1.pop());
                }
                s1.pop();// 将左括号弹出
            } else {
                // 当item的优先级小于或等于栈顶，将栈中的数据输出到pop
                while (s1.size() != 0 && priority(item) <= priority(s1.peek())) {
                    rpnList.add(s1.pop());
                }
                // 比较完之后，当前item需要压栈
                // 当item的优先级大于栈顶 比如 / * 大于 + -，则将item入栈
                s1.push(item);

            }
        }
        // 将栈中的剩余数据输出到表达式
        while (s1.size() != 0) {
            rpnList.add(s1.pop());
        }
        return rpnList;
    }

    // 将逆波兰表达式转为集合
    public static List<String> getListString(String suffixExpression) {
        return Arrays.asList(suffixExpression.split(" "));
    }

    /**
     * 完成对逆波兰表达式的计算
     * 计算规则：
     * 从左至右扫描，遇到数字就压栈，遇到符号就把栈顶和次栈顶出栈进行计算，再将结果压栈，反复循环，一直到最终获得结果
     */
    public static int cal(List<String> ls) {
        // 创建一个栈
        Stack<String> stack = new Stack<>();
        for (String item : ls) {
            if (item.matches("\\d+")) {// 匹配到多位数，则只要是数字就入栈
                stack.push(item);
            } else {
                int num1 = Integer.parseInt(stack.pop());
                int num2 = Integer.parseInt(stack.pop());
                int res = 0;
                switch (item) {
                    case "+":
                        res = num1 + num2;
                        break;
                    case "-":
                        res = num2 - num1;// 用后弹出的数作为被减数，因为后入栈的数据先被取出
                        break;
                    case "*":
                        res = num1 * num2;
                        break;
                    case "/":
                        res = num2 / num1;// 用后弹出的数作为被除数
                        break;
                    default:
                        throw new RuntimeException("运算符有误");
                }
                stack.push(res + "");
            }
        }
        // 最后留在栈中的数据就是运算结果
        return Integer.parseInt(stack.pop());
    }


    // 判断运算符的优先级
    public static int priority(String oper) {
        if (oper.matches("\\+||\\-")) {
            return 1;
        } else if (oper.matches("\\\\|\\*")) {
            return 2;
        } else {
            System.out.println("不存在该运算符");
            return -1;// 假定目前的表达式只有 +,-,*,/
        }
    }
}
