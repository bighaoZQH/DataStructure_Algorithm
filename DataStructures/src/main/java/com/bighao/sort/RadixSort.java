package com.bighao.sort;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

/**
 * @Author: bighao周启豪
 * @Date: 2019/12/23 10:10
 * @Version 1.0
 *
 * 基数排序(空间消耗较大)
 *
 * 基数排序(radix sort)属于"分配式排序"(distribution sort)，
 * 又称"桶子法"(bucket sort)或bin sort，顾名思义，它是透过键值的部份资讯，
 * 将要排序的元素分配至某些"桶"中，藉以达到排序的作用，基数排序法是属于稳定性的排序，
 * 其时间复杂度为O (nlog(r)m)，其中r为所采取的基数，而m为堆数，在某些时候，基数排序法的效率高于其它的稳定性排序法。
 *
 * 往往有0和负数的数组一般我们都不用基数来进行排序
 *
 * 将所有待比较数值统一为同样的数位长度，数位较短的数前面补零。
 * 然后，从最低位开始，依次进行一次排序。这样从最低位排序一直到最高位排序完成以后, 数列就变成一个有序序列。
 *
 * 基数排序的要点就两个：
 * 分配：按照元素的大小来放入不同的桶子里
 * 回收：将桶子里的元素按桶子顺序重新放到数组中
 * 重复.....两个步骤
 *
 *
 * 基数排序的说明:
 *  1.基数排序是对传统桶排序的扩展，速度很快.
 *  2.基数排序是经典的空间换时间的方式，占用内存很大, 当对海量数据排序时，容易造成 OutOfMemoryError 。
 *  3.基数排序时稳定的。[注:假定在待排序的记录序列中，存在多个具有相同的关键字的记录，若经过排序，
 *      这些记录的相对次序保持不变，即在原序列中，r[i]=r[j]，且r[i]在r[j]之前，而在排序后的序列中，
 *      r[i]仍在r[j]之前，则称这种排序算法是稳定的；否则称为不稳定的]
 *  4.有负数的数组，我们不用基数排序来进行排序, 如果要支持负数，参考: https://code.i-harness.com/zh-CN/q/e98fa9
 */
public class RadixSort {
    public static void main(String[] args) {
        /*int[] arr = {53, 3, 542, 748, 14, 214};
        radixSort(arr);*/

        // 80000000 * 11 * 4 / 1024 / 1024 / 1024 =? 3.3G
        int[] arr = new int[80000000];
        for (int i = 0; i < 80000000; i++) {
            arr[i] = (int)(Math.random() * 800000000); //生成[0,8000000)的数
        }

        Instant start = Instant.now();
        radixSort(arr);
        Instant end = Instant.now();
        System.out.println("排序时间" + Duration.between(start, end).getNano() + "s");

        //System.out.println("arr=" + Arrays.toString(arr));
    }

    /** 基数排序 根据下面的推导得出 */
    public static void radixSort(int[] arr) {
        // 1.得到数组中最大的位数
        int max = arr[0]; // 假设第一数就是最大数
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }
        // 得到最大数是几位数
        int maxLength = ("" + max).length();

        /**
         * 定义一个二维数组，表示10个桶，每个桶就是一个一维数组
         * 1.二维数组包含10个一维数组
         * 2.为了防止在数的时候，数据溢出，则每个一维数组的长度为arr.length
         * 3.很明显，基数排序是空间换时间的经典算法
         */
        int[][] bucket = new int[10][arr.length];
        // 为了记录每个桶中实际存放了多少个数据，我们定义一个一维数组来记录每个桶的维护的数据个数
        // bucketElementCounts[0],记录的就是bucket[0]这个桶放入的数据的个数
        int[] bucketElementCounts = new int[10];

