package com.bighao.recursion;

/**
 * @Author: bighao周启豪
 * @Date: 2019/12/20 17:24
 * @Version 1.0
 *
 * 回溯算法解决8皇后问题
 *
 * 皇后问题算法思路分析
 *  第一个皇后先放第一行第一列
 *  第二个皇后放在第二行第一列、然后判断是否OK， 如果不OK，继续放在第二列、第三列、依次把所有列都放完，找到一个合适
 *  继续第三个皇后，还是第一列、第二列……直到第8个皇后也能放在一个不冲突的位置，算是找到了一个正确解
 *  当得到一个正确解时，在栈回退到上一个栈时，就会开始回溯，即将第一个皇后，放到第一列的所有正确解，全部得到.
 *  然后回头继续第一个皇后放第二列，后面继续循环执行 1,2,3,4的步骤 【示意图】
 *
 * 说明：理论上应该创建一个二维数组来表示棋盘，但是实际上可以通过算法，用一个一维数组即可解决问题.
 * arr[8] = {0 , 4, 7, 5, 2, 6, 1, 3} //对应arr 下标 表示第几行，即第几个皇后，arr[i] = val ,
 * val 表示第i+1个皇后，放在第i+1行的第val+1列
 */
public class Queen8 {
    public static void main(String[] args) {
        // 测试8皇后是否正确
        Queen8 queen8 = new Queen8();
        queen8.check(0);
        System.out.printf("一共有%d个解法", count);
        System.out.printf("一共判断冲突的次数%d次", judgeCount); //15720
    }
    /** 解法数 */
    static int count = 0;
    /** 判断冲突次数 */
    static int judgeCount = 0;
    /** 定义一个max表示有多少个皇后 */
    int max = 8;
    /** 定义数组，保存皇后放置位置的结果 比如 {0,4,7,5,2,6,1,3}*/
    int[] array = new int[max];

    /**
     * 放置第n个皇后 特别注意：check是每一次递归时，进入到check中都有for循环,因此会有回溯
     * @param n 表示第n+1皇后(注意n从0开始,n=1表示第二个皇后!!) 我在practice包下写了一个n从1开始的,觉得更好理解
     */
    private void check(int n) {
        if (n == max) { // n=8的时候，代表放第9个皇后,就表示8个皇后以及放好
            print();
            return;
        }
        // 依次放入皇后，并判断是否冲突
        for (int i = 0; i < max; i++) {
            // 先把当前这个皇后n，放到该行的第1列
            array[n] = i;
            // 判断当放置第n个皇后到i列时，是否冲突
            if (judge(n)) {// 不冲突
                // 接着放n+1个皇后(没有冲突就开始递归下一个棋子)
                check(n + 1);
            }
            // 如果有冲突，就继续执行for循环,array[n]=i,即将第n个皇后放置在本行的后移的一个位置
        }
    }

    /**
     * 查看当我们放置第n个皇后，就去检测该皇后是否和前面已经摆放的皇后是否冲突
     * @param n 表示第n+1皇后 (注意n从0开始,n=1表示第二个皇后!!)
     */
    private boolean judge(int n) {
        judgeCount++;
        for (int i = 0; i < n; i++) {
            // 1.array[i] == array[n]表示判断第n个皇后是否和前面的n-1个皇后在同一列
            // 2.Math.abs(n-i) == Math.abs(array[n] - array[i]) 表示判断第n个皇后是否和第i个皇后是否在同一斜线
            // n = 1 放在第2列1 n = 1 array[1] = 1
            // Math.abs(1-0) == 1 Math.abs(array[n] - array[i]) = Math.abs(1-0) = 1 这个需要画图加标上坐标来理解
            // 3.没有必要判断是否在同一行,n每次都在递增
            //if (array[i] == array[n] || Math.abs(n-i) == Math.abs(array[n] - array[i])) {
            if (array[i] == array[n] || (n - i) == Math.abs(array[n] - array[i])) {
                return false;
            }
        }
        return true;
    }

    /** 将皇后摆放的位置打印 */
    private void print() {
        count++;
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }


}
