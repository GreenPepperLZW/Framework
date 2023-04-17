package com.lzw.sort;

/**
 * 选择排序
 *
 * @author : lzw
 * @date : 2023/4/13
 * @since : 1.0
 */
public class SelectionSort {
    public static void main(String[] args) {
        int[] array = {101, 34, 119, 1};
        selection(array);

        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }

        System.out.println("========================");

        // 速度测试
        int[] arr = new int[80000];
        for (int i = 0; i < 80000; i++) {
            arr[i] = (int) (Math.random() * 80000); // 生成一个 [0,80000)的数
        }
        long l = System.currentTimeMillis();
        selection(arr);
        long l1 = System.currentTimeMillis();
        long timeConsume = l1 - l;
        System.out.printf("耗时：" + timeConsume + "ms"); // 两秒左右


    }

    public static void selection(int[] array) {
        int min;
        int minIndex;
        int next;
        // 四个数据只需要循环三次即可排好序，所以是  array.length - 1
        for (int j = 0; j < array.length - 1; j++) {
            // 外面一层循环确定一个最小值之后，那个最小值就不再参与比较了，所以从 j 开始计算最小值
            min = array[j];
            minIndex = j;
            // 每一次循环后找到最小数，i=j+1：表示从当前定义的最小数后一个位置开始找
            for (int i = j + 1; i < array.length; i++) {
                // 当前最小值的后面一个值
                next = array[i];
                if (min > next) { // min不是最小值
                    // 找到最小值
                    min = next;
                    // 找到最小值的下标
                    minIndex = i;
                }
            }
            // 如果最小值下标没变，则意味着不需要进行交换
            if (minIndex != j) {
                // 假如j等于0，把数组中第一个数放到最小值的下标处
                array[minIndex] = array[j];
                // 把最小的值放到数组中第一个位置
                array[j] = min;
            }

            // 将index后移，预防下一次查找最小数时里面的判断（(min > array[i + 1])）不进入，导致index还是保持上一轮循环的最小值下标
            // 移动之后这个下标就是第二次循环时最小值的下标了，这样即使 (min > array[i + 1]) 不进入，再来交换值时，其实就是自己和自己交换，不会影响到数组的整体结果
            // minIndex = j + 1;
        }


    }

}
