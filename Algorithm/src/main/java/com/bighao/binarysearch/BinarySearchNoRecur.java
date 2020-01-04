package com.bighao.binarysearch;

/**
 * @Author: bighao周启豪
 * @Date: 2019/12/31 17:00
 * @Version 1.0
 *
 * 二分查找的非递归实现
 */
public class BinarySearchNoRecur {
    public static void main(String[] args) {
        int[] arr = {1, 3, 8, 10, 11, 67, 100};
        int i = binarySearch(arr, 8);
        System.out.println(i);
    }

    /** 二分查找的非递归实现 */
    public static int binarySearch(int[] arr, int target) {
        int left = 0;
        int right = arr.length - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (arr[mid] == target) {
                return mid;
            } else if (arr[mid] > target) {
                right = mid - 1;//向左查找
            } else {
                left = mid + 1;//向右查找
            }
        }
        return -1;
    }
}


