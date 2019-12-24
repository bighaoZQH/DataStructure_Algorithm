package com.bighao.search;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @Author: bighao周启豪
 * @Date: 2019/12/23 13:28
 * @Version 1.0
 *
 * 二分查找
 * 使用二分查找的前提是该数组是有序的
 */
public class BinarySearch {
    public static void main(String[] args) {
        int arr[] = {1, 8, 10, 89, 1000, 1000, 1000, 1000, 1234};
        /*int i = binarySearch(arr, 0, arr.length, 88);
        System.out.println(i);*/
        ArrayList<Integer> result = binarySearch2(arr, 0, arr.length, 1000);
        System.out.println(Arrays.toString(result.toArray()));

    }

    /**
     * 二分查找，找到多个重复的值
     * 思路分析:
     *  1.在找到mid值时，不要马上返回
     *  2.向mid 索引值的 左边 扫描，将所有满足查找值的元素的下标，加入到集合中ArrayList
     *  3.向mid 索引值的 右边 扫描，将所有满足查找值的元素的下标，加入到集合中ArrayList
     *  4.返回ArrayList
     */
    public static ArrayList<Integer> binarySearch2(int[] arr, int left, int right, int findVal) {
        // 当left > right时，说明递归完毕，但是没有找到
        if (left > right) {
            return new ArrayList<Integer>();
        }
        int mid = (left + right) / 2;
        int midVal = arr[mid];

        if (findVal > midVal) {
            // 向右递归
            return binarySearch2(arr, mid + 1, right, findVal);
        } else if (findVal < midVal) {
            // 向左递归
            return binarySearch2(arr, left, mid - 1, findVal);
        } else {
            ArrayList<Integer> resIndexList = new ArrayList<>();
            // 向mid 索引值的 左边 扫描，将所有满足查找值的元素的下标，加入到集合中ArrayList
            int temp = mid - 1;
            while (true) {
                if (temp < 0 || arr[temp] != findVal) {
                    break;
                }
                // 否则，将temp放入到集合中
                resIndexList.add(temp);
                temp -= 1; //temp左移
            }
            resIndexList.add(mid);

            // 向mid 索引值的 右边 扫描，将所有满足查找值的元素的下标，加入到集合中ArrayList
            temp = mid + 1;
            while (true) {
                if (temp > arr.length - 1 || arr[temp] != findVal) {
                    break;
                }
                // 否则，将temp放入到集合中
                resIndexList.add(temp);
                temp += 1; //temp右移
            }
            return resIndexList;
        }
    }


    /**
     * 二分查找 但无法找到多个重复的
     * @param arr
     * @param left 左边的索引
     * @param right 右边的索引
     * @param findVal 要查找的值
     * @return 如果找到就返回下标，没找到就返回-1
     */
    public static int binarySearch(int[] arr, int left, int right, int findVal) {
        // 当left > right时，说明递归完毕，但是没有找到
        if (left > right) {
            return -1;
        }
        int mid = (left + right) / 2;
        int midVal = arr[mid];

        if (findVal > midVal) {
            // 向右递归
            return binarySearch(arr, mid + 1, right, findVal);
        } else if (findVal < midVal) {
            // 向左递归
            return binarySearch(arr, left, mid - 1, findVal);
        } else {
            return mid;
        }
    }


}
