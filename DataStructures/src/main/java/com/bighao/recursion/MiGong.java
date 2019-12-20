package com.bighao.recursion;

/**
 * @Author: bighao周启豪
 * @Date: 2019/12/20 14:15
 * @Version 1.0
 *
 * 使用递归
 * 迷宫回溯问题实现
 *
 * 小球得到的路径，和程序员设置的找路策略有关 即：找路的上下左右的顺序相关
 * 再得到小球路径时，可以先使用(下右上左)，再改成(上右下左)，看看路径是不是有变化
 * 测试回溯现象
 *
 * 思考: 如何求出最短路径? => 最简单的方法就是对上下左右的找路策略进行穷举，然后比较哪个最短即可(这里还没有学到算法)
 */
public class MiGong {
    public static void main(String[] args) {
        // 先创建一个二维数组,模拟迷宫
        // 地图 8 * 7
        int[][] map = new int[8][7];
        // 1-表示强
        // 上下全部置位1
        for (int i = 0; i < 7; i++) {
            map[0][i] = 1;
            map[7][i] = 1;
        }

        // 左右全部置位1
        for (int i = 0; i < 8; i++) {
            map[i][0] = 1;
            map[i][6] = 1;
        }

        // 设置挡板
        map[3][1] = 1;
        map[3][2] = 1;
        /*map[1][2] = 1;
        map[2][2] = 1;*/


        // 输出地图
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 7; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }

        // 使用递归回溯给小球找路
        //setWay(map, 1, 1);
        setWay2(map, 1, 1);

        // 输出新地图
        System.out.println("\n输出小球走过和标识过的新地图==========>");
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 7; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * 使用递归回溯来给小球找路
     * 说明:
     *      1.map表示地图
     *      2.i和j表示从地图的那个位置开始出发 例如map[1][1]
     *      3.如果小球能到map[6][5]则说明通路找到
     *      4.约定：当map[i][j]的值 0-该点没有走过 1-是墙 2-表示通路可以走 3-表示该位置已经走过，但是走不通
     *      5.在走迷宫时，需要确定一个策略(方法) 下->右->上->左，如果该点走不通再回溯
     * @param map 表示地图
     * @param i 行号
     * @param j 列号
     * @return 如果找到通路返回true,否则返回false
     */
    public static boolean setWay(int[][] map, int i, int j) {
        // 说明通路以及找到
        if (map[6][5] == 2) {
            return true;
        } else {
            // 为0表示还没有走过的
            if (map[i][j] == 0) {
                // 按照策略 下->右->上->左 走
                // 先假定该点是可以走通的
                map[i][j] = 2;
                if (setWay(map, i + 1, j)) { //先向下走
                    return true;
                } else if (setWay(map, i, j + 1)) { //向右走
                    return true;
                } else if (setWay(map, i - 1, j)) { //向上走
                    return true;
                } else if (setWay(map, i, j - 1)){ //向左走
                    return true;
                } else {
                    // 说明该点走不通，是死路
                    map[i][j] = 3;
                    return false;
                }

            } else { //如果map[i][j]!=0，肯能是1，2，3
                return false;
            } // end of if else 2

        } // end of if else 1
    }

    /** 修改找路的策略 改成 上->右->下->左 */
    public static boolean setWay2(int[][] map, int i, int j) {
        // 说明通路以及找到
        if (map[6][5] == 2) {
            return true;
        } else {
            // 为0表示还没有走过的
            if (map[i][j] == 0) {
                // 按照策略 下->右->上->左 走
                // 先假定该点是可以走通的
                map[i][j] = 2;
                if (setWay2(map, i - 1, j)) { //先向上走
                    return true;
                } else if (setWay2(map, i, j + 1)) { //向右走
                    return true;
                } else if (setWay2(map, i + 1, j)) { //向下走
                    return true;
                } else if (setWay2(map, i, j - 1)){ //向左走
                    return true;
                } else {
                    // 说明该点走不通，是死路
                    map[i][j] = 3;
                    return false;
                }

            } else { //如果map[i][j]!=0，肯能是1，2，3
                return false;
            } // end of if else 2

        } // end of if else 1
    }

}
