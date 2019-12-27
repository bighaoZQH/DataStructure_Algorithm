package com.bighao.sort;


import java.util.Arrays;

/**
 * @Author: bighao周启豪
 * @Date: 2019/12/27 14:17
 * @Version 1.0
 *
 * 堆排序
 *
 * 一.堆排序基本介绍
 *  1.堆排序是利用堆这种数据结构而设计的一种排序算法，堆排序是一种选择排序，它的最坏，最好，平均时间复杂度均为O(nlogn)，它也是不稳定排序。
 *  2.堆是具有以下性质的完全二叉树：每个结点的值都大于或等于其左右孩子结点的值，称为大顶堆, 注意 : 没有要求结点的左孩子的值和右孩子的值的大小关系。
 *  3.每个结点的值都小于或等于其左右孩子结点的值，称为小顶堆
 *
 * 大顶堆特点：arr[i] >= arr[2*i+1] && arr[i] >= arr[2*i+2]  // i 对应第几个节点，i从0开始编号
 *  小顶堆：arr[i] <= arr[2*i+1] && arr[i] <= arr[2*i+2] // i 对应第几个节点，i从0开始编号
 * 一般升序采用大顶堆，降序采用小顶堆 
 *
 * 二.堆排序的基本思想是：
 *  1.将待排序序列构造成一个大顶堆
 *      此时，整个序列的最大值就是堆顶的根节点。
 *  2.将其与末尾元素进行交换，此时末尾就为最大值。
 *  3.然后将剩余n-1个元素重新构造成一个堆，这样会得到n个元素的次小值。如此反复执行，便能得到一个有序序列了。
 *
 * 可以看到在构建大顶堆的过程中，元素的个数逐渐减少，最后就得到一个有序序列了.
 *
 * 其实就是
 *      1.将数组构建成大顶堆，此时最大的数在第一个，然后把这个最大值与数组最后一个交换,此时最大值就到了数组最后
 *      2.再把前面未排序的元素再组成大顶堆后，再把数组第一个元素与数组倒数第二个元素交换，此时第二大值就到了倒数第二个位置
 *      3.以此类推
 *
 * 完全二叉树:
 * https://blog.csdn.net/qq_31601743/article/details/89816517
 */
public class HeapSort {
    public static void main(String[] args) {
        //int arr[] = {4, 6, 8, 5, 9};
        int arr[] = {-1, 98, -5, 5, 9, 2, 131, 7};
        heapSort(arr);
    }

    /** 堆排序*/
    public static void heapSort(int[] arr) {
        // 分步演示
        /*adjustHead(arr, 1, arr.length);
        System.out.println("第一次" + Arrays.toString(arr)); // 4,9,8,5,6
        adjustHead(arr, 0, arr.length);
        System.out.println("第二次" + Arrays.toString(arr)); // 9,6,8,5,4*/

        // 构造初始堆(大顶堆或小顶堆),从第一个非叶子节点开始调整,左右孩子节点中较大的交换到父节点中
        for(int i = arr.length / 2 - 1; i >= 0; i--) {
            adjustHead(arr, i, arr.length);
        }
        // 排序，将最大的节点放在堆尾，然后从根节点重新调整
        int temp;
        for (int j = arr.length - 1; j > 0; j--) {
            // 将根结点与尾节点交换
            temp = arr[j];
            arr[j] = arr[0];
            arr[0] = temp;
            adjustHead(arr, 0, j);
        }

        System.out.println("堆排序后===>" + Arrays.toString(arr));
    }

    /**
     * 将一个数组(二叉树)，调整成一个大顶堆
     * 完成将以 i 对应的非叶子节点的数调整成大顶堆
     * 举例: int[] arr = {4,6,8,5,9}; => i=1 => adjustHead => 得到int[] arr = {4,9,8,5,6}
     * 如果我们再次调用adjustHead 传入的是 i=0 => adjustHead => 得到 {9,6,8,5,4}
     * @param arr 待调整的数据
     * @param i 表示非叶子节点在数组中的索引
     * @param length 表示对多少个元素进行调整，length会逐渐减小的
     */
    public static void adjustHead(int[] arr, int i, int length) {
        // 先取出当前元素的值
        int temp = arr[i];
        // 开始调整 公式k = i * 2 + 1表示 K是i节点的左子节点，k = k * 2 + 1 表示k的左子节点
        // 之所以要循环是为了当父节点与其子节点交换后，不能保证这个父节点的值就一定大于其子节点的左右子节点的值,因次还要进行比较和交换
        for (int k = i * 2 + 1; k < length; k = k * 2 + 1) {
            // 先比较i的左右子节点谁大, 当i的左子节点 < i的右子节点,并且k+1还在length的范围内
            if (k + 1 < length && arr[k] < arr[k + 1]) {
                // 就让k指向i的右子节点
                k++;
            }
            // 如果子节点大于父节点,就交换位置
            if (arr[k] > temp) {
                arr[i] = arr[k]; //把较大的值赋给当前节点
                i = k; // 让i指向k，继续循环比较，也就是说k可能还有子节点，继续比较k的子节点
            } else {
                break;
            }
        }

        // 当for循环结束后，我们已经将以 原来的i 为父节点的数的最大值，放在了 原来的i 的位置
        // 其实说白了就是父节点i和子节点k交换位置
        arr[i] = temp;

    }



}
