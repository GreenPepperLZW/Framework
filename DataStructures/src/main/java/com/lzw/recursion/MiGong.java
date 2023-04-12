package com.lzw.recursion;

/**
 * 迷宫回溯问题
 *
 * @author : lzw
 * @date : 2023/4/11
 * @since : 1.0
 */
public class MiGong {
    public static void main(String[] args) {
        // 创建一个二维数组模拟迷宫
        int[][] map = new int[8][7];
        // 数组中 1 表示墙壁，迷宫的上下左右都为墙壁

        // 上下置为墙壁
        for (int i = 0; i < 7; i++) {
            map[0][i] = 1;
            map[7][i] = 1;
        }
        // 左右置为墙壁
        for (int i = 0; i < 8; i++) {
            map[i][0] = 1;
            map[i][6] = 1;
        }
        // 【3，1】，【3，2】设置为墙壁
        map[3][1] = 1;
        map[3][2] = 1;

        // 有一个球在【1，1】的位置，现在需要让他移动到 【6，5】的位置，找出最短移动路线
        print(map);
        System.out.println("===============");
        setWay(map, 1, 1);
        print(map);
    }

    /**
     * 使用递归回溯来给小球找路,
     * 说明：如果小球能到【6,5】，则说明找到通路
     * 当map[i][j]为0时，表示该点没有走过；为1时，表示墙；为2时，表示通路可以走；为3时，表示该点已经走过，但是走不通
     * 需要确定一个策略，走出迷宫的方向，如果走不通就回溯，下->右->上->左
     *
     * @param map 地图
     * @param i   起始横坐标
     * @param j   起始纵坐标
     * @return 找到通路返回true，否则返回false
     */
    public static boolean setWay(int map[][], int i, int j) {
        if (map[6][5] == 2) {
            return true;
        } else {
            if (map[i][j] == 0) { // 如果这个位置还没有走过
                // 开始按照策略走 下->右->上->左
                map[i][j] = 2;// 先假定这个位置可以走，如果四个方向都尝试了，都走不通，则把这个位置标记为死路
                if (setWay(map, i + 1, j)) { //向下走
                    return true;
                } else if (setWay(map, i, j + 1)) { // 向右走
                    return true;
                } else if (setWay(map, i - 1, j)) { // 向下走
                    return true;
                } else if (setWay(map, i, j - 1)) {// 向左走
                    return true;
                } else {
                    // 上下左右都走不通过，该点是思路
                    map[i][j] = 3;
                    return false;
                }
            } else { // 如果map[i][j] != 0,可能是 1，2，3 则直接返回false，等于2是已经走过的位置，比如刚从一个位置过来，不可能在找新路的时候又走回去
                return false;
            }
        }
    }


    // 打印
    public static void print(int[][] map) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 7; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }

}
