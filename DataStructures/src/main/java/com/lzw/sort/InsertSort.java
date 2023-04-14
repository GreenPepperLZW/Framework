package com.lzw.sort;

/**
 * 插入排序：
 * 把n个待排序的元素看成为一个有序表和无序表，开始时有序表中只包含一个元素，无序表中包含n-1个原色
 * 比如数组：{101,34,119,1}
 * 101是有序表中的第一个元素，34,119,1是无序表中的元素
 * 排序过程中每次从无序表中取出第一个元素，把它的排序码和有序表中的排序码 依次 比较，根据从大大小或者从小到大的规则放到合适的位置，使之成为有序表
 *
 * @author : lzw
 * @date : 2023/4/13
 * @since : 1.0
 */
public class InsertSort {

    public static void main(String[] args) {
        int[] array = {101, 34, 1, 119, -1};
        insert(array);
        for (int i = 0; i < array.length; i++) {
            System.out.print("排序后：" + array[i] + " ");
        }
        int[] testArray = new int[80000];
        for (int i = 0; i < 80000; i++) {
            testArray[i] = (int) (Math.random() * 80000);
        }
        long start = System.currentTimeMillis();
        insert(testArray);
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - start) + "ms"); //741秒
    }


    // 首先循环这个数组，取出每一个值
    public static void insert(int[] array) {
        // {101,34,1,119,-1}
        // 有序表: 101
        // 无序表: 34,1,119,-1
        for (int i = 1; i < array.length; i++) { // for循环从 1 开始，无序表第一个值
            // 每次循环都将无序表中的 第一个元素取出 和有序表中的每个元素依次比较
            // 定义无序表中的第一个数
            int insertVal = array[i];
            // 有序表中的最后一个数
            int insertIndex = i - 1;
            // 判断后面一个数是否小于前面一个数，即无序表中的第一个数是否小于有序表中的最后一个数
            while (insertIndex >= 0 && insertVal < array[insertIndex]) {
                // 按照从小到大的排序规则，如果后面一个数小于前面一个数，则后面这个数要往前面移动.前面的数往后移，交换位置
                // 前面的数往后移,将101移动到了34的位置，此时34没有丢失，它保存在insertVal这个变量中
                array[insertIndex + 1] = array[insertIndex];
                // 无序表中的第一个数继续和前面一个数比较,insertIndex下标往前挪一下，这个insertIndex最多等于0，保证不越界，所以在循环中要加个条件，insertIndex >= 0
                insertIndex--;
            }

            // 退出这个循环后，证明无序表中的第一个数此时要大于有序表中的 array[insertIndex]，现在需要把无序表中的数往前移动
            // 所以将 insertVal 移动到 insertIndex 的前面一位
            // array[insertIndex + 1] = insertVal;

            // 如果 while 循环从没有进去过，则表示 无序表中的第一个数 不需要移动，它此时就是要在这个位置，刚好构成有序表
            // 不需要移动代表不用进行赋值操作，可以进行优化
            if (insertIndex + 1 != i) {
                array[insertIndex + 1] = insertVal;
            }
        }
    }
}
