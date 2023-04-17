package com.lzw.sort;

import java.util.Arrays;

/**
 * 基数排序
 *
 * @author : lzw
 * @date : 2023/4/16
 * @since : 1.0
 */
public class RadixSort {
    public static void main(String[] args) {
        int arr[] = {53, 3, 542, 748, 14, 214};


        // inferRadixSort(arr);

        radixSort(arr);
        System.out.println("排序后的数组：" + Arrays.toString(arr));

        // 速度测试
        // 一个int 4个字节，如果是 80000000 个数据，使用基数排序需要用3.3G的内存
        // 80000000 * 11(11 个一维数组) *4 /1024 /1024 /1024 = 3.3G
        int[] testArray = new int[8000000];
        for (int i = 0; i < 8000000; i++) {
            testArray[i] = (int) (Math.random() * 8000000);
        }
        long start = System.currentTimeMillis();
        radixSort(testArray);
        long end = System.currentTimeMillis();
        System.out.println("耗时" + (end - start) + "ms"); // 八百万耗时672ms,八万耗时15ms
    }

    public static void radixSort(int arr[]) {

        // 定义一个二维数组表示十个桶
        int[][] bucket = new int[10][arr.length];

        // 为了记录二维数组每个捅中实际存放了多少个数据
        int[] bucketElementCounts = new int[10];

        // 找到数组中的最大值
        int max = arr[0];
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }
        // 得到最大值的位数,即外层循环次数
        int maxLength = (max + "").length();
        for (int i = 0, n = 1; i < maxLength; i++, n *= 10) {
            // 针对数组中每个元素的 位数 进行排序，第一次是个位，第二次是十位，依次类推
            for (int j = 0; j < arr.length; j++) {
                // 获取 每个进制位（个、十、百、千）上的数,
                int digitOfElement = arr[j] / n % 10;
                // 748这个数，在第一次循环中取出8，将748放在下标为8的桶中；第二次取出4，将748放在下标为4的桶中；第三次取出7，将748放在下标为7的桶中.
                bucket[digitOfElement][bucketElementCounts[digitOfElement]] = arr[j];
                bucketElementCounts[digitOfElement]++;
            }
            int index = 0;
            for (int k = 0; k < bucketElementCounts.length; k++) {
                for (int l = 0; l < bucketElementCounts[k]; l++) {//  bucketElementCounts[k] 表示桶里有几个数
                    int res = bucket[k][l];
                    arr[index++] = res;
                }
                bucketElementCounts[k] = 0;
            }
        }
    }

    /**
     * 推导过程
     *
     * @param arr
     */
    public static void inferRadixSort(int arr[]) {

        // 基本的阿拉伯数组从0-9总共十个数字，定义一个二维数组表示十个桶
        // 未来放置在放入数的时候，数据溢出，将每一个一维数组（桶）的大小定为arr.length
        // 那么很明显，集数排序是使用空间换时间的算法
        int[][] bucket = new int[10][arr.length];

        // 为了记录二维数组每个捅中实际存放了多少个数据
        int[] bucketElementCounts = new int[10];

        // ==============================第一轮开始========================
        for (int j = 0; j < arr.length; j++) {
            // 取出每个元素的个位数
            int digitOfElement = arr[j] % 10;
            // 放入到对应的捅中
            // temp 的值用于debug时查看它的变化
            int temp = bucketElementCounts[digitOfElement];
            /**
             * 假如digitOfElement等于3
             * int temp = bucketElementCounts[digitOfElement];
             * bucketElementCounts 这个数组初始化为10个长度，数据为空;
             * 那么第一次获取bucketElementCounts[3]的值时，temp等于0;
             * 第二次获取bucketElementCounts[3]的值时等于1，因为bucketElementCounts[digitOfElement]++，已经自增了;
             *
             * 所以digitOfElement两次循环都等于3时，第一次将arr[j] 放在bucket数组的[3][0]位置上，第二次放在[3][1]位置上
             *
             */
            bucket[digitOfElement][bucketElementCounts[digitOfElement]] = arr[j];
            /**
             * 数据是引用类型，这里 ++ 之后，数组里的值也随之改变了，假如此时digitOfElement等于3，
             * 那么这句话的意思就是将数组中下标为3的数据加1，初始话为0，加1之后等于1
             */
            bucketElementCounts[digitOfElement]++;
        }
        // 按照这个捅的顺序（一维数组的下标依次取出数据，放入到原来数组中）
        int index = 0;
        for (int k = 0; k < bucketElementCounts.length; k++) {
            // 如果桶中有数据，我们才有必要从中取数据，bucketElementCounts每个下标中的元素记录的就是对应捅中数据的个数，下标 对应第几个捅
            if (bucketElementCounts[k] != 0) { // 这个if判断可以不用写，为了方便理解写起
                for (int l = 0; l < bucketElementCounts[k]; l++) {//  bucketElementCounts[k] 表示桶里有几个数
                    // 取出第k个捅的第l个数放到原数组arr中
                    int i = bucket[k][l];
                    arr[index++] = i;
                }
            }
            // 记录每个统计里有多少个数清零，否则第二次循环取数时会多取，造成越界
            bucketElementCounts[k] = 0;
        }
        // 打印
        print(arr, bucket, bucketElementCounts);

        // ==============================第一轮结束========================

        // ==============================第二轮开始========================
        System.out.println();
        System.out.println("开始第二轮循环");
        for (int j = 0; j < arr.length; j++) {
            // 取 十位上的数
            int digitOfElement = arr[j] / 10 % 10;
            bucket[digitOfElement][bucketElementCounts[digitOfElement]] = arr[j];
            bucketElementCounts[digitOfElement]++;
        }
        index = 0;
        for (int k = 0; k < bucketElementCounts.length; k++) {
            for (int l = 0; l < bucketElementCounts[k]; l++) {//  bucketElementCounts[k] 表示桶里有几个数
                int i = bucket[k][l];
                arr[index++] = i;
            }
            bucketElementCounts[k] = 0;
        }
        print(arr, bucket, bucketElementCounts);

        // ==============================第二轮结束========================

        // ==============================第三轮开始========================
        System.out.println();
        System.out.println("开始第三轮循环");
        for (int j = 0; j < arr.length; j++) {
            // 取 百位上的数
            int digitOfElement = arr[j] / 100 % 10;
            bucket[digitOfElement][bucketElementCounts[digitOfElement]] = arr[j];
            bucketElementCounts[digitOfElement]++;
        }
        index = 0;
        for (int k = 0; k < bucketElementCounts.length; k++) {
            for (int l = 0; l < bucketElementCounts[k]; l++) {//  bucketElementCounts[k] 表示桶里有几个数
                int i = bucket[k][l];
                arr[index++] = i;
            }
            bucketElementCounts[k] = 0;
        }
        print(arr, bucket, bucketElementCounts);

        // ==============================第三轮结束========================
    }

    /**
     * 打印
     *
     * @param arr
     * @param bucket
     * @param bucketElementCounts
     */
    private static void print(int[] arr, int[][] bucket, int[] bucketElementCounts) {
        // 输出二维数组
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < arr.length; j++) {
                System.out.printf("%-7d", bucket[i][j]);
            }
            System.out.println();
        }

        System.out.println("===============================");
        // 输出一维数组
        for (int i = 0; i < bucketElementCounts.length; i++) {
            System.out.printf("%-2d", bucketElementCounts[i]);
        }
        System.out.println();
        System.out.println("===============================");
        // 输出第一轮排序后的数组
        System.out.println("排序后的结果");
        for (int i = 0; i < arr.length; i++) {
            System.out.printf(arr[i] + "  ");
        }
    }

}
