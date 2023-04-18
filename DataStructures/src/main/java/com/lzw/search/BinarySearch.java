package com.lzw.search;

import java.util.ArrayList;

/**
 * 二分查找，在一个有序表里查
 * 必须在一个有序表里进行查找，从小到大、从大到小
 *
 * @author : lzw
 * @date : 2023/4/16
 * @since : 1.0
 */
public class BinarySearch {

    public static void main(String[] args) {
        int[] arr = {1, 8, 10, 89, 89, 1000, 1000, 1000, 1234};
        int i = binarySearch(arr, 0, arr.length - 1, 1235);
        System.out.println("下标：" + i);
        /*ArrayList<Integer> integers = binarySearchPlus(arr, 0, arr.length - 1, 89);
        System.out.println("下标：" + integers);*/

    }

    /**
     * @param arr     有序表，从小到大
     * @param left    最左边的下标
     * @param right   最右边的下标
     * @param findVal 要查找的值
     * @return 如果找到就返回下标，找不到返回-1
     */
    public static int binarySearch(int[] arr, int left, int right, int findVal) {

        // 如果left大于right，说明递归完了整个数组，但是没有找到这个数,为什么是left>right这个条件呢，因为在递归中left总是在递减，right总是在递增
        if (left > right) {
            return left;
        }
        // {1, 8, 10, 89, 1000, 1234}
        int mid = (left + right) / 2;
        int midVal = arr[mid];
        // 如果要查找的值大于中间的值，则向右边再找，即向右递归
        if (findVal > midVal) {
            // 向右递归，将右边的数据看作一个新的有序表，新表的最右边的下标还是right，最右边的下标为 mid+1
            return binarySearch(arr, mid + 1, right, findVal);
        } else if (findVal < midVal) {
            // 向左递归，将左边的数据看作一个新的有序表，新表的最左边的下标还是left，最右边的下标为 mid-1，mid这个下标的值从此不在查找的范围内
            return binarySearch(arr, left, mid - 1, findVal);
        } else {
            // midVal == findVal
            return mid;
            // 找到之后继续向左向右扫描，找到findValue所有的下标
        }
    }

    /**
     * @param arr     有序表，从小到大
     * @param left    最左边的下标
     * @param right   最右边的下标
     * @param findVal 要查找的值
     * @return 找到有序表中所有findValue所有的下标，找不到则返回-1
     */
    public static ArrayList<Integer> binarySearchPlus(int[] arr, int left, int right, int findVal) {

        // 如果left大于right，说明递归完了整个数组，但是没有找到这个数,为什么是left>right这个条件呢，因为在递归中left总是在递减，right总是在递增
        if (left > right) {
            // 返回空
            return new ArrayList<>();
        }
        // {1, 8, 10, 89, 1000, 1234}
        int mid = (left + right) / 2;
        int midVal = arr[mid];
        // 如果要查找的值大于中间的值，则向右边再找，即向右递归
        if (findVal > midVal) {
            // 向右递归，将右边的数据看作一个新的有序表，新表的最右边的下标还是right，最右边的下标为 mid+1
            return binarySearchPlus(arr, mid + 1, right, findVal);
        } else if (findVal < midVal) {
            // 向左递归，将左边的数据看作一个新的有序表，新表的最左边的下标还是left，最右边的下标为 mid-1，mid这个下标的值从此不在查找的范围内
            return binarySearchPlus(arr, left, mid - 1, findVal);
        } else {
            // 找到之后继续向左向右扫描，找到findValue所有的下标
            ArrayList<Integer> resIndexList = new ArrayList<>();

            // 向左边扫描
            int temp = mid - 1;// 因为是个有序表，所以同样的值一定是相邻的
            while (true) {
                // 找到最左边了，或者不是要找的值了就退出
                if (temp < 0 || arr[temp] != findVal) {
                    break;
                }
                // 否则，就意味着temp是要找的值，加入到集合中
                resIndexList.add(temp);
                temp -= 1;
            }
            // 将mid放在中间加入，这样在打印返回结果时也是个有序的
            resIndexList.add(mid);

            // 向右扫描
            temp = mid + 1;
            while (true) {
                if (temp > arr.length - 1 || arr[temp] != findVal) {
                    break;
                }
                resIndexList.add(temp);
                temp += 1;
            }
            return resIndexList;
        }
    }

    // 线性查找
    public int searchInsert(int[] nums, int target) {
        int length = nums.length;
        if (length == 0) {
            return 0;
        }
        for (int i = 0; i < length; i++) {
            if (nums[i] >= target) {
                return i;
            }
        }
        return length;
    }
}
