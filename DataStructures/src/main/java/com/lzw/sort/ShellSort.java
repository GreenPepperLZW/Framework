package com.lzw.sort;

import java.util.Arrays;

/**
 * 希尔排序，在插入排序上进行优化后的算法
 *
 * @author : lzw
 * @date : 2023/4/14
 * @since : 1.0
 */
public class ShellSort {
    public static void main(String[] args) {
        // 特意把最小的值放在最后面
        int[] array = {8, 9, 1, 7, 2, 3, 5, 4, 6, 0};
        // 按照从小到达排序
//        shellSort(array);
//        inferShellSort(array);
        System.out.println("希尔排序后：" + Arrays.toString(array));

        // 交换法速度测试
        int[] testArray = new int[80000];
        for (int i = 0; i < 80000; i++) {
            testArray[i] = (int) (Math.random() * 80000);
        }
        long start = System.currentTimeMillis();
        // 交换法，耗时5014ms
        // shellSort(testArray);
        // 移位法，耗时15ms
        shellSort2(testArray);
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - start) + "ms");
    }


    // 交换法
    public static void shellSort(int[] array) {

        int temp = 0;
        int count = 0;
        for (int gap = array.length / 2; gap > 0; gap /= 2) { // 每次循环都将数据分为两组，直到只能分为1组时
            for (int i = gap; i < array.length; i++) {
                // 遍历各组中所有元素（共五组，每组两个元素，相互比较相邻一个步长的两个元素之间下标相差5，也就是步长为5
                for (int j = i - gap; j >= 0; j -= gap) {
                    if (array[j] > array[j + gap]) {
                        // 交换法，
                        temp = array[j];
                        array[j] = array[j + gap];
                        array[j + gap] = temp;
                    }
                }
            }
//            System.out.println("希尔排序第" + (++count) + "轮后：" + Arrays.toString(array));
        }

    }

    // 移位法
    public static void shellSort2(int[] array) {
        for (int gap = array.length / 2; gap > 0; gap /= 2) { // 每次循环都将数据分为两组，直到只能分为1组时
            for (int i = gap; i < array.length; i++) {
                // 有序列表的中最后一个数的下标
                int j = i;
                // 定义无序列表的第一个值
                int temp = array[j];
                if (temp < array[j - gap]) { // 如果无序列表和比有序列表的第一个值小，则需要往前移动
                    while (j - gap > 0 && temp < array[j - gap]) {
                        // 后移
                        array[j] = array[j - gap]; // 将 j-gap看为变量k,假如此时 j等于10，gap等于2，array[10] = array[8],将下标为8的数据挪到了后面
                        j -= gap;
                    }
                    // 跳出循环后，意味着array[j - gap]大于 array[j]了
                    array[j] = temp;
                }
            }
        }
    }

    // 推导过程
    public static void inferShellSort(int[] array) {
        int temp = 0;
        // 第一轮循环，将长度为10的数组拆分成五个数组，10/2=5，对这五个数组分别进行插入排序，比较大小后交换位置，
        for (int i = 5; i < array.length; i++) {
            // 遍历各组中所有元素（共五组，每组两个元素，相互比较的两个元素之间下标相差5，也就是步长为5
            for (int j = i - 5; j >= 0; j -= 5) {
                if (array[j] > array[j + 5]) {
                    // 交换法，注意这里不是冒泡排序，冒牌排序是前驱与后继进行比较后交换，这里有一个步长，交换的两个元素之间不是前驱与后继的关系
                    // 这个循环采用的也是插入排序的思想，步长在下一次循环时减小，也就意味着会去和前一个数字比较大小
                    temp = array[j];
                    array[j] = array[j + 5];
                    array[j + 5] = temp;
                }
            }
        }

        System.out.println("希尔排序第一轮后：" + Arrays.toString(array));

        // 第二轮循环,将长度为10的数组拆分为两个数组，5/2=2，每组五个元素
        for (int i = 2; i < array.length; i++) {
            for (int j = i - 2; j >= 0; j -= 2) {
                if (array[j] > array[j + 2]) {
                    // 交换法
                    temp = array[j];
                    array[j] = array[j + 2];
                    array[j + 2] = temp;
                }
            }
        }
        System.out.println("希尔排序第二轮后：" + Arrays.toString(array));

        // 进行第三轮排序，将10个数据拆为1组，2/2=1
        for (int i = 1; i < array.length; i++) {
            for (int j = i - 1; j >= 0; j -= 1) {
                if (array[j] > array[j + 1]) {
                    temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
        System.out.println("希尔排序第三轮后：" + Arrays.toString(array));
    }

}
