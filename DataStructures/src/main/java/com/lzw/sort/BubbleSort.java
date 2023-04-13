package com.lzw.sort;

/**
 * 冒泡排序以及优化处理
 *
 * @author : lzw
 * @date : 2023/4/12
 * @since : 1.0
 */
public class BubbleSort {

    public static void main(String[] args) {
        int arr[] = {3, 9, -1, 10, -2, 1, 34, -3};
//        int arr[] = {1, 2, 3, 4, 5, 6, 7, 8};
        sort(arr);
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
    }

    public static void sort(int[] array) {
        int n = 0;
        int temp = 0;
        boolean flag = false;
        // 将 i 的初始值设为1，外层循环每走一次确定一个最大值，所有只需要走 array.length - 1 次就能排完序
        for (int i = 1; i < array.length; i++) {
            n++;
            // 内层循环比较次数每次都减少，外层循环每走一次就能确定一个最大值，i的初始值设置为 1 就不会发生数组越界的情况了。
            for (int j = 0; j < array.length - i; j++) {
                if (array[j] > array[j + 1]) { // 如果前面一位数大于后面一位数
                    // 标记这一轮是否有进行为位置替换
                    flag = true;
                    temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
            if (!flag) {
                // 优化
                // 一轮循环之后发现没有替换过，代表此时已经是从小到大的顺序了
                break;
            } else {
                flag = false;
            }
        }
        System.out.println("n=" + n);
    }
}