        // 这里我们试用循环将代码处理
        for (int i = 0, n = 1; i < maxLength; i++, n *= 10) {
            // 每一轮针对每个元素对应的位进行排序，第一次是个位,第二次是十位,以此类推
            for (int j = 0; j < arr.length; j++) {
                // 取出每个元素的对应位的值
                int digitOfElement = arr[j] / n % 10;
                // 放入到对应的桶中 digitOfElement--桶编号 bucketElementCounts[digitOfElement] -- 这个桶维护了多少个数据
                bucket[digitOfElement][bucketElementCounts[digitOfElement]] = arr[j];
                bucketElementCounts[digitOfElement]++;
            }

            // 按照这个桶的顺序(一维数组的下标依次取出数据，放入原来数组)
            int index = 0;
            // 遍历每一个桶，并将桶中的数据放入到原数组
            for (int k = 0; k < bucket.length; k++) {
                // 如果桶中有数据，我们才放入到原数组中
                if (bucketElementCounts[k] != 0) {
                    // 循环该桶即第k个桶(即第k个一维数组), 放入数据
                    for (int l = 0; l < bucketElementCounts[k]; l++) {
                        // 取出元素，放入到arr中
                        arr[index++] = bucket[k][l];
                    }
                }
                // 第i+1轮处理后，需要将每个bucketElementCounts[k] = 0 !!!
                bucketElementCounts[k] = 0;
            }
            //System.out.println("第" + (i+1) + "轮，对个位数的排序处理" + Arrays.toString(arr));
        }

    }


    /** 基数排序 推导过程*/
    public static void radixSortDeduction(int[] arr) {
        // 第1轮排序(针对每个元素的个位数进行处理)

        /**
         * 定义一个二维数组，表示10个桶，每个桶就是一个一维数组
         * 1.二维数组包含10个一维数组
         * 2.为了防止在数的时候，数据溢出，则每个一维数组的长度为arr.length
         * 3.很明显，基数排序是空间换时间的经典算法
         */
        int[][] bucket = new int[10][arr.length];
        // 为了记录每个桶中实际存放了多少个数据，我们定义一个一维数组来记录每个桶的维护的数据个数
        // bucketElementCounts[0],记录的就是bucket[0]这个桶放入的数据的个数
        int[] bucketElementCounts = new int[10];

        // 第1轮(针对每个元素的个位进行排序处理)
        for (int j = 0; j < arr.length; j++) {
            // 取出每个元素的个位
            int digitOfElement = arr[j] / 1 % 10;
            // 放入到对应的桶中 digitOfElement--桶编号 bucketElementCounts[digitOfElement] -- 这个桶维护了多少个数据
            bucket[digitOfElement][bucketElementCounts[digitOfElement]] = arr[j];
            bucketElementCounts[digitOfElement]++;
        }

        // 按照这个桶的顺序(一维数组的下标依次取出数据，放入原来数组)
        int index = 0;
        // 遍历每一个桶，并将桶中的数据放入到原数组
        for (int k = 0; k < bucket.length; k++) {
            // 如果桶中有数据，我们才放入到原数组中
            if (bucketElementCounts[k] != 0) {
                // 循环该桶即第k个桶(即第k个一维数组), 放入数据
                for (int l = 0; l < bucketElementCounts[k]; l++) {
                    // 取出元素，放入到arr中
                    arr[index++] = bucket[k][l];
                }
            }
            // 第1轮处理后，需要将每个bucketElementCounts[k] = 0 !!!
            bucketElementCounts[k] = 0;

        }
        System.out.println("第1轮，对个位数的排序处理" + Arrays.toString(arr));



        // 第2轮排序(针对每个元素的十位数进行处理)
        for (int j = 0; j < arr.length; j++) {
            // 取出每个元素的十位
            int digitOfElement = arr[j] / 10 % 10; // 748/10=74 74%10=4
            // 放入到对应的桶中 digitOfElement--桶编号 bucketElementCounts[digitOfElement] -- 这个桶维护了多少个数据
            bucket[digitOfElement][bucketElementCounts[digitOfElement]] = arr[j];
            bucketElementCounts[digitOfElement]++;
        }

        // 按照这个桶的顺序(一维数组的下标依次取出数据，放入原来数组)
        index = 0;
        // 遍历每一个桶，并将桶中的数据放入到原数组
        for (int k = 0; k < bucket.length; k++) {
            // 如果桶中有数据，我们才放入到原数组中
            if (bucketElementCounts[k] != 0) {
                // 循环该桶即第k个桶(即第k个一维数组), 放入数据
                for (int l = 0; l < bucketElementCounts[k]; l++) {
                    // 取出元素，放入到arr中
                    arr[index++] = bucket[k][l];
                }
            }
            // 第2轮处理后，需要将每个bucketElementCounts[k] = 0 !!!
            bucketElementCounts[k] = 0;
        }

        System.out.println("第2轮，对个位数的排序处理" + Arrays.toString(arr));


        // 第3轮排序(针对每个元素的百位数进行处理)
        for (int j = 0; j < arr.length; j++) {
            // 取出每个元素的百位
            int digitOfElement = arr[j] / 100 % 10; // 748/100=7 7%10=7
            // 放入到对应的桶中 digitOfElement--桶编号 bucketElementCounts[digitOfElement] -- 这个桶维护了多少个数据
            bucket[digitOfElement][bucketElementCounts[digitOfElement]] = arr[j];
            bucketElementCounts[digitOfElement]++;
        }

        // 按照这个桶的顺序(一维数组的下标依次取出数据，放入原来数组)
        index = 0;
        // 遍历每一个桶，并将桶中的数据放入到原数组
        for (int k = 0; k < bucket.length; k++) {
            // 如果桶中有数据，我们才放入到原数组中
            if (bucketElementCounts[k] != 0) {
                // 循环该桶即第k个桶(即第k个一维数组), 放入数据
                for (int l = 0; l < bucketElementCounts[k]; l++) {
                    // 取出元素，放入到arr中
                    arr[index++] = bucket[k][l];
                }
            }
            // 第3轮处理后，需要将每个bucketElementCounts[k] = 0 !!!
            bucketElementCounts[k] = 0;
        }

        System.out.println("第3轮，对个位数的排序处理" + Arrays.toString(arr));

    }



}
