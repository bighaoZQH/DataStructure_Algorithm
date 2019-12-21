package com.bighao.sort;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

/**
 * @Author: bighao周启豪
 * @Date: 2019/12/21 0:39
 * @Version 1.0
 *
 * 冒泡排序
 *
 * 原始数组：3, 9, -1, 10, 20
 *
 * 第一趟排序
 * (1)  3, 9, -1, 10, 20   // 如果相邻的元素逆序就交换
 * (2)  3, -1, 9, 10, 20
 * (3)  3, -1, 9, 10, 20
 * (4)  3, -1, 9, 10, 20
 *
 * 第二趟排序
 * (1) -1, 3, 9, 10, 20 //交换
 * (2) -1, 3, 9, 10, 20
 * (3) -1, 3, 9, 10, 20
 *
 * 第三趟排序
 * (1) -1, 3, 9, 10, 20
 * (2) -1, 3, 9, 10, 20
 *
 * 第四趟排序
 * (1) -1, 3, 9, 10, 20
 *
 * 小结冒泡排序规则
 * (1) 一共进行 数组的大小-1 次 大的循环
 * (2) 每一趟排序的次数在逐渐的减少
 * (3) 如果我们发现在某趟排序中，没有发生一次交换， 可以提前结束冒泡排序。这个就是优化
 *
 * 为了容易理解，把冒泡排序的演变过程在下面展示了
 *
 *
 */
public class BubbleSort {
    public static void main(String[] args) {
        //int arr[] = {3, 9, -1, 10, -2};
        /*int arr[] = {3, 9, -1, 10, 20};
        System.out.println("排序前的数据======>");
        System.out.println(Arrays.toString(arr));*/

        // 测试冒泡排序的速度O(n^2)，给8万个数据测试
        // 创建8万个随机的数据
        int[] arr = new int[80000];
        for (int i = 0; i < 80000; i++) {
            arr[i] = (int)(Math.random() * 8000000); //生成[0,8000000)的数
        }

        // 测试冒泡排序以及时间
        Instant start = Instant.now();
        bubbleSort(arr);
        Instant end = Instant.now();
        System.out.println("排序时间" + Duration.between(start, end).getSeconds() + "s");
        //System.out.println("排序后的数据======>");
        //System.out.println(Arrays.toString(arr));


        /**
         * 说明 为了容易理解，把冒泡排序的演变过程展示
         */
        // 第一次排序，就是将最大的值排在最后
        /*int temp = 0;
        for (int j = 0; j < arr.length - 1 - 0; j++) {
            // 如果前面的数比后面的数大，就交换
            if (arr[j] > arr[j + 1]) {
                temp = arr[j];
                arr[j] = arr[j + 1];
                arr[j + 1] = temp;
            }
        }
        System.out.println("第一次排序后的数据======>");
        System.out.println(Arrays.toString(arr));


        // 第二次排序，就是把第二大的数排在倒数第二位
        for (int j = 0; j < arr.length - 1 - 1; j++) {
            // 如果前面的数比后面的数大，就交换
            if (arr[j] > arr[j + 1]) {
                temp = arr[j];
                arr[j] = arr[j + 1];
                arr[j + 1] = temp;
            }
        }
        System.out.println("第二次排序后的数据======>");
        System.out.println(Arrays.toString(arr));

        // 第三次排序，就是把第三大的数排在倒数第三位
        for (int j = 0; j < arr.length - 1 - 2; j++) {
            // 如果前面的数比后面的数大，就交换
            if (arr[j] > arr[j + 1]) {
                temp = arr[j];
                arr[j] = arr[j + 1];
                arr[j + 1] = temp;
            }
        }
        System.out.println("第三次排序后的数据======>");
        System.out.println(Arrays.toString(arr));

        // 第四次排序，就是把第四大的数排在倒数第四位
        for (int j = 0; j < arr.length - 1 - 3; j++) {
            // 如果前面的数比后面的数大，就交换
            if (arr[j] > arr[j + 1]) {
                temp = arr[j];
                arr[j] = arr[j + 1];
                arr[j + 1] = temp;
            }
        }
        System.out.println("第四次排序后的数据======>");
        System.out.println(Arrays.toString(arr));*/
    }

    /**
     * 根据上面的推到，得出以下的冒泡排序 时间复杂度是O(n^2) 因为是双层for循环
     */
    public static void bubbleSort(int[] arr) {
        int temp = 0;
        boolean flag = false; //标识表示是否进行过交换,如果没有进行过，就说明已经排好序了，后面就不要再执行了
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++) {
                // <从大到小， >从小到大
                // 如果前面的数比后面的数大，就交换
                if (arr[j] < arr[j + 1]) {
                    flag = true;
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
            if(!flag) { //说明一次交换都没有发生过,说明已然排好序
                break;
            } else {
                // 重置flag进行下次判断
                flag = false;
            }
        }
    }


}
