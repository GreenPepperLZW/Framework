package com.lzw.sparscarray;

/**
 * 稀疏数组学习
 * 将二维数组转为稀疏数组，再将稀疏数组转为二维数组
 *
 * @author : lzw
 * @date : 2022/7/18
 * @since : 1.0
 */
public class SparscArray {

    public static void main(String[] args) {
        // 创建普通二维数组，表示一个棋盘 11*11
        int chessArry[][] = new int[11][11];
        // 0：没有棋子，1：白字，2：蓝子
        // 第二行第三列有一个白子
        chessArry[1][2] = 1;
        // 第三行第四列有一个黑子
        chessArry[2][3] = 2;
        // 将棋盘打印一下，二维数组外层循环行，内层循环列
        for (int[] row : chessArry) {
            for (int item : row) {
                //格式化输出
                System.out.printf("%d\t", item);
            }
            // 每循环一次行换一行
            System.out.println();
        }

        //#####将二维数组转稀疏数组#########################################################################
        //1.得到非0数据的个数，先遍历二维数组
        int sum = 0;
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                if (chessArry[i][j] != 0) {
                    sum++;
                }
            }
        }
        System.out.println("非0数据的总数是：" + sum);

        // 2.创建稀疏数组，总行数为 sum+1,总列数固定为三列，第一行第一列保存原始二维数组的总行数，第一行第二类保存原始数组的总列数，第一行第三列保存原始数据的非0数据的总数
        // 其余行第一列保存原始数组中非0数据所在行数，第二列保存原始数组中非0数据所在的列数，第三列保存数值

        int sparscArray[][] = new int[sum + 1][3];
        sparscArray[0][0] = 11;
        sparscArray[0][1] = 11;
        sparscArray[0][2] = sum;

        // 3.遍历二维数组，将非0数据存进sparscArray中
        int count = 0;// 用于记录是第几个非0的数据
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                if (chessArry[i][j] != 0) {
                    count++;
                    sparscArray[count][0] = i;// 原始数据所在行
                    sparscArray[count][1] = j;// 原始数据所在列
                    // 第三列存值本身体
                    sparscArray[count][2] = chessArry[i][j];
                }
            }
        }

        // 遍历稀疏数组
        System.out.println("得到的稀疏数组是======");
        for (int i = 0; i < sparscArray.length; i++) {
            System.out.printf("%d\t%d\t%d\t\n", sparscArray[i][0], sparscArray[i][1], sparscArray[i][2]);
        }

        //#####将稀疏数组转回二维数组#########################################################################
        // 1.读取稀疏数组第一行的数据得到原始行原始列大小
        int chessArr2[][] = new int[sparscArray[0][1]][sparscArray[0][1]];

        // 2.从第二行读取数据将值赋给原始数组即可
        for (int i = 1; i < sparscArray.length; i++) {
            chessArr2[sparscArray[i][0]][sparscArray[i][1]] = sparscArray[i][2];
        }
        System.out.println("恢复后的二维数组=========");
        for (int[] row : chessArr2) {
            for (int item : row) {
                System.out.printf("%d\t", item);
            }
            System.out.println();
        }
    }
}
