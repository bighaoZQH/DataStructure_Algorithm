package com.bighao.search;

/**
 * @Author: bighao周启豪
 * @Date: 2019/12/23 15:13
 * @Version 1.0
 *
 * 插值查找算法
 *
 * 插值查找注意事项：
 * 对于数据量较大，关键字分布比较均匀的查找表来说，采用插值查找, 速度较快.
 * 关键字分布不均匀的情况下，该方法不一定比折半查找要好
 */
public class InsertValueSearch {
    public static void main(String[] args) {
        int[] arr = new int[100];
        for (int i = 0; i < 100; i++) {
            arr[i] = i + 1;
        }

        int i = insertValueSearch(arr, 0, arr.length - 1, 1);
        System.out.println(i);
    }

    /**
     * 编写插值查找算法
     * @param arr
     * @param left 左边索引
     * @param right 右边索引
     * @param findVal 查找的值
     * @return 如果找到就返回对应的下标，如果没找到返回-1
     */
    public static int insertValueSearch(int[] arr, int left, int right, int findVal) {
        System.out.println("进行查找===>");
        // 因为是顺序数组，所以当findVal小于最小值或者大于最大值时，就可以直接退出了
        if (left > right || findVal < arr[0] || findVal > arr[right]) {
            return -1;
        }
        // 求出mid
        int mid = left + (right - left) * (findVal - arr[left]) / (arr[right] - arr[left]);
        int midVal = arr[mid];
        if (findVal > midVal) {
            // 说明应该向右递归查找
            return insertValueSearch(arr, mid + 1, right, findVal);
        } else if (findVal < midVal) {
            // 说明应该向左递归查找
            return insertValueSearch(arr, left, mid - 1, findVal);
        } else {
            return mid;
        }

    }

}
