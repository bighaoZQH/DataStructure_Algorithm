package com.bighao.sort;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

/**
 * @Author: bighao周启豪
 * @Date: 2019/12/21 23:16
 * @Version 1.0
 *
 * 希尔排序
 *
 * 简单插入排序存在的问题
 * 我们看简单的插入排序可能存在的问题.
 * 数组 arr = {2,3,4,5,6,1} 这时需要插入的数 1(最小), 这样的过程是：
 * {2,3,4,5,6,6}
 * {2,3,4,5,5,6}
 * {2,3,4,4,5,6}
 * {2,3,3,4,5,6}
 * {2,2,3,4,5,6}
 * {1,2,3,4,5,6}
 * 结论: 当需要插入的数是较小的数时，后移的次数明显增多，对效率有影响
 *
 * 希尔排序是希尔（Donald Shell）于1959年提出的一种排序算法。
 * 希尔排序也是一种插入排序，它是简单插入排序经过改进之后的一个【更高效的版本】，也称为缩小增量排序。
 *
 * 希尔排序法基本思想
 * 希尔排序是把记录按下标的一定增量分组，对每组使用直接插入排序算法排序；
 * 随着增量逐渐减少，每组包含的关键词越来越多，当增量减至1时，整个文件恰被分成一组，算法便终止
 *
 * 希尔排序分组后，每组只有有两个元素
 */
public class ShellSort {
    public static void main(String[] args) {
        //int[] arr = {8, 9, 1, 7, 2, 3, 5, 4, 6, 0};

        // 测试希尔排序的速度
        int[] arr = new int[8000000];
        for (int i = 0; i < 8000000; i++) {
            arr[i] = (int)(Math.random() * 8000000); //生成[0,8000000)的数
        }

        Instant start = Instant.now();
        //shellSort(arr);//交换式
        shellSort2(arr);//移位式
        Instant end = Instant.now();
        System.out.println("排序时间" + Duration.between(start, end).getSeconds() + "s");
        System.out.println(Arrays.toString(arr));
    }

    /** 改进交换式的希尔排序 移位式 */
    public static void shellSort2(int[] arr) {
        for (int gap = arr.length / 2; gap > 0; gap /= 2) {
            // 从第gap个元素，开始逐个对其所在的组进行直接插入
            for (int i = gap; i < arr.length; i++) {
                // 先判断当前组内的两个元素在比较是否要进行交换,不交换就直接进行下一组的判断
                if (arr[i] < arr[i - gap]) {
                    // 如果组内后面位置的数小于前面位置的数，就需要进行插入交换
                    int j = i; //记录当前待插入的值的下标
                    int temp = arr[j]; //记录当前待插入的值
                    while (j - gap >= 0 && temp < arr[j - gap]) {
                        // 把前面大的值插入到同组小的值的位置 arr[j-gap]是同组前面大的值
                        arr[j] = arr[j - gap];
                        j -= gap;
                    }
                    // 当退出while后，就给temp找到插入的位置,此时的j是同组前面的位置
                    arr[j] = temp;
                }
            }
        }
    }

    /**
     * 希尔排序 移位法 另一种写法
     */
    public static void shellSort3(int[] arr) {
        int temp;
        int gap = arr.length;
        while (true) {
            gap /= 2;
            // 确定分组数
            for (int i = 0; i < gap; i++) {
                // 对分组数据进行直接插入排序
                for (int j = i + gap; j < arr.length; j = j + gap) {
                    temp = arr[j];
                    int k;
                    for (k = j - gap; k >= 0; k = k - gap) {
                        if (arr[k] > temp) {
                            arr[k + gap] = arr[k];
                        } else {
                            break;
                        }
                    }
                    arr[k + gap] = temp;
                }
            }
            // 当不能再分组时，退出循环
            if (gap == 1) {
                break;
            }
        }
    }

    /** 交换式的希尔排序 根据下面的推到过程，我们试用循环处理 */
    public static void shellSort(int[] arr) {
        //int count = 0;
        int temp = 0;
        // 分组
        for (int gap = arr.length / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < arr.length; i++) {
                // 遍历各组中所有的元素(共gap组，每组有n个元素)，步长gap
                for (int j = i - gap; j >= 0; j -= gap) {
                    // 如果当前这个元素>加上步长后的那个元素,说明需要交换
                    if (arr[j] > arr[j + gap]) {
                        temp = arr[j];
                        arr[j] = arr[j + gap];
                        arr[j + gap] = temp;
                    }
                }
            }
            //System.out.println("希尔排序第" + (++count) + "轮后===>" + Arrays.toString(arr));
        }
    }

    /** 逐步推导希尔排序 交换式 */
    public static void shellSortDeduction(int[] arr) {
        // 第1轮
        // 因为第1轮排序，是将10个数据分成了 10/2=5组 每组它只会有两个元素
        int temp = 0;
        for (int i = 5; i < arr.length; i++) {
            // 遍历各组中所有的元素(共5组，每组有2个元素)，步长5
            for (int j = i - 5; j >= 0; j -= 5) {
                // 如果当前这个元素>加上步长后的那个元素,说明需要交换
                if (arr[j] > arr[j + 5]) {
                    temp = arr[j];
                    arr[j] = arr[j + 5];
                    arr[j + 5] = temp;
                }
            }
        }
        System.out.println("希尔排序1轮后===>" + Arrays.toString(arr));

        // 第2轮
        // 因为第2轮排序，是将10个数据分成了 10/2/2=2组
        for (int i = 2; i < arr.length; i++) {
            // 遍历各组中所有的元素(共5组，每组有2个元素)，步长5
            for (int j = i - 2; j >= 0; j -= 2) {
                // 如果当前这个元素>加上步长后的那个元素,说明需要交换
                if (arr[j] > arr[j + 2]) {
                    temp = arr[j];
                    arr[j] = arr[j + 2];
                    arr[j + 2] = temp;
                }
            }
        }
        System.out.println("希尔排序2轮后===>" + Arrays.toString(arr));

        // 第3轮
        // 因为第2轮排序，是将10个数据分成了 10/2/2/2=1组
        for (int i = 1; i < arr.length; i++) {
            // 遍历各组中所有的元素(共5组，每组有2个元素)，步长5
            for (int j = i - 1; j >= 0; j -= 1) {
                // 如果当前这个元素>加上步长后的那个元素,说明需要交换
                if (arr[j] > arr[j + 1]) {
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
        System.out.println("希尔排序3轮后===>" + Arrays.toString(arr));

    }


}
