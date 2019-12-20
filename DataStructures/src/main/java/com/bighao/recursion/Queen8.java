package com.bighao.recursion;

/**
 * @Author: bighao周启豪
 * @Date: 2019/12/20 17:24
 * @Version 1.0
 */
public class Queen8 {
    public static void main(String[] args) {

    }
    /** 定义一个max表示有多少个皇后 */
    int max = 8;
    /** 定义数组，保存皇后放置位置的结果 */
    int[] array = new int[max];

    /** 写一个方法，可以将皇后摆放的位置输出 */
    private void print() {
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }


}
