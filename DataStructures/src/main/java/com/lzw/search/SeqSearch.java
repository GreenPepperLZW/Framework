package com.lzw.search;

/**
 * 线性查找
 *
 * @author : lzw
 * @date : 2023/4/16
 * @since : 1.0
 */
public class SeqSearch {

    public static void main(String[] args) {
        int[] arr = {1, 5, 2, 5, 43, 543, 2, -1};
        System.out.println("-1的下标" + seqSearch(arr, -1));
    }

    public static int seqSearch(int arr[], int value) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == value) {
                return i;
            }
        }
        return -1;
    }
}
