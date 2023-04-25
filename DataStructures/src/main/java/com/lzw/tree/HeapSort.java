package com.lzw.tree;

/**
 * 堆排序
 *
 * @author : lzw
 * @date : 2023/4/24
 * @since : 1.0
 */
public class HeapSort {

    public static void main(String[] args) {
//        int arr[] = {4, 6, 8, 5, 9};
        int arr[] = {50, 10, 90, 30, 70, 40, 85, 80, 20};
        heapSort(arr);

        for (int i = 0; i < arr.length; i++) {
            System.out.printf(arr[i] + " ");
        }

        // 性能测试
        int[] testArr = new int[80000000];
        for (int i = 0; i < 80000000; i++) {
            testArr[i] = (int) (Math.random() * 80000000);
        }
        long start = System.currentTimeMillis();

        heapSort(testArr);

        long end = System.currentTimeMillis();
        long res = end - start;
        System.out.println("耗时：" + res + "ms");// 八千万数据排序耗时23.6 s,八百万耗时2.3 s
    }

    /**
     * 升序排序
     *
     * @param arr
     */
    public static void heapSort(int arr[]) {
        int temp = 0;
        // 分步完成排序
        /*adjustHeap(arr, 3, arr.length);
        adjustHeap(arr, 2, arr.length);
        adjustHeap(arr, 1, arr.length);*/
        // 首先将无序序列构建成一个堆，根据升序降序排序需求选择大顶堆或小顶堆
        for (int i = arr.length / 2 - 1; i >= 0; i--) {
            adjustHeap(arr, i, arr.length);
        }

        for (int i = arr.length - 1; i > 0; i--) {
            // 将堆顶记录和当前未经排序子序列最后一记录交换
            temp = arr[i];
            arr[i] = arr[0];
            arr[0] = temp;


            // 将array[0,i-1]重新调整为大顶堆，i逐渐减小，末尾的数据不再参与比较，因为他们此时就是最大值
            adjustHeap(arr, 0, i);

        }


    }

    /**
     * 将一个二叉树调整为一个大顶堆;
     * 将 i 做为根节点，将这棵二叉树调整为大顶堆，
     *
     * @param arr    待调整的数组
     * @param i      表示非叶子节点在数组中的索引
     * @param length 表示多少个元素需要调整，在每一轮最大值和最小值交换之后，数组末尾的最大值就不需要在参与比较了，所以元素逐渐减少，
     *               第一次构建大顶堆时需要比较全部的原素
     */
    public static void adjustHeap(int arr[], int i, int length) {
        int temp = arr[i];
        // k的值为 i的左子节点，在数组中一个非叶子节点的左子节点的下标计算公式为：n*2+1，
        // 如果将数组的第一个位置一直置为空，那么k的值可以为 i*2，不需要+1
        // k的自增条件为：k * 2 + 1，表示需要找当前左子节点的左子节点，直到将这颗二叉树排成一个大顶堆
        // 调用adjustHeap()这个方法的循环中，传进来的 i 是不断递减的，也就是 非叶子节点在不断向上，直到找到根节点
        // 而在这个循环中，k是不断递增的，k的值是由i来决定的，k不断递增意味着无论 i 这个非叶子节点在哪，都能保证它下面是一个大顶堆，因为这个循环中在不断的比较
        for (int k = i * 2 + 1; k < length; k = k * 2 + 1) {

            // 先判断两个左右子节点的大小
            // 如果左子节点小于右子节点，那么就让当前非叶子节点去和右子节点比较，需要保证下一次比较时，当前非叶子节点是和它的两个字节中较大的一个去进行比较的
            if (k + 1 < length && arr[k] < arr[k + 1]) {
                ++k;// ++k之后，此时k的下标是当前节点的右子节点下标
            }

            if (temp > arr[k]) {
                // 如果当前非叶子节点大于它的左子节或右子节点，则表示当前节点大于它的两个左右子节点，满足大顶堆的规则，所以此时不需要做任何移动
                break;// 跳出这个循环，如果满足条件，将他左子节点的左子节点左为根节点继续去构造大顶堆
            } else {
                // 将左右子节点中较大的那一个移动到当前非叶子节点的位置
                arr[i] = arr[k];
                // i 的定义是非叶子节点在数组中的索引，移动位置之后 k 就是当前非叶子节点在数组中的索引，所以把 i = k，之后继续循环比较
                // 并且跳出循环后，还需要将原本的非叶子节点移动到k的位置，也就是它的左右子节点中的一个，从而完成位置交换
                i = k;
            }
        }
        // 将temp和 k 交换位置
        arr[i] = temp;
    }


}
