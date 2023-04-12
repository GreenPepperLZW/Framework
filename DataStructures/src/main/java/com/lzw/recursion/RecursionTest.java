package com.lzw.recursion;

/**
 * @author : lzw
 * @date : 2023/4/11
 * @since : 1.0
 */
public class RecursionTest {
    public static void main(String[] args) {
//        test(10);

        int factorial = factorial(3);
        System.out.println("factorial=" + factorial);

        int res = test2(100);
        System.out.println("res=" + res);
    }

    public static void test(int n) {
        if (n > 2) {
            test(n - 1);
        }
        System.out.println("n=" + n);
    }

    public static int factorial(int n) {
        if (n == 1) {
            return 1;
        } else {
            return factorial(n - 1) * n;
        }
    }

    public static int test2(int n) {
        if (n == 1) {
            return 1;
        } else {
            return test2(n - 1) + n;
        }
    }

}
