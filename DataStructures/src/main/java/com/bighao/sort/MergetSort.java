package com.bighao.sort;

import java.time.Duration;
import java.time.Instant;

/**
 * @Author: bighao周启豪
 * @Date: 2019/12/22 20:09
 * @Version 1.0
 *
 * 归并排序
 */
public class MergetSort {
    public static void main(String[] args) {
        //int[] arr = {8, 4, 5, 7, 1, 3, 6, 2}; //8->merge 7次 80000->merge 80000-1 而冒泡是O(n^2) 80000*80000
        //int[] temp = new int[arr.length]; // 归并排序需要额外空间

        int[] arr = new int[8000000];
        for (int i = 0; i < 8000000; i++) {
            arr[i] = (int)(Math.random() * 8000000); //生成[0,8000000)的数
        }
        int[] temp = new int[8000000];

        Instant start = Instant.now();
        mergeSort(arr, 0, arr.length-1, temp);
        Instant end = Instant.now();
        System.out.println("排序时间" + Duration.between(start, end).getSeconds() + "s");

        //System.out.println("归并排序后" + Arrays.toString(arr));
    }

    /** 分+合的方法 */
    public static void mergeSort(int[] arr, int left, int right, int[] temp) {
        if (left < right) {
            int mid = (left + right) / 2; //中间的索引
            // 向左递归进行分解
            mergeSort(arr, left, mid, temp);
            // 向右递归进行分解
            mergeSort(arr, mid + 1, right, temp);
            // 到合并
            merge(arr, left, mid, right, temp);
        }


    }

    /**
     * 合并的方法
     * @param arr 待排序的数组
     * @param left 左边有序序列的初始索引
     * @param mid 中间索引
     * @param right 右边索引
     * @param temp 做中转的数组
     */
    public static void merge(int[] arr, int left, int mid, int right, int[] temp) {
        int i = left; // 初始化i,表示左边有序序列的初始索引
        int j = mid + 1; // 初始化j,表示右边有序序列的初始索引
        int t = 0; // 指向temp数组的当前索引

        // 1.先把左右两边(有序)的数据按照规则填充到temp数组中,直到左右两边的有序序列，有一边处理完毕为止
        while (i <= mid && j<= right) {
            // 如果左边的有序序列的当前元素 <= 右边有序序列的当前元素
            // 即将左边的当前元素，拷贝到temp数组中
            // 然后t和i后移
            if (arr[i] <= arr[j]) {
                temp[t] = arr[i];
                t++;
                i++;
            } else {
                // 若右边有序序列的当前元素 < 左边的有序序列的当前元素，就将右边的当前元素，拷贝到temp数组中
                temp[t] = arr[j];
                t++;
                j++;
            }
        }
        // 2.把有剩余数据的一边的组，依次填充到temp中
        while (i <= mid) { //i <= mid说明左边的有序序列还有剩余的元素,就全部依次填充到temp
            temp[t] = arr[i];
            t++;
            i++;
        }

        while (j <= right) { //j <= right说明右边的有序序列还有剩余的元素,就全部依次填充到temp
            temp[t] = arr[j];
            t++;
            j++;
        }

        // 3.将temp数组的元素拷贝到arr 但注意并不是每次都拷贝所有
        t = 0;
        int tempLeft = left; //第一次合并时tempLeft=0, right=1 //第二次合并 tempLeft=2 right=3 //第三次合并 tempLeft=0 right=3
        // 最后一次tempLeft=0, right=length-1
        //System.out.println("tempLeft=" + tempLeft + " right" + right);
        while (tempLeft <= right) {
            arr[tempLeft] = temp[t];
            t++;
            tempLeft++;
        }

    }

}
