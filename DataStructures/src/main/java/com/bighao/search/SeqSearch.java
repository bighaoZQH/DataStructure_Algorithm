package com.bighao.search;

/**
 * @Author: bighao周启豪
 * @Date: 2019/12/23 11:40
 * @Version 1.0
 *
 * 线性查找算法
 */
public class SeqSearch {
    public static void main(String[] args) {
        int arr[] = {1, 9, 11, -1, 34, 89};//没有顺序的数组
        int i = seqSearch(arr, -1);
        System.out.println(i);
    }

    /** 这里实现的线性查找是找到一个满足条件的值就返回 */
    public static int seqSearch(int[] arr, int value) {
        // 线性查找是逐一比对，发现有相同值，就返回下标
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == value) {
                return i;
            }
        }
        return -1;
    }

}
