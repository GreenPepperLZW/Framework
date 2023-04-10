package com.lzw.stack;

/**
 * 使用栈实现四则运算,该版本只支持个位数的+、-、*、\运算，
 *
 * @author : lzw
 * @date : 2023/4/6
 * @since : 1.0
 */
public class Calculator {

    public static void main(String[] args) {
        // 表达式
        String expression = "355+2*6-2";
        // 创建两个栈，数栈，符号栈
        ArrayStack numStack = new ArrayStack(10);
        ArrayStack operStack = new ArrayStack(10);
        // 定义相关变量

        // 用于扫描表达式的指针
        int index = 0;
        // 用于接收从数栈中取出的数字
        int num1 = 0;
        int num2 = 0;
        // 用于接收从符号栈中取出的符号
        int oper = 0;
        // 两数计算后的结果
        int res = 0;
        // 每次从表达式中扫描出来的结果
        char ch = ' ';
        // 用于拼接多位数的情况
        String keepNumber = "";

        // 开始while循环扫描expression
        while (true) {
            ch = expression.substring(index, index + 1).charAt(0);
            // 判断ch是符号还是数字
            if (operStack.isOper(ch)) {
                // 符号，判断符号栈是否为空
                if (!operStack.isEmpty()) {
                    // 如果符号栈有操作符，就进行比较，如果当前的的操作符优先级小于或者等于栈中的操作符，就需要从数栈中取出两个数
                    // 再从符号栈中取出一个符号，进行运算得到结果，将得到的结果入数栈，然后将当前的操作符入符号栈
                    if (operStack.priority(ch) <= operStack.priority(operStack.peek())) {
                        num1 = numStack.pop();
                        num2 = numStack.pop();
                        oper = operStack.pop();
                        res = operStack.cal(num1, num2, (char) oper);
                        // 把运算的结果入数栈
                        numStack.push(res);
                        // 把当前的操作符入符号栈
                        operStack.push(ch);
                    } else {
                        // 当前的操作符号优先级大于符号栈中的操作符
                        operStack.push(ch);
                    }

                } else {
                    // 如果符号栈为空，直接入栈
                    operStack.push(ch);
                }

            } else {
                // 如果是数字则直接入数栈,个位数的情况
                //numStack.push(ch - 48); // ASICC码与字符之间相差48


                // 考虑多位数的情况
                keepNumber += ch;
                // 如果此时ch就是最后一位数则直接入栈
                if (index == expression.length() - 1) {
                    numStack.push(Integer.parseInt(keepNumber));
                }


                // 判断下一个字符是不是数字，如果是数字就继续扫描，如果是运算符，则将该数字入栈
                else if (operStack.isOper(expression.substring(index + 1, index + 2).charAt(0))) {
                    numStack.push(Integer.parseInt(keepNumber));
                    keepNumber = "";// 清空
                }
            }
            // 让index++，判断是否是expression最后
            index++;
            if (index >= expression.length()) {
                break;
            }
        }
        // 当表达式扫描完毕，就顺序的从数栈和符号栈中取出相应的数和符号，并运行
        while (true) {
            // 如果符号栈为空，则计算到最后的结果，数栈中只有一个数字，即结果
            if (operStack.isEmpty()) {
                break;
            }
            num1 = numStack.pop();
            num2 = numStack.pop();
            oper = operStack.pop();
            res = operStack.cal(num1, num2, (char) oper);

            numStack.push(res);
        }
        System.out.printf("表达式 %s = %d \n", expression, numStack.pop());
    }
}


// 栈
class ArrayStack {
    private int maxSize;
    private int top = -1;
    private int stack[];

    public ArrayStack(int maxSize) {
        this.maxSize = maxSize;
        stack = new int[maxSize];
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
            System.out.println("栈满");
            return;
        }
        stack[++top] = value;
    }

    // 出栈
    public int pop() {
        if (isEmpty()) {
            throw new RuntimeException("栈空");
        }
        int value = stack[top];
        top--;
        return value;
    }

    // 返回运算符的优先级
    public int priority(int oper) {
        if (oper == '*' || oper == '/') {
            return 1;
        } else if (oper == '+' || oper == '-') {
            return 0;
        } else {
            return -1;// 假定目前的表达式只有 +,-,*,/
        }
    }

    // 返回当前栈顶的值，但不出栈
    public int peek() {
        return stack[top];
    }

    // 判断是不是一个运算符
    public boolean isOper(char oper) {
        return oper == '*' || oper == '/' || oper == '+' || oper == '-';
    }

    // 计算
    public int cal(int num1, int num2, char oper) {
        int res = 0;
        switch (oper) {
            case '+':
                res = num1 + num2;
                break;
            case '-':
                ;
                res = num2 - num1; // 后出栈的数做为被减数
                break;
            case '*':
                res = num1 * num2;
                break;
            case '/':
                res = num1 / num2;
                break;
            default:
                break;
        }
        return res;
    }

    // 遍历栈
    public void list() {
        if (isEmpty()) {
            throw new RuntimeException("栈空");
        }
        for (int i = top; i >= 0; i--) {
            System.out.printf("stack[%d]=%d \n", i, stack[i]);
        }
    }

}
