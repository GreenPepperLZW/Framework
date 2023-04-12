package com.lzw.recursion;

/**
 * 八皇后问题
 * 在8X8格的国际象棋上摆放八个皇后，使其不能互相攻击，即:任意两个皇后都不能处于同一行、同一列或同一斜线上，问有多少种摆法(92)
 *
 * @author : lzw
 * @date : 2023/4/11
 * @since : 1.0
 */
public class Queen8 {

    static int count = 0;
    // 定义一个max，表示总共有多少个皇后
    int max = 8;
    // 定义数组array,保存皇后放置位置的结果，比如 arr = {0,4,7,5,2,6,1,3},
    // 这个一维数组的 下标+1 表示一个位置在棋盘的行，value+1表示一个位置在棋盘的列，（比如4这个值，表示在第2行的第五列），使用这个关系用一维数组就代替了一个二维数组
    int[] array = new int[max];

    public static void main(String[] args) {
        Queen8 queen8 = new Queen8();
        // 从第1行开始放放棋子
        queen8.check(0);
        System.out.println("一共有多少种解法：" + count);

    }

    /**
     * debug发现，从0开始循环，进入到第六个栈帧时，依然不满足规则，
     * for循环条件不满足了，跳出，回溯到第五个栈帧，第五个栈帧i从4开始，开始找位置，如果for循环也走完了，但是还没满足条件，则退回第上一个栈帧，就这样一直回溯，
     * 如果在这个栈帧中摆放的棋子满足了规则，则又递归到下一行，在这一行中开始摆放棋子
     *
     * @param n 从0开始计算，表示第几个皇后
     */
    private void check(int n) {
        if (n == max) { // n从0开始，如果等于n时，代表这是第九个皇后了
            // 如果能够走到 第八行，证明摆放的位置都是正确的，如果不正确都会回溯
            print();
            count++;
            return;
        }
        // 开始在第 n 行中放置棋子，在这一行的八列中都会放置一次
        for (int i = 0; i < max; i++) {
            // 注意，在现有逻辑中，一维数组的下标代表这个点在棋盘上的行，值代表这个点在棋盘上的列
            // 假如 n 等于0，在这个循环中，会在棋盘的第一行的 max-1 列中分别去放置棋子
            array[n] = i;
            // 检查放置的棋子是否符合规则，注意array时全局变量
            if (judge(n)) {
                // 不冲突，接着放 n+1 个皇后，即在下一行开始放置棋子，开始递归
                check(n + 1); // check() 方法里有一个循环，递归调用时又会调用一次循环
            } else {
                // 这里的else块可以不用写，为了方便理解写一下
                // 如果冲突了则在循环中回到 array[n] = i; 这一行，i 自增后表示将棋子移动到本行下一列，然后再判断是否符合规则
            }
        }
    }

    /**
     * 查看当我们放置第n个皇后时，就去检测该皇后是否鹤前面已经摆放的皇后冲突
     *
     * @param n 第几个皇后
     * @return 是否满足摆放的规则，即任意两个皇后都不能处于同一行、同一列或同一斜线上
     */
    private boolean judge(int n) {
        for (int i = 0; i < n; i++) {
            // 第n个皇后即第n列
            // 1.判断和n-i皇后是否在同一列上（array[i] == array[n]），
            // 2.判断是否在同一斜线上,如果两个点位于同一斜线上时，在棋盘会构成一个等腰三角形，等腰三角形的斜率为1，标准写法为 y2-y1/x2-x2=1，或者写为|y2-y1|=|x2-x2|
            // 那么根据这两个点的坐标就能算出是否在同一斜线上
            // 3.判断是否在同一行，没有必要判断，现在的逻辑中， n代表的是当前皇后的行，i代表的是前面几个皇后的行，在循环中n不可能等于i
            if (array[i] == array[n] || Math.abs(n - i) == Math.abs(array[n] - array[i])) {
                return false;
            }
        }
        return true;
    }

    // 输出结果
    private void print() {
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }
}
