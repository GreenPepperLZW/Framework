package com.lzw.search;

/**
 * 插值查找算法
 * 在二分查找算法的基础上进行改进，二分查找法寻找中间值的公式是这样的 mid = (right+left)/2,这个公式可以进一步转为
 * <p>
 * mid = right/2 + left/2 - right/2 - right/2 + right ==> mid = right + (left-right)/2;
 * <p>
 * 如果想要更快的查到想要的值，只需要把mid的值减小，就可以更快的查到。二分之一是吧数组分成两半，如果将他分为 findKey-a[right]/a[left]-a[right]份
 * 查找范围可以动态变化，比二分之一小，可以查的更快,这个前提是这个数组中值的分布是均匀的，如果不均匀可能会查的更慢，分布不均匀指的是数组中的值跳跃性很大。
 * 在插值查找算法中，中间值查找的公式可以变为如下：
 * mid = right + (left-right)*findKey-a[right]/a[left]-a[right]
 * <p>
 * 说明：findKey为要查找的值，left为数组最左边的下标，right为数组最右边的下标，a[left]为数组最左边的值，a[right]为数组最右边的值。
 *
 * @author : lzw
 * @date : 2023/4/18
 * @since : 1.0
 */
public class InsertValueSearch {

    public static void main(String[] args) {
        // 构造一个分布均匀的数组
        int[] arr = new int[100];
        for (int i = 0; i < 100; i++) {
            arr[i] = i + 1;
        }
        int i = insertValueSearch(arr, 0, arr.length - 1, 2);
        System.out.println("下标：" + i);
    }

    /**
     * @param arr       数组
     * @param left      左边的下标
     * @param right     右边的下标
     * @param findValue 要查找的值
     * @return 如果找到，就返回对应值的下标，否则返回-1
     */
    public static int insertValueSearch(int[] arr, int left, int right, int findValue) {
        System.out.println("插值查找次数------");
        // 小于最小或大于最大时退出，前提是数组是有序的
        // findValue<arr[0] || findValue>arr[arr.length-1] 这两个条件必须有，否则得到的mid可能为负数或者超级大，造成越界
        if (left > right || findValue < arr[0] || findValue > arr[arr.length - 1]) {
            return -1;
        }
        //
        int mid = left + (right - left) * (findValue - arr[left]) / (arr[right] - arr[left]);
        int midValue = arr[mid];
        if (findValue > midValue) {
            // 向右边递归查找
            return insertValueSearch(arr, mid + 1, right, findValue);
        } else if (findValue < midValue) {
            // 向左边递归查找
            return insertValueSearch(arr, left, mid - 1, findValue);
        } else {
            return mid;
        }
    }
}
