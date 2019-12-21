package com.bighao.sort;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

/**
 * @Author: bighao周启豪
 * @Date: 2019/12/21 15:28
 * @Version 1.0
 *
 * 选择排序
 * 逐步推导的方式，讲解选择排序
 *
 * 原始的数组 ： 101, 34, 119, 1
 * 第一轮排序 :   1, 34, 119, 101
 * 第二轮排序 :   1, 34, 119, 101
 * 第三轮排序 :   1, 34, 101, 119
 *
 * 说明：
 * 1. 选择排序一共有 数组大小 - 1 轮排序
 * 2. 每1轮排序，又是一个循环, 循环的规则(代码)
 * 2.1先假定当前这个数是最小数
 * 2.2 然后和后面的每个数进行比较，如果发现有比当前数更小的数，就重新确定最小数，并得到下标
 * 2.3 当遍历到数组的最后时，就得到本轮最小数和下标
 * 2.4 交换 [代码中再继续说 ]
 */
public class SelectSort {
    public static void main(String[] args) {
        //int[] arr = {101, 34, 119, 1, -1, -1, 90, 123};
        /* System.out.println("排序前===>");
        System.out.println(Arrays.toString(arr));*/

        // 测试选择排序的速度O(n^2)，给8万个数据测试
        // 创建8万个随机的数据
        int[] arr = new int[80000];
        for (int i = 0; i < 80000; i++) {
            arr[i] = (int)(Math.random() * 8000000); //生成[0,8000000)的数
        }

        Instant start = Instant.now();
        selectSort(arr);
        Instant end = Instant.now();
        System.out.println("排序时间" + Duration.between(start, end).getSeconds() + "s");
        /*System.out.println("排序后===>");
        System.out.println(Arrays.toString(arr));*/

    }


    /**
     * 选择排序 在下面推到的过程中，我们发现了规律，因此可以用for循环来解决
     * 选择排序的时间复杂是O(n^2)
     */
    public static void selectSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            int minIndex = i; //假定最小值的index
            int min = arr[i]; //最小值
            for (int j = i + 1; j < arr.length; j++) {
                // >是从小到大，<是从大到小
                if (min > arr[j]) {//说明我们假定的最小值并不是最小值,此时重置minIndex和min
                    min = arr[j]; //最小值
                    minIndex = j; //最小值索引
                }
            }
            // 将最小值，放在arr[0]，把arr[0]，即交换
            if (minIndex != i) {
                arr[minIndex] = arr[i];
                arr[i] = min;
            }
        }
    }



    /** 使用逐步推导的方式，讲解选择排序 */
    public static void selectSortDeduction(int[] arr) {
        // 使用逐步推导的方式，讲解选择排序
        // 第1轮
        // 原始的数组: 101, 34, 119, 1
        // 第一轮排序:  1, 34, 119, 101
        // 算法 先简单==》做复杂，就是把一个复杂的算法，拆分成简单的问题==》逐步解决

        // 第1轮 找到最小值
        int minIndex = 0; //假定最小值的index
        int min = arr[minIndex]; //最小值
        for (int j = 0 + 1; j < arr.length; j++) {
            if (min > arr[j]) {//说明我们假定的最小值并不是最小值,此时重置minIndex和min
                min = arr[j]; //最小值
                minIndex = j; //最小值索引
            }
        }

        // 将最小值，放在arr[0]，把arr[0]，即交换
        if (minIndex != 0) {
            arr[minIndex] = arr[0];
            arr[0] = min;
        }

        System.out.println("第1轮后===>");
        System.out.println(Arrays.toString(arr)); //[1, 34, 119, 101]

        // 第2轮 找到第二小值
        minIndex = 1;//最小值已经找到,此时找第二小的
        min = arr[1];
        for (int j = 1 + 1; j < arr.length; j++) {
            if (min > arr[j]) {//说明我们假定的最小值并不是最小值,此时重置minIndex和min
                min = arr[j]; //最小值
                minIndex = j; //最小值索引
            }
        }

        // 将最小值，放在arr[0]，把arr[0]，即交换
        if (minIndex != 1) {
            arr[minIndex] = arr[1];
            arr[1] = min;
        }

        System.out.println("第2轮后===>");
        System.out.println(Arrays.toString(arr)); //[1, 34, 119, 101]


        // 第2轮 找到第三小值
        minIndex = 2;
        min = arr[2];
        for (int j = 2 + 1; j < arr.length; j++) {
            if (min > arr[j]) {//说明我们假定的最小值并不是最小值,此时重置minIndex和min
                min = arr[j]; //最小值
                minIndex = j; //最小值索引
            }
        }

        // 将最小值，放在arr[0]，把arr[0]，即交换
        if (minIndex != 2) {
            arr[minIndex] = arr[2];
            arr[2] = min;
        }

        System.out.println("第3轮后===>");
        System.out.println(Arrays.toString(arr)); //[1, 34, 101, 119]
    }


}
