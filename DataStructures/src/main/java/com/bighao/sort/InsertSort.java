package com.bighao.sort;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

/**
 * @Author: bighao周启豪
 * @Date: 2019/12/21 22:07
 * @Version 1.0
 *
 * 插入排序
 * 一句话简单理解，摸排
 *
 * 插入式排序属于内部排序法，是对于欲排序的元素以插入的方式找寻该元素的适当位置，以达到排序的目的。
 *
 * 插入排序（Insertion Sorting）的基本思想是：把n个待排序的元素看成为一个有序表和一个无序表，
 * 开始时有序表中只包含一个元素，无序表中包含有n-1个元素，排序过程中每次从无序表中取出第一个元素，
 * 把它的排序码依次与有序表元素的排序码进行比较，将它插入到有序表中的适当位置，使之成为新的有序表。
 */
public class InsertSort {
    public static void main(String[] args) {
        //int[] arr = {101, 34, 119, 1, -1, 89};
        // 测试插入排序的速度O(n^2)，给8万个数据测试
        // 创建8万个随机的数据
        int[] arr = new int[80000];
        for (int i = 0; i < 80000; i++) {
            arr[i] = (int)(Math.random() * 8000000); //生成[0,8000000)的数
        }

        Instant start = Instant.now();
        insertSort(arr);
        Instant end = Instant.now();
        System.out.println("排序时间" + Duration.between(start, end).getSeconds() + "s");
        //System.out.println(Arrays.toString(arr));
    }

    /**
     * 使用for循环来简化代码
     */
    public static void insertSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int insertVal = arr[i];// 待插入的数
            int insertIndex = i - 1; //即待插入数前面一个数的下标
            // insertVal < arr[insertIndex] 是从小到大牌 >是从大到小
            while (insertIndex >= 0 && insertVal > arr[insertIndex]) {
                // 若待插入的数比它前面一个数小，就把前面的数的值赋给待插入的数的下标(即把大的数往后移动)
                arr[insertIndex + 1] = arr[insertIndex];
                // 然后再往前走，拿待插入的数和再往前前面的数去比较
                insertIndex--;
            }
            // 当退出while循环时，待说明插入数的插入的位置找到，是insertIndex + 1
            // 判断是否需要重新赋值,也就是上面while循环没有进去的时候，insertIndex没有被--,那就不用重新赋值了,就不进下面的if
            // 因为insertIndex + 1 != i表示不需要进行交换,此时待插入数要插入的位置就是他自己目前的位置
            if (insertIndex + 1 != i) {
                arr[insertIndex + 1] = insertVal;
            }
        }
    }

    /** 插入排序 逐步推到的方式来讲解，便于理解*/
    public static void insertSortDeduction(int[] arr) {
        // 逐步推到的方式

        // 第1轮 给第2个数找位置 {101, 34, 119, 1} => {34, 101, 119, 1}
        int insertVal = arr[1];// 待插入的数
        int insertIndex = 1 - 1; //即待插入数前面一个数的下标
        // 给insertVal找到插入的位置
        // 1.insertIndex >= 0是用来保证给insertVal找到插入的位置时不越界
        // 2.insertVal < arr[insertIndex] 待插入的数是否小于它前面的值
        // 3.就需要将arr[insertIndex] 后移
        while (insertIndex >= 0 && insertVal < arr[insertIndex]) {
            // 若待插入的数比它前面一个数小，就把前面的数的值赋给待插入的数的下标(即把大的数往后移动)
            arr[insertIndex + 1] = arr[insertIndex];//{101, 34, 119, 1} => {101, 101, 119, 1}
            // 然后再往前走，拿带插入的数和再前面的数去比较
            insertIndex--;
        }
        // 当退出while循环时，说明插入的位置找到，是insertIndex + 1
        arr[insertIndex + 1] = insertVal;

        System.out.println("第1轮插入后===>");
        System.out.println(Arrays.toString(arr));

        // 第2轮 给第三个数找位置
        insertVal = arr[2];
        insertIndex = 2 - 1;
        while (insertIndex >= 0 && insertVal < arr[insertIndex]) {
            arr[insertIndex + 1] = arr[insertIndex];
            insertIndex--;
        }
        arr[insertIndex + 1] = insertVal;

        System.out.println("第2轮插入后===>");
        System.out.println(Arrays.toString(arr));

        // 第3轮 给第四个数找位置
        insertVal = arr[3];
        insertIndex = 3 - 1;
        while (insertIndex >= 0 && insertVal < arr[insertIndex]) {
            arr[insertIndex + 1] = arr[insertIndex];
            insertIndex--;
        }
        arr[insertIndex + 1] = insertVal;

        System.out.println("第3轮插入后===>");
        System.out.println(Arrays.toString(arr));

    }

}
