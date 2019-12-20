package com.bighao.recursion.practice;

/**
 * @Author: bighao周启豪
 * @Date: 2019/12/20 22:21
 * @Version 1.0
 */
public class Queen8 {
    public static void main(String[] args) {
        // 测试8皇后是否正确
        Queen8 queen8 = new Queen8();
        queen8.check(1);
        System.out.printf("一共有%d个解法", count);
        System.out.printf("一共判断冲突的次数%d次", judgeCount); //15720
    }

    /** 有多少种解法 */
    static int count = 0;
    /** 判断冲突次数 */
    static int judgeCount = 0;
    /** 最多放多少个皇后 */
    int max = 8;
    /** 定义数组，保存皇后放置位置的结果 比如 {0,4,7,5,2,6,1,3}*/
    int[] arr = new int[max];

    /**
     * 放置第n个皇后 特别注意：check是每一次递归时，进入到check中都有for循环,因此会有回溯
     * @param n 表示放入第n个皇后 这里和老师讲的不一样，老师是n从0开始的，但我觉得n从1开始更好理解
     */
    public void check(int n) {
        if (n == max + 1) { // 当n=9，表示放第9个皇后，就表示前面8个皇后已经放好,此时计算已经结束
            print();
            return;
        }
        // 放入第n个皇后的行号
        int row = n - 1;
        // 依次放入皇后
        for (int i = 0; i < max; i++) {
            // 先把当前这个皇后n，放到该行的第1列
            arr[row] = i;
            if (judge(n)) {
                // 没有冲突就开始递归下一个棋子
                check(n + 1);
            }
            // 如果有冲突，就继续执行for循环,array[n]=i,即将第n个皇后放置在本行的后移的一个位置
        }
    }

    /**
     * 判断当放入第n个皇后的时候与其之前放入的皇后，是否冲突
     * @param n 表示放入第n个皇后 这里和老师讲的不一样，老师是n从0开始的,但我觉得n从1开始更好理解
     */
    public boolean judge(int n) {
        judgeCount++;
        /** 放入第n个皇后所在的行号， n-1是因为第2个皇后的行号是1(行号从0开始) */
        int row = n - 1;
        /**
         * 1.判断第n个皇后与之前放置的皇后是否在同一列上
         * 2.判断在同一斜线上是否有冲突 行差 = 列差的绝对值， 之所以是绝对值 是因为斜线会有两边的斜线
         */
        for (int i = 0; i < row; i++) {
            // 有冲突
            if (arr[i] == arr[row] || (row - i) == Math.abs(arr[row] - arr[i])) {
                return false;
            }
        }
        return true;
    }


    public void print() {
        count++;
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

}
